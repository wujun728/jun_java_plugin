package com.abc.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.abc.demo.domain.User;
import com.abc.demo.service.UserService;

@Controller
public class UserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private UserService userService;
	//根据ID查找用户
	@RequestMapping(value="/findById/{id}",method=RequestMethod.GET)
	public String findById(@PathVariable("id")int id,Model m) {
		LOGGER.info("访问findById id:"+id);
		User user = userService.findById(id);
		m.addAttribute("user", user);
		System.out.println(user);
		
		return "user";
	}
	
	//查找所有用户
	@RequestMapping(value="/findByAll",method=RequestMethod.GET)
	public String findByAll(Model m) {
		LOGGER.info("访问findByAll");
		List<User> userAll = userService.findByAll();
		m.addAttribute("users", userAll);
		System.out.println(userAll);
		return "user_list";
	}
	//访问新增用户
	@GetMapping("/user_for")
	public String userForAdd() {
		
		return "user_add";
	}
	//新增用户
	@RequestMapping(value="/insert",method=RequestMethod.POST)
	public String insertUser(User user) {
		LOGGER.info("访问insertUser user:"+user);
		userService.insert(user);
		return "SUCCESS";
	}
	
	//修改用户
	@Transactional
	@RequestMapping(value= {"/update","/update/{id}"})
	public String updateUser(@PathVariable(value="id",required=false)Integer id, User user,Model m){
		if(id!=null) {
			User u = userService.findById(id);
			m.addAttribute("user",u);
			return "user_update";
		}else {
			
			LOGGER.info("访问upadteUser user:"+user);
			userService.update(user);
			return "success";
		}
		
	}


	//根据ID删除用户
	@RequestMapping(value="/delete/{id}")
	public String deleteUser(@PathVariable("id")int id) {
		LOGGER.info("访问deleteUser id:"+id);
		userService.delete(id);
		return "SUCCESS";
	}
}
