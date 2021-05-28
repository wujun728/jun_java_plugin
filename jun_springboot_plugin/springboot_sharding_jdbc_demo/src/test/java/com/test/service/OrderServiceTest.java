package com.test.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.test.Application;
import com.test.model.Order;
import com.test.service.OrderService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@Transactional
public class OrderServiceTest {
	
	@Autowired
	private OrderService orderService;

    @Test
    public void getOrderListByUserId() throws Exception {
    	List<Order>  orderList=orderService.getOrderListByUserId(1);
    	for(Order o:orderList){
    		System.out.println(o.getUserId());
    	}
    }
    
    
    @Test
    @Rollback(false)
    public void createOrder() throws Exception {
    	Order o1=new Order();
    	o1.setOrderId(1);
    	o1.setUserId(1);
    	orderService.createOrder(o1);
    	Order o2=new Order();
    	o2.setOrderId(2);
    	o2.setUserId(2);
    	orderService.createOrder(o2);
    }

}
