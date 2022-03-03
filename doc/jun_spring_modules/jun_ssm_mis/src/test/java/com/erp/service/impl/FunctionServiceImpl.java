/**  
 * @Project: erp
 * @Title: FunctionServiceImpl.java
 * @Package com.erp.serviceImpl
 * @Description: TODO:
 * @author Wujun
 * @date 2013-5-9 下午1:48:01
 * @Copyright: 2013 www.example.com Inc. All rights reserved.
 * @version V1.0  
 */
package com.erp.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.model.Permission;
import com.erp.service.IFunctionService;
import com.erp.viewModel.TreeGridModel;
import com.erp.viewModel.TreeModel;
import com.jun.plugin.utils.Constants;

/**
 * 类功能说明 TODO:程式管理service实现 类修改者 修改日期 修改说明
 * <p>
 * Title: FunctionServiceImpl.java
 * </p>
 * <p>
 * Description:福产流通科技
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company:福产流通科技有限公司
 * </p>
 * 
 * @author Wujun
 * @date 2013-5-9 下午1:48:01
 * @version V1.0
 */
@Service("functionService")
public class FunctionServiceImpl implements IFunctionService {
	private static final Logger logger = Logger.getLogger(FunctionServiceImpl.class);
	private PublicDao<Permission> permissionPublicDao;

	@Autowired
	public void setPermissionPublicDao(PublicDao<Permission> permissionPublicDao) {
		this.permissionPublicDao = permissionPublicDao;
	}

	/*
	 * (非 Javadoc) <p>Title: persistenceFunction</p> <p>Description:持久化程式 </p>
	 * 
	 * @param list
	 * 
	 * @return
	 * 
	 * @see com.erp.service.FunctionService#persistenceFunction(java.util.List)
	 */
	public boolean persistenceFunction(List<Permission> list) {
		logger.debug("f");
		Integer userId = Constants.getCurrendUser().getUserId();
		for (Permission function : list) {
			function.setLastmod(new Date());
			function.setModifyer(userId);
			if (Constants.TREE_GRID_ADD_STATUS.equals(function.getStatus())) {
				function.setPermissionId(null);
				function.setCreated(new Date());
				function.setLastmod(new Date());
				function.setModifyer(userId);
				function.setCreater(userId);
				function.setStatus(Constants.PERSISTENCE_STATUS);
			}

			permissionPublicDao.saveOrUpdate(function);
		}
		return true;
	}

	/*
	 * (非 Javadoc) <p>Title: persistenceFunction</p> <p>Description: 持久化程式弹窗模式</p>
	 * 
	 * @param permission
	 * 
	 * @return
	 * 
	 * @see com.erp.service.FunctionService#persistenceFunction(com.erp.model.Permission)
	 */
	public boolean persistenceFunction(Permission permission) {
		Integer userId = Constants.getCurrendUser().getUserId();
		if (null == permission.getPermissionId() || 0 == permission.getPermissionId() || "".equals(permission.getPermissionId())) {
			permission.setCreated(new Date());
			permission.setLastmod(new Date());
			permission.setCreater(userId);
			permission.setModifyer(userId);
			permission.setStatus(Constants.PERSISTENCE_STATUS);
			if (Constants.IS_FUNCTION.equals(permission.getType())) {
				permission.setState(Constants.TREE_STATUS_CLOSED);
			} else {
				permission.setState(Constants.TREE_STATUS_OPEN);
			}
			if (0==permission.getPid()) {
				permission.setPid(null);
			}
			permissionPublicDao.save(permission);
		} else {
			if (Constants.IS_FUNCTION.equals(permission.getType())) {
				permission.setState(Constants.TREE_STATUS_CLOSED);
			} else {
				permission.setState(Constants.TREE_STATUS_OPEN);
			}
			permission.setLastmod(new Date());
			permission.setModifyer(userId);
			if (0==permission.getPid()) {
				permission.setPid(null);
			}
			permissionPublicDao.update(permission);
		}
		return true;
	}

	/*
	 * (非 Javadoc) <p>Title: findAllFunctionList</p> <p>Description:按节点查询所有程式 </p>
	 * 
	 * @param pid
	 * 
	 * @return
	 * 
	 * @see com.erp.service.FunctionService#findAllFunctionList(java.lang.Integer)
	 */
	public List<TreeGridModel> findAllFunctionList(Integer pid) {
		String hql = "from Permission t where t.status='A' ";
		if (pid == null || "".equals(pid)) {
			hql += " and t.pid is null";
		} else {
			hql += " and t.pid=" + pid;
		}
		List<Permission> list = permissionPublicDao.find(hql);
		List<TreeGridModel> tempList = new ArrayList<TreeGridModel>();
		for (Permission function : list) {
			TreeGridModel treeGridModel = new TreeGridModel();
			try {
				BeanUtils.copyProperties(treeGridModel, function);
				if (pid == null || "".equals(pid)) {
					treeGridModel.setPid(null);
				}
				tempList.add(treeGridModel);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return tempList;
	}

	/*
	 * (非 Javadoc) <p>Title: findAllFunctionList</p> <p>Description:按节点查询所有程式 </p>
	 * 
	 * @param pid
	 * 
	 * @return
	 * 
	 * @see com.erp.service.FunctionService#findAllFunctionList(java.lang.Integer)
	 */
	public List<TreeGridModel> findAllFunctionList(String pid) {
		String hql = "from Permission t where t.status='A' ";
		if (pid == null || "".equals(pid)) {
			hql += " and t.pid is null";
		} else {
			hql += " and t.pid=" + pid;
		}
		List<Permission> list = permissionPublicDao.find(hql);
		List<TreeGridModel> tempList = new ArrayList<TreeGridModel>();
		for (Permission function : list) {
			TreeGridModel treeGridModel = new TreeGridModel();
			try {
				BeanUtils.copyProperties(treeGridModel, function);
				if (pid == null || "".equals(pid)) {
					treeGridModel.setPid(null);
				}
				tempList.add(treeGridModel);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return tempList;
	}

	public List<TreeModel> findAllFunctionList() {
		String hql = "from Permission t where t.status='A' and t.type='F' ";
		List<Permission> list = permissionPublicDao.find(hql);
		List<TreeModel> tempList = new ArrayList<TreeModel>();
		for (Permission function : list) {
			TreeModel treeModel = new TreeModel();
			treeModel.setId(function.getPermissionId().toString());
			treeModel.setPid(function.getPid() == null ? "" : function.getPid().toString());
			treeModel.setName(function.getName());
			treeModel.setIconCls(function.getIconCls());
			treeModel.setState(Constants.TREE_STATUS_OPEN);
			tempList.add(treeModel);
		}
		return tempList;
	}

	/*
	 * (非 Javadoc) <p>Title: delFunction</p> <p>Description: 删除程式</p>
	 * 
	 * @param id
	 * 
	 * @return
	 * 
	 * @see com.erp.service.FunctionService#delFunction(java.lang.Integer)
	 */
	public boolean delFunction(Integer id) {
		String hql = " from Permission t where t.status='A' and t.pid=" + id;
		List<Permission> list = permissionPublicDao.find(hql);
		if (list.size() != 0) {
			return false;
		} else {
			Permission function = permissionPublicDao.get(Permission.class, id);
			function.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
			function.setLastmod(new Date());
			function.setModifyer(Constants.getCurrendUser().getUserId());
			permissionPublicDao.deleteToUpdate(function);
			return true;
		}
	}
}
