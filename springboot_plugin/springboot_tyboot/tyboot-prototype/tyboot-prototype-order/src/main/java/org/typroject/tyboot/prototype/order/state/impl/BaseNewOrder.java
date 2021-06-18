package org.typroject.tyboot.prototype.order.state.impl;


import org.typroject.tyboot.face.order.model.OrderInfoModel;
import org.typroject.tyboot.face.order.orm.entity.OrderProductRelation;
import org.typroject.tyboot.prototype.order.BaseOrder;
import org.typroject.tyboot.prototype.order.standard.StandardProduct;
import org.typroject.tyboot.prototype.order.state.BaseOrderState;
import org.typroject.tyboot.prototype.order.state.OrderStatus;

import java.util.List;



public abstract class BaseNewOrder extends BaseOrderState
{
	/**
	 * 创建订单信息
	 * @param orderStatus
	 * @return
	 * @throws Exception
	 */
	protected BaseOrder createOrderInfo(OrderStatus orderStatus) throws Exception
	{
		//保存订单基础信息
		BaseOrder baseOrder = this.getOrder();
		OrderInfoModel orderInfoModel = baseOrder.getOrderInfoModel();
		orderInfoModel.setOrderStatus(orderStatus.toString());
		return null;
	}

	/**
	 * 创建商品订单关系
	 * @return
	 * @throws Exception
	 */
	protected List<OrderProductRelation> createOrderProductRelation() throws Exception
	{
		List<StandardProduct> products = this.getOrder().getProducts();
		List<OrderProductRelation> newEntities = null;
		return newEntities;
	}
	
}
