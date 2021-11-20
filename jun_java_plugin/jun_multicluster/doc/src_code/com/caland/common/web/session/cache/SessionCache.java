package com.caland.common.web.session.cache;

import java.io.Serializable;
import java.util.Map;

public interface SessionCache {
	public Serializable getAttribute(String root, String name);

	public void setAttribute(String root, String name, Serializable value,
			int exp);

	public void clear(String root);

	public boolean exist(String root);

	public Map<String, Serializable> getSession(String root);

	public void setSession(String root, Map<String, Serializable> session,
			int exp);
}
