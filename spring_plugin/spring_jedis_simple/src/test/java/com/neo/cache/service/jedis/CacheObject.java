package com.neo.cache.service.jedis;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class CacheObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int key;
	private String value;
	
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
    public String toString() {
    	return ToStringBuilder.reflectionToString(this);
    }
}
