package com.xjf.apollo.util;

import org.apache.commons.lang.StringUtils;
import org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 加密工具
 *
 * @author xjf
 * @date 2020/2/18 11:54
 */
public class EncryptUtil {

    /**
     * 制表符、空格、换行符 PATTERN
     */
    private static Pattern BLANK_PATTERN = Pattern.compile("\\s*|\t|\r|\n");

    /**
     * 加密 key
     */
    private static String PASSWORD = "xjf";

    /**
     * 加密算法
     */
    private static String ALGORITHM = "PBEWithMD5AndDES";

    public static Map<String, String> getEncryptedParams(String input){
        // 输出流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        PrintStream cacheStream = new PrintStream(byteArrayOutputStream);

        // 更换数据输出位置
        System.setOut(cacheStream);

        // 加密参数组装
        String[] args = {"input=" + input, "password=" + PASSWORD, "algorithm=" +ALGORITHM};
        JasyptPBEStringEncryptionCLI.main(args);

        // 执行加密后输出
        String message = byteArrayOutputStream.toString();
        String str = replaceBlank(message);
        int index = str.lastIndexOf("-");

        // 返回加密后的数据
        Map<String, String> result = new HashMap<>();
        result.put("input", str.substring(index + 1));
        result.put("password", PASSWORD);

        return result;
    }

    /**
     * 替换制表符、空格、换行符
     *
     * @param str
     * @return
     */
    private static String replaceBlank(String str){
        String dest = "";
        if (!StringUtils.isEmpty(str)){
            Matcher matcher = BLANK_PATTERN.matcher(str);
            dest = matcher.replaceAll("");
        }

        return dest;
    }

    public static void main(String[] args) {
        System.out.println(getEncryptedParams("咖啡"));
    }
}
