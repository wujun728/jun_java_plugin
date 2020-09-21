package com.osmp.web.user.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.osmp.http.client.CxfClient;
import com.osmp.web.base.core.SystemConstant;
import com.osmp.web.core.SystemFrameWork;
import com.osmp.web.role.dao.RoleMapper;
import com.osmp.web.role.dao.RolePrivilegeMapper;
import com.osmp.web.role.entity.Role;
import com.osmp.web.test.entity.Test;
import com.osmp.web.user.dao.UserMapper;
import com.osmp.web.user.entity.User;
import com.osmp.web.user.entity.UserRole;
import com.osmp.web.user.service.UserService;
import com.osmp.web.utils.DataGrid;
import com.osmp.web.utils.Md5Encode;
import com.osmp.web.utils.Pagination;
import com.osmp.web.utils.Utils;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年8月22日 上午10:51:30
 */
@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private RolePrivilegeMapper rolePrivilegeMapper;

	@SuppressWarnings("rawtypes")
	@RequestMapping("/toList")
	public String getUser() {
		CxfClient cxfClient = SystemFrameWork.getBean(CxfClient.class);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("snbid", "wangkaiping");
		map.put("password", "123456");
		map.put("uuid", "abccccccccfc");
		try {
			Map result = cxfClient.getForObject("/snbidLogin", map, Map.class);
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "user/userList";
	}

	@RequestMapping("/userList")
	@ResponseBody
	public Map<String, Object> userList(DataGrid dg) {
		Pagination<User> p = new Pagination<User>(dg.getPage(), dg.getRows());
		List<User> list = userService.selectAll(p, "");
		dg.setResult(list);
		dg.setTotal(p.getTotal());
		return dg.getMap();
	}

	@RequestMapping("/toAdd")
	public String toAdd() {
		return "user/add";
	}

	@RequestMapping("/toEditUser")
	public ModelAndView toEditUser(String id) {
		ModelAndView mav = new ModelAndView("user/edit");
		User user = userService.getUserById(id);
		mav.addObject("user", user);
		return mav;
	}

	/**
	 * 进入增加角色页面
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/toAddRole")
	public ModelAndView toAddRole(String id) {
		ModelAndView mav = new ModelAndView("user/addRole");
		mav.addObject("userId", id);
		return mav;
	}

	@RequestMapping("/toGetUserRole")
	public ModelAndView toGetUserRole(String id) {
		ModelAndView mav = new ModelAndView("user/getUserRole");
		mav.addObject("userId", id);
		return mav;
	}

	@RequestMapping("/getUserRole")
	@ResponseBody
	public Map<String, Object> getUserRole(DataGrid dg, String id) {
		Pagination<Test> p = new Pagination<Test>(dg.getPage(), dg.getRows());
		dg.setResult(roleMapper.getUserRole(id));
		dg.setTotal(p.getTotal());
		return dg.getMap();
	}

	/**
	 * 获得可用的角色
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/roleOkList")
	@ResponseBody
	public Map<String, Object> roleOkList(DataGrid dg, HttpServletResponse response) {
		Pagination<Test> p = new Pagination<Test>(dg.getPage(), dg.getRows());
		dg.setResult(roleMapper.selectAll(Role.class, "status='" + SystemConstant.ROLE_OK + "'"));
		dg.setTotal(p.getTotal());
		return dg.getMap();
	}

	/**
	 * 增加角色入库
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/ajaxAddUserRole")
	@ResponseBody
	public Map<String, Object> ajaxAddUserRole(@RequestParam("id") String id, @RequestParam("roleIds") String[] roleIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<String> lis = userMapper.selectUserRole(id);
			if (this.isIdsequals(lis, roleIds)) {
				map.put("success", true);
				map.put("msg", "操作成功");
				return map;
			}
			userService.deletByUserId(id);
			if (Utils.contains(roleIds, SystemConstant.ADMIN_ROLE_ID)) {// 是超级管理员
				UserRole ur = new UserRole();
				ur.setId(UUID.randomUUID().toString());
				ur.setUserId(id);
				ur.setRoleId(SystemConstant.ADMIN_ROLE_ID);
				userMapper.addUserRole(ur);
				map.put("success", true);
				map.put("msg", "操作成功");
				return map;
			}
			for (String integer : roleIds) {
				UserRole ur = new UserRole();
				ur.setId(UUID.randomUUID().toString());
				ur.setUserId(id);
				ur.setRoleId(integer);
				userMapper.addUserRole(ur);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "操作失败");
			return map;
		}
		map.put("success", true);
		map.put("msg", "操作成功");
		return map;
	}

	/**
	 * 保存或者修改用户
	 * 
	 * @param user
	 * @param type
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public Map<String, Object> saveOrUpdate(User user, @RequestParam("type") String type, HttpServletRequest request) {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String password = user.getPassword();
			password = Md5Encode.encodeByMD5(password).toUpperCase();
			user.setPassword(password);
			user.setLastUpdateTime(new Date());
			if (SystemConstant.ADD_TYPE.equals(type)) {
				user.setId(UUID.randomUUID().toString());
				user.setCreateTime(new Date());
				user.setLastLoginIp(Utils.getRemoteHost(request));
				userMapper.insert(user);
			} else {
				User u = userMapper.getById(user.getId());
				user.setCreateTime(u.getCreateTime());
				user.setLastLoginIp(u.getLastLoginIp());
				userMapper.update(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("msg", "操作失败");
			return map;
		}
		map.put("success", true);
		map.put("msg", "操作成功");
		return map;
	}

	@RequestMapping("/deleteUser")
	@ResponseBody
	public Map<String, Object> testDel(User p) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			userService.deletUser(p);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "操作失败");
			return map;
		}
		map.put("success", true);
		map.put("msg", "操作成功");
		return map;
	}

	/**
	 * 判断用户角色是否变化
	 * 
	 * @param ids
	 * @param roleIds
	 * @return
	 */
	private boolean isIdsequals(List<String> ids, String[] roleIds) {
		if (ids != null && roleIds.length == ids.size()) {
			for (int i = 0; i < roleIds.length; i++) {
				if (ids.contains(roleIds[i])) {
					continue;
				} else {
					return false;
				}
			}
			return true;
		}
		return false;
	}

}
