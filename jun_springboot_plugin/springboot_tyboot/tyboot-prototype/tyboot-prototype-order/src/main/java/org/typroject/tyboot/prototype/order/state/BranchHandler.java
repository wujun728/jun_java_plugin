package org.typroject.tyboot.prototype.order.state;

import org.typroject.tyboot.prototype.order.BaseOrder;


public interface BranchHandler
{
	/**
	 *	流程分支处理方法
	 * @return
	 */
	public BaseOrder branchOperate()throws Exception;
	
	/**
	 * 获得订单实体
	 * @return
	 */
	BaseOrder getOrder();
	
	
	/**
	 * 设置订单实体
	 */
	void setOrder(BaseOrder order);
	
	
	/**
	 * 订单状态
	 */
	void setStatus(OrderStatus orderStatus);
	
	
	/**
	 * 当前状态
	 * @return
	 */
	OrderStatus getStatus();
}

/*
*$Log: av-env.bat,v $
*/