package com.kind.springboot.common.interceptor;

import com.kind.springboot.common.annotation.Authorization;
import com.kind.springboot.common.config.UserConstants;
import com.kind.springboot.common.manager.TokenManager;
import com.kind.springboot.common.token.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Function:自定义拦截器，判断此次请求是否有权限. <br/>
 * Date:     2016年8月11日 下午1:09:56 <br/>
 *
 * @author Wujun
 * @see
 * @since JDK 1.7
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenManager manager;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 从header中得到token
        String authorization = request.getHeader(UserConstants.AUTHORIZATION);
        // 验证token
        TokenDto model = manager.getToken(authorization);
        if (manager.checkToken(model)) {
            // 如果token验证成功，将token对应的用户id存在request中，便于之后注入
            request.setAttribute(UserConstants.CURRENT_USER_ID, model.getUserId());
            return true;
        }
        // 如果验证token失败，并且方法注明了Authorization，返回401错误
        if (method.getAnnotation(Authorization.class) != null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }
}
