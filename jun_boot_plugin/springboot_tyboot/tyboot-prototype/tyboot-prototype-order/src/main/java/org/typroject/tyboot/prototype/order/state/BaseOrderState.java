package org.typroject.tyboot.prototype.order.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.typroject.tyboot.face.order.service.OrderInfoService;
import org.typroject.tyboot.prototype.order.BaseOrder;



public abstract class BaseOrderState  implements StateHandler {


	@Autowired
	protected OrderInfoService orderInfoService;

	/**
	 * 订单实体
	 */
	private BaseOrder order;

	/**
	 * 获得当前状态枚举
	 */
	public OrderStatus getStatus() {
		return this.getOrder().getOrderStatus();
	}
	
	public void setStatus(OrderStatus status)
	{
		this.order.setOrderStatus(status);
	}


	
	
	

	
	
	/**
	 * 获得订单实体
	 */
	public BaseOrder getOrder() {
		return order;
	}

	/**
	 * 设置订单实体
	 * @param order  
	 * 		订单实体对象
	 */
	public  void setOrder(BaseOrder order) {
		this.order = order;
	}
	
}
