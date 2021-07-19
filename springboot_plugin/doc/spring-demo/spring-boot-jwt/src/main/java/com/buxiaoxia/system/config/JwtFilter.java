package com.buxiaoxia.system.config;

import com.buxiaoxia.system.exception.AuthorizationException;
import com.buxiaoxia.system.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class JwtFilter extends HandlerInterceptorAdapter {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		final String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new AuthorizationException("认证失败");
		}

		final String token = authHeader.substring(7); // The part after "Bearer "
		final Claims claims = jwtUtil.checkJWT(token);
		if (claims != null) {
			request.setAttribute("claims", claims);
			return true;
		} else {
			throw new AuthorizationException("认证失败");
		}
	}

}
