package cn.antcore.security.session.strategy;

import cn.antcore.security.config.SecurityConfig;
import cn.antcore.security.session.SessionIdStrategy;
import cn.antcore.security.session.SessionManager;
import cn.antcore.security.session.SessionStrategy;
import cn.antcore.security.session.impl.LoginSessionImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Redis Session策略
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/24</p>
 **/
public class RedisSessionStrategy implements SessionStrategy {

    private SessionManager sessionManager;

    public RedisSessionStrategy(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public SecurityConfig getSecurityConfig() {
        return sessionManager.getSecurityConfig();
    }

    @Override
    public SessionIdStrategy getSessionIdStrategy() {
        return sessionManager.getSessionIdStrategy();
    }

    @Override
    public HttpSession getSession(HttpServletRequest request, HttpServletResponse response) {
        return new LoginSessionImpl(request, response, sessionManager.getSecurityConfig(), sessionManager.getSessionIdStrategy(), sessionManager.getRedisTemplate());
    }
}
