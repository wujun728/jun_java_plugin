package com.roncoo.jui.web.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.roncoo.jui.common.util.base.BaseController;
import com.roncoo.jui.web.bean.vo.SysMenuRoleVO;
import com.roncoo.jui.web.service.SysMenuRoleService;
import com.roncoo.jui.web.service.SysMenuService;

/**
 * 菜单角色关联表
 *
 * @author Wujun
 * @since 2017-10-20
 */
@Controller
@RequestMapping(value = "/admin/sysMenuRole")
public class SysMenuRoleController extends BaseController {

	private final static String TARGETID = "admin-sysMenuRole";

	@Autowired
	private SysMenuRoleService service;

	@Autowired
	private SysMenuService sysMenuService;

	/**
	 * 设置
	 * 
	 * @param id
	 * @param modelMap
	 */
	@RequestMapping(value = "/set", method = RequestMethod.GET)
	public void setGet(Long roleId, ModelMap modelMap) {
		List<SysMenuRoleVO> list = service.listByRoleId(roleId);
		modelMap.put("roleId", roleId);
		modelMap.put("menuRoleList", list);
		modelMap.put("ids", service.getIds(list));
		modelMap.put("menuList", sysMenuService.checkMenucByRole(list));
	}

	/**
	 * 设置
	 * 
	 * @param id
	 * @param modelMap
	 */
	@RequestMapping(value = "/setMenu", method = RequestMethod.POST)
	@ResponseBody
	public String setPost(Long roleId, String ids, ModelMap modelMap) {
		System.out.println(ids);
		service.save(roleId, ids);
		return success(TARGETID);
	}

}
