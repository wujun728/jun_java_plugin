package com.caland.common.web.session;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Session提供者
 */
public interface SessionProvider {
	public Serializable getAttribute(HttpServletRequest request, String name);

	public void setAttribute(HttpServletRequest request,
			HttpServletResponse response, String name, Serializable value);

	public String getSessionId(HttpServletRequest request,
			HttpServletResponse response);

	public void logout(HttpServletRequest request, HttpServletResponse response);
}
