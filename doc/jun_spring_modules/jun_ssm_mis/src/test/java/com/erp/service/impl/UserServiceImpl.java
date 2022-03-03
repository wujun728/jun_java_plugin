package com.erp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.model.Role;
import com.erp.model.UserRole;
import com.erp.model.Users;
import com.erp.service.IUserService;
import com.erp.shiro.ShiroUser;
import com.erp.viewModel.UserRoleModel;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.biz.PageUtil;

@Service("userService")
@SuppressWarnings("rawtypes")
public class UserServiceImpl implements IUserService {
	private PublicDao<Users> publicDao;
	private PublicDao publicDaoSQL;

	@Autowired
	public void setPublicDao(PublicDao<Users> publicDao) {
		this.publicDao = publicDao;
	}

	@Autowired
	public void setPublicDaoSQL(PublicDao publicDaoSQL) {
		this.publicDaoSQL = publicDaoSQL;
	}

	public boolean persistenceUsers(Map<String, List<Users>> map) {
		this.addUsers(map.get("addList"));
		this.updUsers(map.get("updList"));
		this.delUsers(map.get("delList"));
		return true;
	}

	public List<Users> findAllUserList(Map<String, Object> map, PageUtil pageUtil) {
		String hql = "from Users u where u.status='A' ";
		hql += Constants.getSearchConditionsHQL("u", map);
		hql += Constants.getGradeSearchConditionsHQL("u", pageUtil);
		List<Users> list = publicDao.find(hql, map, pageUtil.getPage(), pageUtil.getRows());
		for (Users users : list) {
			users.setUserRoles(null);
		}
		return list;
	}

	public Long getCount(Map<String, Object> map, PageUtil pageUtil) {
		String hql = "select count(*) from Users  u where u.status='A' ";
		hql += Constants.getSearchConditionsHQL("u", map);
		hql += Constants.getGradeSearchConditionsHQL("u", pageUtil);
		return publicDao.count(hql, map);
	}

	private boolean addUsers(List<Users> addList) {
		if (addList != null && addList.size() != 0) {
			ShiroUser user = Constants.getCurrendUser();
			for (Users users : addList) {
				users.setCreated(new Date());
				users.setLastmod(new Date());
				users.setLastVisits(new Date());
				users.setCreater(user.getUserId());
				users.setModifyer(user.getUserId());
				users.setStatus(Constants.PERSISTENCE_STATUS);
				publicDao.save(users);
			}
		}
		return true;
	}

	private boolean updUsers(List<Users> updList) {
		if (updList != null && updList.size() != 0) {
			ShiroUser user = Constants.getCurrendUser();
			for (Users users : updList) {
				users.setLastmod(new Date());
				users.setModifyer(user.getUserId());
				publicDao.update(users);
			}
		}
		return true;
	}

	private boolean delUsers(List<Users> delList) {
		ShiroUser user = Constants.getCurrendUser();
		if (delList != null && delList.size() != 0) {
			for (Users users : delList) {
				users.setLastmod(new Date());
				users.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
				users.setModifyer(user.getUserId());
				publicDao.update(users);
			}
		}
		return true;
	}

	public boolean persistenceUsers(Users u) {
		Integer userId = Constants.getCurrendUser().getUserId();
		if (null == u.getUserId() || 0 == u.getUserId() || "".equals(u.getUserId())) {
			u.setCreated(new Date());
			u.setLastmod(new Date());
			u.setCreater(userId);
			u.setModifyer(userId);
			u.setStatus(Constants.PERSISTENCE_STATUS);
			publicDao.save(u);
		} else {
			u.setLastmod(new Date());
			u.setModifyer(userId);
			publicDao.update(u);
		}
		return true;
	}

	public boolean delUsers(Integer userId) {
		Users users = publicDao.get(Users.class, userId);
		users.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
		users.setLastmod(new Date());
		users.setModifyer(Constants.getCurrendUser().getUserId());
		publicDao.deleteToUpdate(users);
		return true;
	}

	public List<UserRoleModel> findUsersRolesList(Integer userId) {
		String sql = "SELECT ur.USER_ID,ur.ROLE_ID FROM\n" + "SYS_USER_ROLE AS ur where ur.STATUS ='A' and ur.USER_ID=" + userId;
		List list = publicDaoSQL.findBySQL(sql);
		List<UserRoleModel> listm = getUserRoleModelList(userId, list);
		return listm;
	}

	private List<UserRoleModel> getUserRoleModelList(Integer userId, List list) {
		List<UserRoleModel> listm = new ArrayList<UserRoleModel>();
		for (Object object : list) {
			Object[] obj = (Object[]) object;
			UserRoleModel userRoleModel = new UserRoleModel();
			userRoleModel.setUserId(userId);
			userRoleModel.setRoleId(obj[1] == null ? null : Integer.valueOf(obj[1].toString()));
			listm.add(userRoleModel);
		}
		return listm;
	}

	@SuppressWarnings("unchecked")
	public boolean saveUserRoles(Integer userId, String isCheckedIds) {
		Users user = publicDao.get(Users.class, userId);
		Set<UserRole> set = user.getUserRoles();
		Map<Integer, UserRole> map = new HashMap<Integer, UserRole>();
		for (UserRole userRole : set) {
			map.put(userRole.getRole().getRoleId(), userRole);
			userRole.setLastmod(new Date());
			userRole.setStatus(Constants.PERSISTENCE_DELETE_STATUS);
			publicDaoSQL.deleteToUpdate(userRole);
		}
		if (!"".equals(isCheckedIds) && isCheckedIds.length() != 0) {
			String[] ids = isCheckedIds.split(",");
			ShiroUser currUser = Constants.getCurrendUser();
			for (String id : ids) {
				Integer tempId = Integer.valueOf(id);
				Role role = (Role) publicDaoSQL.get(Role.class, Integer.valueOf(id));
				UserRole userRole = null;
				if (map.containsKey(tempId)) {
					userRole = map.get(tempId);
					userRole.setStatus(Constants.PERSISTENCE_STATUS);
					userRole.setCreater(currUser.getUserId());
					userRole.setModifyer(currUser.getUserId());
					publicDaoSQL.update(userRole);
				} else {
					userRole = new UserRole();
					userRole.setCreated(new Date());
					userRole.setLastmod(new Date());
					userRole.setRole(role);
					userRole.setUsers(user);
					userRole.setCreater(currUser.getUserId());
					userRole.setModifyer(currUser.getUserId());
					userRole.setStatus(Constants.PERSISTENCE_STATUS);
					publicDaoSQL.save(userRole);
				}
			}
		}
		return true;
	}
}
