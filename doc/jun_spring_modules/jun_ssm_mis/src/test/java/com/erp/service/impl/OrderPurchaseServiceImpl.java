package com.erp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.model.OrderPurchase;
import com.erp.model.OrderPurchaseLine;
import com.erp.service.IOrderPurchaseService;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.biz.PageUtil;

@SuppressWarnings("unchecked")
@Service("orderPurchaseService")
public class OrderPurchaseServiceImpl implements IOrderPurchaseService {
	@SuppressWarnings("rawtypes")
	private PublicDao publicDao;

	@SuppressWarnings("rawtypes")
	@Autowired
	public void setPublicDao(PublicDao publicDao) {
		this.publicDao = publicDao;
	}

	public List<OrderPurchaseLine> findPurchaseOrderLineList(Integer orderPurchaseId) {
		if (orderPurchaseId == null || "".equals(orderPurchaseId)) {
			return new ArrayList<OrderPurchaseLine>();
		} else {
			String hql = "from OrderPurchaseLine t where t.status='A' and t.orderPurchaseId=" + orderPurchaseId;
			return publicDao.find(hql);
		}

	}

	public List<OrderPurchase> findPurchaseOrderList(Map<String, Object> param, PageUtil pageUtil) {
		String hql = "from OrderPurchase t where t.status='A' ";
		hql += Constants.getSearchConditionsHQL("t", param);
		hql += Constants.getGradeSearchConditionsHQL("t", pageUtil);

		return publicDao.find(hql, param, pageUtil.getPage(), pageUtil.getRows());
	}

	public Long getCount(Map<String, Object> param, PageUtil pageUtil) {
		String hql = "select count(*) from OrderPurchase t where t.status='A' ";
		hql += Constants.getSearchConditionsHQL("t", param);
		hql += Constants.getGradeSearchConditionsHQL("t", pageUtil);
		return publicDao.count(hql, param);
	}

	/*
	 * (非 Javadoc) <p>Title: persistenceOrderPurchase</p> <p>Description: 持久化采购单</p>
	 * 
	 * @param c
	 * 
	 * @param map
	 * 
	 * @return
	 * 
	 * @see com.erp.service.OrderPurchaseService#persistenceOrderPurchase(com.erp.model.OrderPurchase, java.util.Map)
	 */
	public boolean persistenceOrderPurchase(OrderPurchase c, Map<String, List<OrderPurchaseLine>> map) {
		Integer userId = Constants.getCurrendUser().getUserId();
		if (c.getOrderPurchaseId() == null || c.getOrderPurchaseId() == 0 || "".equals(c.getOrderPurchaseId())) {
			c.setCreated(new Date());
			c.setLastmod(new Date());
			c.setCreater(userId);
			c.setModifyer(userId);
			c.setStatus(Constants.PERSISTENCE_STATUS);
			publicDao.save(c);
			List<OrderPurchaseLine> addList = map.get("addList");
			if (addList != null && addList.size() != 0) {
				for (OrderPurchaseLine cus : addList) {
					cus.setCreated(new Date());
					cus.setLastmod(new Date());
					cus.setCreater(userId);
					cus.setModifyer(userId);
					cus.setOrderPurchaseId(c.getOrderPurchaseId());
					cus.setStatus(Constants.PERSISTENCE_STATUS);
					publicDao.save(cus);
				}
			}
		} else {
			c.setLastmod(new Date());
			c.setModifyer(userId);
			publicDao.update(c);
			List<OrderPurchaseLine> addList = map.get("addList");
			if (addList != null && addList.size() != 0) {
				for (OrderPurchaseLine cus : addList) {
					cus.setCreated(new Date());
					cus.setLastmod(new Date());
					cus.setCreater(userId);
					cus.setModifyer(userId);
					cus.setOrderPurchaseId(c.getOrderPurchaseId());
					cus.setStatus(Constants.PERSISTENCE_STATUS);
					publicDao.save(cus);
				}
			}
			List<OrderPurchaseLine> updList = map.get("updList");
			if (updList != null && updList.size() != 0) {
				for (OrderPurchaseLine cus : updList) {
					cus.setLastmod(new Date());
					cus.setModifyer(userId);
					cus.setOrderPurchaseId(c.getOrderPurchaseId());
					publicDao.update(cus);
				}
			}
			List<OrderPurchaseLine> delList = map.get("delList");
			if (delList != null && delList.size() != 0) {
				for (OrderPurchaseLine cus : delList) {
					cus.setLastmod(new Date());
					cus.setModifyer(userId);
					cus.setOrderPurchaseId(c.getOrderPurchaseId());
					cus.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
					publicDao.deleteToUpdate(cus);
				}
			}
		}
		return true;
	}

	/*
	 * (非 Javadoc) <p>Title: delOrderPurchase</p> <p>Description:删除采购单 </p>
	 * 
	 * @param orderPurchaseId
	 * 
	 * @return
	 * 
	 * @see com.erp.service.OrderPurchaseService#delOrderPurchase(java.lang.Integer)
	 */
	public boolean delOrderPurchase(Integer orderPurchaseId) {
		Integer userId = Constants.getCurrendUser().getUserId();
		OrderPurchase c = (OrderPurchase) publicDao.get(OrderPurchase.class, orderPurchaseId);
		c.setLastmod(new Date());
		c.setModifyer(userId);
		c.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
		publicDao.deleteToUpdate(c);
		String hql = "from OrderPurchaseLine t where t.status='A' and t.orderPurchaseId=" + orderPurchaseId;
		List<OrderPurchaseLine> list = publicDao.find(hql);
		for (OrderPurchaseLine cus : list) {
			cus.setLastmod(new Date());
			cus.setModifyer(userId);
			cus.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
			publicDao.deleteToUpdate(cus);
		}
		return true;
	}
}
