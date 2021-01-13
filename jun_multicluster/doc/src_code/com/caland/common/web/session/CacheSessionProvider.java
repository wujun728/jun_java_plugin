package com.caland.common.web.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.caland.common.web.Constants;
import com.caland.common.web.RequestUtils;
import com.caland.common.web.session.cache.SessionCache;
import com.caland.common.web.session.id.SessionIdGenerator;

/**
 * 使用Memcached分布式缓存实现Session
 */
public class CacheSessionProvider implements SessionProvider, InitializingBean {

	public Serializable getAttribute(HttpServletRequest request, String name) {
		
		Map<String, Serializable> session = null;

		String root = RequestUtils.getRequestedSessionId(request);
		if (StringUtils.isBlank(root)) {
			return null;
		}
		session = sessionCache.getSession(root);
		if (session != null) {
			return session.get(name);
		} else {
			return null;
		}
	}

	public void setAttribute(HttpServletRequest request,
			HttpServletResponse response, String name, Serializable value) {
		Map<String, Serializable> session = new HashMap<String, Serializable>();
		String root = RequestUtils.getRequestedSessionId(request);
//		do {
//			root = sessionIdGenerator.get();
//		} while (sessionCache.exist(root));
//		response.addCookie(createCookie(request, root));
		session.put(name, value);
		sessionCache.setSession(root, session, sessionTimeout);
	}

	public String getSessionId(HttpServletRequest request,
			HttpServletResponse response) {
		String root = RequestUtils.getRequestedSessionId(request);
		if (root == null || root.length() != 32 || !sessionCache.exist(root)) {
			do {
				root = sessionIdGenerator.get();
			} while (sessionCache.exist(root));
			sessionCache.setSession(root, new HashMap<String, Serializable>(),
					sessionTimeout);
			response.addCookie(createCookie(request, root));
		}
		return root;
	}

	public void logout(HttpServletRequest request, HttpServletResponse response) {
		String root = RequestUtils.getRequestedSessionId(request);
		if (!StringUtils.isBlank(root)) {
			sessionCache.clear(root);
			Cookie cookie = createCookie(request, null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
	}

	private Cookie createCookie(HttpServletRequest request, String value) {
		Cookie cookie = new Cookie(Constants.JSESSION_COOKIE, value);
		String ctx = request.getContextPath();
		cookie.setPath(StringUtils.isBlank(ctx) ? "/" : ctx);
		return cookie;
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(sessionCache);
		Assert.notNull(sessionIdGenerator);
	}

	private SessionCache sessionCache;
	private SessionIdGenerator sessionIdGenerator;
	private int sessionTimeout = 30;

	public void setSessionCache(SessionCache sessionCache) {
		this.sessionCache = sessionCache;
	}

	/**
	 * 设置session过期时间
	 * 
	 * @param sessionTimeout
	 *            分钟
	 */
	public void setSessionTimeout(int sessionTimeout) {
		Assert.isTrue(sessionTimeout > 0);
		this.sessionTimeout = sessionTimeout;
	}

	public void setSessionIdGenerator(SessionIdGenerator sessionIdGenerator) {
		this.sessionIdGenerator = sessionIdGenerator;
	}
}
