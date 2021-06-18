package cn.itcast.crm.service.impl;

import cn.itcast.crm.dao.CustomerDao;
import cn.itcast.crm.entity.Customer;
import cn.itcast.crm.service.CustomerService;

/**
 * 文件名字:CustomerServiceImpl.java<br>
 * 文件作用:Customer服务层的实现<br>
 */
public class CustomerServiceImpl implements CustomerService {

	private CustomerDao customerDao;

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	@Override
	public Customer findById(Long id) {
		return customerDao.findById(id);
	}

}
