//package com.jun.plugin.common.utils;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Jwt工具类
// *
// */
//public class JwtUtil {
//    private static String header = "Authorization";
//    // 令牌秘钥
//    private static String secret = "crabc";
//    private static int expireTime;
//    public static final String TOKEN_PREFIX = "bearer ";
//
//    /**
//     * 创建令牌
//     */
//    public static String createToken(Long userId, String userName) {
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("userId", userId);
//        claims.put("userName", userName);
//        claims.put("expireTime", System.currentTimeMillis());
//        return createToken(claims);
//    }
//
//    /**
//     * 生成令牌
//     */
//    public static String createToken(Map<String, Object> claims) {
//        String token = Jwts.builder()
//                .setClaims(claims)
//                .signWith(SignatureAlgorithm.HS512, secret).compact();
//        return token;
//    }
//
//    /**
//     * 从令牌中获取数据声明
//     *
//     * @param token 令牌
//     * @return 数据声明
//     */
//    public static Claims parseToken(String token) {
//        try {
//            return Jwts.parser()
//                    .setSigningKey(secret)
//                    .parseClaimsJws(token)
//                    .getBody();
//        } catch (Exception e) {
//
//        }
//        return null;
//    }
//
//    /**
//     * 从令牌中获取用户名
//     *
//     * @param token 令牌
//     * @return 用户名
//     */
//    public static String getUserId(String token) {
//        Claims claims = parseToken(token);
//        return claims.getSubject();
//    }
//
//    /**
//     * 获取请求token
//     *
//     * @param request
//     * @return token
//     */
//    public static String getToken(HttpServletRequest request) {
//        String token = request.getHeader(header);
//        if (token != null && token.startsWith(TOKEN_PREFIX)) {
//            token = token.replace(TOKEN_PREFIX, "");
//        }
//        return token;
//    }
//}
