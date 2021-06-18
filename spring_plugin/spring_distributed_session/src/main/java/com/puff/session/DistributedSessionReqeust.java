package com.puff.session;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 
 * @author Wujun
 *
 */
public class DistributedSessionReqeust extends HttpServletRequestWrapper {

	private DistributedSession session;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private SessionConfig config = SessionConfig.INSTANCE;

	public DistributedSessionReqeust(HttpServletRequest request, HttpServletResponse response) {
		super(request);
		this.request = request;
		this.response = response;
	}

	@Override
	public HttpSession getSession() {
		return getSession(true);
	}

	@Override
	public HttpSession getSession(boolean create) {
		return doGetSession(create);
	}

	private HttpSession doGetSession(boolean create) {
		if (session == null) {
			Cookie cookie = null;
			Cookie[] cookies = super.getCookies();
			if (cookies != null) {
				for (Cookie c : cookies) {
					if (c.getName().equals(config.getCookieName())) {
						cookie = c;
						break;
					}
				}
			}
			if (cookie != null) {
				String sessionId = cookie.getValue();
				session = buildSession(sessionId, false);
			} else {
				session = buildSession();
			}
		}
		return session;
	}

	private DistributedSession buildSession(String sessionId, boolean create) {
		DistributedSession session = new DistributedSession(sessionId);
		if (create) {
			addCookie(sessionId);
		}
		return session;
	}

	private DistributedSession buildSession() {
		String sessionId = UUID.randomUUID().toString().replace("-", "").toUpperCase();
		return buildSession(sessionId, true);
	}

	private void addCookie(String sessionId) {
		Cookie cookie = new Cookie(config.getCookieName(), sessionId);
		cookie.setMaxAge(config.getCookieMaxAge());
		cookie.setSecure(request.isSecure());
		cookie.setPath(config.getCookiePath());
		String cookieDomain = config.getCookieDomain();
		if (cookieDomain != null && !"".equals(cookieDomain.trim())) {
			cookie.setDomain(cookieDomain);
		}
		response.addCookie(cookie);
	}
}
