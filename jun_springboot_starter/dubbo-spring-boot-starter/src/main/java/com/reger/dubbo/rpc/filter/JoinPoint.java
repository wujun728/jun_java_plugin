package com.reger.dubbo.rpc.filter;

import java.util.Map;

import com.alibaba.dubbo.common.Node;
import com.alibaba.dubbo.rpc.Result;

public interface JoinPoint<T> extends Node {

	/**
	 * 调用下一个过滤器，如果当前是最后一个，就调用具体业务方法
	 * 
	 * @return 返回值
	 */
	Result proceed();

	/**
	 * 接口信息
	 */
	Class<T> getInterface();

	/**
	 * 方法名
	 * 
	 * @return 返回值
	 */
	String getMethodName();

	/**
	 * 参数类型
	 * 
	 * @return 返回值
	 */
	Class<?>[] getParameterTypes();

	/**
	 * 参数名
	 * 
	 * @return 返回值
	 */
	Object[] getArguments();

	/**
	 * 隐式传参
	 * 
	 * @return 返回值
	 */
	Map<String, String> getAttachments();

	/**
	 * 获取隐式参数
	 * 
	 * @param key 参数
	 * @return 返回值
	 */
	String getAttachment(String key);

	/**
	 * 获取隐式参数
	 * 
	 * @param key 参数
	 * @param defaultValue  参数默认值
	 * @return 返回值
	 */
	String getAttachment(String key, String defaultValue);

	/**
	 * 获取参数
	 * 
	 * @param key 参数
	 * @return 返回值
	 */
	Object getAttribute(String key);

	/**
	 * 设置参数
	 * 
	 * @param key 参数 
	 * @param  value  值
	 */
	void setAttribute(String key, Object value);
}
