package  org.springrain.system.web;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springrain.frame.common.SessionUser;
import org.springrain.frame.controller.BaseController;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.frame.util.ReturnDatas;
import org.springrain.frame.util.property.MessageUtils;
import org.springrain.system.entity.Menu;
import org.springrain.system.service.IMenuService;
import org.springrain.system.service.IUserRoleMenuService;



/**
 * 
 * @Title: MenuController.java
 * @Package org.springrain.springrain.web
 * @Description: o
 * @author springrain
 * @date 2013-7-11 下午9:36:31
 * @version V1.0
 *
 */
@Controller
@RequestMapping(value="/system/menu")
public class MenuController  extends BaseController {
	
	@Resource
	private IMenuService menuService;
	
	@Resource
	private IUserRoleMenuService userRoleMenuService;
	
	private String listurl="/system/menu/menuList";  //菜单列表路径
	
	/**
	 * 带权限查询
	 * 
	 * @param request
	 * @param model
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request, Model model, Menu menu) throws Exception {
		ReturnDatas returnObject = listjson(request, model, menu);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}

	/**
	 * 带权限查询
	 * 
	 * @param request
	 * @param model
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/json")
	@ResponseBody 
	public ReturnDatas listjson(HttpServletRequest request, Model model, Menu menu) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
	
		List<Menu> datas =userRoleMenuService.findMenuByUserIdAll(SessionUser.getUserId());
		returnObject.setQueryBean(menu);
		returnObject.setData(datas);
		return returnObject;
	}
	
	/**
	 * 查询菜单 管理员使用
	 * 
	 * @param request
	 * @param model
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/all")
	public String listall(HttpServletRequest request, Model model, Menu menu) throws Exception {
		ReturnDatas returnObject = listjson(request, model, menu);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return listurl;
	}
	/**
	 * 查询菜单 管理员使用
	 * 
	 * @param request
	 * @param model
	 * @param menu
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list/all/json")
	@ResponseBody 
	public ReturnDatas listalljson(HttpServletRequest request, Model model, Menu menu) throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		List<Menu> datas = menuService.findListDataByFinder(null, null, Menu.class, menu); 
		returnObject.setQueryBean(menu);
		//returnObject.setPage(page);
		returnObject.setData(datas);
		return returnObject;
	}

	
	
	
	
	

	/**
	 * 查看操作,调用APP端lookjson方法
	 */
	@RequestMapping(value = "/look")
	public String look(Model model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/system/menu/menuLook";
	}

	/**
	 * 查看的Json格式数据,为APP端提供数据
	 */
	@RequestMapping(value = "/look/json")
	@ResponseBody 
	public ReturnDatas lookjson(Model model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		String id = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			Menu menu = menuService.findMenuById(id);
			returnObject.setData(menu);
		} else {
			returnObject.setStatus(ReturnDatas.ERROR);
		}

		return returnObject;

	}

	/**
	 * 新增/修改 操作吗,返回json格式数据
	 * 
	 */
	@RequestMapping("/update")
	@ResponseBody 
	public  ReturnDatas saveorupdate(Menu menu, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReturnDatas returnObject = ReturnDatas.getSuccessReturnDatas();
		returnObject.setMessage(MessageUtils.UPDATE_SUCCESS);
		try {
			String id=menu.getId();
			String pid=menu.getPid();
			String pageurl=menu.getPageurl();
			if(StringUtils.isBlank(id)){
				menu.setId(null);
			}	
			if(StringUtils.isBlank(pid)){
				menu.setPid(null);
			}
			if(StringUtils.isBlank(pageurl)){
				menu.setPageurl(null);
			}
			menuService.saveorupdateMenu(menu);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			returnObject.setStatus(ReturnDatas.ERROR);
			returnObject.setMessage(MessageUtils.UPDATE_ERROR);
		}
		return returnObject;
	}

	/**
	 * 进入修改页面,APP端可以调用 lookjson 获取json格式数据
	 */
	@RequestMapping(value = "/update/pre")
	public String edit(Model model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReturnDatas returnObject = lookjson(model, request, response);
		model.addAttribute(GlobalStatic.returnDatas, returnObject);
		return "/system/menu/menuCru";
	}

	/**
	 * 删除操作
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody 
	public ReturnDatas destroy(HttpServletRequest request) throws Exception {
		// 执行删除
		try {
			java.lang.String id = request.getParameter("id");
			if (StringUtils.isNotBlank(id)) {
				menuService.deleteMenuById(id);
				return new ReturnDatas(ReturnDatas.SUCCESS, MessageUtils.DELETE_SUCCESS);
			} else {
				return new ReturnDatas(ReturnDatas.WARNING, MessageUtils.DELETE_WARNING);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new ReturnDatas(ReturnDatas.WARNING, MessageUtils.DELETE_WARNING);
	}

	
	
	/**
	 * 
	 * @Title: menu
	 * @Description: 菜单列表
	 * @return
	 * @return Map
	 * @throws
	 */
	@RequestMapping("/leftMenu")
	@ResponseBody 
	public  ReturnDatas leftMenu(){
		//获取当前登录人
		String userId=SessionUser.getUserId();
		if(StringUtils.isBlank(userId)){
			return null;
		}
		
		try {
		     List<Menu> listMenu=userRoleMenuService.findMenuAndLeafByUserId(userId);
			return new ReturnDatas(ReturnDatas.SUCCESS, null,listMenu);
		
		} catch (Exception e) {
			logger.error("获取导航菜单异常", e);
			return new ReturnDatas(ReturnDatas.ERROR, MessageUtils.SELECT_WARING);
		}
	
	}
	
	
	
	
}