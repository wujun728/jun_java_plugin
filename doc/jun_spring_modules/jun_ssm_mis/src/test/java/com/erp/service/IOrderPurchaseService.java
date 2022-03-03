package com.erp.service;

import java.util.List;
import java.util.Map;

import com.erp.model.OrderPurchase;
import com.erp.model.OrderPurchaseLine;
import com.jun.plugin.utils.biz.PageUtil;

public interface IOrderPurchaseService
{

	List<OrderPurchase> findPurchaseOrderList(Map<String, Object> param, PageUtil pageUtil );

	Long getCount(Map<String, Object> param, PageUtil pageUtil );

	boolean persistenceOrderPurchase(OrderPurchase c, Map<String, List<OrderPurchaseLine>> map );

	boolean delOrderPurchase(Integer orderPurchaseId );

	List<OrderPurchaseLine> findPurchaseOrderLineList(Integer orderPurchaseId );

}
