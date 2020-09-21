/*   
 * Project: OSMP
 * FileName: TestServiceImpl.java
 * version: V1.0
 */
package com.osmp.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.osmp.intf.define.model.Parameter;
import com.osmp.intf.define.service.BaseDataService;
import com.osmp.demo.service.user.UserService;
import com.osmp.demo.service.user.entity.User;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年9月26日 下午3:03:55
 */
@Component
public class TestServiceImpl implements BaseDataService {

	@Autowired
	private UserService userservice;

	@Override
	public Object execute(Parameter parameter) {
		String name = parameter.getQueryMap().get("name");
		String age = parameter.getQueryMap().get("age");
		userservice.getUserAge(age);
		userservice.getUserName(name);
		User u = new User();
		u.setAge(userservice.getUserAge(age));
		u.setName(userservice.getUserName(name));
		return u;
	}

}
