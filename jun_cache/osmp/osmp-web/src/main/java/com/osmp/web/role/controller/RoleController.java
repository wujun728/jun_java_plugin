/*   
 * Project: OSMP
 * FileName: RoleController.java
 * version: V1.0
 */
package com.osmp.web.role.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.osmp.web.base.core.SystemConstant;
import com.osmp.web.role.dao.RoleMapper;
import com.osmp.web.role.dao.RolePrivilegeMapper;
import com.osmp.web.role.entity.Role;
import com.osmp.web.role.entity.RolePrivilege;
import com.osmp.web.role.service.RolePrivilegeService;
import com.osmp.web.role.service.RoleService;
import com.osmp.web.user.dao.UserMapper;
import com.osmp.web.user.entity.SysPrivilege;
import com.osmp.web.user.entity.SysPrivilegeZtreeGrid;
import com.osmp.web.user.service.SysPrivilegeService;
import com.osmp.web.utils.DataGrid;
import com.osmp.web.utils.Pagination;

/**
 * 
 * Description: 
 *
 * @author: chelongquan
 * @date: 2015年4月18日
 */
@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private RolePrivilegeMapper rolePrivilegeMapper;
	
	@Autowired
	private RolePrivilegeService rolePrivilegeService;
	
	@Autowired
	private SysPrivilegeService sysPrivilegeService;

	
	@RequestMapping("/toList")
	public String toRole() {
		return "role/roleList";
	}
	
	@RequestMapping("/roleList")
	@ResponseBody
	public Map<String, Object> userList(DataGrid dg) {
		Pagination<Role> p = new Pagination<Role>(dg.getPage(), dg.getRows());
		List<Role> list = roleService.selectAll(p, "");
		dg.setResult(list);
		dg.setTotal(p.getTotal());
		return dg.getMap();
	}
	
	@RequestMapping("/toAdd")
	public String toAdd() {
		return "role/add";
	}
	
	@RequestMapping("/toEditRole")
	public ModelAndView toEditUser(String id) {
		ModelAndView mav = new ModelAndView("role/edit");
		Role role = roleService.getRoleById(id);
		mav.addObject("role", role);
		return mav;
	}
	
	@RequestMapping("/toAddPrivilege")
	public ModelAndView toAddPrivilege(String id) {
		ModelAndView mav = new ModelAndView("role/addPrivilege");
		mav.addObject("roleId", id);
		return mav;
	}
	
	/**
	 * 获取该角色拥有的权限
	 * @param id
	 * @return
	 */
	@RequestMapping("/ajaxGetPrivilege")
	@ResponseBody
	public Map<String, Object> ajaxGetPrivilege(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SysPrivilege> list = sysPrivilegeService.getList();
		List<RolePrivilege> rpList = rolePrivilegeService.selectAll("role_id='"+id+"'");
		List<Integer> rpIds = new ArrayList<Integer>();
		for (RolePrivilege rolePrivilege : rpList) {
			rpIds.add(rolePrivilege.getPrivilegeId());
		}
		List<SysPrivilegeZtreeGrid> sptList = this.changeTreeGrid(list, rpIds);
		map.put("sptList", sptList);
		map.put("rpIds", rpIds);
		return map;
	}
	
	/**
	 * 增加权限请求
	 * @param id
	 * @param menuIds
	 * @return
	 */
	@RequestMapping("/ajaxAddPrivilege")
	@ResponseBody
	public Map<String, Object> ajaxAddPrivilege(@RequestParam("id")String id,
			@RequestParam("menuIds")Integer[] menuIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			rolePrivilegeService.deletByRoleId(id);
			for (Integer idstr : menuIds) {
				RolePrivilege rp = new RolePrivilege();
				rp.setId(UUID.randomUUID().toString());
				rp.setRoleId(id);
				rp.setPrivilegeId(idstr);
				rolePrivilegeMapper.insert(rp);
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
	
	public List<SysPrivilegeZtreeGrid> changeTreeGrid(List<SysPrivilege> plist, List<Integer> rpList) {
		List<SysPrivilegeZtreeGrid> list = new ArrayList<SysPrivilegeZtreeGrid>();
		for (SysPrivilege i : plist) {
			if (i.getPid() == -1) {
				SysPrivilegeZtreeGrid stg = new SysPrivilegeZtreeGrid(i);
				list.add(stg);
				if(rpList.contains(stg.getId())){
					stg.setChecked(true);
				}
				this.fuck(stg, plist, rpList);
			}
		}
		return list;
	}
	
	public void fuck(SysPrivilegeZtreeGrid stg, List<SysPrivilege> plist, List<Integer> rpList) {
		for (SysPrivilege i : plist) {
			if (i.getPid() == stg.getId()) {
				SysPrivilegeZtreeGrid child = new SysPrivilegeZtreeGrid(i);
				child.setIcon("");
				if(rpList.contains(child.getId())){
					child.setChecked(true);
				}
				stg.getChildren().add(child);
				stg.setOpen("true");
				stg.setIcon("");
				this.fuck(child, plist, rpList);
			}
		}
	}
	
	
	
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public Map<String, Object> saveOrUpdate(Role role, @RequestParam("type")String type) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(SystemConstant.ADD_TYPE.equals(type)){
				role.setId(UUID.randomUUID().toString());
				roleMapper.insert(role);
			}else{
				roleMapper.update(role);
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
	
	@RequestMapping("/deleteRole")
	@ResponseBody
	public Map<String, Object> deleteRole(Role p) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			roleService.deletRole(p);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "操作失败");
			return map;
		}
		map.put("success", true);
		map.put("msg", "操作成功");
		return map;
	}
	
	

}
