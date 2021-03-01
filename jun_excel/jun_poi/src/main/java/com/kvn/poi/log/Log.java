package com.kvn.poi.log;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志规范的工具封装
 * 
 */
public class Log {
	/**
	 * 操作类型
	 */
	private String operation;

	/**
	 * 日志信息
	 */
	private String message = EMPTY_STR;

	/**
	 * 额外的参数
	 */
	private Map<String, Object> params = new HashMap<String, Object>();

	/**
	 * 日志格式 [operation][message],{key1=value1,key2=value2,key3=value3}
	 */
	private static final String LOG_FORMAT = "%d|%s|%s|%s";

	/**
	 * 空字符串
	 */
	private static final String EMPTY_STR = "";
	
	/**
	 * 
	 * @param operation 操作
	 */
	public Log(String operation) {
		this.operation = operation;
	}

	/**
	 * 创建Log对象
	 * @param op 操作
	 * @return
	 */
	public static Log op(String op) {
		return new Log(op);
	}

	/**
	 * 
	 * @param message 信息
	 * @return
	 */
	public Log msg(String message) {
		this.message = message;
		return this;
	}
	
	/**
	 * 
	 * @param message 信息
	 * @return
	 */
	public Log msg(String message, Object... args) {
		this.message = MessageFormat.format(message, args);
		return this;
	}

	/**
	 * 键值对
	 * @param key key
	 * @param value 值
	 * @return
	 */
	public Log kv(String key, Object value) {
		params.put(key, value);
		return this;
	}

	
	
	/**
	 * 
	 * @param maps 
	 * @return
	 */
	public Log kvs(Map<String, Object> maps) {
		params.putAll(maps);
		return this;
	}
	
	@Override
	public String toString() {
		return String.format(LOG_FORMAT, System.currentTimeMillis(), operation, params.toString(), message);
	}
}
