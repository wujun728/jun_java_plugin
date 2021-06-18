package spring_mvc.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import spring_mvc.model.user;
@Controller
@SessionAttributes("user2")
public class AccountController {
	@RequestMapping(value="/hello/hellow")
	public String helloWorld(String name,Model model
			,Map<String,Object> map,@ModelAttribute("user2")user user2){
		System.out.println("姓名："+name);
		model.addAttribute("user",user2);
		map.put("time", new Date());
		System.out.println(user2);
		return "hello/hellow";
	}
	@RequestMapping("/helloworld")
	public String helloworld(){
		return "helloworld";
	}
	@ModelAttribute("user2")
	public user getUser(){
		user user=new user();
		user.setName("kl");
		return user;
	}
	@RequestMapping(value="/helloworld/{name1}")
	public String helloWorld(user user1,@PathVariable("name1") String name,Model model
			,Map<String,Object> map,@ModelAttribute("user2")user user2){
		System.out.println("user1:"+user1);
		helloWorld(name, model, map, user2);
		return "helloworld";
	}
	@InitBinder("teacher")
	public void teacher(WebDataBinder wb){
		wb.setFieldDefaultPrefix("teacher.");
	}
	@InitBinder("user2")
	public void user2(WebDataBinder wb){
		wb.setFieldDefaultPrefix("");
	}
}
