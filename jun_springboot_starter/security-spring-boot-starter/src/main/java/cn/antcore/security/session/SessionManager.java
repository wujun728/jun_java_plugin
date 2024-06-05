package cn.antcore.security.session;

import cn.antcore.security.config.SecurityConfig;
import cn.antcore.security.event.CreateSessionEvent;
import cn.antcore.security.event.UpdateSessionEvent;
import cn.antcore.security.session.max.MaxSession;
import cn.antcore.security.session.refresh.SessionTimeoutRefresh;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Session管理
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/25</p>
 **/
@Component
public class SessionManager implements InitializingBean {

    private static Logger LOG = LoggerFactory.getLogger(SessionManager.class);

    @Autowired
    @Qualifier("sessionRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private SessionIdStrategy sessionIdStrategy;

    @Autowired
    private SessionTimeoutRefresh sessionTimeoutRefresh;

    @Autowired
    private MaxSession maxSession;

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public SecurityConfig getSecurityConfig() {
        return securityConfig;
    }

    public SessionIdStrategy getSessionIdStrategy() {
        return sessionIdStrategy;
    }

    @EventListener(value = UpdateSessionEvent.class)
    public void updateSessionEvent(UpdateSessionEvent event) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("更新Session: {}", event.getSessionId());
        }
        if (securityConfig.isAutomaticRenewal()) {
            sessionTimeoutRefresh.refresh(event.getSessionId());
        }
    }

    @EventListener(value = CreateSessionEvent.class)
    public void createSessionEvent(CreateSessionEvent event) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("创建Session，原始Session: {}，新Session: {}", event.getOldSessionId(), event.getSessionId());
        }
        maxSession.handle(event.getSessionId());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        sessionTimeoutRefresh.setSecurityConfig(securityConfig);
        sessionTimeoutRefresh.setSessionIdStrategy(sessionIdStrategy);

        maxSession.setSecurityConfig(securityConfig);
        maxSession.setSessionIdStrategy(sessionIdStrategy);
    }
}
