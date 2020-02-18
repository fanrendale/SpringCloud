package com.ctrip.framework.apollo.internals;

import com.ctrip.framework.apollo.core.utils.ClassLoaderUtil;
import com.ctrip.framework.apollo.enums.ConfigSourceType;
import com.ctrip.framework.apollo.enums.PropertyChangeType;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.tracer.Tracer;
import com.ctrip.framework.apollo.util.ExceptionUtil;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.RateLimiter;
import com.xjf.apollo.util.AesEncryptUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


/**
 * 对 updateConfig 方法重新实现，对 Apollo 值初始化和修改时实现解密。
 * 目前只是基于 AES 算法进行加密解密
 *
 * @author Jason Song(song_s@ctrip.com)
 */
public class DefaultConfig extends AbstractConfig implements RepositoryChangeListener {
    // 加密的 Key
    public static final String DECRYPT_KEY = "1111222233334444";
    private static final Logger logger = LoggerFactory.getLogger(DefaultConfig.class);
    private final String m_namespace;
    private final Properties m_resourceProperties;
    private final AtomicReference<Properties> m_configProperties;
    private final ConfigRepository m_configRepository;
    private final RateLimiter m_warnLogRateLimiter;

    private volatile ConfigSourceType m_sourceType = ConfigSourceType.NONE;

    /**
     * Constructor.
     *
     * @param namespace        the namespace of this config instance
     * @param configRepository the config repository for this config instance
     */
    public DefaultConfig(String namespace, ConfigRepository configRepository) {
        m_namespace = namespace;
        m_resourceProperties = loadFromResource(m_namespace);
        m_configRepository = configRepository;
        m_configProperties = new AtomicReference<>();
        m_warnLogRateLimiter = RateLimiter.create(0.017); // 1 warning log output per minute
        initialize();
    }

    private void initialize() {
        try {
            updateConfig(m_configRepository.getConfig(), m_configRepository.getSourceType());
        } catch (Throwable ex) {
            Tracer.logError(ex);
            logger.warn("Init Apollo Local Config failed - namespace: {}, reason: {}.",
                    m_namespace, ExceptionUtil.getDetailMessage(ex));
        } finally {
            //register the change listener no matter config repository is working or not
            //so that whenever config repository is recovered, config could get changed
            m_configRepository.addChangeListener(this);
        }
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        // step 1: check system properties, i.e. -Dkey=value
        String value = System.getProperty(key);

        // step 2: check local cached properties file
        if (value == null && m_configProperties.get() != null) {
            value = m_configProperties.get().getProperty(key);
        }

        /**
         * step 3: check env variable, i.e. PATH=...
         * normally system environment variables are in UPPERCASE, however there might be exceptions.
         * so the caller should provide the key in the right case
         */
        if (value == null) {
            value = System.getenv(key);
        }

        // step 4: check properties file from classpath
        if (value == null && m_resourceProperties != null) {
            value = (String) m_resourceProperties.get(key);
        }

        if (value == null && m_configProperties.get() == null && m_warnLogRateLimiter.tryAcquire()) {
            logger.warn("Could not load config for namespace {} from Apollo, please check whether the configs are released in Apollo! Return default value now!", m_namespace);
        }

        return value == null ? defaultValue : value;
    }

    @Override
    public Set<String> getPropertyNames() {
        Properties properties = m_configProperties.get();
        if (properties == null) {
            return Collections.emptySet();
        }

        return stringPropertyNames(properties);
    }

    @Override
    public ConfigSourceType getSourceType() {
        return m_sourceType;
    }

    private Set<String> stringPropertyNames(Properties properties) {
        //jdk9以下版本Properties#enumerateStringProperties方法存在性能问题，keys() + get(k) 重复迭代, jdk9之后改为entrySet遍历.
        Map<String, String> h = new HashMap<>();
        for (Map.Entry<Object, Object> e : properties.entrySet()) {
            Object k = e.getKey();
            Object v = e.getValue();
            if (k instanceof String && v instanceof String) {
                h.put((String) k, (String) v);
            }
        }
        return h.keySet();
    }

