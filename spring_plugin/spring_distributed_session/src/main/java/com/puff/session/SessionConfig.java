package com.puff.session;

import javax.servlet.ServletContext;

import com.puff.storage.Storage;

public enum SessionConfig {
	INSTANCE;

	public static final String DEFAULT_COOKIENNAME = "PUFF_SESSIONID";

	public static final int DEFAULT_COOKIEMAXAGE = -1;

	public static final int DEFAULT_SESSION_TIMEOUT = 1800;

	private String cookieDomain;

	private String cookieName;

	private int cookieMaxAge;

	private String cookiePath;

	private int sessionTimeout;

	private Storage storage;

	private ServletContext context;

	public String getCookieDomain() {
		return cookieDomain;
	}

	public void setCookieDomain(String cookieDomain) {
		this.cookieDomain = cookieDomain;
	}

	public String getCookieName() {
		return cookieName;
	}

	public void setCookieName(String cookieName) {
		this.cookieName = cookieName;
	}

	public int getCookieMaxAge() {
		return cookieMaxAge;
	}

	public void setCookieMaxAge(int cookieMaxAge) {
		this.cookieMaxAge = cookieMaxAge;
	}

	public String getCookiePath() {
		return cookiePath;
	}

	public void setCookiePath(String cookiePath) {
		this.cookiePath = cookiePath;
	}

	public int getSessionTimeout() {
		return sessionTimeout;
	}

	public void setSessionTimeout(int sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}

	public Storage getStorage() {
		return storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}

	public ServletContext getContext() {
		return context;
	}

	public void setContext(ServletContext context) {
		this.context = context;
	}

}
