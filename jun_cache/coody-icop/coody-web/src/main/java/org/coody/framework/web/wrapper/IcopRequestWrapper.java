package org.coody.framework.web.wrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.coody.framework.core.annotation.AutoBuild;
import org.coody.framework.web.container.HttpContainer;

@SuppressWarnings({ "unchecked", "rawtypes" })
@AutoBuild
public class IcopRequestWrapper implements ServletRequest,HttpServletRequest {

	@Override
	public String getParameter(String name) {
		return HttpContainer.getRequest().getParameter(name);
	}
	@Override
	public String getHeader(String name) {
		return HttpContainer.getRequest().getHeader(name);
	}

	@Override
	public Enumeration<String> getHeaderNames() {
		return HttpContainer.getRequest().getHeaderNames();
	}
	@Override
	public Enumeration<String> getParameterNames() {
		return HttpContainer.getRequest().getParameterNames();
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return HttpContainer.getRequest().getParameterMap();
	}
	public static HttpServletRequest getOrgRequest() {
		return HttpContainer.getRequest();
	}

	@Override
	public Object getAttribute(String arg0) {
		return HttpContainer.getRequest().getAttribute(arg0);
	}

	@Override
	public Enumeration getAttributeNames() {
		return HttpContainer.getRequest().getAttributeNames();
	}

	@Override
	public String getCharacterEncoding() {
		return HttpContainer.getRequest().getCharacterEncoding();
	}

	@Override
	public int getContentLength() {
		return HttpContainer.getRequest().getContentLength();
	}

	@Override
	public String getContentType() {
		return HttpContainer.getRequest().getContentType();
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return HttpContainer.getRequest().getInputStream();
	}

	@Override
	public String getLocalAddr() {
		return HttpContainer.getRequest().getLocalAddr();
	}

	@Override
	public String getLocalName() {
		return HttpContainer.getRequest().getLocalName();
	}

	@Override
	public int getLocalPort() {
		return HttpContainer.getRequest().getLocalPort();
	}

	@Override
	public Locale getLocale() {
		return HttpContainer.getRequest().getLocale();
	}

	@Override
	public Enumeration getLocales() {
		return HttpContainer.getRequest().getLocales();
	}

	@Override
	public String[] getParameterValues(String arg0) {
		return HttpContainer.getRequest().getParameterValues(arg0);
	}

	@Override
	public String getProtocol() {
		return HttpContainer.getRequest().getProtocol();
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return HttpContainer.getRequest().getReader();
	}

	@Override
	public String getRealPath(String arg0) {
		return HttpContainer.getRequest().getSession().getServletContext().getRealPath(arg0);
	}

	@Override
	public String getRemoteAddr() {
		return HttpContainer.getRequest().getRemoteAddr();
	}

	@Override
	public String getRemoteHost() {
		return HttpContainer.getRequest().getRemoteHost();
	}

	@Override
	public int getRemotePort() {
		return HttpContainer.getRequest().getRemotePort();
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String arg0) {
		return HttpContainer.getRequest().getRequestDispatcher(arg0);
	}

	@Override
	public String getScheme() {
		return HttpContainer.getRequest().getScheme();
	}

	@Override
	public String getServerName() {
		return HttpContainer.getRequest().getServerName();
	}

	@Override
	public int getServerPort() {
		return HttpContainer.getRequest().getServerPort();
	}

	@Override
	public boolean isSecure() {
		return HttpContainer.getRequest().isSecure();
	}
	@Override
	public void removeAttribute(String arg0) {
		HttpContainer.getRequest().removeAttribute(arg0);
	}
	@Override
	public void setAttribute(String arg0, Object arg1) {
		HttpContainer.getRequest().setAttribute(arg0, arg1);
	}
	@Override
	public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException {
		HttpContainer.getRequest().setCharacterEncoding(arg0);
	}
	@Override
	public String getAuthType() {
		return HttpContainer.getRequest().getAuthType();
	}
	@Override
	public String getContextPath() {
		return HttpContainer.getRequest().getContextPath();
	}
	@Override
	public Cookie[] getCookies() {
		return HttpContainer.getRequest().getCookies();
	}
	@Override
	public long getDateHeader(String arg0) {
		return HttpContainer.getRequest().getDateHeader(arg0);
	}
	@Override
	public Enumeration getHeaders(String arg0) {
		return HttpContainer.getRequest().getHeaders(arg0);
	}

	@Override
	public int getIntHeader(String arg0) {
		return HttpContainer.getRequest().getIntHeader(arg0);
	}

	@Override
	public String getMethod() {
		return HttpContainer.getRequest().getMethod();
	}

	@Override
	public String getPathInfo() {
		return HttpContainer.getRequest().getPathInfo();
	}

	@Override
	public String getPathTranslated() {
		return HttpContainer.getRequest().getPathTranslated();
	}

	@Override
	public String getQueryString() {
		return HttpContainer.getRequest().getQueryString();
	}

	@Override
	public String getRemoteUser() {
		return HttpContainer.getRequest().getRemoteUser();
	}

	@Override
	public String getRequestURI() {
		return HttpContainer.getRequest().getRequestURI();
	}

	@Override
	public StringBuffer getRequestURL() {
		return HttpContainer.getRequest().getRequestURL();
	}

	@Override
	public String getRequestedSessionId() {
		return HttpContainer.getRequest().getRequestedSessionId();
	}

	@Override
	public String getServletPath() {
		return HttpContainer.getRequest().getServletPath();
	}

	@Override
	public HttpSession getSession() {
		return HttpContainer.getRequest().getSession();
	}

	@Override
	public HttpSession getSession(boolean arg0) {
		return HttpContainer.getRequest().getSession(arg0);
	}

	@Override
	public Principal getUserPrincipal() {
		return HttpContainer.getRequest().getUserPrincipal();
	}

	@Override
	public boolean isRequestedSessionIdFromCookie() {
		return HttpContainer.getRequest().isRequestedSessionIdFromCookie();
	}

	@Override
	public boolean isRequestedSessionIdFromURL() {
		return HttpContainer.getRequest().isRequestedSessionIdFromURL();
	}

	@Override
	public boolean isRequestedSessionIdFromUrl() {
		return HttpContainer.getRequest().isRequestedSessionIdFromURL();
	}

	@Override
	public boolean isRequestedSessionIdValid() {
		return HttpContainer.getRequest().isRequestedSessionIdValid();
	}

	@Override
	public boolean isUserInRole(String arg0) {
		return HttpContainer.getRequest().isUserInRole(arg0);
	}

}
