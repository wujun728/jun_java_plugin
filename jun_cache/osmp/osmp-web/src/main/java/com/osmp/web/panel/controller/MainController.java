/*   
 * Project: OSMP
 * FileName: MainController.java
 * version: V1.0
 */
package com.osmp.web.panel.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.osmp.web.user.entity.SysPrivilege;
import com.osmp.web.user.service.SysPrivilegeService;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年10月13日 下午1:21:02
 */
@Controller
@RequestMapping("/main")
public class MainController {

	@Autowired
	private SysPrivilegeService pservice;

	@RequestMapping("/login")
	public String login(@RequestParam(value = "username", defaultValue = "", required = false) String username,
			@RequestParam(value = "password", defaultValue = "", required = false) String password,
			HttpSession session, HttpServletResponse response, HttpServletRequest request, PrintWriter out) {
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-Type", "text/html;charset=utf-8");

		if ("".equals(username)) {
			out.print("<script>alert('账号或密码不正确');history.back(1)</script>");
			out.flush();
			return null;
		}
		return "redirect:main.do";

	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		return "redirect:../login.jsp";
	}

	@RequestMapping("/main")
	public void main() {

	}

	@RequestMapping("/left")
	public ModelAndView left() {
		ModelAndView mav = new ModelAndView();
		List<SysPrivilege> menuList = pservice.getList();
		mav.addObject("menuList", menuList);
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
