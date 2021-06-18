package spring_mvc.controller;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import spring_mvc.model.user;

@Controller
public class conversionTest {
	@RequestMapping("/date")
	public  String conversion(@DateTimeFormat(iso=ISO.DATE) Date date,Model model){
		System.out.println(date);
		model.addAttribute("date", date);
		return "hello/formatterTest";
	}
	@RequestMapping("/user")
	public  String user(user user,Model model){
		System.out.println(user);
		model.addAttribute("user", user);
		return "hello/formatterTest";
	}
	@RequestMapping("/user2")
	public  String user2(user user,Model model){
		System.out.println(user);
		model.addAttribute("user", user);
		return "hello/formatterTest";
	}
	@RequestMapping("/bool")
	public @ResponseBody String bool(boolean bool){
		System.out.println(bool);
		return "ok";
	}
}