    @Override
    public synchronized void onRepositoryChange(String namespace, Properties newProperties) {
        if (newProperties.equals(m_configProperties.get())) {
            return;
        }

        ConfigSourceType sourceType = m_configRepository.getSourceType();
        Properties newConfigProperties = new Properties();
        newConfigProperties.putAll(newProperties);

        Map<String, ConfigChange> actualChanges = updateAndCalcConfigChanges(newConfigProperties, sourceType);

        //check double checked result
        if (actualChanges.isEmpty()) {
            return;
        }

        this.fireConfigChange(new ConfigChangeEvent(m_namespace, actualChanges));

        Tracer.logEvent("Apollo.Client.ConfigChanges", m_namespace);
    }

    /**
     * 添加 jasypt 解密
     *
     * @param newConfigProperties
     * @param sourceType
     */
    private void updateConfig(Properties newConfigProperties, ConfigSourceType sourceType) {

        Set<Object> keySet = newConfigProperties.keySet();

        for (Object k : keySet) {
            String key = k.toString();
            String value = newConfigProperties.getProperty(key);

            // 获取加密的 value
            if (value.startsWith("ENC(") && value.endsWith(")")){
                logger.debug("加密 Value {}", value);

                // 对加密值进行解密
                try {
                    String decryptValue = AesEncryptUtils.aesDecrypt(value.substring(3, value.length() -1), DECRYPT_KEY);
                    newConfigProperties.setProperty(key, decryptValue);
                } catch (Exception e) {
                    logger.error("加密配置解密失败 ", e);
                }
            }
        }

        m_configProperties.set(newConfigProperties);
        m_sourceType = sourceType;
    }

    private Map<String, ConfigChange> updateAndCalcConfigChanges(Properties newConfigProperties,
                                                                 ConfigSourceType sourceType) {
        List<ConfigChange> configChanges =
                calcPropertyChanges(m_namespace, m_configProperties.get(), newConfigProperties);

        ImmutableMap.Builder<String, ConfigChange> actualChanges =
                new ImmutableMap.Builder<>();

        /** === Double check since DefaultConfig has multiple config sources ==== **/

        //1. use getProperty to update configChanges's old value
        for (ConfigChange change : configChanges) {
            change.setOldValue(this.getProperty(change.getPropertyName(), change.getOldValue()));
        }

        //2. update m_configProperties
        updateConfig(newConfigProperties, sourceType);
        clearConfigCache();

        //3. use getProperty to update configChange's new value and calc the final changes
        for (ConfigChange change : configChanges) {
            change.setNewValue(this.getProperty(change.getPropertyName(), change.getNewValue()));
            switch (change.getChangeType()) {
                case ADDED:
                    if (Objects.equals(change.getOldValue(), change.getNewValue())) {
                        break;
                    }
                    if (change.getOldValue() != null) {
                        change.setChangeType(PropertyChangeType.MODIFIED);
                    }
                    actualChanges.put(change.getPropertyName(), change);
                    break;
                case MODIFIED:
                    if (!Objects.equals(change.getOldValue(), change.getNewValue())) {
                        actualChanges.put(change.getPropertyName(), change);
                    }
                    break;
                case DELETED:
                    if (Objects.equals(change.getOldValue(), change.getNewValue())) {
                        break;
                    }
                    if (change.getNewValue() != null) {
                        change.setChangeType(PropertyChangeType.MODIFIED);
                    }
                    actualChanges.put(change.getPropertyName(), change);
                    break;
                default:
                    //do nothing
                    break;
            }
        }
        return actualChanges.build();
    }

    private Properties loadFromResource(String namespace) {
        String name = String.format("META-INF/config/%s.properties", namespace);
        InputStream in = ClassLoaderUtil.getLoader().getResourceAsStream(name);
        Properties properties = null;

        if (in != null) {
            properties = new Properties();

            try {
                properties.load(in);
            } catch (IOException ex) {
                Tracer.logError(ex);
                logger.error("Load resource config for namespace {} failed", namespace, ex);
            } finally {
                try {
                    in.close();
                } catch (IOException ex) {
                    // ignore
                }
            }
        }

        return properties;
    }
}
