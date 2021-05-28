package com.reger.swagger.properties;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(Swagger2Properties.prefix)
public class Swagger2Properties {
	public static final String prefix = "spring.swagger";

	/**
	 * 启用swagger，如果不配置，spring.profiles中包含api依然会启用，如果配置为false，swagger不会启用，如果配置为true，swagger会启用
	 */
	private Boolean enabled;

	/**
	 * swagger2组配置
	 */
	private Map<String, Swagger2GroupProperties> group;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Map<String, Swagger2GroupProperties> getGroup() {
		return group;
	}

	public void setGroup(Map<String, Swagger2GroupProperties> group) {
		this.group = group;
	}
}
