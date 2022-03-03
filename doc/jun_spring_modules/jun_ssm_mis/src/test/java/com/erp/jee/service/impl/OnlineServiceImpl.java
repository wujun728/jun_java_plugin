package com.erp.jee.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.erp.dao.IBaseDao;
import com.erp.jee.model.Tonline;
import com.erp.jee.pageModel.DataGrid;
import com.erp.jee.pageModel.Online;
import com.erp.jee.service.OnlineServiceI;
import com.erp.service.impl.BaseServiceImpl;

/**
 * 在线用户Service
 * 
 * @author Wujun
 * 
 */
@Service("onlineService")
public class OnlineServiceImpl extends BaseServiceImpl implements OnlineServiceI {

	private static final Logger logger = Logger.getLogger(OnlineServiceImpl.class);

	private IBaseDao<Tonline> onlineDao;

	public IBaseDao<Tonline> getOnlineDao() {
		return onlineDao;
	}

	@Autowired
	public void setOnlineDao(IBaseDao<Tonline> onlineDao) {
		this.onlineDao = onlineDao;
	}

	/**
	 * 更新或插入用户在线列表
	 */
	public void updateOnline(Tonline online) {
		Tonline t = onlineDao.get("from Tonline t where t.cname=? and t.cip=?", online.getCname(), online.getCip());
		if (t != null) {
			t.setCdatetime(new Date());
		} else {
			online.setCid(UUID.randomUUID().toString());
			onlineDao.save(online);
		}
	}

	/**
	 * 删除用户在线列表
	 */
	public void deleteOnline(String loginName, String ip) {
		onlineDao.executeHql("delete Tonline t where t.cname=? and t.cip=?", loginName, ip);
	}

	/**
	 * 获得用户在线列表datagrid
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public DataGrid datagrid(Online online) {
		DataGrid j = new DataGrid();
		j.setRows(find(online));
		j.setTotal(total(online));
		return j;
	}

	private List<Tonline> find(Online online) {
		String hql = "from Tonline t where 1=1 ";

		List<Object> values = new ArrayList<Object>();
		hql = addWhere(online, hql, values);

		if (online.getSort() != null && online.getOrder() != null) {
			hql += " order by " + online.getSort() + " " + online.getOrder();
		}
		return onlineDao.find(hql, online.getPage(), online.getRows(), values);
	}

	private Long total(Online online) {
		String hql = "select count(*) from Tonline t where 1=1 ";
		List<Object> values = new ArrayList<Object>();
		hql = addWhere(online, hql, values);
		return onlineDao.count(hql, values);
	}

	private String addWhere(Online online, String hql, List<Object> values) {
		return hql;
	}

}
