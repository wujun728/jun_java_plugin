package org.simple.web.jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.simple.web.jwt.property.JwtAuthFilterProperty;
import org.simple.web.jwt.property.JwtPayloadProperty;
import org.simple.web.jwt.property.SimpleSecurityProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 项目名称：simple-framework
 * 类名称：JWTService
 * 类描述：JWTService
 * 创建时间：2018/8/9
 *
 * @author jiangjunjie   (E-mail:1620657419@qq.com)
 * @version v1.0
 */
@Service
public class JwtService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JwtPayloadProperty jwtPayloadProperty;

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtPayloadProperty.getSecret()).parseClaimsJws(token).getBody();
    }

    /**
     * 从令牌中获取认证的唯一标识
     *
     * @param token 令牌
     * @return 用户id
     */
    public String getSubjectFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            logger.error("", e);
            username = null;
        }
        return username;
    }

    /**
     * 验证令牌是否时间有效
     *
     * @param token 令牌
     * @return 是否有效
     */
    public Boolean validateToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            Date notBefore = claims.getNotBefore();
            return new Date().after(notBefore) && new Date().before(expiration);
        } catch (Exception e) {
            logger.error("", e);
            return false;
        }
    }

    /**
     * 生成令牌
     *
     * @param userId .
     * @return .
     */
    public String generateToken(String userId) {
        return Jwts.builder()
                //jwt签发者
                .setIssuer(jwtPayloadProperty.getIssuer())
                // jwt所面向的用户
                .setSubject(userId)
                //接收jwt的一方
                .setAudience(jwtPayloadProperty.getAudience())
                .setExpiration(new Date(System.currentTimeMillis() + jwtPayloadProperty.getExpirationMinute() * 60 * 1000))
                .setNotBefore(new Date(System.currentTimeMillis() - jwtPayloadProperty.getNotBeforeMinute() * 60 * 1000))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, jwtPayloadProperty.getSecret())
                .compact();
    }

}
