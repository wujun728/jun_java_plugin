package com.jun.permission.service.impl;

import com.jun.permission.controller.core.LoginIdentity;
import com.jun.permission.core.constant.CommonDic;
import com.jun.permission.core.constant.CommonDic.HttpSessionKeyDic;
import com.jun.permission.core.model.junPermissionMenu;
import com.jun.permission.core.model.junPermissionRole;
import com.jun.permission.core.model.junPermissionUser;
import com.jun.permission.core.result.ReturnT;
import com.jun.permission.core.util.HttpSessionUtil;
import com.jun.permission.core.util.JacksonUtil;
import com.jun.permission.dao.IjunPermissionMenuDao;
import com.jun.permission.dao.IjunPermissionRoleDao;
import com.jun.permission.dao.IjunPermissionUserDao;
import com.jun.permission.service.IUserPermissionService;
import com.jun.permission.service.helper.MenuModuleHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 用户-角色-权限
 * @author wujun
 */
@Service
public class UserPermissionServiceImpl implements IUserPermissionService {
	//private static transient Logger logger = LoggerFactory.getLogger(UserPermissionServiceImpl.class);
	
	@Autowired
	private IjunPermissionUserDao junPermissionUserDao;
	@Autowired
	private IjunPermissionRoleDao junPermissionRoleDao;
	@Autowired
	private IjunPermissionMenuDao junPermissionMenuDao;

	// ---------------------- user ----------------------

	@Override
	public Map<String, Object> userQuery(int page,int rows, String userName) {
		int offset = page<1? 0 : (page-1)*rows;
		int pagesize = rows;
		
		List<junPermissionUser> rowsData = junPermissionUserDao.queryUser(offset, pagesize, userName);
		int totalNumber = junPermissionUserDao.queryUserCount(offset, pagesize, userName);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("rows", rowsData);  
		resultMap.put("total", totalNumber);
		return resultMap;
	}

	@Override
	public ReturnT<Integer> userAdd(String userName, String password) {
		if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
			return new ReturnT<Integer>(ReturnT.FAIL_CODE, "操作失败,用户参数不完整");
		}

		junPermissionUser user = new junPermissionUser();
		user.setUserName(userName);
		user.setPassword(password);

