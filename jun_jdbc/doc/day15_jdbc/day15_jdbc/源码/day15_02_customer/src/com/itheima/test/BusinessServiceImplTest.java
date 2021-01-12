package com.itheima.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.itheima.domain.Customer;
import com.itheima.exception.IdIsNullException;
import com.itheima.service.BusinessService;
import com.itheima.service.impl.BusinessServiceImpl;

public class BusinessServiceImplTest {

	private BusinessService s = new BusinessServiceImpl();

	@Test
	public void testSaveCustomer() {
		Customer c = new Customer();
		c.setName("杨洋");
		c.setBirthday(new Date());
		c.setGender("female");
		c.setPhonenum("119");
		c.setEmail("yy@itheima.com");
		c.setHobby("吃饭,睡觉,学java");
		c.setType("VVIP");
		c.setDescription("美女一个");
		s.saveCustomer(c);
	}
	@Test
	public void testFindAllCustomers() {
		List<Customer> cs = s.findAllCustomers();
		assertEquals(1, cs.size());
	}

	@Test(expected=com.itheima.exception.IdIsNullException.class)
	public void testUpdateCustomer() throws IdIsNullException {
		Customer c = new Customer();
		s.updateCustomer(c);
	}
	@Test
	public void testFindCustomerById() {
		Customer c = s.findCustomerById("96LCX41R9ZHPJE8EOI2HHX6P3R5UGHX8");
		assertNotNull(c);
	}
	@Test
	public void testUpdateCustomer1() throws IdIsNullException {
		Customer c = s.findCustomerById("96LCX41R9ZHPJE8EOI2HHX6P3R5UGHX8");
		c.setPhonenum("110");
		s.updateCustomer(c);
	}
	
	@Test
	public void testDeleteCustomerById() {
		s.deleteCustomerById("96LCX41R9ZHPJE8EOI2HHX6P3R5UGHX8");
	}

}
