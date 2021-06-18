package com.drore.jsonp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	
	@RequestMapping(value="/render.json")
	public List<Map<String, Object>> render(){
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		for (int i = 0; i < 10; i++) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("userName", "张三"+i);
			map.put("sex", "男");
			map.put("phone", "1598723212"+i);
			list.add(map);
		}
		return list;
	}
}
