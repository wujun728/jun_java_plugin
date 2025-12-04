package com.flying.cattle.wf.web;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flying.cattle.wf.aid.AbstractController;
import com.flying.cattle.wf.entity.User;
import com.flying.cattle.wf.service.UserService;
import com.flying.cattle.wf.utils.ValidationResult;
import com.flying.cattle.wf.utils.ValidationUtils;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController extends AbstractController<UserService, User>{
	
	@GetMapping("/add")
	public Mono<User> add() {
		User user = new User();
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
			return save(user);
		} else {
			 System.err.println(vr.getFirstErrors());
		}
		return null;
	}
}
