package cn.antcore.security.session.max;

import cn.antcore.security.config.SecurityConfig;
import cn.antcore.security.session.SessionIdStrategy;

/**
 * 用户最大存活数管理
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/25</p>
 **/
public interface MaxSession {

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
     * 处理Session存货问题
     *
     * @param sessionId SessionId
     */
    void handle(String sessionId);
}
