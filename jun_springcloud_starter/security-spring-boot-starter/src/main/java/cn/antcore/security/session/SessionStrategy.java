package cn.antcore.security.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Session策略
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/24</p>
 **/
public interface SessionStrategy {

    /**
     * 获取SessionId生成策略
     * @return
     */
    SessionIdStrategy getSessionIdStrategy();

    /**
     * 获取Session
     * @return 返回Session
     */
    HttpSession getSession(HttpServletRequest request, HttpServletResponse response);

}
