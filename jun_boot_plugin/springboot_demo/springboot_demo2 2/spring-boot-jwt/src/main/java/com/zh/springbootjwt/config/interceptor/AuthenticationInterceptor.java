package com.zh.springbootjwt.config.interceptor;

import com.zh.springbootjwt.config.annotation.Token;
import com.zh.springbootjwt.model.User;
import com.zh.springbootjwt.service.UserService;
import com.zh.springbootjwt.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token校验拦截器
 * @author Wujun
 * @date 2019/6/25
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Token token = handlerMethod.getMethodAnnotation(Token.class);
            if (token != null){
                String requsetToken = request.getHeader("token");
                if (StringUtils.isEmpty(requsetToken)){
                    throw new RuntimeException("登录失效,请重新登录");
                }
                Integer userId = JwtUtil.getUserId(requsetToken);
                if (userId == null){
                    throw new RuntimeException("登录失效,请重新登录");
                }
                User user = this.userService.findByUserId(userId);
                if (!JwtUtil.verifyToken(requsetToken,user.getPassword())){
                    throw new RuntimeException("登录失效,请重新登录");
                }
            }
        }
        return true;
    }

}
