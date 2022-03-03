package com.erp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.model.Organization;
import com.erp.service.IOrganizationService;
import com.erp.viewModel.TreeModel;
import com.jun.plugin.utils.Constants;

@Service("organizationService")
@SuppressWarnings("unchecked")
public class OrganizationServiceImpl implements IOrganizationService {
	@SuppressWarnings("rawtypes")
	private PublicDao publicDao;

	@SuppressWarnings("rawtypes")
	@Autowired
	public void setPublicDao(PublicDao publicDao) {
		this.publicDao = publicDao;
	}

	public List<TreeModel> findOrganizationList() {
		String hql = "from Organization o where o.status='A'";
		List<Organization> tempList = publicDao.find(hql);
		List<TreeModel> list = new ArrayList<TreeModel>();
		for (Organization o : tempList) {
			TreeModel treeModel = new TreeModel();
			treeModel.setId(o.getOrganizationId() + Constants.NULL_STRING);
			treeModel.setPid(o.getPid() == null ? null : o.getPid().toString());
			treeModel.setName(o.getFullName());
			treeModel.setState(Constants.TREE_STATUS_OPEN);
			treeModel.setIconCls(o.getIconCls());
			list.add(treeModel);
		}
		return list;
	}

	public List<Organization> findOrganizationList(Integer id) {
		String hql = "from Organization o where o.status='A' ";
		if (null == id || "".equals(id)) {
			hql += " and o.pid is null";
		} else {
			hql += " and o.pid=" + id;
		}
		return publicDao.find(hql);
	}

	public boolean persistenceOrganization(Organization o) {

		Integer userId = Constants.getCurrendUser().getUserId();
		if (null == o.getOrganizationId() || 0 == o.getOrganizationId() || "".equals(o.getOrganizationId())) {
			o.setCreated(new Date());
			o.setLastmod(new Date());
			o.setCreater(userId);
			o.setModifyer(userId);
			o.setStatus(Constants.PERSISTENCE_STATUS);
			publicDao.save(o);
		} else {
			o.setLastmod(new Date());
			o.setModifyer(userId);
			publicDao.update(o);
		}
		return true;
	}

	@SuppressWarnings("rawtypes")
	public boolean delOrganization(Integer id) {
		String hql = "from Organization o where o.status='A' and o.pid=" + id;
		List<Organization> list = publicDao.find(hql);
		if (list.size() != 0) {
			return false;
		} else {
			String hql2 = "from Users t where t.organizeId=" + id;
			List list2 = publicDao.find(hql2);
			if (list2.size() != 0) {
				return false;
			} else {
				Organization o = (Organization) publicDao.get(Organization.class, id);
				o.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
				o.setLastmod(new Date());
				o.setModifyer(Constants.getCurrendUser().getUserId());
				publicDao.deleteToUpdate(o);
			}
			return true;
		}
	}
}
