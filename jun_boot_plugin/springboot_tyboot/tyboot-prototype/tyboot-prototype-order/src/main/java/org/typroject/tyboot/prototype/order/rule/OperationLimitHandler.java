package org.typroject.tyboot.prototype.order.rule;

import org.typroject.tyboot.prototype.order.BaseOrder;



public interface OperationLimitHandler{

	/**
	 * 
	 * 通用操作限制
	 *
	 * @param order
	 * @return
	 * @throws Exception
	 */
	boolean checkOperation(BaseOrder order) throws Exception;
}
