package com.itheima.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.itheima.dao.impl.CustomerDaoImpl;
import com.itheima.domain.Customer;
import com.itheima.domain.Order;

public class CustomerDaoImplTest {
	private CustomerDaoImpl dao = new CustomerDaoImpl();
	@Test
	public void testSaveCustomer() {
		Customer c = new Customer();
		c.setId(1);
		c.setName("范青霞");
		c.setCity("北京");
		
		Order o1 = new Order();
		o1.setId(1);
		o1.setNum("001");
		o1.setPrice(10000);
		

		Order o2 = new Order();
		o2.setId(2);
		o2.setNum("002");
		o2.setPrice(100000);
		
		//建立关联关系
		c.getOrders().add(o1);
		c.getOrders().add(o2);
		
		dao.saveCustomer(c);
	}

	@Test
	public void testFindCustomerById() {
		Customer c = dao.findCustomerById(1);
		System.out.println("客户姓名："+c.getName()+"买了以下商品：");
		for(Order o:c.getOrders()){
			System.out.println(o);
		}
	}

}
