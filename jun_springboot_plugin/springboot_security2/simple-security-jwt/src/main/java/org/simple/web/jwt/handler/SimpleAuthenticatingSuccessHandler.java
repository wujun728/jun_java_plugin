package org.simple.web.jwt.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.simple.web.jwt.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class SimpleAuthenticatingSuccessHandler implements AuthenticationSuccessHandler {

    private static ObjectMapper globalObjectMapper = new ObjectMapper();

    @Autowired
    private JwtService jwtService;

    static {
        globalObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    private Logger logger = LoggerFactory.getLogger(SimpleAuthenticatingSuccessHandler.class);

    private static void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Map<String, Object> result = new HashMap<>();
        result.put("token", jwtService.generateToken(authentication.getName()));
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        globalObjectMapper.writeValue(response.getWriter(), result);
        clearAuthenticationAttributes(request);
    }

}

