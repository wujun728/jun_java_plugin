package com.baijob.commonTools.workflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消费者线程<br/>
 * 结束条件：1、isFinished() 2、生态系统中取出null 3、被线程中断
 * 
 * @author Luxiaolei
 * 
 * @param <E> 产品类型
 */
public abstract class Consumer<E> extends Thread {
	private static Logger logger = LoggerFactory.getLogger(Consumer.class);

	/** 生态系统对象（产品队列） */
	private Ecosystem<E> queue;

	public Consumer(Ecosystem<E> queue, ThreadGroup group, String name) {
		super(group, name);
		this.queue = queue;
	}

	@Override
	public final void run() {
		try {
			while (!isFinished()) {
				E product = queue.take();
				if (product == null) {
					//由于queue为null时会阻塞队列，故返回产品为null的情况表示生态系统结束
					break;
				}
				consume(product);
			}
		} catch (InterruptedException e) {
			logger.debug("【" + getName() + "】中断结束。");
		}
		this.end();
		queue.consumerEnd(); // 通知生态系统自己已经关闭
	}

	/**
	 * 线程结束条件方法<br/>
	 * 默认为false，即只有被强制中断或从队列中取到 null 时才结束<br/>
	 * 若想自定义结束条件，请重写此方法
	 * 
	 * @return 线程是否结束
	 */
	protected boolean isFinished() {
		return false;
	}

	/**
	 * 线程结束调用的方法<br/>
	 * 当线程结束前会调用此方法
	 */
	protected abstract void end();

	/**
	 * 消费<br/>
	 * 线程会循环调用此方法直到达到结束条件
	 * 
	 * @param product 待处理的产品
	 */
	protected abstract void consume(E product);
}
