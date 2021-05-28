package com.test.mapper;

import java.util.List;

import com.test.model.Order;

public interface OrderMapper {
	
	List<Order> getOrderListByUserId(Integer userId);
	
	void createOrder(Order order);

}
