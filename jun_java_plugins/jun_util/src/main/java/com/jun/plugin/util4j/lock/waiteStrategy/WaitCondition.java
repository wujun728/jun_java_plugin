package com.jun.plugin.util4j.lock.waiteStrategy;

/**
 * 等待条件
 * @author juebanlin
 * @param <T>
 */
public interface WaitCondition<T> {
	
	/**
	 * 附件
	 * @return
	 */
	public T getAttach();
	
	/**
	 * 条件是否成立
	 * @return
	 */
	public boolean isComplete();
}
