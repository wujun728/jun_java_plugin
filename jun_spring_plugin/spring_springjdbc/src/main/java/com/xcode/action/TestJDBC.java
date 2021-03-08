package com.xcode.action;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xcode.beans.User;
import com.xcode.dao.UserDao;
import com.xcode.server.UserServer;

@Controller
public class TestJDBC {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserServer userServer;
	
	private List<User> users=null;
	
	@RequestMapping("/test")
	public ModelAndView test(){
		System.out.println("sucess");

		List<User> users=userDao.getAllUsers();
		
		System.err.println(users);
		
//		return "sucess";
		
		String viewName = "sucess";//

	    ModelAndView modelAndView = new ModelAndView(viewName);

	    modelAndView.addObject("list", users);

	    return modelAndView;
	}
	
	public void print_userDao(){
		System.out.println(userDao);
//		User user=userDao.getUserById(1);
//		List<User> users=userDao.getAllUsers();
//		System.out.println("users");
//		System.out.println(users);
//		System.out.println(user.getId()+"  "+user.getUsername()+"  "+user.getPassword());
		
		User user=new User();
		user.setId(9);
		user.setUsername("xb");
		user.setPassword("123");
//		userDao.addUser(user);
		
//		userDao.deleteUserById(8);
//		userDao.update(user);	
		
	}
	
	public void print_userServer(){
		System.out.println("userServer sucess");
		System.out.println(userServer);
		users=userServer.getAllUsers();
		System.out.println(users);		
//		User user=userServer.getUserById(1);
//		System.out.println(user.getId()+"  "+user.getUsername()+"  "+user.getPassword());		
	}	
	
	public List<User> getUsers(){
		return users;
	}
	
	

}
