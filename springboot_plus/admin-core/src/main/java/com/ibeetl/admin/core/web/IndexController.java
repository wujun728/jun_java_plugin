package com.ibeetl.admin.core.web;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ibeetl.admin.core.entity.CoreOrg;
import com.ibeetl.admin.core.entity.CoreUser;
import com.ibeetl.admin.core.rbac.UserLoginInfo;
import com.ibeetl.admin.core.rbac.tree.MenuItem;
import com.ibeetl.admin.core.service.CorePlatformService;
import com.ibeetl.admin.core.service.CoreUserService;
import com.ibeetl.admin.core.util.HttpRequestLocal;
import com.ibeetl.admin.core.util.PlatformException;

@Controller
public class IndexController {

	@Autowired
	CorePlatformService platformService;

	@Autowired
	CoreUserService userService;

	@Autowired
	HttpRequestLocal httpRequestLocal;

	@RequestMapping("/")
	public ModelAndView login() {
		ModelAndView view = new ModelAndView("/login.html");
		return view;
	}

	

	@PostMapping("/login.do")
	public ModelAndView login(String code, String password) {
		UserLoginInfo info = userService.login(code, password);
		if (info == null) {
			throw new PlatformException("用户名密码错");
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
		ModelAndView view = new ModelAndView("redirect:/index.do");
		return view;
	}
	
	@RequestMapping("/index.do")
	public ModelAndView index() {
		ModelAndView view = new ModelAndView("/index.html");
		CoreUser currentUser = platformService.getCurrentUser();
		Long orgId = platformService.getCurrentOrgId();
		MenuItem menuItem = platformService.getMenuItem(currentUser.getId(), orgId);
		view.addObject("menus", menuItem);
		return view;
	}

	@RequestMapping("/logout.do")
	public ModelAndView logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Enumeration eum = session.getAttributeNames();
		while(eum.hasMoreElements()) {
			String key = (String)eum.nextElement();
			session.removeAttribute(key);
		}
		ModelAndView view = new ModelAndView("redirect:/");
		return view;
	}
	@RequestMapping("/changeOrg.do")
	public ModelAndView changeOrg(HttpServletRequest request,Long orgId) {
		platformService.changeOrg(orgId);
		ModelAndView view = new ModelAndView("redirect:/index.do");
		return view;
	}

}
