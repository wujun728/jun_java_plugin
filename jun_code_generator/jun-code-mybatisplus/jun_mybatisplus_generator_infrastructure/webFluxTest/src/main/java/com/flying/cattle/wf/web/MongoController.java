package com.flying.cattle.wf.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSON;
import com.flying.cattle.wf.entity.User;
import com.flying.cattle.wf.service.MongoService;
import com.flying.cattle.wf.service.impl.RedisGenerateId;
import com.flying.cattle.wf.utils.ValidationResult;
import com.flying.cattle.wf.utils.ValidationUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/mongo")
public class MongoController {
	
	public final static String USER_KEY="user";
	
	@Autowired
	private RedisGenerateId redisGenerateId;
	
	@Autowired
	private MongoService mongoService;
	
	@GetMapping("/getId")
	public Long getUserId(){
		return redisGenerateId.generate(USER_KEY);
	}

	@GetMapping("/getById")
	public Mono<User> getById(Long id) {
		return mongoService.getById(id);
	}
	@GetMapping("/add")
	public Mono<User> add(User user) {
		user = new User();
		user.setAccount("admin1");
		user.setPassword("123123");
		user.setNickname("admin");
		user.setEmail("505237@qq.com");
		user.setPhone("13666275002");
		user.setSex(true);
		String bd = "1990-01-01";
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
		ValidationResult vr = ValidationUtils.validateEntity(user);
		if (!vr.isHasErrors()) {
			user.setId(getUserId());
			System.out.println(JSON.toJSONString(user));
			return mongoService.addUser(user);
		} else {
			 System.err.println(vr.getFirstErrors());
		}
		return null;
	}
	
	/**
	 *	注意这里produces = MediaType.APPLICATION_STREAM_JSON_VALUE
	 *	如果不是application/stream+json则调用端无法滚动得到结果，将一直阻塞等待数据流结束或超时。
	 */
	@GetMapping(value="/findAll",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
	public Flux<User> findAll(){
		return mongoService.findAllUser();
	}
}
