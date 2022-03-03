package com.erp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.model.CustomerContact;
import com.erp.service.ICstContactService;
@Service("cstContactService")
public class CstContactServiceImpl implements ICstContactService
{
	private PublicDao<CustomerContact> publicDao;
	@Autowired
	public void setPublicDao(PublicDao<CustomerContact> publicDao )
	{
		this.publicDao = publicDao;
	}
	
	public List<CustomerContact> findCustomerContactList(Integer customerId)
	{
		if (null==customerId||"".equals(customerId))
		{
			return new ArrayList<CustomerContact>();
		}else {
			String hql="from CustomerContact t where t.status='A' and t.customerId="+customerId;
			return publicDao.find(hql);
		}
	}
}
