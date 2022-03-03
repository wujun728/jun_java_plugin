package com.erp.listener;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import net.sf.ehcache.CacheManager;

@SuppressWarnings("deprecation")
public class SessionListenerHandler implements HttpSessionListener, HttpSessionBindingListener, HttpSession

{

	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub

	}

	public void sessionDestroyed(HttpSessionEvent se) {
		CacheManager.getInstance().clearAll();

	}

	public void valueBound(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void valueUnbound(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub

	}

	public Object getAttribute(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("rawtypes")
	public Enumeration getAttributeNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getCreationTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public long getLastAccessedTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getMaxInactiveInterval() {
		// TODO Auto-generated method stub
		return 0;
	}

	public ServletContext getServletContext() {
		// TODO Auto-generated method stub
		return null;
	}

	public HttpSessionContext getSessionContext() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getValue(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getValueNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public void invalidate() {
		CacheManager.getInstance().clearAll();

	}

	public boolean isNew() {
		// TODO Auto-generated method stub
		return false;
	}

	public void putValue(String name, Object value) {
		// TODO Auto-generated method stub

	}

	public void removeAttribute(String name) {
		// TODO Auto-generated method stub

	}

	public void removeValue(String name) {
		// TODO Auto-generated method stub

	}

	public void setAttribute(String name, Object value) {
		// TODO Auto-generated method stub

	}

	public void setMaxInactiveInterval(int interval) {
		// TODO Auto-generated method stub

	}

}
