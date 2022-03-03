package com.baomidou.springwind.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.springwind.entity.Role;
import com.baomidou.springwind.entity.RolePermission;
import com.baomidou.springwind.service.IPermissionService;
import com.baomidou.springwind.service.IRolePermissionService;
import com.baomidou.springwind.service.IRoleService;
import com.baomidou.springwind.service.IUserRoleService;

/**
 * <p>
 * 角色管理相关操作
 * </p>
 *
 *
 * @Author hubin
 * @Date 2016-04-15
 */
@Controller
@RequestMapping("/perm/role")
public class RoleController extends BaseController {

	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IPermissionService permissionService;
	
	@Autowired
	private IRolePermissionService rolePermissionService;

	@Autowired
	private IUserRoleService userRoleService;


	@Permission("2002")
	@RequestMapping("/list")
	public String list( Model model ) {
		return "/role/list";
	}


	@ResponseBody
	@Permission("2002")
	@RequestMapping("/getRoleList")
	public String getUserList() {
		Page<Role> page = getPage();
		return jsonPage(roleService.selectPage(page, null));
	}


	@ResponseBody
	@Permission("2003")
	@RequestMapping("/delete/{roleId}")
	public String delete( @PathVariable Long roleId ) {
		boolean exist = userRoleService.existRoleUser(roleId);
		if ( exist ) {
			return "false";
		}
		return booleanToString(roleService.deleteById(roleId));
	}


	@Permission("2002")
	@RequestMapping("/edit")
	public String edit( Model model, Long id ) {
		if ( id != null ) {
			model.addAttribute("role", roleService.selectById(id));
		}
		return "/role/edit";
	}


	@ResponseBody
	@Permission("2002")
	@RequestMapping("/editRole")
	public String editRole( Role role ) {
		boolean rlt = false;
		if ( role != null ) {
			if ( role.getId() != null ) {
				rlt = roleService.updateById(role);
			} else {
				rlt = roleService.insert(role);
			}
		}
		return callbackSuccess(rlt);
	}
	
	/**
	 * 跳转到权限分配页面
	 * @param model
	 * @param id
	 * @return
	 */
	@Permission("2003")
	@RequestMapping("/assigning")
	public String assigning( Model model, Long id ) {
		model.addAttribute("roleId", id);
		return "/role/assigning";
	}
	
	/**
	 * 获取权限树
	 * @param model
	 * @param roleId
	 * @return
	 */
	@Permission("2003")
	@RequestMapping("/right")
	@ResponseBody
	public String right( Model model, 
			@RequestParam(value="roleId",required = true)  Long roleId ) {
		List<com.baomidou.springwind.entity.Permission> list = permissionService.selectList(null);
		List<Long> roleRightList = rolePermissionService.selecPermissionIdsByRoleId(roleId);
		List<Map<String,String>> rightList = new ArrayList<Map<String,String>>();
		for(com.baomidou.springwind.entity.Permission r : list){
			Map<String,String> map = new HashMap<String,String>();
			map.put("id", r.getId().toString());
			map.put("pId", r.getPid().toString());
			map.put("name", r.getTitle());
			//默认展开树
			map.put( "open", "true");
			//如果角色已有该权限，则默认选中
			if(roleRightList.contains(r.getId())){
				map.put( "checked", "true");
			}
			rightList.add(map);
		}
		return JSONObject.toJSONString(rightList);
	}
	
	/**
	 * 更新权限列表
	 * @param right
	 * @param page
	 * @param rows
	 * @return
	 * @throws IOException 
	 */
	@Permission("2003")
	@RequestMapping("updateRoleRight")
	@ResponseBody
	public String updateRoleRight(HttpServletResponse response,HttpServletRequest request,
			@RequestParam(value="roleId",required = false) Long roleId,
			@RequestParam(value="rights",required = false) String rights)  throws Exception{
		try {
			//查询出本角色已经分配了的权限
			RolePermission rolePermission = new RolePermission();
			rolePermission.setRid(roleId);
			EntityWrapper<RolePermission> ew = new EntityWrapper<RolePermission>();
			ew.setEntity(rolePermission);
			List<RolePermission> roleRightList = rolePermissionService.selectList(ew);
			
			//如果存在权限，先进行删除
			if (roleRightList.size() > 0) {
				for (RolePermission rp :roleRightList) {
					rolePermissionService.delete(new EntityWrapper<RolePermission>(rp));
				}
			}
			
			String[] rightIds = rights.split(",");
			if(StringUtils.isNotBlank(rights) && rightIds != null){
				//添加新分配的权限
				List<RolePermission> permissions = new ArrayList<RolePermission>();
				RolePermission e = null;
				for (String pid : rightIds) {
					e = new RolePermission();
					e.setPid(Long.valueOf(pid));
					e.setRid(roleId);
					permissions.add(e);
				}
				rolePermissionService.insertBatch(permissions);
			}
			return "true";
		} catch (Exception e) {
			return "false";
		}
	}
}
