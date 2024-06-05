package cn.antcore.security.session.impl;

import cn.antcore.security.config.SecurityConfig;
import cn.antcore.security.entity.UserDetails;
import cn.antcore.security.session.SessionIdStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Redis实现的Session
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/24</p>
 **/
public class RedisSecuritySessionImpl implements HttpSession {

    private static final Logger LOG = LoggerFactory.getLogger(RedisSecuritySessionImpl.class);

    protected String id;
    private RedisTemplate<String, Object> redisTemplate;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private SecurityConfig securityConfig;
    private SessionIdStrategy sessionIdStrategy;

    public RedisSecuritySessionImpl(HttpServletRequest request, HttpServletResponse response, SecurityConfig securityConfig, SessionIdStrategy sessionIdStrategy, RedisTemplate<String, Object> redisTemplate) {
        this.request = request;
        this.response = response;
        this.securityConfig = securityConfig;
        this.redisTemplate = redisTemplate;
        this.sessionIdStrategy = sessionIdStrategy;
        // 获取Id并写入响应数据中
        this.id = sessionIdStrategy.getSessionId(request);
    }

    /**
     * 生成Redis前缀
     *
     * @param keys
     * @return
     */
    public String buildKey(String... keys) {
        StringBuffer buffer = new StringBuffer(StringUtils.isEmpty(this.id) ? "temp" : this.id);
        for (String key : keys) {
            buffer.append(":").append(key);
        }
        return buffer.toString();
    }

    @Override
    public long getCreationTime() {
        return (long) getAttribute("CreationTime");
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public long getLastAccessedTime() {
        return (long) getAttribute("LastAccessedTime");
    }

    @Override
    public ServletContext getServletContext() {
        return request.getSession().getServletContext();
    }

    @Override
    public void setMaxInactiveInterval(int i) {
        setAttribute("MaxInactiveInterval", i);
    }

    @Override
    public int getMaxInactiveInterval() {
        return (int) getAttribute("MaxInactiveInterval");
    }

    @Override
    public HttpSessionContext getSessionContext() {
        return request.getSession().getSessionContext();
    }

    @Override
    public Object getAttribute(String s) {
        return redisTemplate.opsForValue().get(buildKey("Attribute", s));
    }

    @Override
    public Object getValue(String s) {
        return getAttribute(s);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        Set<String> strings = redisTemplate.keys(buildKey("Attribute", "*"));
        int keyLength = buildKey("Attribute").length();
        Iterator<String> iterator = strings.iterator();
        return new Enumeration<String>() {
            @Override
            public boolean hasMoreElements() {
                return iterator.hasNext();
            }

            @Override
            public String nextElement() {
                return iterator.next().substring(keyLength + 1);
            }
        };
    }

    @Override
    public String[] getValueNames() {
        Set<String> strings = redisTemplate.keys(buildKey("Attribute", "*"));
        int keyLength = buildKey("Attribute").length();
        String[] values = new String[strings.size()];
        Iterator<String> iterator = strings.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            String value = iterator.next();
            values[index] = value.substring(keyLength + 1);
            index++;
        }
        return values;
    }

    @Override
    public void setAttribute(String s, Object o) {
        redisTemplate.opsForValue().set(buildKey("Attribute", s), o, securityConfig.getSessionTime().getSeconds(), TimeUnit.SECONDS);
    }

    @Override
    public void putValue(String s, Object o) {
        setAttribute(s, o);
    }

    @Override
    public void removeAttribute(String s) {
        redisTemplate.delete(buildKey("Attribute", s));
    }

    @Override
    public void removeValue(String s) {
        removeAttribute(s);
    }

    @Override
    public void invalidate() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("销毁Session: {}", id);
        }
        UserDetails userDetails = (UserDetails) getAttribute("UserDetails");
        if (userDetails != null) {
            redisTemplate.delete("UserId:" + userDetails.getId() + ":" + id);
        }
        List<String> keys = Arrays.stream(getValueNames()).collect(Collectors.toList());
        for (int i = 0; i < keys.size(); i++) {
            keys.set(i, buildKey("Attribute", keys.get(i)));
        }
        redisTemplate.delete(keys);
    }

    @Override
    public boolean isNew() {
        return false;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public SessionIdStrategy getSessionIdStrategy() {
        return sessionIdStrategy;
    }

    public SecurityConfig getSecurityConfig() {
        return securityConfig;
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }
}
