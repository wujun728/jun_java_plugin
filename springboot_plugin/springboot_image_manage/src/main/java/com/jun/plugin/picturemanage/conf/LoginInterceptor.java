package com.jun.plugin.picturemanage.conf;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author YuChen
 * @Email 835033913@qq.com
 * @Create 2019/10/31 16:41
 */
public class LoginInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();
        if (session.getAttribute(Constant.LOGIN_STATE) != null) {
            return true;
        }
        response.sendRedirect("/login");
        return false;
    }
}
