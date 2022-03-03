package com.erp.jee.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.erp.dao.IBaseDao;
import com.erp.jee.model.Tbug;
import com.erp.jee.pageModel.Bug;
import com.erp.jee.pageModel.DataGrid;
import com.erp.jee.service.BugServiceI;
import com.erp.service.impl.BaseServiceImpl;

/**
 * Bug Service
 * 
 * @author Wujun
 * 
 */
@Service("bugService2")
public class BugServiceImpl extends BaseServiceImpl implements BugServiceI {

	private IBaseDao<Tbug> bugDao;

	public IBaseDao<Tbug> getBugDao() {
		return bugDao;
	}

	@Autowired
	public void setBugDao(IBaseDao<Tbug> bugDao) {
		this.bugDao = bugDao;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Bug bug) {
		DataGrid j = new DataGrid();
		j.setRows(getBugsFromTbugs(find(bug)));
		j.setTotal(total(bug));
		return j;
	}

	private List<Bug> getBugsFromTbugs(List<Tbug> tbugs) {
		List<Bug> bugs = new ArrayList<Bug>();
		if (tbugs != null && tbugs.size() > 0) {
			for (Tbug tb : tbugs) {
				Bug b = new Bug();
				BeanUtils.copyProperties(tb, b);
				bugs.add(b);
			}
		}
		return bugs;
	}

	private List<Tbug> find(Bug bug) {
		String hql = "select new Tbug(t.cid,t.cname,t.ccreatedatetime) from Tbug t where 1=1 ";

		List<Object> values = new ArrayList<Object>();
		hql = addWhere(bug, hql, values);

		if (bug.getSort() != null && bug.getOrder() != null) {
			hql += " order by " + bug.getSort() + " " + bug.getOrder();
		}
		return bugDao.find(hql, bug.getPage(), bug.getRows(), values);
	}

	private Long total(Bug bug) {
		String hql = "select count(*) from Tbug t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(bug, hql, values);
		return bugDao.count(hql, values);
	}

	private String addWhere(Bug bug, String hql, List<Object> values) {
		return hql;
	}

	public void add(Bug bug) {
		if (bug.getCid() == null || bug.getCid().trim().equals("")) {
			bug.setCid(UUID.randomUUID().toString());
		}
		if (bug.getCcreatedatetime() == null) {
			bug.setCcreatedatetime(new Date());
		}
		Tbug t = new Tbug();
		BeanUtils.copyProperties(bug, t);
		bugDao.save(t);
	}

	public void update(Bug bug) {
		Tbug t = bugDao.get(Tbug.class, bug.getCid());
		if (t != null) {
			if (bug.getCcreatedatetime() == null) {
				bug.setCcreatedatetime(new Date());
			}
			BeanUtils.copyProperties(bug, t, new String[] { "cid" });
		}
	}

	public void delete(String ids) {
		if (ids != null) {
			for (String id : ids.split(",")) {
				Tbug t = bugDao.get(Tbug.class, id);
				if (t != null) {
					bugDao.delete(t);
				}
			}
		}
	}

	public Tbug get(Bug bug) {
		return bugDao.get(Tbug.class, bug.getCid());
	}

}
