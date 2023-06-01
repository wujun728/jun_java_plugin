package com.reger.tcc.core;

public interface Queue {
	/**
	 * 设置当前队列的名字
	 * 
	 * @param name
	 */
	void setName(String name);

	/**
	 * 出队列
	 * 
	 * @param message
	 */
	void poll(Message message);

	/**
	 * 向指定消息队列发送消息
	 * 
	 * @param queueName 队列名字
	 * @param transactionId 事务id
	 */
	void send(String queueName, String transactionId);

	public static interface Message {
		
		void event(String transactionId);
		
	}
}
