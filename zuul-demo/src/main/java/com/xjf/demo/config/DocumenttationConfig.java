package com.xjf.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Zuul 聚合多个服务的 Swagger 配置
 *
 * @author xjf
 * @date 2020/2/6 16:44
 */
@EnableSwagger2
@Primary
@Component
public class DocumenttationConfig implements SwaggerResourcesProvider {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();

        // 排除自身，将其他的服务添加进去
        discoveryClient.getServices().stream()
                .filter(s -> !s.equals(applicationName))
                .forEach(name -> {
                    System.out.println("服务name: " + name);
                    resources.add(swaggerResource(name, "/rest/" + name + "/v2/api-docs", "2.0"));
                });
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version){
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);

        return swaggerResource;
    }
}
