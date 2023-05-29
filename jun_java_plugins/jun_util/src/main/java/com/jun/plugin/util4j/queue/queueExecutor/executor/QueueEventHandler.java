package com.jun.plugin.util4j.queue.queueExecutor.executor;

public interface QueueEventHandler {

	/**
	 * 拒绝添加
	 */
	void onOfferReject(Runnable r,QueueExecutor executor);
	
	/**
	 * 运行之前
	 */
	void onRunBefore(Runnable r,QueueExecutor executor);
	
	/**
	 * 运行之后
	 */
	void onRunAfter(Runnable r,QueueExecutor executor,Exception exception);
}
