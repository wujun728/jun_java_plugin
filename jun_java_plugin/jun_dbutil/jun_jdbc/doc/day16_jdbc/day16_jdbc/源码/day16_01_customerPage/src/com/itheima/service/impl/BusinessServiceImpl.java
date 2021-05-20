package com.itheima.service.impl;

import java.util.List;

import com.itheima.dao.CustomerDao;
import com.itheima.dao.impl.CustomerDaoImpl;
import com.itheima.domain.Customer;
import com.itheima.exception.IdIsNullException;
import com.itheima.service.BusinessService;
import com.itheima.util.IdGenertor;
import com.itheima.web.common.Page;

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

	public Page findPage(String pageNum) {
		int num = 1;//用户要看的页码,默认是1
		if(pageNum!=null&&!pageNum.trim().equals("")){//解析用户要看的页码
			num = Integer.parseInt(pageNum);
		}
		int totalRecords = dao.getTotalRecordsNum();//得到总记录的条数
		Page page = new Page(num, totalRecords);//对象创建出来后,很多的参数就已经计算完毕
		
		//查询分页记录
		List records = dao.getPageRecords(page.getStartIndex(), page.getPageSize());
		page.setRecords(records);
		return page;
	}

}
