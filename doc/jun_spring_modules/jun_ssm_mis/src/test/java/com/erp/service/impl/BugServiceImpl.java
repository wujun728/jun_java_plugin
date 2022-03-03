package com.erp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.model.Bug;
import com.erp.service.IBugService;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.biz.PageUtil;

@Service("bugService")
public class BugServiceImpl implements IBugService {
	private PublicDao<Bug> publicDao;

	@Autowired
	public void setPublicDao(PublicDao<Bug> publicDao) {
		this.publicDao = publicDao;
	}

	/*
	 * (非 Javadoc) <p>Title: findBugList</p> <p>Description: </p>
	 * 
	 * @param param
	 * 
	 * @param pageUtil
	 * 
	 * @return
	 * 
	 * @see com.erp.service.BugService#findBugList(java.util.Map, com.erp.util.PageUtil)
	 */
	public List<Bug> findBugList(Map<String, Object> param, PageUtil pageUtil) {
		String hql = "from Bug t where t.status='A' ";
		hql += Constants.getSearchConditionsHQL("t", param);
		hql += Constants.getGradeSearchConditionsHQL("t", pageUtil);
		hql += " order by t.bugId desc";
		return publicDao.find(hql, param, pageUtil.getPage(), pageUtil.getRows());
	}

	/*
	 * (非 Javadoc) <p>Title: getCount</p> <p>Description: </p>
	 * 
	 * @param param
	 * 
	 * @param pageUtil
	 * 
	 * @return
	 * 
	 * @see com.erp.service.BugService#getCount(java.util.Map, com.erp.util.PageUtil)
	 */
	public Long getCount(Map<String, Object> param, PageUtil pageUtil) {
		String hql = "select count(*) from Bug t where t.status='A' ";
		hql += Constants.getSearchConditionsHQL("t", param);
		hql += Constants.getGradeSearchConditionsHQL("t", pageUtil);
		hql += " order by t.bugId desc";
		return publicDao.count(hql, param);
	}

	/*
	 * (非 Javadoc) <p>Title: addBug</p> <p>Description: </p>
	 * 
	 * @param bug
	 * 
	 * @return
	 * 
	 * @see com.erp.service.BugService#addBug(com.erp.model.Bug)
	 */
	public boolean addBug(Bug bug) {
		Integer userId = Constants.getCurrendUser().getUserId();
		if (bug.getBugId() == null || "".equals(bug.getBugId()) || 0 == bug.getBugId()) {
			bug.setLastmod(new Date());
			bug.setCreated(new Date());
			bug.setModifyer(userId);
			bug.setStatus(Constants.PERSISTENCE_STATUS);
			bug.setCreater(userId);
			publicDao.save(bug);
		} else {
			bug.setLastmod(new Date());
			bug.setModifyer(userId);
			bug.setCreater(userId);
			publicDao.update(bug);
		}
		return true;
	}

	public boolean delBug(Integer bugId) {
		Bug b = publicDao.get(Bug.class, bugId);
		b.setLastmod(new Date());
		b.setModifyer(Constants.getCurrendUser().getUserId());
		b.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
		publicDao.deleteToUpdate(b);
		return true;
	}

}
