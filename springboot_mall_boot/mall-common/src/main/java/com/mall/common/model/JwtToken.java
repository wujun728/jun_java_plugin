package com.mall.common.model;


import com.alibaba.fastjson.JSONObject;
import com.mall.common.utils.StrKit;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtToken
 * @author Wujun
 * @version 1.0
 * @create_at 2018/11/2417:30
 */
public class JwtToken {

    private static final Logger logger = LoggerFactory.getLogger(JwtToken.class);
    private static final JwtToken jwtToken = new JwtToken();
    private static String secret = null;

    private JwtToken() {

    }

    public static JwtToken getJwtToken(String secret) {
        if (secret == null)
            throw new NullPointerException("secret cant not be null");
        JwtToken.secret = secret;
        return jwtToken;
    }

    /**
     * 创建token
     * @param expirationTime
     * @param value
     * @return
     */
    public String createToken(Object value, long expirationTime) {
        SecretKey secretKey = generalKey(secret);
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        String subject = JSONObject.toJSONString(value);
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder()
                                 .setIssuedAt(now)
                                 .setSubject(subject)
                                 .signWith(signatureAlgorithm, secretKey);
        if (expirationTime > 0L) {
            long expMillis = nowMillis + expirationTime;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);   //  设置过期时间
        }
        return builder.compact();
    }

    /**
     * 解析token 字符串
     * @param token
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T parseToken(String token, Class<T> clazz) {
        SecretKey secretKey = this.generalKey(secret);
        try {
            Claims claims = (Claims)Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
            String subject = claims.getSubject();
            if (StrKit.isEmpty(subject)) {
                return null;
            }
            return JSONObject.parseObject(subject, clazz);
        } catch (SignatureException | MalformedJwtException e) {
            logger.error("jwt 签名错误或解析错误");
            // don't trust the JWT!
            // jwt 签名错误或解析错误，可能是伪造的，不能相信
        } catch (ExpiredJwtException e) {
            logger.error("jwt 已失效");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public Map<String, Object> parseToken(String token) {
        return parseToken(token, HashMap.class);
    }

    /**
     * 获取解析后的字符串
     * @param token
     * @return
     */
    public String parseTokenToString(String token) {
        return parseToken(token, String.class);
    }

    private SecretKey generalKey(String secret) {
        byte[] encodedKey = DatatypeConverter.parseBase64Binary(secret);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }
}
