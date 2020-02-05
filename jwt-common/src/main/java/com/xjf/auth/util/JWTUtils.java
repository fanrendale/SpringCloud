package com.xjf.auth.util;

import com.xjf.auth.common.ResponseCode;
import io.jsonwebtoken.*;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

/**
 * API调用认证工具类， 采用 RSA 加密
 *
 * @author xjf
 * @date 2020/2/5 11:48
 */
public class JWTUtils {

    private static RSAPrivateKey priKey;
    private static RSAPublicKey pubKey;

    private static class SingletonHolder{
        private static final JWTUtils INSTANCE = new JWTUtils();
    }

    /**
     * 生成公钥和私钥，返回实例
     * @param modulus
     * @param privateExponent
     * @param publicExponent
     * @return
     */
    public synchronized static JWTUtils getInstance(String modulus, String privateExponent, String publicExponent){
        if (priKey == null && pubKey == null){
            priKey = RSAUtils.getPrivateKey(modulus, privateExponent);
            pubKey = RSAUtils.getPublicKey(modulus, publicExponent);
        }

        return SingletonHolder.INSTANCE;
    }

    public synchronized static JWTUtils getInstance(){
        if (priKey == null && pubKey == null){
            priKey = RSAUtils.getPrivateKey(RSAUtils.modulus,RSAUtils.private_exponent);
            pubKey = RSAUtils.getPublicKey(RSAUtils.modulus, RSAUtils.public_exponent);
        }

        return SingletonHolder.INSTANCE;
    }

    public synchronized static void reload(String modulus, String privateExponent, String publicExponent){
        priKey = RSAUtils.getPrivateKey(modulus, privateExponent);
        pubKey = RSAUtils.getPublicKey(modulus, publicExponent);
    }

    /**
     * 获取 Token
     * @param uid 用户ID
     * @param exp 失效时间，单位分钟
     * @return
     */
    public String getToken(String uid, int exp){
        long endTime = System.currentTimeMillis() + 1000 * 60 * exp;
        return Jwts.builder().setSubject(uid).setExpiration(new Date(endTime))
                .signWith(SignatureAlgorithm.RS512, priKey).compact();
    }

    /**
     * 获取 Token
     * @param uid 用户ID
     * @return
     */
    public String getToken(String uid){
        // 默认有效期为 24 小时
        long endTime = System.currentTimeMillis() + 1000 * 60 * 1440;
        return Jwts.builder().setSubject(uid).setExpiration(new Date(endTime))
                .signWith(SignatureAlgorithm.RS512, priKey).compact();
    }

    /**
     * 检查 Token 是否合法
     * @param token
     * @return
     */
    public JWTResult checkToken(String token){
        try {
            Claims claims = Jwts.parser().setSigningKey(pubKey).parseClaimsJws(token).getBody();
            String sub = claims.get("sub", String.class);
            return new JWTResult(true, sub, "合法请求", ResponseCode.SUCCESS_CODE.getCode());
        } catch (ExpiredJwtException e) {
            // 在解析 JWT 字符串时，如果 “过期时间字段” 已经早于当前时间，将会抛出 ExpiredJwtException 异常， 说明本次请求已经失效
            return new JWTResult(false, null, "token已过期", ResponseCode.TOKEN_TIMEOUT_CODE.getCode());
        } catch (SignatureException e) {
            // 在解析 JWT 字符串时，如果密钥不正确， 将会解析失败，抛出 SignatureException 异常，说明该 JWT 字符串时伪造的
            return new JWTResult(false, null, "非法请求", ResponseCode.NO_AUTH_CODE.getCode());
        }  catch (Exception e) {
            return new JWTResult(false, null, "非法请求", ResponseCode.NO_AUTH_CODE.getCode());
        }
    }

    /**
     * 内部类， JWT 的返回结果
     */
    public static class JWTResult{
        private boolean status;
        private String uid;
        private String msg;
        private int code;

        public JWTResult() {
            super();
        }

        public JWTResult(boolean status, String uid, String msg, int code) {
            this.status = status;
            this.uid = uid;
            this.msg = msg;
            this.code = code;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }
}
