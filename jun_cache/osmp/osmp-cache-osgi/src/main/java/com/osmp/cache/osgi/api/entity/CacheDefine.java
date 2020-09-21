/*   
 * Project: OSMP
 * FileName: Cacheable.java
 * version: V1.0
 */
package com.osmp.cache.osgi.api.entity;

import java.io.Serializable;

/**
 * Description:缓存定义包装类。传到前台转JSON原来的实体对象不能转
 * 
 * @author: wangkaiping
 * @date: 2014年12月3日 上午10:29:54
 */
public class CacheDefine implements Serializable {

	private static final long serialVersionUID = 707927076221394489L;

	private String id;

	// 缓存名称
	private String name;

	// 前缀
	private String prefix;

	// 有效时间 秒
	private int timeToLive;

	// 空闲时长 秒
	private int timeToIdle;

	// 缓存状态
	private int state;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
