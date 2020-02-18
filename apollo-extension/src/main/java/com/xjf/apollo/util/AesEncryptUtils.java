package com.xjf.apollo.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * 基于 AES 的加密解密工具类
 *
 * @author xjf
 * @date 2020/2/18 14:41
 */
public class AesEncryptUtils {
    private static final String KEY = "d7b85f6e214abcda";
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    public AesEncryptUtils() {
    }

    public static String base64Encode(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    public static byte[] base64Decode(String base64Code) throws Exception {
        return Base64.decodeBase64(base64Code);
    }

    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(1, new SecretKeySpec(encryptKey.getBytes(), "AES"));
        return cipher.doFinal(content.getBytes("utf-8"));
    }

    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }

    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(2, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }

    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        return aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }

    public static void main(String[] args) throws Exception {
        /*String content = "你好";
        System.out.println("加密前：" + content);
        String encrypt = aesEncrypt(content, "d7b85f6e214abcda");
        System.out.println(encrypt.length() + ":加密后：" + encrypt);
        String decrypt = aesDecrypt(encrypt, "d7b85f6e214abcda");
        System.out.println("解密后：" + decrypt);*/

        String msg = "嘉文四世";
        String key = "1111222233334444";

        // 加密值
        String encryptMsg = aesEncrypt(msg, key);
        System.out.println("加密后值为：" + encryptMsg);
    }
}
