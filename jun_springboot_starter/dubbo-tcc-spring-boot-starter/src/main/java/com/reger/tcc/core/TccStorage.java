package com.reger.tcc.core;

import java.util.List;

public interface TccStorage {

	/**
	 * 查询当前事务
	 * 
	 * @param transactionId
	 * @return
	 */
	TccTransaction findById(String id);

	/**
	 * 查询当前事务id下的所有子事务
	 * 
	 * @param transactionId
	 * @return
	 */
	List<TccTransaction> findByTransactionId(String transactionId);

	/**
	 * 查询当前事务id下的所有在当前平台（appName,版本号）的子事务
	 * 
	 * @param transactionId
	 * @return
	 */
	List<TccTransaction> findByTransactionIdAndCurPlatform(String transactionId);

	/**
	 * 保存一个已提交的事务
	 * 
	 * @param transaction
	 */
	TccTransaction save(TccTransaction transaction);

	/**
	 * 删除当前id的事务
	 * 
	 * @param id
	 */
	void delById(String id);

	/**
	 * 删除当前id下的所有子事务，包括自己
	 * 
	 * @param transactionId
	 */
	void delByTransactionId(String transactionId);

	/**
	 * 删除当前id下的所有子事务，包括自己
	 * 
	 * @param transactionId
	 */
	void delByTransactionIdAndCurPlatform(String transactionId);
}
