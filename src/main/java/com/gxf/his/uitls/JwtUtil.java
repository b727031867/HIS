package com.gxf.his.uitls;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * <p>Title: JWT的工具类，用于生成、加密、解密Token</p>
 * <p>Description: </p>
 * <p>Company: www.gxf727031867.top</p>
 * <p>Component注解用于交给Spring容器管理，注入静态变量</p>
 *
 * @author 龚秀峰
 * @date 2019-8-25
 */
@Component
@Slf4j
public class JwtUtil {

    /**
     * accessTokenExpireTime token过期时间
     */
    private static String accessTokenExpireTime;

    /**
     * 静态变量使用Setter注入
     * @param accessTokenExpireTime Token的过期时间
     */
    @Value("${config.accessToken-expireTime}")
    public void setAccessTokenExpireTime(String accessTokenExpireTime) {
        JwtUtil.accessTokenExpireTime = accessTokenExpireTime;
    }

    /**
     * JWT认证加密私钥(Base64加密)
     */
    private static String encryptJWTKey;

    @Value("${config.encrypt-jwtKey}")
    public void setEncryptJWTKey(String encryptJWTKey) {
        JwtUtil.encryptJWTKey = encryptJWTKey;
    }

    /**
     * 校验token是否正确
     * @param token 密钥
     * @return 是否验证成功
     */
    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(encryptJWTKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (TokenExpiredException e) {
            log.info("accessToken过期！" + e.getMessage());
            throw new TokenExpiredException("accessToken" +
                    "过期");
        } catch (JWTVerificationException e) {
            log.info("accessToken不合法");
            throw new JWTVerificationException(
                    "accessToken" +
                    "验证不合法");
        } catch (Exception e) {
            log.info("其他Token校验异常：" + e.getMessage());
        }
        return false;
    }

    /**
     * 获取登录名
     *
     * @param token 令牌
     * @return 用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 获取签名的时间戳
     *
     * @param token 令牌
     * @return 用户名
     */
    public static String getSignTime(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("signTime").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }


    /**
     * 生成签名
     *
     * @param username 用户名
     * @return token令牌
     */
    public static String sign(String username,
                              String currentTimeMillis) {
        try {
            // 此处过期时间是以毫秒为单位，所以乘以1000
            Date date =
                    new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpireTime) * 1000L);
            Algorithm algorithm = Algorithm.HMAC256(encryptJWTKey);
            return JWT.create()
                    .withClaim("username", username)
                    .withClaim("signTime", currentTimeMillis)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }


}
