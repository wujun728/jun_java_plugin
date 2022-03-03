package com.erp.service;

import java.util.List;
import java.util.Map;

import com.erp.model.Customer;
import com.erp.model.CustomerContact;
import com.erp.viewModel.TreeModel;
import com.jun.plugin.utils.biz.PageUtil;

public interface ICstService
{

	List<Customer> findCustomerList(Map<String, Object> param, PageUtil pageUtil );

	Long getCount(Map<String, Object> param, PageUtil pageUtil );

	boolean persistenceCustomer(Customer c,Map<String, List<CustomerContact>> map);

	List<TreeModel> findSaleNameList();

	boolean delCustomer(Integer customerId );

	List<Customer> findCustomerListNoPage(Map<String, Object> param, PageUtil pageUtil );

}
