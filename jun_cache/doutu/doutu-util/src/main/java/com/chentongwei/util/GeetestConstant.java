package com.chentongwei.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 极验验证配置
 * 
 * @author TongWei.Chen 2017-6-4 11:37:56
 */
@Configuration
@ConfigurationProperties(prefix="geetest")
@PropertySource("classpath:geetest.properties")
public class GeetestConstant {
	//id
	private String id;
	//key
	private String key;
	//是否开启新的failback
	private Boolean newfailback;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Boolean getNewfailback() {
		return newfailback;
	}

	public void setNewfailback(Boolean newfailback) {
		this.newfailback = newfailback;
	}

	@Override
	public String toString() {
		return "GeetestConstant [id=" + id + ", key=" + key + ", newfailback=" + newfailback + "]";
	}
}