		int count = junPermissionUserDao.add(user);
		return new ReturnT<Integer>(count);
	}

	@Override
	public ReturnT<Integer> userDel(HttpSession session, int[] userIds) {
		if (ArrayUtils.isEmpty(userIds)) {
			return new ReturnT<Integer>(ReturnT.FAIL_CODE, "删除失败,未选中用户");
		}
		
		LoginIdentity identity = (LoginIdentity) HttpSessionUtil.get(session, HttpSessionKeyDic.LOGIN_IDENTITY);
		for (int userId : userIds) {
			// 01：不允许删除,自个儿
			if (identity.getUserId() == userId) {
				return new ReturnT<Integer>(ReturnT.FAIL_CODE, "删除失败,不允许删除自个儿");
			}
			// 02：不允许删除,不存在用户
			junPermissionUser user = junPermissionUserDao.loadUser(userId);
			if (user == null) {
				return new ReturnT<Integer>(ReturnT.FAIL_CODE, "删除失败,用户[" + userId + "]不存在");
			}
			// 03：存在关联角色，不允许删除
			List<junPermissionRole> roleList = junPermissionRoleDao.findRoleByUserId(userId);
			if (CollectionUtils.isNotEmpty(roleList)) {
				return new ReturnT<Integer>(ReturnT.FAIL_CODE, "删除失败,用户[" + userId + "]存在关联角色，不允许删除");
			}
		}
		
		// 批量删除-用户
		int count = junPermissionUserDao.delete( Arrays.asList( ArrayUtils.toObject(userIds) ) );
		return new ReturnT<Integer>(count);
	}

	@Override
	public ReturnT<Integer> userUpdate(int userId, String userName, String password) {
		if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
			return new ReturnT<Integer>(ReturnT.FAIL_CODE, "操作失败,用户参数不完整");
		}

		junPermissionUser user = junPermissionUserDao.loadUser(userId);
		if (user == null) {
			return new ReturnT<Integer>(ReturnT.FAIL_CODE, "操作失败,用户不存在");
		}

		user.setUserName(userName);
		user.setPassword(password);

		int count = junPermissionUserDao.update(user);
		if (count < 1) {
			return new ReturnT<Integer>(ReturnT.FAIL_CODE, "操作失败");
		}
		return new ReturnT<Integer>(count);
	}

	// ---------------------- user-role ----------------------

	public String userRoleQueryJson(int userId) {

		// all
		List<junPermissionRole> allRoles = junPermissionRoleDao.getAllRoles();

		// my
		List<junPermissionRole> myRoles = junPermissionRoleDao.findRoleByUserId(userId);

		// 设置选中
		for (junPermissionRole all : allRoles) {
			for (junPermissionRole my : myRoles) {
				if (all.getId() == my.getId()) {
					all.setChecked(true);
				}
			}
		}
		
		return JacksonUtil.writeValueAsString(allRoles);
	}

	public ReturnT<Integer> userRoleUpdate(HttpSession session, int userId, int[] roleIds) {
		
		junPermissionUser user = junPermissionUserDao.loadUser(userId);
		if (user == null) {
			return new ReturnT<Integer>(ReturnT.FAIL_CODE, "操作失败,用户不存在");
		}
		
		// remove old
		junPermissionUserDao.unBindUserRoleAll(new int[]{userId});

		// add new
		if (roleIds!=null && roleIds.length>0) {
			junPermissionUserDao.bindUserRoles(userId, new HashSet<Integer>(Arrays.asList(ArrayUtils.toObject(roleIds))));
		}

		return new ReturnT<Integer>(ReturnT.SUCCESS_CODE, null);
	}

	// ---------------------- role ----------------------
	@Override
	public Map<String, Object> roleQuery() {
		List<junPermissionRole> rowsData = junPermissionRoleDao.getAllRoles();
		int totalNumber = rowsData!=null?rowsData.size():0;
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("rows", rowsData);  
		resultMap.put("total", totalNumber);
		return resultMap;
	}

	@Override
	public ReturnT<Integer> roleAdd(String name, int order) {
		if (StringUtils.isBlank(name)) {
			return new ReturnT<Integer>(ReturnT.FAIL_CODE, "操作失败,角色名称为空");
		}

		junPermissionRole role = new junPermissionRole();
		role.setName(name);
		role.setOrder(order);

		int count = junPermissionRoleDao.add(role);
		return new ReturnT<Integer>(count);
	}

	@Override
	public ReturnT<Integer> roleDel(int[] roleIds) {
		if (ArrayUtils.isEmpty(roleIds)) {
			return new ReturnT<Integer>(ReturnT.FAIL_CODE, "删除失败,未选中角色");
		}
		
		for (int roleId : roleIds) {
			// Role-关联用户,不允许删除
			int count = junPermissionRoleDao.findUserCountByRole(roleId);
			if (count > 0) {
				return new ReturnT<Integer>(ReturnT.FAIL_CODE, "删除失败,角色["+roleId+"]已绑定用户，不允许删除");
			}
			List<junPermissionMenu> roleMenuList = junPermissionMenuDao.getMenusByRoleId(roleId);
			if (CollectionUtils.isNotEmpty(roleMenuList)) {
				return new ReturnT<Integer>(ReturnT.FAIL_CODE, "删除失败,角色["+roleId+"]已分配菜单，不允许删除");
			}

			if (roleId == CommonDic.SUPER_ROLE_ID) {
				return new ReturnT<Integer>(ReturnT.FAIL_CODE, "删除失败,超级管理员不允许删除");
			}
		}
		
		// 角色-菜单关联,删除批量
		int count = junPermissionRoleDao.delete(Arrays.asList(ArrayUtils.toObject(roleIds)));

		return new ReturnT<Integer>(count);
	}

	@Override
	public ReturnT<Integer> roleUpdate(int roleId, String name, int order) {
		if (StringUtils.isBlank(name)) {
			return new ReturnT<Integer>(ReturnT.FAIL_CODE, "操作失败,角色名称为空");
		}

		if (roleId == CommonDic.SUPER_ROLE_ID) {
			return new ReturnT<Integer>(ReturnT.FAIL_CODE, "操作失败,超级管理员不允许操作");
		}

		junPermissionRole role = junPermissionRoleDao.loadRole(roleId);
		if (role == null) {
			return new ReturnT<Integer>(ReturnT.FAIL_CODE, "操作失败,角色ID非法");
		}

		role.setName(name);
		role.setOrder(order);

		int count = junPermissionRoleDao.update(role);
		if (count < 1) {
			return new ReturnT<Integer>(ReturnT.FAIL_CODE, "操作失败");
		}
		return new ReturnT<Integer>(count);
	}

	// ---------------------- role-menu ----------------------
	@Override
	public String roleMenuQueryJson(int roleId) {
		List<junPermissionMenu> Allmenus = junPermissionMenuDao.getAllMenus();
		List<junPermissionMenu> myMenus = junPermissionMenuDao.getMenusByRoleId(roleId);
		
		// 标志我的权限
		for (junPermissionMenu my : myMenus) {
			for (junPermissionMenu all : Allmenus) {
				if (my.getId() == all.getId()) {
					all.setChecked(true);
				}
			}
		}
		
		// 用户权限菜单, JSON tree (easyui tree格式)
		String result = MenuModuleHelper.initMenuModuleEasyJson(Allmenus);
		return result;
	}

	@Override
	public ReturnT<String> roleMenuUpdate(int roleId, int[] menuIds) {
		
		if (roleId == CommonDic.SUPER_ROLE_ID) {
			return new ReturnT<String>(ReturnT.FAIL_CODE, "超级管理员不需要分配权限");
		}
		
		junPermissionRole role = junPermissionRoleDao.loadRole(roleId);
		if (role == null) {
			return new ReturnT<String>(ReturnT.FAIL_CODE, "角色菜单更新失败,角色不存在");
		}
		
		// remove old
		junPermissionRoleDao.unBindRoleMenu(new int[]{roleId});

		// add new
		if (menuIds!=null && menuIds.length>0) {
			junPermissionRoleDao.bindRoleMenu(roleId, new HashSet<Integer>(Arrays.asList(ArrayUtils.toObject(menuIds))));
		}

		return ReturnT.SUCCESS;
	}

	// ---------------------- menu ----------------------

	@Override
	public Map<String, Object> menuQuery() {
		List<junPermissionMenu> rowsData = junPermissionMenuDao.getAllMenus();
		int totalNumber = rowsData!=null ? rowsData.size() : 0;
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("rows", rowsData);  
		resultMap.put("total", totalNumber);
		return resultMap;
	}

	@Override
	public ReturnT<Integer> menuAdd(junPermissionMenu menu) {
		// 校验父菜单
		boolean ifBiz = menu.getParentId() == CommonDic.BIZ_MENU_ID;	// 0-biz
		if (!ifBiz) {
			junPermissionMenu parent = junPermissionMenuDao.load(menu.getParentId());
			if (parent == null) {
				return new ReturnT<Integer>(ReturnT.FAIL_CODE, "新增失败,父菜单ID为空");
			}

			boolean ifGroup = parent.getParentId() == 0;	// 0-biz-group
			if (!ifGroup) {
				junPermissionMenu parent2 = junPermissionMenuDao.load(parent.getParentId());
				boolean ifMenu = parent2.getParentId()==0;		// 0-biz-group-menu
				if (!ifMenu) {
					return new ReturnT<Integer>(ReturnT.FAIL_CODE, "新增失败,父菜单只能为菜单组");
				}
			}

		}

		// 校验菜单名称
		if (StringUtils.isBlank(menu.getName())) {
			return new ReturnT<Integer>(ReturnT.FAIL_CODE, "新增失败,菜单名称为空");
		}

		int count = junPermissionMenuDao.add(menu);
		return new ReturnT<Integer>(count);
	}

	@Override
	public ReturnT<Integer> menuDel(int menuId) {
		// 角色-菜单关联,不允许删除
		int count = junPermissionMenuDao.findBindRoleCount(menuId);
		if (count > 0) {
			return new ReturnT<Integer>(ReturnT.FAIL_CODE, "删除失败,该菜单使用中");
		}
		
		// 存在子菜单,不允许删除
		List<junPermissionMenu> subList = junPermissionMenuDao.getMenusByParentId(menuId);
		if (CollectionUtils.isNotEmpty(subList)) {
			return new ReturnT<Integer>(ReturnT.FAIL_CODE, "删除失败,存在子菜单依赖");
		}
		
		count = junPermissionMenuDao.delete(menuId);
		return new ReturnT<Integer>(count);
	}

	@Override
	public ReturnT<Integer> menuUpdate(junPermissionMenu menu) {

		junPermissionMenu existsMenu = junPermissionMenuDao.load(menu.getId());
		if (existsMenu == null) {
			return new ReturnT<Integer>(ReturnT.FAIL_CODE, "更新失败,菜单ID非法");
		}

		if (menu.getParentId() != existsMenu.getParentId()) {
			List<junPermissionMenu> childMenuList = junPermissionMenuDao.getMenusByParentId(menu.getId());
			if (CollectionUtils.isNotEmpty(childMenuList)) {
				return new ReturnT<Integer>(ReturnT.FAIL_CODE, "更新失败,存在子菜单时不允许更新父菜单");
			}

			// 校验父菜单
			boolean ifBiz = menu.getParentId() == CommonDic.BIZ_MENU_ID;	// 0-biz
			if (!ifBiz) {
				junPermissionMenu parent = junPermissionMenuDao.load(menu.getParentId());
				if (parent == null) {
					return new ReturnT<Integer>(ReturnT.FAIL_CODE, "新增失败,父菜单ID为空");
				}

				boolean ifGroup = parent.getParentId() == 0;	// 0-biz-group
				if (!ifGroup) {
					junPermissionMenu parent2 = junPermissionMenuDao.load(parent.getParentId());
					boolean ifMenu = parent2.getParentId()==0;		// 0-biz-group-menu
					if (!ifMenu) {
						return new ReturnT<Integer>(ReturnT.FAIL_CODE, "新增失败,父菜单只能为菜单组");
					}
				}

			}
		}

		// 校验菜单名称
		if (StringUtils.isBlank(menu.getName())) {
			return new ReturnT<Integer>(ReturnT.FAIL_CODE, "更新失败,菜单名称为空");
		}

		int count = junPermissionMenuDao.update(menu);
		return new ReturnT<Integer>(count);
	}

}