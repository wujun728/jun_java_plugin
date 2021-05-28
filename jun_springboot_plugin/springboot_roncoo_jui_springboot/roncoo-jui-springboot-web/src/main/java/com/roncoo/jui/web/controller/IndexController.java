/**
 * Copyright 2015-2016 广州市领课网络科技有限公司
 */
package com.roncoo.jui.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.roncoo.jui.common.entity.SysUser;
import com.roncoo.jui.common.util.Constants;
import com.roncoo.jui.common.util.base.BaseController;
import com.roncoo.jui.web.bean.vo.SysMenuRoleVO;
import com.roncoo.jui.web.bean.vo.SysMenuVO;
import com.roncoo.jui.web.bean.vo.SysRoleUserVO;
import com.roncoo.jui.web.service.SysMenuRoleService;
import com.roncoo.jui.web.service.SysMenuService;
import com.roncoo.jui.web.service.SysRoleUserService;
import com.roncoo.jui.web.service.WebSiteService;

/**
 * @author Wujun
 */
@Controller
public class IndexController extends BaseController {

	@Autowired
	private SysMenuService sysMenuService;

	@Autowired
	private SysMenuRoleService sysMenuRoleService;

	@Autowired
	private SysRoleUserService sysRoleUserService;

	@Autowired
	private WebSiteService webSiteService;

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public void index(@RequestParam(value = "id", defaultValue = "1") Long id, ModelMap modelMap) {
		modelMap.put("vo", webSiteService.main(id));
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpSession session) {
		return redirect("/index");
	}

	@RequestMapping("/index")
	public void index(ModelMap modelMap) {
		SysUser sysUser = (SysUser) SecurityUtils.getSubject().getSession().getAttribute(Constants.Session.USER);

		// 用户-->角色-->菜单
		List<SysMenuRoleVO> sysMenuRoleVOList = new ArrayList<>();
		List<SysRoleUserVO> sysRoleUserVOList = sysRoleUserService.listByUserId(sysUser.getId());
		for (SysRoleUserVO sruVO : sysRoleUserVOList) {
			sysMenuRoleVOList.addAll(sysMenuRoleService.listByRoleId(sruVO.getRoleId()));
		}
		// 筛选
		List<SysMenuVO> menuVOList = sysMenuService.listMenucByRole(sysMenuRoleVOList);

		SecurityUtils.getSubject().getSession().setAttribute(Constants.Session.MENU, menuVOList);
		modelMap.put("menuVOList", menuVOList);

		modelMap.put("javaVersion", System.getProperty("java.version"));
		modelMap.put("osName", System.getProperty("os.name"));
	}

}
