package com.mis.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.erp.dao.IBaseDao;
import com.erp.model2.Syportal;
import com.erp.service.impl.BaseServiceImpl;
import com.mis.httpModel.EasyuiDataGrid;
import com.mis.httpModel.EasyuiDataGridJson;
import com.mis.httpModel.Portal;
import com.mis.service.PortalServiceI;

/**
 * portal Service实现
 * 
 * @author Wujun
 * 
 */
@Service("portalService")
public class PortalServiceImpl extends BaseServiceImpl implements PortalServiceI {

	private IBaseDao<Syportal> portalDao;

	public IBaseDao<Syportal> getPortalDao() {
		return portalDao;
	}

	@Autowired
	public void setPortalDao(IBaseDao<Syportal> portalDao) {
		this.portalDao = portalDao;
	}

	@Transactional(readOnly = true)
	public EasyuiDataGridJson datagrid(EasyuiDataGrid dg, Portal portal) {
		EasyuiDataGridJson j = new EasyuiDataGridJson();
		String hql = " from Syportal t where 1=1 ";
		String totalHql = " select count(*) " + hql;
		j.setTotal(portalDao.count(totalHql));// 设置总记录数
		if (dg.getSort() != null) {// 设置排序
			hql += " order by " + dg.getSort() + " " + dg.getOrder();
		}
		List<Syportal> syportals = portalDao.find(hql);// 查询
		List<Portal> portals = new ArrayList<Portal>();
		if (syportals != null && syportals.size() > 0) {
			for (Syportal syportal : syportals) {
				Portal p = new Portal();
				BeanUtils.copyProperties(syportal, p);
				portals.add(p);
			}
		}
		j.setRows(portals);// 设置返回的行
		return j;
	}

	@CacheEvict(value = "syproPortalCache", allEntries = true)
	public void del(String ids) {
		for (String id : ids.split(",")) {
			Syportal syportal = portalDao.get(Syportal.class, id);
			if (syportal != null) {
				portalDao.delete(syportal);
			}
		}
	}

	@CacheEvict(value = "syproPortalCache", allEntries = true)
	public void edit(Portal portal) {
		Syportal syportal = portalDao.get(Syportal.class, portal.getId());
		if (syportal != null) {
			BeanUtils.copyProperties(portal, syportal);
		}
	}

	@CacheEvict(value = "syproPortalCache", allEntries = true)
	public void add(Portal portal) {
		Syportal syportal = new Syportal();
		BeanUtils.copyProperties(portal, syportal);
		portalDao.save(syportal);
	}

}
