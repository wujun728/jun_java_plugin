package com.itheima.service.impl;

import java.util.List;

import com.itheima.dao.CustomerDao;
import com.itheima.dao.impl.CustomerDaoImpl;
import com.itheima.domain.Customer;
import com.itheima.exception.IdIsNullException;
import com.itheima.service.BusinessService;
import com.itheima.util.IdGenertor;

public class BusinessServiceImpl implements BusinessService {
	private CustomerDao dao = new CustomerDaoImpl();
	public List<Customer> findAllCustomers() {
		return dao.findAll();
	}

	public void saveCustomer(Customer c) {
		if(c==null)
			throw new IllegalArgumentException("参数不全");
		c.setId(IdGenertor.genGUID());
		dao.save(c);
	}

	public Customer findCustomerById(String customerId) {
		return dao.findById(customerId);
	}

	public void updateCustomer(Customer c) throws IdIsNullException {
		if(c==null)
			throw new IllegalArgumentException("参数不全");
		if(c.getId()==null||c.getId().trim().equals("")){
			throw new IdIsNullException("更新的客户信息，id不能为空");
		}
		dao.update(c);
	}

	public void deleteCustomerById(String customerId) {
		if(customerId==null||customerId.trim().equals("")){
			throw new IllegalArgumentException("客户的id不能为空");
		}
		dao.delete(customerId);
	}

}
