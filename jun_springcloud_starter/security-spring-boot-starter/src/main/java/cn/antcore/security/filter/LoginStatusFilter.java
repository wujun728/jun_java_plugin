package cn.antcore.security.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录状态拦截
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/26</p>
 **/
public interface LoginStatusFilter {

    /**
     * 是否登录
     * @param request
     * @param response
     * @return true已登录，false未登录
     */
    boolean isLogin(HttpServletRequest request, HttpServletResponse response);
}
