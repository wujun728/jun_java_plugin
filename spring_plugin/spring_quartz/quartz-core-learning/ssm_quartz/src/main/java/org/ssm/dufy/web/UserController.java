package org.ssm.dufy.web;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.ssm.dufy.constant.CommonConstant;
import org.ssm.dufy.entity.User;
import org.ssm.dufy.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;
	
	@RequestMapping(value="/toLogin",method=RequestMethod.GET)
	public String toLogin(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//若用户登录跳转到JobList页面。这里可以抽象出来一个公共的方法！
		String toke = (String) request.getSession().getAttribute("toke");
		if(!StringUtils.isEmpty(toke)){
			response.sendRedirect(request.getContextPath() + "/quartz/listJob");
		}
		return "user/login";
		
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(@RequestParam("username") String username,@RequestParam("password") String password,
			HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		User u = new User(username, password);
		User user = userService.getUser(u);
		if(user != null){
			//登录成功
			HttpSession session = request.getSession();
			session.setAttribute(CommonConstant.LONGIN_TOKE, UUID.randomUUID().toString().replace("-", ""));
			session.setAttribute("name", username);
			response.sendRedirect(request.getContextPath() + "/quartz/listJob");
		}else{
			//跳转回登录页面
			request.setAttribute("message","用户名或者密码错误！");
		}
		
		return "user/login";
	}
	
	@RequestMapping(value="logout")
	public String logout(HttpServletRequest request ){
		HttpSession session = request.getSession();
		session.removeAttribute(CommonConstant.LONGIN_TOKE);;
		return "redirect:/user/toLogin";
	}
	
	
}
