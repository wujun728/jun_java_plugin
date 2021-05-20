package com.ibeetl.admin.core.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibeetl.admin.core.conf.PasswordConfig.PasswordEncryptService;
import com.ibeetl.admin.core.entity.CoreOrg;
import com.ibeetl.admin.core.entity.CoreUser;
import com.ibeetl.admin.core.rbac.UserLoginInfo;
import com.ibeetl.admin.core.rbac.tree.FunctionItem;
import com.ibeetl.admin.core.rbac.tree.MenuItem;
import com.ibeetl.admin.core.rbac.tree.OrgItem;
import com.ibeetl.admin.core.service.CorePlatformService;
import com.ibeetl.admin.core.service.CoreUserService;
import com.ibeetl.admin.core.util.HttpRequestLocal;
import com.ibeetl.admin.core.web.dto.FunctionNodeView;
import com.ibeetl.admin.core.web.dto.MenuNodeView;
import com.ibeetl.admin.core.web.dto.SystemMenuView;

@Controller
@SuppressWarnings("unchecked")
public class CoreUserController {
	private final Log log = LogFactory.getLog(this.getClass());
	private static final String MODEL = "/core/user";

	@Autowired
	CorePlatformService platformService;

	@Autowired
	CoreUserService userService;

	@Autowired
	HttpRequestLocal httpRequestLocal;



	
	@Autowired
	PasswordEncryptService passwordEncryptService ;

	@PostMapping(MODEL + "/login.json")
	@ResponseBody
	public JsonResult<UserLoginInfo> login(String code, String password) {
		UserLoginInfo info = userService.login(code, password);
		if (info == null) {
			
			return JsonResult.failMessage("用户名密码错");
		}
		CoreUser user = info.getUser();
		CoreOrg currentOrg = info.getOrgs().get(0);
		for (CoreOrg org : info.getOrgs()) {
			if (org.getId() == user.getOrgId()) {
				currentOrg = org;
				break;
			}
		}

		info.setCurrentOrg(currentOrg);
		// 记录登录信息到session
		this.platformService.setLoginUser(info.getUser(), info.getCurrentOrg(), info.getOrgs());
		return JsonResult.success(info);
	}

	/**
	 * 用户所在部门
	 * 
	 * @return
	 */
	@PostMapping(MODEL + "/myOrgs.json")
	@ResponseBody
	public JsonResult<List<CoreOrg>> myOrgs() {
		List<CoreOrg> orgs = (List<CoreOrg>) httpRequestLocal.getSessionValue(CorePlatformService.ACCESS_USER_ORGS);
		return JsonResult.success(orgs);

	}

	/**
	 * 切换部门
	 * 
	 * @param code
	 * @param orgId
	 * @return
	 */
	@PostMapping(MODEL + "/setOrg.json")
	@ResponseBody
	public JsonResult login(Long orgId) {

		CoreUser user = platformService.getCurrentUser();

		// 检查是否存在orgId
		List<CoreOrg> orgs = platformService.getCurrentOrgs();
		CoreOrg currentOrg = null;
		for (CoreOrg org : orgs) {
			if (orgId == org.getId()) {
				currentOrg = org;
				break;
			}
		}
		if (currentOrg == null) {
			// 非法切换
			return JsonResult.failMessage("切换到不存在的部门");
		}

		httpRequestLocal.setSessionValue(CorePlatformService.ACCESS_CURRENT_ORG, currentOrg);

		return JsonResult.success();
	}

	@PostMapping(MODEL + "/changePassword.json")
	@ResponseBody
	public JsonResult chnagePassword(String password, String newPassword) {
		CoreUser temp = platformService.getCurrentUser();
		CoreUser realUser = userService.getUserById(temp.getId());
		String pwd = passwordEncryptService.password(password);
		if (realUser.getPassword().equals(pwd)) {
			realUser.setPassword(passwordEncryptService.password(newPassword));
			userService.update(realUser);
			return JsonResult.success();
		} else {
			return JsonResult.failMessage("密码错误");
		}

	}

