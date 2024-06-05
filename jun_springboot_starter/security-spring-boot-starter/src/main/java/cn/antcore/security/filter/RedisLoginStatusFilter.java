package cn.antcore.security.filter;

import cn.antcore.security.session.UserSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录状态拦截
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/26</p>
 **/
public class RedisLoginStatusFilter implements LoginStatusFilter {

    @Override
    public boolean isLogin(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession() instanceof UserSession) {
            UserSession userSession = (UserSession) request.getSession();
            if (userSession.isLogin()) {
                return true;
            }
        }
        return false;
    }
}
