package cn.antcore.security.handle;

import cn.antcore.security.annotation.ApiAuthorize;
import cn.antcore.security.annotation.Login;
import cn.antcore.security.exception.NoLoginException;
import cn.antcore.security.filter.LoginStatusFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/26</p>
 **/
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginStatusFilter loginStatusFilter;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            Login login = method.getMethodAnnotation(Login.class);
            ApiAuthorize authorize = method.getMethodAnnotation(ApiAuthorize.class);
            if (((login != null && login.value())
                    || (authorize != null && (authorize.authority().length > 0 || authorize.roles().length > 0)))) {
                if (!loginStatusFilter.isLogin(request, response)) {
                    throw new NoLoginException("未登录");
                }
            }
        }
        return true;
    }
}
