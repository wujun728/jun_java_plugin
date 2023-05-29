package com.jun.plugin.util4j.cache.callBack.impl;

import java.lang.reflect.Method;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.util4j.cache.callBack.AnnotationCallBack;

/**
 * 注解回调实现
 * @author Administrator
 * @param <T>
 */
public class AnnotationCallBackImpl<T> implements AnnotationCallBack<T>{
	protected Logger _log = LoggerFactory.getLogger(this.getClass());
	private Object callTarget;
	private final int functionId;
	private final Method method;
	
	/**
	 * @param callTarget 回调目标
	 * @param functionId 回调目标方法上CallBackFunction注解的ID
	 */
	public AnnotationCallBackImpl(Object callTarget,int functionId) {
		if(callTarget==null)
		{
			throw new NullPointerException("callTarget is null");
		}
		this.functionId=functionId;
		method=findMethod(callTarget,functionId);
		if(method==null)
		{
			throw new NullPointerException("CallBackFunction not found from callTarget:"+callTarget);
		}
	}
	
	/**
	 * 根据注解ID匹配
	 * @param obj
	 * @param functionId
	 * @return
	 */
	protected Method findMethod(Object obj,int functionId)
	{
		Method method=null;
		if(callTarget!=null)
		{
			Method[] methods=callTarget.getClass().getDeclaredMethods();
			for(Method m:methods)
			{
				CallBackFunction function=m.getAnnotation(CallBackFunction.class);
				if(function!=null && function.id()==functionId)
				{
					method=m;
					break;
				}
			}
		}
		return method;
	}

	@Override
	public void call(boolean timeOut, Optional<T> result) {
		try {
			method.setAccessible(true);
			method.invoke(callTarget,timeOut,result);
		} catch (Exception e) {
			_log.error("实例方法调用异常:"+result,e);
		}
	}

	@Override
	public Object getCallTarget() {
		return callTarget;
	}

	@Override
	public int getFunctionId() {
		return functionId;
	}
}
