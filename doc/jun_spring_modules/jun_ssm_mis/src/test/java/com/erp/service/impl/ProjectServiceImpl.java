package com.erp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.model.Customer;
import com.erp.model.Project;
import com.erp.model.ProjectFollow;
import com.erp.service.IProjectService;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.biz.PageUtil;

@SuppressWarnings("unchecked")
@Service("projectService")
public class ProjectServiceImpl implements IProjectService {
	@SuppressWarnings("rawtypes")
	private PublicDao publicDao;

	@SuppressWarnings("rawtypes")
	@Autowired
	public void setPublicDao(PublicDao publicDao) {
		this.publicDao = publicDao;
	}

	public List<Customer> findCustomers() {
		String hql = "from Customer t where t.status='A'";
		return publicDao.find(hql);
	}

	public List<ProjectFollow> findProjectFollowsList(Integer projectId) {
		String hql = "from ProjectFollow t where t.status='A' and t.projectId=" + projectId;
		return publicDao.find(hql);
	}

	public List<Project> findProjectList(Map<String, Object> param, PageUtil pageUtil) {
		String hql = "from Project t where t.status='A'";
		hql += Constants.getSearchConditionsHQL("t", param);
		hql += Constants.getGradeSearchConditionsHQL("t", pageUtil);
		return publicDao.find(hql, param, pageUtil.getPage(), pageUtil.getRows());
	}

	public List<Project> findProjectListCombobox() {
		String hql = "from Project t where t.status='A'";
		return publicDao.find(hql);
	}

	public Long getCount(Map<String, Object> param, PageUtil pageUtil) {
		String hql = "select count(*) from Project t where t.status='A' ";
		hql += Constants.getSearchConditionsHQL("t", param);
		hql += Constants.getGradeSearchConditionsHQL("t", pageUtil);
		return publicDao.count(hql, param);
	}

	public boolean persistenceProject(Project p, Map<String, List<ProjectFollow>> map) {
		Integer userId = Constants.getCurrendUser().getUserId();
		if (p.getProjectId() == null || p.getProjectId() == 0 || "".equals(p.getProjectId())) {
			p.setCreated(new Date());
			p.setLastmod(new Date());
			p.setCreater(userId);
			p.setModifyer(userId);
			p.setStatus(Constants.PERSISTENCE_STATUS);
			publicDao.save(p);
			List<ProjectFollow> addList = map.get("addList");
			if (addList != null && addList.size() != 0) {
				for (ProjectFollow cus : addList) {
					cus.setCreated(new Date());
					cus.setLastmod(new Date());
					cus.setCreater(userId);
					cus.setModifyer(userId);
					cus.setProjectId(p.getProjectId());
					cus.setStatus(Constants.PERSISTENCE_STATUS);
					publicDao.save(cus);
				}
			}
		} else {
			p.setLastmod(new Date());
			p.setModifyer(userId);
			publicDao.update(p);

			List<ProjectFollow> addList = map.get("addList");
			if (addList != null && addList.size() != 0) {
				for (ProjectFollow cus : addList) {
					cus.setCreated(new Date());
					cus.setLastmod(new Date());
					cus.setCreater(userId);
					cus.setModifyer(userId);
					cus.setProjectId(p.getProjectId());
					cus.setStatus(Constants.PERSISTENCE_STATUS);
					publicDao.save(cus);
				}
			}
			List<ProjectFollow> updList = map.get("updList");
			if (updList != null && updList.size() != 0) {
				for (ProjectFollow cus : updList) {
					cus.setLastmod(new Date());
					cus.setModifyer(userId);
					cus.setProjectId(p.getProjectId());
					publicDao.update(cus);
				}
			}
			List<ProjectFollow> delList = map.get("delList");
			if (delList != null && delList.size() != 0) {
				for (ProjectFollow cus : delList) {
					cus.setLastmod(new Date());
					cus.setModifyer(userId);
					cus.setProjectId(p.getProjectId());
					cus.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
					publicDao.deleteToUpdate(cus);
				}
			}
		}
		return true;
	}

	public boolean delProject(Integer projectId) {
		Integer userId = Constants.getCurrendUser().getUserId();
		Project i = (Project) publicDao.get(Project.class, projectId);
		i.setLastmod(new Date());
		i.setModifyer(userId);
		i.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
		publicDao.deleteToUpdate(i);
		String hql = "from ProjectFollow t where t.status='A' and t.projectId=" + projectId;
		List<ProjectFollow> list = publicDao.find(hql);
		for (ProjectFollow pf : list) {
			pf.setLastmod(new Date());
			pf.setModifyer(userId);
			pf.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
			publicDao.deleteToUpdate(pf);
		}
		return true;
	}
}
