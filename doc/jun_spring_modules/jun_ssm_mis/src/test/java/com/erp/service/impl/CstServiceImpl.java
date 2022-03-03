package com.erp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.model.Customer;
import com.erp.model.CustomerContact;
import com.erp.model.Organization;
import com.erp.model.Users;
import com.erp.service.ICstService;
import com.erp.viewModel.Attributes;
import com.erp.viewModel.TreeModel;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.biz.PageUtil;
@SuppressWarnings("unchecked")
@Service("cstService")
public class CstServiceImpl implements ICstService
{
	@SuppressWarnings("rawtypes")
	private PublicDao publicDao;
	@SuppressWarnings("rawtypes")
	@Autowired
	public void setPublicDao(PublicDao publicDao )
	{
		this.publicDao = publicDao;
	}

	
	
	/* (非 Javadoc) 
	* <p>Title: findCustomerList</p> 
	* <p>Description: 查询所有客户</p> 
	* @param param
	* @param pageUtil
	* @return 
	* @see com.erp.service.CstService#findCustomerList(java.util.Map, com.erp.util.PageUtil) 
	*/
	public List<Customer> findCustomerList(Map<String, Object> param,PageUtil pageUtil)
	{
		String hql="from Customer t where t.status='A'";
		hql+=Constants.getSearchConditionsHQL("t", param);
		hql+=Constants.getGradeSearchConditionsHQL("t", pageUtil);
		return publicDao.find(hql, param, pageUtil.getPage(), pageUtil.getRows());
	}
	
	/* (非 Javadoc) 
	* <p>Title: findCustomerListNoPage</p> 
	* <p>Description:查询所有客户不分页 </p> 
	* @param param
	* @param pageUtil
	* @return 
	* @see com.erp.service.CstService#findCustomerListNoPage(java.util.Map, com.erp.util.PageUtil) 
	*/
	public List<Customer> findCustomerListNoPage(Map<String, Object> param,PageUtil pageUtil)
	{
		String hql="from Customer t where t.status='A'";
		hql+=Constants.getSearchConditionsHQL("t", param);
		hql+=Constants.getGradeSearchConditionsHQL("t", pageUtil);
		return publicDao.find(hql, param);
	}
	
	/* (非 Javadoc) 
	* <p>Title: getCount</p> 
	* <p>Description: 查询所有总数</p> 
	* @param param
	* @param pageUtil
	* @return 
	* @see com.erp.service.CstService#getCount(java.util.Map, com.erp.util.PageUtil) 
	*/
	public Long getCount(Map<String, Object> param,PageUtil pageUtil)
	{
		String hql="select count(*) from Customer t where t.status='A' ";
		hql+=Constants.getSearchConditionsHQL("t", param);
		hql+=Constants.getGradeSearchConditionsHQL("t", pageUtil);
		return publicDao.count(hql, param);
	}
	
