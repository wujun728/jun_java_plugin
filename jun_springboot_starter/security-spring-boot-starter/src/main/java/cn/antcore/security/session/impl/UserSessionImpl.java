package cn.antcore.security.session.impl;

import cn.antcore.security.config.SecurityConfig;
import cn.antcore.security.entity.UserDetails;
import cn.antcore.security.exception.NoLoginException;
import cn.antcore.security.session.SessionIdStrategy;
import cn.antcore.security.session.UserSession;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * 用户Session登录
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/25</p>
 **/
public class UserSessionImpl extends RedisSecuritySessionImpl implements UserSession {

    public UserSessionImpl(HttpServletRequest request, HttpServletResponse response, SecurityConfig securityConfig, SessionIdStrategy sessionIdStrategy, RedisTemplate<String, Object> redisTemplate) {
        super(request, response, securityConfig, sessionIdStrategy, redisTemplate);
    }

    @Override
    public boolean isLogin() {
        return getRedisTemplate().hasKey(buildKey("Attribute", "CreationTime"));
    }

    @Override
    public Serializable getUserId() {
        UserDetails userDetails = getUserDetails();
        return userDetails.getId();
    }

    @Override
    public String getUsername() {
        return getUserDetails().getUsername();
    }

    @Override
    public UserDetails getUserDetails() {
        if (!isLogin()) {
            throw new NoLoginException("未登录");
        }
        return (UserDetails) getAttribute("UserDetails");
    }

}
