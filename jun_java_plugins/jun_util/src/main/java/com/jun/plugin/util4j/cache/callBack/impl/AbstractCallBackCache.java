package com.jun.plugin.util4j.cache.callBack.impl;

import java.lang.management.ManagementFactory;
import java.util.Objects;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.util4j.cache.callBack.CallBack;
import com.jun.plugin.util4j.cache.map.TimedMap;
import com.jun.plugin.util4j.cache.map.TimedMapImpl;
import com.jun.plugin.util4j.cache.map.TimedMap.EventListener;

public abstract class AbstractCallBackCache<KEY,TYPE> {
	protected Logger _log = LoggerFactory.getLogger(this.getClass());
	public volatile boolean clearing;
	
	private final TimedMap<KEY,CallBack<TYPE>> callBacks;
	
	/**
	 * 超时执行器
	 * @param timeOutExecutor 处理超时事件的执行器
	 */
	public AbstractCallBackCache(Executor timeOutExecutor) {
		 callBacks=new TimedMapImpl<KEY,CallBack<TYPE>>(timeOutExecutor,true);
	}
	
	public KEY put(CallBack<TYPE> callBack,long timeOut)
	{
		Objects.requireNonNull(callBack);
		KEY ck=nextCallKey();
		if(timeOut<=0)
		{
			timeOut=CallBack.DEFAULT_TIMEOUT;
		}
		EventListener<KEY,CallBack<TYPE>> listener=new EventListener<KEY,CallBack<TYPE>>(){
			@Override
			public void removed(KEY key, CallBack<TYPE> value, boolean expire) {
				if(expire)
				{
					value.call(true);
				}
			}
		};
		callBacks.put(ck, callBack, timeOut,listener);
		return ck;
	}
	
	/**
	 * 手动指定超时执行器
	 * @param callBack
	 * @param timeOutExecutor
	 * @return
	 */
	public KEY put(CallBack<TYPE> callBack,long timeOut,final Executor timeOutExecutor)
	{
		Objects.requireNonNull(callBack);
		Objects.requireNonNull(timeOutExecutor);
		KEY ck=nextCallKey();
		if(timeOut<=0)
		{
			timeOut=CallBack.DEFAULT_TIMEOUT;
		}
		EventListener<KEY,CallBack<TYPE>> listener=new EventListener<KEY,CallBack<TYPE>>(){
			@Override
			public void removed(KEY key, CallBack<TYPE> value, boolean expire) {
				if(expire)
				{
					timeOutExecutor.execute(new Runnable() {
						@Override
						public void run() {
							value.call(true);
						}
					});
				}
			}
		};
		callBacks.put(ck, callBack, timeOut,listener);
		return ck;
	}
	
	public CallBack<TYPE> poll(KEY callKey)
	{
		return callBacks.remove(callKey);
	}
	
	public int size()
	{
		return callBacks.size();
	}
	
	public Runnable getCleanTask()
	{
		return cleanTask;
	}
	
	CleanTask cleanTask=new CleanTask();
	
	class CleanTask implements Runnable{
		@Override
		public void run() {
			callBacks.cleanExpire();
		}
	}
	
	public abstract KEY nextCallKey();

	private  static String JVM_PID;
	static{
		String pid = ManagementFactory.getRuntimeMXBean().getName();  
        int indexOf = pid.indexOf('@');  
        if (indexOf > 0)  
        {  
        	JVM_PID = pid.substring(0, indexOf);  
        }  
	}
	
	public static String getJVM_PID()
	{
		return JVM_PID;
	}
}
