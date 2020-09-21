/*   
 * Project: OSMP
 * FileName: PanelController.java
 * version: V1.0
 */
package com.osmp.web.panel.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.osmp.web.base.core.SystemConstant;
import com.osmp.web.base.core.SystemGlobal;
import com.osmp.web.panel.service.LoginService;
import com.osmp.web.user.dao.UserMapper;
import com.osmp.web.user.entity.SysPrivilege;
import com.osmp.web.user.entity.User;
import com.osmp.web.user.service.SysPrivilegeService;
import com.osmp.web.user.service.UserService;
import com.osmp.web.utils.DESEncoder;
import com.osmp.web.utils.Md5Encode;
import com.osmp.web.utils.Utils;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年10月13日 下午1:21:02
 */
@Controller
@RequestMapping("/panel")
public class PanelController {

	@Autowired
	private LoginService loginService;

	@Autowired
	private SysPrivilegeService pservice;

	@Autowired
	private UserService userService;

	@Autowired
	private UserMapper userMapper;

	@RequestMapping("/login")
	public String login(@RequestParam(value = "username", defaultValue = "", required = false) String username,
			@RequestParam(value = "password", defaultValue = "", required = false) String password,
			HttpSession session, HttpServletResponse response, HttpServletRequest request, PrintWriter out) {
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		if (Utils.isEmpty(username)) {
			return "redirect:../login.jsp";
		} else {
			if (Utils.isEmpty(username) && Utils.isEmpty(password)) {
				return "redirect:../login.jsp";
			}
		}
		String pass = password;
		password = Md5Encode.encodeByMD5(password).toUpperCase();
		Map<String, Object> map = loginService.login(request, username, password);
		boolean temp = (boolean) map.get("flag");
		if (!temp) {// 登陆失败
			return "redirect:../login.jsp";
		} else {
			User user = (User) map.get("LoginUser");
			try {
				user.setPassword(DESEncoder.getInstance().encode(Utils.base64Encode(pass)));
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute(SystemConstant.GLOB_MES, "加密用户信息异常");
				return "redirect:../login.jsp";
			}
			SystemGlobal.setLoginUser(user);
			userMapper.updateLoadIp(user.getId(), Utils.getRemoteHost(request));
		}

		/*
		 * if ("".equals(username)) {
		 * out.print("<script>alert('账号或密码不正确');history.back(1)</script>");
		 * out.flush(); return null; }
		 */
		return "redirect:main.do";

	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		Object object = session.getAttribute(SystemConstant.LOGIN_USER);
		if (object != null) {
			try {
				session.removeAttribute(SystemConstant.LOGIN_USER);
			} catch (Exception e) {
				object = null;
			}
		}
		return "redirect:../login.jsp";
	}

	@RequestMapping("/main")
	public void main() {

	}

	/**
	 * 得到菜单列表
	 * 
	 * @return
	 */
	@RequestMapping("/left")
	public ModelAndView left() {
		ModelAndView mav = new ModelAndView();
		List<SysPrivilege> menuList = pservice.getList();
		User loginUser = SystemGlobal.currentLoginUser();
		if (userService.isAdminRole(loginUser.getId())) {
			mav.addObject("menuList", menuList);
		} else {
			List<SysPrivilege> userMenuList = new ArrayList<SysPrivilege>();
			List<Integer> lis = userService.selectUserPrivilege(loginUser.getId());
			for (SysPrivilege sp : menuList) {
				int spId = sp.getId();
				if (lis.contains(spId)) {
					userMenuList.add(sp);
				}
			}
			mav.addObject("menuList", userMenuList);
		}

		return mav;
	}

	@RequestMapping("/north")
	public ModelAndView north() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("username", "admin");
		return mav;
	}

	@RequestMapping("/welcome")
	public void welcome() {

	}

}
