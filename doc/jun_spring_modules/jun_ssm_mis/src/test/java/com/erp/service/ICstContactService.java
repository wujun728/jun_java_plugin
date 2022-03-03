package com.erp.service;

import java.util.List;

import com.erp.model.CustomerContact;

public interface ICstContactService
{

	List<CustomerContact> findCustomerContactList(Integer customerId );

}
