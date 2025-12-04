/**
 * @filename:User 2019年6月1日
 * @project webFlux-redis  V1.0
 * Copyright(c) 2018 BianPeng Co. Ltd. 
 * All right reserved. 
 */
package com.flying.cattle.wf.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.flying.cattle.wf.entity.User;
import com.flying.cattle.wf.service.RedisService;
import com.flying.cattle.wf.service.impl.RedisGenerateId;
import com.flying.cattle.wf.utils.ValidationResult;
import com.flying.cattle.wf.utils.ValidationUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**   
 * Copyright: Copyright (c) 2019 
 * com.flying.cattle.wfRedis.service.impl
 * <p>说明： 用户API接口层</P>
 * @version: V1.0
 * @author: BianPeng
 * 
 */
@RestController
@RequestMapping("/redis")
public class RedisController {
	
	public final static String USER_KEY="user";
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private RedisGenerateId redisGenerateId;
	
	@GetMapping("/getId")
	public Long getUserId(){
		return redisGenerateId.generate(USER_KEY);
		
	}
	
	public String getKey(Long id) {
		return USER_KEY+"_"+id;
	}
	
	@GetMapping("/getById/{id}")
	public Mono<Object> getUserById(@PathVariable("id")Long id){
		return redisService.getById(getKey(id));
	}
	
	@GetMapping("/add")
	public Mono<Object> add(User user){
		user = new User();
		user.setAccount("admin1");
		user.setPassword("123123");
		user.setNickname("admin");
		user.setEmail("505237@qq.com");
		user.setPhone("13666275002");
		user.setSex(true);
		String bd="1990-01-01";
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			user.setBirthday(fmt.parse(bd));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user.setProvince("四川省");
		user.setCity("成都市");
		user.setCounty("高新区");
		user.setAddress("天 府大道XXd段XX号");
		user.setState("1");
		// 以上是模拟数据
		ValidationResult vr=ValidationUtils.validateEntity(user);
		if (!vr.isHasErrors()) {
			user.setId(getUserId());
			System.out.println(JSON.toJSONString(user));
			return redisService.addUser(getKey(user.getId()),user);
		}else {
			return Mono.just(vr.getFirstErrors());
		}
		
	}
	
	@GetMapping("/addlist")
	public Mono<Long> addlist(){
		List<Object> list=new ArrayList<Object>();
		User user = new User();
		user.setAccount("admin1");
		user.setPassword("123123");
		user.setNickname("admin");
		user.setEmail("505237@qq.com");
		user.setPhone("13666275002");
		user.setSex(true);
		user.setBirthday(new Date());
		user.setProvince("四川省");
		user.setCity("成都市");
		user.setCounty("高新区");
		user.setAddress("天 府大道XXd段XX号");
		user.setState("1");
		//添加第一条数据
		Long id=redisGenerateId.generate("user");
		user.setId(id);
		list.add(JSON.toJSONString(user));
		//添加第二条数据
		id=redisGenerateId.generate("user");
		user.setId(id);
		list.add(JSON.toJSONString(user));
		//添加第三条数据
		id=redisGenerateId.generate("user");
		user.setId(id);
		list.add(user);
		
		return redisService.addlist("list", list);
	}
	
	/**
	 *	这个就是流响应式的接口了，是一个一个的返回数据的，异步返回 
	 *  delayElements(Duration.ofSeconds(2))这个是不要的，只是方便看效果
	 *  redis 直接就是一个一个返回，不需要produces，不知道为什么...还木有深究。
	 */
	@GetMapping(value="/findAll",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<Object> findAll(){
		return redisService.findAll("list").delayElements(Duration.ofSeconds(2));
	}
	
	@GetMapping("/getUsers")
	public Flux<Object> findUsers() {
		// TODO Auto-generated method stub
		return redisService.findUsers(USER_KEY+"_"+"*").delayElements(Duration.ofSeconds(2));
	}
}
