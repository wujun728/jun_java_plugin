package com.itheima.service;

import java.util.List;

import com.itheima.domain.Customer;
import com.itheima.exception.IdIsNullException;

public interface BusinessService {
	/**
	 * 查询所有的客户信息
	 * @return
	 */
	List<Customer> findAllCustomers();
	/**
	 * 添加客户信息
	 * @param c
	 */
	void saveCustomer(Customer c);
	/**
	 * 根据id查询客户信息
	 * @param customerId
	 * @return 不存在返回null
	 */
	Customer findCustomerById(String customerId);
	/**
	 * 更新客户信息
	 * @param c
	 * @throws IdIsNullException 客户的id如果为null或者"",抛出此异常
	 */
	void updateCustomer(Customer c) throws IdIsNullException;
	/**
	 * 根据id删除客户信息
	 * @param customerId
	 */
	void deleteCustomerById(String customerId);
	
}
