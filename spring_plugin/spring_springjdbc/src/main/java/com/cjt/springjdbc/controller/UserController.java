package com.cjt.springjdbc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cjt.springjdbc.model.User;
import com.cjt.springjdbc.service.IUserService;

@Controller
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "redirect:/users";
	}
	
	//所有用户一览
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String showAllUsers(Model model) {
		List<User> userlist = userService.findAll();
		System.out.println(userlist);
		model.addAttribute("users", userlist);		
		return "users/list";
	}
	
	//显示添加用户form
	@RequestMapping(value = "/add/user", method = RequestMethod.GET)
	public String addUser(Model model) {
		//设置用户表单默认值
		User user = new User();
		user.setName("如：李自成");
		user.setAddress("如：西安");
		model.addAttribute("userForm", user);
		return "users/userform";
	}
	
	//保存用户
	@RequestMapping(value = "/save/user", method = RequestMethod.POST)
	public String addUser(User user) {
		//设置用户表单默认值
		System.out.println(user.getName()+user.getAddress());
		userService.saveOrUpdate(user);
		return "redirect:/users";
	}
	
	//删除用户
	@RequestMapping(value = "/delete/user/{id}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable("id") int id) {
		userService.delete(id);
		return "redirect:/users";
	}
	
	//编辑用户
	@RequestMapping(value= "/edit/user/{id}", method = RequestMethod.GET)
	public ModelAndView updateUser(@PathVariable("id") int id){
		User user = userService.findById(id);
		ModelAndView mv = new ModelAndView("users/userform");
		mv.addObject("userForm", user);
		return mv;
	}	
}
