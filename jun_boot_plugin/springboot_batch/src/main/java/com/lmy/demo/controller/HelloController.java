package com.lmy.demo.controller;

import java.util.List;

import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lmy.demo.pojo.PostPojo;
import com.lmy.demo.rest.RestService;
import com.lmy.demo.utils.JSONUtils;

@RestController
public class HelloController {

	@Autowired
	RestService restService;
	
	@GetMapping("/hello")
	public String sayHello(){
		String result=restService.getPosts();
		List<PostPojo> stringToList = JSONUtils.stringToList(result, new TypeReference<List<PostPojo>>() {});
    	System.out.println(stringToList);
    	return result;
	}
}
