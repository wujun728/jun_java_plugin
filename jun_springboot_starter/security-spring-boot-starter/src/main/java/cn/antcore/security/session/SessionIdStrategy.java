package cn.antcore.security.session;

import javax.servlet.http.HttpServletRequest;

/**
 * SessionId创建策略
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/24</p>
 **/
public interface SessionIdStrategy {

    /**
     * 获取Session名字
     * @return Session名字
     */
    String getSessionName();

    /**
     * 获取请求的SessionId
     * @param request HttpServletRequest
     * @return SessionId
     */
    String getSessionId(HttpServletRequest request);

    /**
     * 创建SessionId
     * @return SessionId
     * @param request
     */
    String createSessionId(HttpServletRequest request);
}
