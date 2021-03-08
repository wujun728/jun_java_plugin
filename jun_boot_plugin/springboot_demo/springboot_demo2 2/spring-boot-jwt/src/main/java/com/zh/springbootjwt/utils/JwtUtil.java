package com.zh.springbootjwt.utils;

import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.zh.springbootjwt.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Optional;

/**
 * @author Wujun
 * @date 2019/6/25
 */
@Slf4j
public class JwtUtil {

    public static String createToken(User user){
        Date now = new Date();
        return JWT.create()
                  //设置载荷payload
                  .withClaim("userId",user.getId())
                  //生成签名的时间
                  .withIssuedAt(now)
                  //签名过期的时间
                  .withExpiresAt(DateUtil.offsetHour(now,1).toJdkDate())
                  .sign(Algorithm.HMAC256(user.getPassword()));
    }

    public static boolean verifyToken(String token,String secret){
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(secret)){
            return false;
        }
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
        try {
            jwtVerifier.verify(token);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return false;
        }
    }

    public static Integer getUserId(String token){
        try {
            return Optional.ofNullable(JWT.decode(token).getClaim("userId")).map(e -> e.asInt()).orElse(null);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return null;
        }
    }
}