	/* (非 Javadoc) 
	* <p>Title: persistenceCustomer</p> 
	* <p>Description:持久化Customer和持久化关联的 CustomerContact</p> 
	* @param c
	* @param map
	* @return 
	* @see com.erp.service.CstService#persistenceCustomer(com.erp.model.Customer, java.util.Map) 
	*/
	public boolean persistenceCustomer(Customer c,Map<String, List<CustomerContact>> map)
	{
		Integer userId=Constants.getCurrendUser().getUserId();
		if (c.getCustomerId()==null||c.getCustomerId()==0||"".equals(c.getCustomerId()))
		{
			c.setCreated(new Date());
			c.setLastmod(new Date());
			c.setCreater(userId);
			c.setModifiyer(userId);
			c.setStatus(Constants.PERSISTENCE_STATUS);
			publicDao.save(c);
			List<CustomerContact> addList = map.get("addList");
			if (addList!=null&&addList.size()!=0)
			{
				for (CustomerContact cus : addList)
				{
					cus.setCreated(new Date());
					cus.setLastmod(new Date());
					cus.setCreater(userId);
					cus.setModifyer(userId);
					cus.setCustomerId(c.getCustomerId());
					cus.setStatus(Constants.PERSISTENCE_STATUS);
					publicDao.save(cus);
				}
			}
		}else {
			c.setLastmod(new Date());
			c.setModifiyer(userId);
			publicDao.update(c);
			List<CustomerContact> addList = map.get("addList");
			if (addList!=null&&addList.size()!=0)
			{
				for (CustomerContact cus : addList)
				{
					cus.setCreated(new Date());
					cus.setLastmod(new Date());
					cus.setCreater(userId);
					cus.setModifyer(userId);
					cus.setCustomerId(c.getCustomerId());
					cus.setStatus(Constants.PERSISTENCE_STATUS);
					publicDao.save(cus);
				}
			}
			List<CustomerContact> updList = map.get("updList");
			if (updList!=null&&updList.size()!=0)
			{
				for (CustomerContact cus : updList)
				{
					cus.setLastmod(new Date());
					cus.setModifyer(userId);
					cus.setCustomerId(c.getCustomerId());
					publicDao.update(cus);
				}
			}
			List<CustomerContact> delList = map.get("delList");
			if (delList!=null&&delList.size()!=0)
			{
				for (CustomerContact cus : delList)
				{
					cus.setLastmod(new Date());
					cus.setModifyer(userId);
					cus.setCustomerId(c.getCustomerId());
					cus.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
					publicDao.deleteToUpdate(cus);
				}
			}
		}
		return true;
	}
	
	/* (非 Javadoc) 
	* <p>Title: delCustomer</p> 
	* <p>Description: 删除客户以及关联联系人</p> 
	* @param customerId
	* @return 
	* @see com.erp.service.CstService#delCustomer(java.lang.Integer) 
	*/
	public boolean delCustomer(Integer customerId)
	{
		Integer userId = Constants.getCurrendUser().getUserId();
		Customer c = (Customer)publicDao.get(Customer.class, customerId);
		c.setLastmod(new Date());
		c.setModifiyer(userId);
		c.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
		publicDao.deleteToUpdate(c);
		String hql="from CustomerContact t where t.status='A' and t.customerId="+customerId;
		List<CustomerContact> list = publicDao.find(hql);
		for (CustomerContact cus : list)
		{
			cus.setLastmod(new Date());
			cus.setModifyer(userId);
			cus.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
			publicDao.deleteToUpdate(cus);
		}
		return true;
	}
	/* (非 Javadoc) 
	* <p>Title: findSaleNameList</p> 
	* <p>Description: 查询销售代表</p> 
	* @return 
	* @see com.erp.service.CstService#findSaleNameList() 
	*/
	public List<TreeModel> findSaleNameList()
	{
		String hql="from Organization o where o.status='A'";
		String hql2="from Users u where u.status='A'";
		List<Users> list2=publicDao.find(hql2);
		List<Organization> tempList = publicDao.find(hql);
		List<TreeModel> list=new ArrayList<TreeModel>();
		for (Users u : list2)
		{
			if (u.getOrganizeId()!=null||!"".equals(u.getOrganizeId()))
			{
				TreeModel treeModel=new TreeModel();
				treeModel.setId("0"+u.getUserId());
				treeModel.setPid(u.getOrganizeId()+"");
				treeModel.setName(u.getName());
				treeModel.setState(Constants.TREE_STATUS_OPEN);
				treeModel.setPermissionId(u.getUserId());
				Attributes attributes=new Attributes();
				attributes.setStatus("u");
				treeModel.setAttributes(attributes);
				//treeModel.setIconCls();
				list.add(treeModel);
			}
		}
		for (Organization o : tempList)
		{
			TreeModel treeModel=new TreeModel();
			treeModel.setId(o.getOrganizationId()+Constants.NULL_STRING);
			treeModel.setPid(o.getPid()==null?null:o.getPid().toString());
			treeModel.setName(o.getFullName());
			treeModel.setState(Constants.TREE_STATUS_OPEN);
			treeModel.setIconCls(o.getIconCls());
			Attributes attributes=new Attributes();
			attributes.setStatus("o");
			treeModel.setAttributes(attributes);
			list.add(treeModel);
		}
		return list;
	}
}
