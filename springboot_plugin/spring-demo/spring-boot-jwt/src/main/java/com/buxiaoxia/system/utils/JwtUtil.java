package com.buxiaoxia.system.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64UrlCodec;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/**
 * Created by xw on 2017/1/5.
 * 2017-01-05 14:38
 */
@Data
@Slf4j
@Component
@ConfigurationProperties("jwt.config")
public class JwtUtil {
	
	private String key;

	private long ttl;

	@Value("${spring.profiles.active}")
	private String profiles;
	private Base64UrlCodec base64UrlCodec = new Base64UrlCodec();

	/**
	 * 生成签名的Key
	 *
	 * @return
	 */
	private SecretKey generalKey() {
		String stringKey = profiles + key;
		byte[] encodedKey = base64UrlCodec.decode(stringKey);
		return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
	}

	/**
	 * 生成JWT
	 *
	 * @param id
	 * @param subject
	 * @return
	 */
	public String createJWT(String id, String subject, String roles) {
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		JwtBuilder builder = Jwts.builder().setId(id)
				.setSubject(subject)
				.setIssuedAt(now)
				.signWith(SignatureAlgorithm.HS256, generalKey()).claim("roles", roles);
		if (ttl > 0) {
			long expMillis = nowMillis + ttl;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}
		return builder.compact();
	}

	/**
	 * 检验Token是否
	 *
	 * @param compactJws
	 * @return
	 */
	public Claims checkJWT(String compactJws) {
		Claims result = null;
		try {
			Claims claims = Jwts.parser().setSigningKey(generalKey()).parseClaimsJws(compactJws).getBody();
			long expTime = claims.getExpiration().getTime();
			long nowTime = System.currentTimeMillis();
			if (expTime > nowTime) {
				result = claims;
			}
		} catch (Exception e) {
			result = null;
			log.error("parseJWT", e);
		}
		return result;
	}

	/**
	 * 解析JWT字符串
	 *
	 * @param compactJws
	 * @return
	 */
	public Claims parseJWT(String compactJws) {
		Claims claims = null;
		try {
			claims = Jwts.parser().setSigningKey(generalKey()).parseClaimsJws(compactJws).getBody();
		} catch (Exception e) {
			log.error("checkJWT", e);
		}
		return claims;
	}
}
