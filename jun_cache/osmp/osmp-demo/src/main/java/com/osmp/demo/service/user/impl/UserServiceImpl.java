/*   
 * Project: OSMP
 * FileName: UserServiceImpl.java
 * version: V1.0
 */
package com.osmp.demo.service.user.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osmp.jdbc.support.JdbcDao;
import com.osmp.cache.define.annotation.Cacheable;
import com.osmp.demo.service.user.UserService;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年11月28日 下午5:28:39
 */
@Service
public class UserServiceImpl implements UserService {

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private JdbcDao jdbcDao;
	
	@Cacheable(name="demo测试缓存获取用户名称",timeToIdle=300,timeToLive=300)
	@Override
	public String getUserName(String name) {
		logger.info("==============demo test =========="+name);
		 //jdbcDao.update("jwms.update.area.time", "jwms", new Date(),"app001301");
		return "我的名字叫:" + name;
	}

	@Cacheable(name="demo测试缓存获取用户年龄",timeToIdle=300,timeToLive=300)
	@Override
	public String getUserAge(String age) {
		return "我今年 : " + age + " 岁啦!";
	}

}
