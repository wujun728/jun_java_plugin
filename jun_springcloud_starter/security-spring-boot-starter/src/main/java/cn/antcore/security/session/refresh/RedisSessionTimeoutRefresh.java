package cn.antcore.security.session.refresh;

import cn.antcore.security.config.SecurityConfig;
import cn.antcore.security.entity.UserDetails;
import cn.antcore.security.session.SessionIdStrategy;
import cn.antcore.security.session.impl.SessionIdWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

/**
 * Redis Session刷新过期时间
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/25</p>
 **/
public class RedisSessionTimeoutRefresh implements SessionTimeoutRefresh {

    private static final Logger LOG = LoggerFactory.getLogger(RedisSessionTimeoutRefresh.class);

    private SecurityConfig securityConfig;

    private SessionIdStrategy sessionIdStrategy;

    @Autowired
    @Qualifier("sessionRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void setSecurityConfig(SecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
    }

    @Override
    public void setSessionIdStrategy(SessionIdStrategy sessionIdStrategy) {
        this.sessionIdStrategy = sessionIdStrategy;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void refresh(String sessionId) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("刷新Session({})的过期时间");
        }
        SessionIdWrapper wrapper = new SessionIdWrapper(sessionId, securityConfig, sessionIdStrategy, redisTemplate);
        if (wrapper.isLogin()) {
            UserDetails userDetails = (UserDetails) wrapper.getAttribute("UserDetails");
            if (userDetails != null) {
                Serializable userId = userDetails.getId();
                redisTemplate.expire("UserId:" + userId + ":" + sessionId, securityConfig.getSessionTime().getSeconds(), TimeUnit.SECONDS);
            }
            Enumeration<String> enumeration = wrapper.getAttributeNames();
            while (enumeration.hasMoreElements()) {
                String key = wrapper.buildKey("Attribute", enumeration.nextElement());
                redisTemplate.expire(key, securityConfig.getSessionTime().getSeconds(), TimeUnit.SECONDS);
            }
        }
    }
}
