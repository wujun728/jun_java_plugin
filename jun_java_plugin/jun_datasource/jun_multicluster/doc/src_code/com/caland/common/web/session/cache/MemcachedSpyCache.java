package com.caland.common.web.session.cache;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.spy.memcached.MemcachedClient;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class MemcachedSpyCache implements SessionCache, InitializingBean,
		DisposableBean {
	private MemcachedClient client;
	private String[] servers;
	private Integer[] weights;

	@SuppressWarnings("unchecked")
	public HashMap<String, Serializable> getSession(String root) {
		return (HashMap<String, Serializable>) client.get(root);
	}

	public void setSession(String root, Map<String, Serializable> session,
			int exp) {
		client.set(root, exp * 60, session);
	}

	public Serializable getAttribute(String root, String name) {
		HashMap<String, Serializable> session = getSession(root);
		return session != null ? session.get(name) : null;
	}

	public void setAttribute(String root, String name, Serializable value,
			int exp) {
		HashMap<String, Serializable> session = getSession(root);
		if (session == null) {
			session = new HashMap<String, Serializable>();
		}
		session.put(name, value);
		client.set(root, exp * 60, session);
	}

	public void clear(String root) {
		client.delete(root);
	}

	public boolean exist(String root) {
		return client.get(root) != null;
	}

	public void afterPropertiesSet() throws Exception {
		List<InetSocketAddress> addr = new ArrayList<InetSocketAddress>(
				servers.length);
		int index;
		for (String s : servers) {
			index = s.indexOf(":");
			addr.add(new InetSocketAddress(s.substring(0, index), Integer
					.parseInt(s.substring(index + 1))));
		}
		client = new MemcachedClient(addr);
	}

	public void destroy() throws Exception {
		client.shutdown();
	}

	public String[] getServers() {
		return servers;
	}

	public void setServers(String[] servers) {
		this.servers = servers;
	}

	public Integer[] getWeights() {
		return weights;
	}

	public void setWeights(Integer[] weights) {
		this.weights = weights;
	}
}
