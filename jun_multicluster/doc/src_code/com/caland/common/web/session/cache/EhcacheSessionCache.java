package com.caland.common.web.session.cache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class EhcacheSessionCache implements SessionCache, InitializingBean {
	@SuppressWarnings("unchecked")
	public Map<String, Serializable> getSession(String root) {
		Element e = cache.get(root);
		return e != null ? (HashMap<String, Serializable>) e.getValue() : null;
	}

	public void setSession(String root, Map<String, Serializable> session,
			int exp) {
		cache.put(new Element(root, session));
	}

	public Serializable getAttribute(String root, String name) {
		Map<String, Serializable> session = getSession(root);
		return session != null ? session.get(name) : null;
	}

	public void setAttribute(String root, String name, Serializable value,
			int exp) {
		Map<String, Serializable> session = getSession(root);
		if (session == null) {
			session = new HashMap<String, Serializable>();
		}
		session.put(name, value);
		cache.put(new Element(root, session));
	}

	public void clear(String root) {
		cache.remove(root);
	}

	public boolean exist(String root) {
		return cache.isKeyInCache(root);
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(cache);
	}

	private Ehcache cache;

	public void setCache(Ehcache cache) {
		this.cache = cache;
	}

}
