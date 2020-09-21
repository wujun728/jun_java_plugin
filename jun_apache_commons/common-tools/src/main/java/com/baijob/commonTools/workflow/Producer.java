package com.baijob.commonTools.workflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 生产者线程
 * 结束条件：1、isFinished() 2、产品插入失败 3、被线程中断
 * @author Luxiaolei
 * 
 * @param <E> 产品类型
 */
public abstract class Producer<E> extends Thread {
	private static Logger logger = LoggerFactory.getLogger(Producer.class);

	/** 生态系统对象（产品队列） */
	private Ecosystem<E> queue;

	public Producer(Ecosystem<E> queue, ThreadGroup group, String name) {
		super(group, name);
		this.queue = queue;
	}

	@Override
	public final void run() {
		try {
			while (!isFinished()) {
				E product = produce();
				if (product == null) {
					//空产品跳过此条继续
					continue;
				}
				if(!queue.put(product)) {
					//插入失败（队列关闭了）
					logger.debug("【"+getName()+"】生态系统关闭，结束。");
					break;
				}
			}
		} catch (InterruptedException e) {
			logger.debug("【" + getName() + "】中断结束。");
		}
		this.end();
		queue.producerEnd(); // 通知生态系统自己已经关闭
	}
	
	/**
	 * 线程结束条件方法
	 * @return 线程是否结束
	 */
	protected boolean isFinished(){
		return false;
	}

	/**
	 * 线程结束时调用的方法
	 */
	protected abstract void end();

	/**
	 * 生产
	 * 
	 * @return 生产的结果供消费者使用，若为null，则跳过此条
	 */
	protected abstract E produce();
}
