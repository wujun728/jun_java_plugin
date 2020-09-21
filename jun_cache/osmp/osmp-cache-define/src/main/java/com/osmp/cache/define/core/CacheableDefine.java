/*   
 * Project: OSMP
 * FileName: CacheableDefine.java
 * version: V1.0
 */
package com.osmp.cache.define.core;

import java.lang.reflect.Method;

/**
 * Description:缓存定义
 * 
 * @author: wangkaiping
 * @date: 2014年8月8日 下午1:34:53
 */

public class CacheableDefine {
	public final static int STATE_OPEN = 1;

	public final static int STATE_CLOSE = 0;

	// 方法的唯一标示，由方法全路径加参数类型组成
	private String id;

	// 缓存名称
	private String name;

	// 方法
	private Method method;

	// 前缀
	private String prefix;

	// 有效时间 秒
	private int timeToLive;

	// 空闲时长 秒
	private int timeToIdle;

	// 缓存状态
	private int state;

	// key生成策略
	private CacheKeyGenerator cacheKeyGenerator;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public int getTimeToLive() {
		return timeToLive;
	}

	public void setTimeToLive(int timeToLive) {
		this.timeToLive = timeToLive;
	}

	public int getTimeToIdle() {
		return timeToIdle;
	}

	public void setTimeToIdle(int timeToIdle) {
		this.timeToIdle = timeToIdle;
	}

	public CacheKeyGenerator getCacheKeyGenerator() {
		return cacheKeyGenerator;
	}

	public void setCacheKeyGenerator(CacheKeyGenerator cacheKeyGenerator) {
		this.cacheKeyGenerator = cacheKeyGenerator;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Object getCacheKey(Object[] args) {
		return cacheKeyGenerator.getKey(this, args);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