	/**
	 * 用户能查看的菜单
	 * 
	 * @return
	 */
	@PostMapping(MODEL + "/menu/menuTree.json")
	@ResponseBody
	public JsonResult<List<MenuNodeView>> menus() {
		CoreUser currentUser = platformService.getCurrentUser();
		Long orgId = platformService.getCurrentOrgId();
		MenuItem item = platformService.getMenuItem(currentUser.getId(), orgId);
		List<MenuNodeView> view = this.build(item);
		return JsonResult.success(view);
	}

	/**
	 * 获取系统
	 * 
	 * @return
	 */
	@PostMapping(MODEL + "/menu/system.json")
	@ResponseBody
	public JsonResult<List<SystemMenuView>> getSystem() {
		CoreUser currentUser = platformService.getCurrentUser();
		Long orgId = platformService.getCurrentOrgId();
		MenuItem menuItem = platformService.getMenuItem(currentUser.getId(), orgId);
		List<MenuItem> list = menuItem.getChildren();
		List<SystemMenuView> systems = new ArrayList<SystemMenuView>();
		for (MenuItem item : list) {
			systems.add(new SystemMenuView(item.getId(), item.getData().getCode(), item.getData().getName()));
		}
		return 	JsonResult.success(systems);
	}

	/**
	 * 获取系统对应的菜单树
	 * 
	 * @return
	 */
	@PostMapping(MODEL + "/menu/systemMenu.json")
	@ResponseBody
	public JsonResult<List<MenuNodeView>> getMenuBySystem(long systemId) {
		CoreUser currentUser = platformService.getCurrentUser();
		Long orgId = platformService.getCurrentOrgId();
		MenuItem menuItem = platformService.getMenuItem(currentUser.getId(), orgId);
		MenuItem item = menuItem.findChild(systemId);
		List<MenuNodeView> view = this.build(item);
		return JsonResult.success(view);
	}

	/**
	 * 用户所在公司的组织机构树
	 * 
	 * @return
	 */
	@PostMapping(MODEL + "/org.json")
	@ResponseBody
	public JsonResult<OrgItem> getUserCompany() {
		OrgItem orgItem = platformService.getUserOrgTree();
		return JsonResult.success(orgItem);
	}

	/**
	 * 获取系统的菜单树
	 * 
	 * @return
	 */
	@PostMapping(MODEL + "/menu/tree.json")
	@ResponseBody
	public JsonResult<List<MenuNodeView>> getMenuTree() {
		MenuItem menuItem = platformService.buildMenu();
		List<MenuNodeView> view = this.build(menuItem);
		return JsonResult.success(view);
	}
	/**
	 * 获取功能点树
	 * @return
	 */
	@PostMapping(MODEL + "/function/tree.json")
    @ResponseBody
    public JsonResult<List<FunctionNodeView> > getFunctionTree() {
    		FunctionItem root = this.platformService.buildFunction();
    		List<FunctionNodeView> tree = buildFunctionTree(root);
    		return JsonResult.success(tree);
       
    }

	
	private List<MenuNodeView> build(MenuItem node) {
		List<MenuItem> list = node.getChildren();
		if (list.size() == 0) {
			return Collections.EMPTY_LIST;
		}

		List<MenuNodeView> views = new ArrayList<MenuNodeView>(list.size());

		for (MenuItem item : list) {
			MenuNodeView view = new MenuNodeView();
			view.setCode(item.getData().getCode());
			view.setName(item.getData().getName());
			view.setIcon(item.getData().getIcon());
			view.setId(item.getData().getId());
			view.setPath((String) item.getData().get("accessUrl"));
			List<MenuNodeView> children = this.build(item);
			view.setChildren(children);
			views.add(view);
		}
		return views;
	}
	
    private List<FunctionNodeView> buildFunctionTree(FunctionItem node){
  		List<FunctionItem> list = node.getChildren();
      	if(list.size()==0){
      		return Collections.EMPTY_LIST;
      	}
      	List<FunctionNodeView> views = new ArrayList<FunctionNodeView>(list.size());
      	for(FunctionItem item :list){
      		FunctionNodeView view = new FunctionNodeView();
      		view.setCode(item.getData().getCode());
      		view.setName(item.getData().getName());
      		view.setId(item.getData().getId());
      		view.setAccessUrl(item.getData().getAccessUrl());
      		List<FunctionNodeView> children = this.buildFunctionTree(item);
      		view.setChildren(children);
      		views.add(view);
      	}
      	return views;
     }

}
