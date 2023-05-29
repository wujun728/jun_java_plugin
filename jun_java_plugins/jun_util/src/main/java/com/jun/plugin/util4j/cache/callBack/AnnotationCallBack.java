package com.jun.plugin.util4j.cache.callBack;

/**
 * 注解回调
 * @author Administrator
 * @param <T>
 */
public interface AnnotationCallBack<T> extends CallBack<T>{
	
	/**
	 * 回调目标
	 * @param obj
	 */
	public Object getCallTarget();
	
	/**
	 * 获取处理函数名称
	 * 方法上的CallBackFunction注解名称
	 * @param name
	 */
	public int getFunctionId();
}
