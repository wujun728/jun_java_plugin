package org.springrain.frame.dao;

import org.springframework.core.NamedThreadLocal;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;

/**
 * Spring 没有提供获取当前县城的事务状态,自定义ThreadLocal实现,在事务开始前记录.
 * @author caomei
 *
 */

public class FrameDataSourceTransactionManager extends DataSourceTransactionManager {
	private static final long serialVersionUID = 1L;
	private static final ThreadLocal<Boolean> currentTransactionIsExist =
			new NamedThreadLocal<Boolean>("Current transaction is exist");
	@Override
	protected void doBegin(Object transaction, TransactionDefinition definition) {

		/*
		Integer transactionIsolationLevel=definition.getIsolationLevel() != TransactionDefinition.ISOLATION_DEFAULT ? definition.getIsolationLevel() : null;
		
		if(transactionIsolationLevel!=null) {
			TransactionSynchronizationManager.setCurrentTransactionIsolationLevel(transactionIsolationLevel);
		}
		*/
		
		currentTransactionIsExist.set(true);
		super.doBegin(transaction, definition);
	}
	

	@Override
	protected void doCleanupAfterCompletion(Object transaction) {
		super.doCleanupAfterCompletion(transaction);
		currentTransactionIsExist.set(false);
	}
	
	
	/**
	 * 判断是否存在数据库事务
	 * @return
	 */
	public static Boolean isExistTransaction() {
		Boolean existTransaction = currentTransactionIsExist.get();
		if(existTransaction==null||existTransaction==false) {
			return false;
		}
		return existTransaction;
	}
	
	
	

}
