package cn.antcore.security.session.refresh;

import cn.antcore.security.config.SecurityConfig;
import cn.antcore.security.session.SessionIdStrategy;

/**
 * Session失效时间刷新
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/25</p>
 **/
public interface SessionTimeoutRefresh {

    /**
     * 设置配置文件
     *
     * @param securityConfig 配置文件
     */
    void setSecurityConfig(SecurityConfig securityConfig);

    /**
     * 设置SessionId生成策略
     *
     * @param sessionIdStrategy SessionId生成策略
     */
    void setSessionIdStrategy(SessionIdStrategy sessionIdStrategy);

    /**
     * 刷新Session
     *
     * @param sessionId
     */
    void refresh(String sessionId);
}
