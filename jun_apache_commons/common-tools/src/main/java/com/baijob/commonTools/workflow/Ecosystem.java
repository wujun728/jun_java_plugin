package com.baijob.commonTools.workflow;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baijob.commonTools.thread.SyncQueue;

/**
 * 生态系统类（生产者消费者模式实现）<br/>
 * 生态系统的平衡条件：<br/>
 * 1、队列满阻塞生产者，队列空阻塞消费者<br/>
 * 2、生产者结束后，关闭生态系统，等队列中的数据全部消费完毕后，消费者结束<br/>
 * 3、消费者若提前结束，则生态系统结束<br/>
 * @author Luxiaolei
 * @param <E> 生产和消费的产品类型
 *
 */
public class Ecosystem<E> extends SyncQueue<E>{
	private static Logger logger = LoggerFactory.getLogger(Ecosystem.class);
	
	public final static String PRODUCER_GROUP = "producerGroup";
	public final static String CONSUMER_GROUP = "producerGroup";
	
	/** 生产者组 */
	private final ThreadGroup producerGroup = new ThreadGroup(PRODUCER_GROUP);
	/** 活动的生产者数，由生产者线程修改 */
	private volatile int activeProducerCount;
	/** 生产者锁 */
	private Lock producerLock = new ReentrantLock();
	
	/** 消费者组 */
	private final ThreadGroup consumerGroup = new ThreadGroup(CONSUMER_GROUP);
	/** 活动的消费者数，由消费者线程修改 */
	private volatile int activeConsumerCount;
	
	public Ecosystem(int capacity) {
		super(capacity);
	}
	
	/**
	 * 增加一个生产者
	 * @param producerClass 生产者
	 * @param name 名称
	 * @return 生产者对象
	 */
	public <T extends Producer<E>> T newProducer(Class<T> producerClass, String name){
		if(this.isClosed()) {
			logger.warn("生态系统已关闭，无法加入生产者！");
			return null;
		}
		
		T producer = null;
		try {
			producer = producerClass.getConstructor(Ecosystem.class, ThreadGroup.class, String.class).newInstance(this, this.producerGroup, name);
		} catch (Exception e) {
			logger.error("初始化生产者失败！", e);
			return null;
		}
		activeProducerCount++;
		return producer;
	}
	
	/**
	 * 准备添加新的生产者<br/>
	 * 由于存在新的生产者未加入而已有生产者结束后导致生态系统关闭的情况，请在有多个生产者添加前调用此方法。<br/>
	 * 请在生产者添加且启动完毕后，调用finishNewProducer()方法，否则生产者将无法终止！
	 */
	public void prepareNewProducer() {
		try {
			this.producerLock.lockInterruptibly();
		} catch (InterruptedException e) {
			this.producerLock.unlock();
		}
	}
	
	/**
	 * 结束添加新的生产者<br/>
	 * 当所有生产者添加并启动完毕后调用此方法，与prepareNewProducer()配合使用。
	 */
	public void finishNewProducer() {
		this.producerLock.unlock();
	}
	
	/**
	 * 增加一个消费者
	 * @param consumerClass 消费者类
	 * @param name 名称
	 * @return 消费者对象
	 */
	public <T extends Consumer<E>> T newConsumer(Class<T> consumerClass, String name){
		if(this.isClosed()) {
			logger.warn("生态系统已关闭，无法加入消费者！");
			return null;
		}
		
		T consumer = null;
		try {
			consumer = consumerClass.getConstructor(Ecosystem.class, ThreadGroup.class, String.class).newInstance(this, this.consumerGroup, name);
		} catch (Exception e) {
			logger.error("初始化消费者失败！", e);
			return null;
		}
		activeConsumerCount++;
		return consumer;
	}
	
	/**
	 * 结束生态系统<br/>
	 * 结束的时执行以下步骤：
	 * 1、清空队列
	 * 2、关闭队列
	 * 3、中断所有生产者和消费者线程
	 */
	synchronized public void shutDown(){
		this.clear();	//清空队列
		this.close();	//关闭队列
		producerGroup.interrupt();	//强制中断生产者及其子线程
		consumerGroup.interrupt();	//强制中断消费者及其子线程
	}
	
	/**
	 * 是否正在运行<br/>
	 * @return 若生产者或消费者有其一运行，返回true，否则false
	 */
	public boolean isActive(){
		return this.activeProducerCount > 0 || this.activeConsumerCount > 0;
	}
	
	/**
	 * 运行中的生产者数
	 * @return 生产者数
	 */
	public int activeProducerCount(){
		return this.activeProducerCount;
	}
	
	/**
	 * 运行中的消费者数
	 * @return 消费者数
	 */
	public int activeConsumerCount(){
		return this.activeConsumerCount;
	}
	
	/**
	 * 生产者线程结束时调用此方法<br/>
	 * 若自己为最后一个关闭的生产者线程则关闭队列，唤醒所有消费者线程将剩余数据处理后生态系统终结。<br/>
	 * 结束的前提是调用了finishNewProducer()，即所有生产者已经添加完毕。
	 * protected修饰表示可以被同包的consumer对象访问，但是不可以被子类覆盖
	 */
	protected final void producerEnd(){
		producerLock.lock();
		try {
			this.activeProducerCount--;
			if(this.activeProducerCount <= 0){
				super.close();
				super.signalAllTake();
			}
		}finally {
			producerLock.unlock();
		}
	}
	
	/**
	 * 消费者线程结束时调用此方法<br/>
	 * 若自己为最后一个关闭的消费者线程则关闭队列（因为已经无消费者，生产者活动也必须终止）<br/>
	 * protected修饰表示可以被同包的consumer对象访问，但是不可以被子类覆盖
	 */
	synchronized protected final void consumerEnd(){
		activeConsumerCount--;
		if(this.activeConsumerCount <= 0){
			super.close();
			producerGroup.interrupt();
			//消费者全部结束代表生态系统结束
			actionAfterClose();
		}
	}
	
	/**
	 * 当生态系统关闭执行的方法
	 */
	synchronized protected void actionAfterClose(){
	}
}
