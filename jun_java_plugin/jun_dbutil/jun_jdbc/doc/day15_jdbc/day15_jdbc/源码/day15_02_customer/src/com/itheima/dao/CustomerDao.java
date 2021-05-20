package com.itheima.dao;

import java.util.List;

import com.itheima.domain.Customer;

public interface CustomerDao {

	List<Customer> findAll();

	void save(Customer c);

	Customer findById(String customerId);

	void update(Customer c);

	void delete(String customerId);

}
