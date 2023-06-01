package cn.antcore.security.request;

import cn.antcore.security.session.SessionStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Request包装类, 生成自定义Session
 * <br/>
 * <p>Created by Hong.</p>
 * <p>2021/3/24</p>
 **/
public class HttpSecurityServletRequestWrapper extends HttpServletRequestWrapper {

    private HttpSession session;

    public HttpSecurityServletRequestWrapper(HttpServletRequest request, HttpServletResponse response, SessionStrategy sessionStrategy) {
        super(request);
        if (sessionStrategy != null) {
            this.session = sessionStrategy.getSession(request, response);
            return;
        }
        this.session = super.getSession();
    }

    @Override
    public HttpSession getSession() {
        return session;
    }

    @Override
    public HttpSession getSession(boolean create) {
        return this.getSession();
    }

    @Override
    public String getRequestedSessionId() {
        return this.session.getId();
    }

}
