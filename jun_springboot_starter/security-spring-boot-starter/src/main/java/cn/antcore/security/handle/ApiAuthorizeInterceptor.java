package cn.antcore.security.handle;

import cn.antcore.security.annotation.ApiAuthorize;
import cn.antcore.security.entity.UserDetails;
import cn.antcore.security.exception.NotAuthorizeException;
import cn.antcore.security.filter.AuthorizeFilter;
import cn.antcore.security.session.UserSession;
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
public class ApiAuthorizeInterceptor implements HandlerInterceptor {

    @Autowired
    private AuthorizeFilter authorizeFilter;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            ApiAuthorize authorize = method.getMethodAnnotation(ApiAuthorize.class);
            if (authorize != null && (authorize.authority().length > 0 || authorize.roles().length > 0)
                    && request.getSession() instanceof UserSession) {
                UserSession userSession = (UserSession) request.getSession();
                UserDetails userDetails = userSession.getUserDetails();
                if (!authorizeFilter.check(request, response, method, authorize, userDetails)) {
                    throw new NotAuthorizeException("权限不足");
                }
            }
        }
        return true;
    }
}
