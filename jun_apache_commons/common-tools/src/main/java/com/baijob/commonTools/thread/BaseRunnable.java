package com.baijob.commonTools.thread;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseRunnable implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(BaseRunnable.class);
	
	/** 此线程的标识ID */
	protected long id;
	/** 此线程的名称，默认为空串 */
	protected String name = "";
	/** 线程是否启动标志 */
	protected boolean isRunning;
	/** 被调用的次数（包括当前正在运行的） */
	protected int callCount;
	/** 此线程每次运行的时长，key为次数，value为时长 */
	protected Map<Integer, Long> runTimes = new HashMap<Integer, Long>();
	
	//---------------------------------------------------------------------------- 构造 start
	/**
	 * 默认构造
	 */
	public BaseRunnable() {
	}
	
	/**
	 * 构造
	 * @param id ID
	 * @param name 线程名称
	 */
	public BaseRunnable(long id, String name) {
		this.id = id;
		this.name = name;
	}
	//---------------------------------------------------------------------------- 构造 end

	@Override
	public void run() {
		if(isRunning){
			logger.warn("** 【" + id + "】线程 " + name + " 正在运行，请停止当前正在运行的线程或等待 **");
			return;
		}
		isRunning = true;
		callCount ++;
		long timesBefore = System.currentTimeMillis();
		work();
		runTimes.put(callCount, System.currentTimeMillis() - timesBefore);
		isRunning = false;
	}
	
	/**
	 * 开始工作
	 */
	public abstract void work();
	
	/**
	 * 本线程是否正在运行
	 * @return 是否运行
	 */
	public boolean isRunning(){
		return this.isRunning;
	}
	/**
	 * 停止线程
	 */
	public void stopRunning(){
		this.isRunning = false;
	}
	
	/**
	 * 获得已经运行的线程统计，包括已经运行的次数以及对应的运行时间
	 * @return 已经运行的线程统计
	 */
	public Map<Integer, Long> getRunTimes(){
		return this.runTimes;
	}
	
	/**
	 * 获得被调用的次数，包括当前正在运行的
	 * @return 被调用的次数
	 */
	public int getCallCount(){
		return this.callCount;
	}
	
	/**
	 * 自定义设置调用次数
	 * @param callCount 调用次数
	 */
	public void setCallCount(int callCount){
		this.callCount = callCount;
	}
	
	/**
	 * 线程ID
	 * @return ID
	 */
	public long getId() {
		return id;
	}
	/**
	 * 设置线程ID
	 * @param id ID
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 线程名称
	 * @return 名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置线程名称
	 * @param name 名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public int hashCode() {
		return (int)id;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj != null && obj instanceof BaseRunnable){
			return this.id == ((BaseRunnable)obj).id;
		}
		return false;
	}
}
