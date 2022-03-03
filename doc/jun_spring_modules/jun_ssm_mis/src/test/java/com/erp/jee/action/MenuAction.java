package com.erp.jee.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.erp.jee.pageModel.Json;
import com.erp.jee.pageModel.Menu;
import com.erp.jee.service.MenuServiceI;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.ExceptionUtil;

/**
 * 菜单ACTION
 * 
 */
//@Action(value = "menuAction", results = { @Result(name = "menu", location = "/com/jeecg/menu.jsp") })
@Controller
@RequestMapping("/menuAction.do")
public class MenuAction extends BaseAction{

	private static final Logger logger = Logger.getLogger(MenuAction.class);

	private MenuServiceI menuService;

	private Menu menu = new Menu();

	public Menu getModel() {
		return menu;
	}

	public MenuServiceI getMenuService() {
		return menuService;
	}

	@Autowired
	public void setMenuService(MenuServiceI menuService) {
		this.menuService = menuService;
	}

	/**
	 * 首页获得菜单树
	 */
	@RequestMapping(params = "method=ctrlTree")
	public void ctrlTree(Model model, HttpServletRequest request, HttpServletResponse response) {
		writeJson(response, menuService.tree(menu, false));
	}

	/**
	 * 跳转到菜单管理页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "method=goMenu")
	public String goMenu(Model model, HttpServletRequest request, HttpServletResponse response) {
		return "menu";
	}

	/**
	 * 获得菜单treegrid
	 */
	@RequestMapping(params = "method=treegrid")
	public void treegrid(Model model, HttpServletRequest request, HttpServletResponse response) {
		writeJson(response, menuService.treegrid(menu));
	}

	/**
	 * 获取菜单树,递归子节点
	 */
	@RequestMapping(params = "method=treeRecursive")
	public void treeRecursive(Model model, HttpServletRequest request, HttpServletResponse response) {
		writeJson(response, menuService.tree(menu, true));
	}

	/**
	 * 所有人都有权限的菜单
	 */
	@RequestMapping(params = "method=menuTreeRecursive")
	public void menuTreeRecursive(Model model, HttpServletRequest request, HttpServletResponse response) {
		writeJson(response, menuService.tree(menu, true));
	}

	/**
	 * 编辑菜单
	 */
	@RequestMapping(params = "method=edit")
	public void edit(Model model, HttpServletRequest request, HttpServletResponse response) {
		Json j = new Json();
		try {
			menuService.edit(menu);
			j.setSuccess(true);
			j.setMsg("编辑成功!请手动刷新左侧功能菜单树！");
			j.setObj(menu.getCpid());
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("编辑失败！");
		}
		writeJson(response, j);
	}

	/**
	 * 添加菜单
	 */
	@RequestMapping(params = "method=add")
	public void add(Model model, HttpServletRequest request, HttpServletResponse response) {
		Json j = new Json();
		try {
			menu.setCtype(Constants.TAUTH_CTYPE_01);
			menuService.add(menu);
			j.setSuccess(true);
			j.setMsg("添加成功!请手动刷新左侧功能菜单树！");
			j.setObj(menu.getCpid());
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("添加失败！");
		}
		writeJson(response, j);
	}

	/**
	 * 删除一个菜单
	 */
	@RequestMapping(params = "method=delete")
	public void delete(Model model, HttpServletRequest request, HttpServletResponse response) {
		Json j = new Json();
		try {
			menuService.delete(menu);
			j.setSuccess(true);
			j.setMsg("删除成功！请手动刷新左侧功能菜单树！");
			j.setObj(menu.getCid());
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e));
			j.setMsg("删除失败！");
		}
		writeJson(response, j);
	}

}
