package spring_mvc.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import spring_mvc.model.user;
import spring_mvc.vo.msg;

@Controller
public class JacksonController {
	@RequestMapping("/jack")
	public String Jackson(){
		return "jackson";
	}
	@ResponseBody
	@RequestMapping(value="/jackson",method=RequestMethod.GET)
	public msg regex(String username){
		msg msg=new msg(500,"账号已存在");
		if(!"kangli".equalsIgnoreCase(username)){
			msg=new msg(200,"此账号可用");
		}
		return msg;
	}
	@ResponseBody
	@RequestMapping(value="/jackson",method=RequestMethod.POST)
	public  user Jackson2(){
		user user=new user("11","康力",12.80,"1997-08-07");
		return user;
	}
	@ResponseBody
	@RequestMapping("/getusers")
	public List<user> getusers(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		List<user> users=new ArrayList<user>();
		users.add(new user("11","康力1",192.80,"1999-08-06"));
		users.add(new user("12","康力2",123.80,sdf.format(new Date())));
		users.add(new user("13","康力3",122.80,"1987-08-01"));
		users.add(new user("14","康力4",11142.80,sdf.format(new Date())));
		users.add(new user("15","康力5",162.80,"2016-10-10"));
		return users;
	}
	@ResponseBody
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public msg register(String username,String password,@RequestBody user user){
		msg msg=new msg(500,"注册失败");
		if(!("kangli".equalsIgnoreCase(user.getUsername())||"kangli".equalsIgnoreCase(username))){
			msg=new msg(200,"注册成功");
		}
		return msg;
	}
}
