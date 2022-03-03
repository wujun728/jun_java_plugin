package com.erp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.model.OrderSale;
import com.erp.model.OrderSaleLine;
import com.erp.service.IOrderSaleService;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.biz.PageUtil;
@SuppressWarnings("unchecked")
@Service("orderSaleService")
public class OrderSaleServiceImpl implements IOrderSaleService
{
	@SuppressWarnings("rawtypes")
	private PublicDao publicDao;
	@SuppressWarnings("rawtypes")
	@Autowired
	public void setPublicDao(PublicDao publicDao )
	{
		this.publicDao = publicDao;
	}
	
	public List<OrderSaleLine> findOrderSaleLineList(Integer orderSaleId) 
	{
		if (orderSaleId==null||"".equals(orderSaleId))
		{
			return new ArrayList<OrderSaleLine>();
		}else {
			String hql="from OrderSaleLine t where t.status='A' and t.orderSaleId="+orderSaleId;
			return  publicDao.find(hql);
		}
		
	}
	
	public List<OrderSale> findOrderSaleList(Map<String, Object> param,PageUtil pageUtil) 
	{
		String  hql="from OrderSale t where t.status='A' ";
		hql+=Constants.getSearchConditionsHQL("t", param);
		hql+=Constants.getGradeSearchConditionsHQL("t", pageUtil);
		
		return publicDao.find(hql, param, pageUtil.getPage(), pageUtil.getRows());
	}
	
	public Long getCount(Map<String, Object> param,PageUtil pageUtil)
	{
		String hql="select count(*) from OrderSale t where t.status='A' ";
		hql+=Constants.getSearchConditionsHQL("t", param);
		hql+=Constants.getGradeSearchConditionsHQL("t", pageUtil);
		return publicDao.count(hql, param);
	}
	
	public boolean persistenceOrderSale(OrderSale c,Map<String, List<OrderSaleLine>> map)
	{
		Integer userId=Constants.getCurrendUser().getUserId();
		if (c.getOrderSaleId()==null||c.getOrderSaleId()==0||"".equals(c.getOrderSaleId()))
		{
			c.setCreated(new Date());
			c.setLastmod(new Date());
			c.setCreater(userId);
			c.setModifyer(userId);
			c.setStatus(Constants.PERSISTENCE_STATUS);
			publicDao.save(c);
			List<OrderSaleLine> addList = map.get("addList");
			if (addList!=null&&addList.size()!=0)
			{
				for (OrderSaleLine cus : addList)
				{
					cus.setCreated(new Date());
					cus.setLastmod(new Date());
					cus.setCreater(userId);
					cus.setModifyer(userId);
					cus.setOrderSaleId(c.getOrderSaleId());
					cus.setStatus(Constants.PERSISTENCE_STATUS);
					publicDao.save(cus);
				}
			}
		}else {
			c.setLastmod(new Date());
			c.setModifyer(userId);
			publicDao.update(c);
			List<OrderSaleLine> addList = map.get("addList");
			if (addList!=null&&addList.size()!=0)
			{
				for (OrderSaleLine cus : addList)
				{
					cus.setCreated(new Date());
					cus.setLastmod(new Date());
					cus.setCreater(userId);
					cus.setModifyer(userId);
					cus.setOrderSaleId(c.getOrderSaleId());
					cus.setStatus(Constants.PERSISTENCE_STATUS);
					publicDao.save(cus);
				}
			}
			List<OrderSaleLine> updList = map.get("updList");
			if (updList!=null&&updList.size()!=0)
			{
				for (OrderSaleLine cus : updList)
				{
					cus.setLastmod(new Date());
					cus.setModifyer(userId);
					cus.setOrderSaleId(c.getOrderSaleId());
					publicDao.update(cus);
				}
			}
			List<OrderSaleLine> delList = map.get("delList");
			if (delList!=null&&delList.size()!=0)
			{
				for (OrderSaleLine cus : delList)
				{
					cus.setLastmod(new Date());
					cus.setModifyer(userId);
					cus.setOrderSaleId(c.getOrderSaleId());
					cus.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
					publicDao.deleteToUpdate(cus);
				}
			}
		}
		return true;
	}

	public boolean delOrderSale(Integer orderSaleId)
	{
		Integer userId = Constants.getCurrendUser().getUserId();
		OrderSale c = (OrderSale)publicDao.get(OrderSale.class, orderSaleId);
		c.setLastmod(new Date());
		c.setModifyer(userId);
		c.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
		publicDao.deleteToUpdate(c);
		String hql="from OrderSaleLine t where t.status='A' and t.orderSaleId="+orderSaleId;
		List<OrderSaleLine> list = publicDao.find(hql);
		for (OrderSaleLine cus : list)
		{
			cus.setLastmod(new Date());
			cus.setModifyer(userId);
			cus.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
			publicDao.deleteToUpdate(cus);
		}
		return true;
	}
}
