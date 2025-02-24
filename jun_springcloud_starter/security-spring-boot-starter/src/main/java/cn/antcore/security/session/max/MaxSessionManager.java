package cn.antcore.security.session.max;

import cn.antcore.security.config.SecurityConfig;
import cn.antcore.security.session.SessionIdStrategy;
import cn.antcore.security.session.impl.SessionIdWrapper;
import cn.antcore.security.session.refresh.RedisSessionTimeoutRefresh;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Session 用户最大存活数管理
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/29</p>
 **/
public class MaxSessionManager implements MaxSession {

    private static final Logger LOG = LoggerFactory.getLogger(MaxSessionManager.class);

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
    public void handle(String sessionId) {
        // 无限制
        if (securityConfig.getMaxLive() <= 0) {
            return;
        }

        // 获取当前用户的全部SessionId
        SessionIdWrapper wrapper = new SessionIdWrapper(sessionId, securityConfig, sessionIdStrategy, redisTemplate);
        if (!wrapper.isLogin()) {
            return;
        }
        Serializable userId = wrapper.getUserId();
        Set<String> keys = redisTemplate.keys("UserId:" + userId + ":*");
        Iterator<String> iterator = keys.iterator();

        // 获取Key的失效时间
        List<KeyInfo> keyInfos = new ArrayList<>();
        while (iterator.hasNext()) {
            String oldSessionId = iterator.next();
            Long expire = redisTemplate.getExpire(oldSessionId);
            oldSessionId = oldSessionId.substring(("UserId:" + userId + ":").length());
            if (sessionId.equals(oldSessionId)) {
                continue;
            }
            keyInfos.add(new KeyInfo(oldSessionId, expire));
        }

        // 根据失效时间降序
        if (keys.size() <= securityConfig.getMaxLive()) {
            return;
        }
        // 删除历史会话
        keyInfos.stream().sorted(Comparator.comparing(KeyInfo::getExpire))
                .collect(Collectors.toList())
                .subList(securityConfig.getMaxLive() - 1, keys.size() - 1)
                .forEach(it -> new SessionIdWrapper(it.getKey(), securityConfig, sessionIdStrategy, redisTemplate)
                        .logout());
    }

    /**
     * Redis 键值对信息
     * <br/>
     * <p>Created by Hong.</p>
     * <p>2021/3/29</p>
     **/
    private static class KeyInfo {

        private String key;

        private Long expire;

        public KeyInfo(String key, Long expire) {
            this.key = key;
            this.expire = expire;
        }

        public String getKey() {
            return key;
        }

        public Long getExpire() {
            return expire;
        }
    }
}
