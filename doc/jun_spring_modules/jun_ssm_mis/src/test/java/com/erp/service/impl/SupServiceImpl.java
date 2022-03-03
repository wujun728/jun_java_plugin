package com.erp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.model.Suplier;
import com.erp.model.SuplierContact;
import com.erp.service.ISupService;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.biz.PageUtil;

@SuppressWarnings("unchecked")
@Service("supService")
public class SupServiceImpl implements ISupService {
	@SuppressWarnings("rawtypes")
	private PublicDao publicDao;

	@SuppressWarnings("rawtypes")
	@Autowired
	public void setPublicDao(PublicDao publicDao) {
		this.publicDao = publicDao;
	}

	/*
	 * (非 Javadoc) <p>Title: findSuplierContactList</p> <p>Description:查询供应商联系人 </p>
	 * 
	 * @param SuplierId
	 * 
	 * @return
	 * 
	 * @see com.erp.service.SupService#findSuplierContactList(java.lang.Integer)
	 */
	public List<SuplierContact> findSuplierContactList(Integer SuplierId) {
		if (null == SuplierId || "".equals(SuplierId)) {
			return new ArrayList<SuplierContact>();
		} else {
			String hql = "from SuplierContact t where t.status='A' and t.suplierId=" + SuplierId;
			return publicDao.find(hql);
		}
	}

	/*
	 * (非 Javadoc) <p>Title: findCustomerList</p> <p>Description: 查询所有供应商</p>
	 * 
	 * @param param
	 * 
	 * @param pageUtil
	 * 
	 * @return
	 * 
	 * @see com.erp.service.CstService#findCustomerList(java.util.Map, com.erp.util.PageUtil)
	 */

	public List<Suplier> findSuplierList(Map<String, Object> param, PageUtil pageUtil) {
		String hql = "from Suplier t where t.status='A'";
		hql += Constants.getSearchConditionsHQL("t", param);
		hql += Constants.getGradeSearchConditionsHQL("t", pageUtil);
		return publicDao.find(hql, param, pageUtil.getPage(), pageUtil.getRows());
	}

	public List<Suplier> findSuplierListNoPage(Map<String, Object> param, PageUtil pageUtil) {
		String hql = "from Suplier t where t.status='A'";
		hql += Constants.getSearchConditionsHQL("t", param);
		hql += Constants.getGradeSearchConditionsHQL("t", pageUtil);
		return publicDao.find(hql, param);
	}

	/*
	 * (非 Javadoc) <p>Title: getCount</p> <p>Description:查询所有总数 </p>
	 * 
	 * @param param
	 * 
	 * @param pageUtil
	 * 
	 * @return
	 * 
	 * @see com.erp.service.SupService#getCount(java.util.Map, com.erp.util.PageUtil)
	 */
	public Long getCount(Map<String, Object> param, PageUtil pageUtil) {
		String hql = "select count(*) from Suplier t where t.status='A' ";
		hql += Constants.getSearchConditionsHQL("t", param);
		hql += Constants.getGradeSearchConditionsHQL("t", pageUtil);
		return publicDao.count(hql, param);
	}

	/*
	 * (非 Javadoc) <p>Title: persistenceSuplier</p> <p>Description: 持久化Suplier和持久化关联的 SuplierContact</p>
	 * 
	 * @param c
	 * 
	 * @param map
	 * 
	 * @return
	 * 
	 * @see com.erp.service.SupService#persistenceSuplier(com.erp.model.Suplier, java.util.Map)
	 */
	public boolean persistenceSuplier(Suplier c, Map<String, List<SuplierContact>> map) {
		Integer userId = Constants.getCurrendUser().getUserId();
		if (c.getSuplierId() == null ||c.getSuplierId() == 0 || "".equals(c.getSuplierId())) {
			c.setCreated(new Date());
			c.setLastmod(new Date());
			c.setCreater(userId);
			c.setModifiyer(userId);
			c.setStatus(Constants.PERSISTENCE_STATUS);
			publicDao.save(c);
			List<SuplierContact> addList = map.get("addList");
			if (addList != null && addList.size() != 0) {
				for (SuplierContact cus : addList) {
					cus.setCreated(new Date());
					cus.setLastmod(new Date());
					cus.setCreater(userId);
					cus.setModifyer(userId);
					cus.setSuplierId(c.getSuplierId());
					cus.setStatus(Constants.PERSISTENCE_STATUS);
					publicDao.save(cus);
				}
			}
		} else {
			c.setLastmod(new Date());
			c.setModifiyer(userId);
			publicDao.update(c);
			List<SuplierContact> addList = map.get("addList");
			if (addList != null && addList.size() != 0) {
				for (SuplierContact cus : addList) {
					cus.setCreated(new Date());
					cus.setLastmod(new Date());
					cus.setCreater(userId);
					cus.setModifyer(userId);
					cus.setSuplierId(c.getSuplierId());
					cus.setStatus(Constants.PERSISTENCE_STATUS);
					publicDao.save(cus);
				}
			}
			List<SuplierContact> updList = map.get("updList");
			if (updList != null && updList.size() != 0) {
				for (SuplierContact cus : updList) {
					cus.setLastmod(new Date());
					cus.setModifyer(userId);
					cus.setSuplierId(c.getSuplierId());
					publicDao.update(cus);
				}
			}
			List<SuplierContact> delList = map.get("delList");
			if (delList != null && delList.size() != 0) {
				for (SuplierContact cus : delList) {
					cus.setLastmod(new Date());
					cus.setModifyer(userId);
					cus.setSuplierId(c.getSuplierId());
					cus.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
					publicDao.deleteToUpdate(cus);
				}
			}
		}
		return true;
	}

	/*
	 * (非 Javadoc) <p>Title: delSuplier</p> <p>Description: 删除供应商以及关联联系人</p>
	 * 
	 * @param customerId
	 * 
	 * @return
	 * 
	 * @see com.erp.service.supService#delSuplier(java.lang.Integer)
	 */
	public boolean delSuplier(Integer SuplierId) {
		Integer userId = Constants.getCurrendUser().getUserId();
		Suplier c = (Suplier) publicDao.get(Suplier.class, SuplierId);
		c.setLastmod(new Date());
		c.setModifiyer(userId);
		c.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
		publicDao.deleteToUpdate(c);
		String hql = "from SuplierContact t where t.status='A' and t.suplierId=" + SuplierId;
		List<SuplierContact> list = publicDao.find(hql);
		for (SuplierContact cus : list) {
			cus.setLastmod(new Date());
			cus.setModifyer(userId);
			cus.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
			publicDao.deleteToUpdate(cus);
		}
		return true;
	}
}
