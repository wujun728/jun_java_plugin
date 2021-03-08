package com.sam.demo.spring.boot.jpa.json.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Sets;
import com.sam.demo.AppStart;
import com.sam.demo.spring.boot.jpa.json.entity.Order;
import com.sam.demo.spring.boot.jpa.json.entity.UserInfo;
import com.sam.demo.spring.boot.jpa.json.service.UserInfoService;

import java.util.Date;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={AppStart.class})
public class TestUserInfoService {
	
	
	@Autowired
	UserInfoService userInfoService;
	
	
	@Test
	public void saveTest(){
		UserInfo info = new UserInfo();
		info.setBirthday(new Date());
		info.setId(1L);
		
		Set<Order> orders = Sets.newHashSet();
		Order o1 = new Order();
		o1.setId("100");
		o1.setName("aaa");
		o1.setPrice(123.00);
		
		Order o2 = new Order();
		o2.setId("200");
		o2.setName("bbb");
		o2.setPrice(321.00);
		
		orders.add(o1);
		orders.add(o2);
		info.setOrders(orders);
		userInfoService.save(info);
	}
	
	@Test
	public void findOneTest(){
		UserInfo info = userInfoService.findOne(18L);
		System.out.println(info.getOrders());
		
		for(Order order : info.getOrders()){
			System.out.println(order.getName() + "," + order.getPrice());
		}
	}
	
}
