package cn.antcore.security.session.impl;

import cn.antcore.security.config.SecurityConfig;
import cn.antcore.security.entity.UserDetails;
import cn.antcore.security.event.CreateSessionEvent;
import cn.antcore.security.helper.ContextUtils;
import cn.antcore.security.session.LoginSession;
import cn.antcore.security.session.SessionIdStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * 用户登录
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/25</p>
 **/
public class LoginSessionImpl extends UserSessionImpl implements LoginSession {

    private static final Logger LOG = LoggerFactory.getLogger(LoginSessionImpl.class);

    public LoginSessionImpl(HttpServletRequest request, HttpServletResponse response, SecurityConfig securityConfig, SessionIdStrategy sessionIdStrategy, RedisTemplate<String, Object> redisTemplate) {
        super(request, response, securityConfig, sessionIdStrategy, redisTemplate);
    }

    @Override
    public String loginSuccess(UserDetails userDetails) {
        String sessionId = getSessionIdStrategy().createSessionId(getRequest());
        String oldSession = super.id;
        super.id = sessionId;
        long createTime = System.currentTimeMillis();
        getRedisTemplate().opsForValue().set("UserId:" + userDetails.getId() + ":" + sessionId, sessionId, getSecurityConfig().getSessionTime().getSeconds(), TimeUnit.SECONDS);
        setAttribute("CreationTime", createTime);
        setAttribute("LastAccessedTime", createTime);
        setAttribute("UserDetails", userDetails);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Session登录成功：{}", sessionId);
        }
        ContextUtils.publishEvent(new CreateSessionEvent(this, oldSession, sessionId));
        return getId();
    }

    @Override
    public void logout() {
        invalidate();
    }
}
