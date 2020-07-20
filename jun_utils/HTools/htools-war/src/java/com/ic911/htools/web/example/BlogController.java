package com.ic911.htools.web.example;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ic911.htools.entity.example.Blog;
import com.ic911.htools.service.example.BlogService;

@Controller
@RequestMapping("/example/blog")
public class BlogController {

	@Autowired
	private BlogService blogService;
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String index(HttpServletRequest request) {
		request.setAttribute("blogs", blogService.findAll());
		return "/example/blog/index";
	}

	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String save(Blog blog,HttpServletRequest request){
		if(blog!=null){
			blog.setPublishDate(new Date());
			blogService.save(blog);
		}
		return "redirect:/example/blog/index";
	}
}
