package com.erp.service;

import java.util.List;
import java.util.Map;

import com.erp.model.OrderSale;
import com.erp.model.OrderSaleLine;
import com.jun.plugin.utils.biz.PageUtil;

public interface IOrderSaleService
{

	boolean delOrderSale(Integer orderSaleId );

	boolean persistenceOrderSale(OrderSale c, Map<String, List<OrderSaleLine>> map );

	Long getCount(Map<String, Object> param, PageUtil pageUtil );

	List<OrderSale> findOrderSaleList(Map<String, Object> param, PageUtil pageUtil );

	List<OrderSaleLine> findOrderSaleLineList(Integer orderSaleId );

}
