package boot.spring.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import boot.spring.service.LoginService;



@Controller
public class Login {
	@Autowired
	LoginService loginservice;
	
	@RequestMapping(value="/loginvalidate",method=RequestMethod.POST)
	public String loginvalidate(@RequestParam("username") String username,@RequestParam("password") String pwd,HttpSession httpSession){
		if(username==null)
			return "login";
		String realpwd=loginservice.getpwdbyname(username);
		if(realpwd!=null&&pwd.equals(realpwd))
		{
			httpSession.setAttribute("username", username);
			return "showactor";
		}else
			return "fail";
	}
	
	
	@RequestMapping("/logout")
	public String logout(HttpSession httpSession){
		httpSession.removeAttribute("username");
		return "login";
	}
	
	@RequestMapping("/login")
	String login(){
		return "login";
	}
  }
