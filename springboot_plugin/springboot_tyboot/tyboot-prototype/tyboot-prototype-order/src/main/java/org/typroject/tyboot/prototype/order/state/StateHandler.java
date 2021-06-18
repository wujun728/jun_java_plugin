package org.typroject.tyboot.prototype.order.state;

import org.typroject.tyboot.prototype.order.BaseOrder;

/**
 * 订单顶级接口,只负责主流程的流转
 * @author Wujun
 *
 */
public interface StateHandler {

	/**
	 * 主线流程流转：
	 * 订单流程流转处理方法，从新创建的订单，直到订单成功完成，不同的状态对象实现各自需要的处理；
	 * 此方法只处理主线流程流转过程，支线处理在各自实现类中定义和实现
	 * @return
	 * @throws Exception
	 */
	BaseOrder process()throws Exception;
	
	
	/**
	 * 当前状态
	 * @return OrderStatus
	 */
	OrderStatus getStatus();
	
	
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
	
}
