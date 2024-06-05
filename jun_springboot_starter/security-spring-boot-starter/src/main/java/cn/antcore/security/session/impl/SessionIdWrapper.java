package cn.antcore.security.session.impl;

import cn.antcore.security.config.SecurityConfig;
import cn.antcore.security.session.SessionIdStrategy;
import cn.antcore.security.session.impl.LoginSessionImpl;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 刷新Session Timeout包装类
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/25</p>
 **/
public class SessionIdWrapper extends LoginSessionImpl {

    public SessionIdWrapper(String sessionId, SecurityConfig securityConfig, SessionIdStrategy sessionIdStrategy, RedisTemplate<String, Object> redisTemplate) {
        super(null, null, securityConfig, sessionIdStrategy, redisTemplate);
        this.id = sessionId;
    }
}
