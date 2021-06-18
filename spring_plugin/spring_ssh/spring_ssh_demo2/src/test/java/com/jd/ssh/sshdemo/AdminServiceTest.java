/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.jd.ssh.sshdemo;

import java.util.List;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.jd.ssh.sshdemo.bean.SpringClassTest;
import com.jd.ssh.sshdemo.entity.Admin;
import com.jd.ssh.sshdemo.service.AdminService;
import com.jd.ssh.sshdemo.test.spring.SpringTransactionalTestCase;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class AdminServiceTest extends SpringTransactionalTestCase {

	@Autowired
	private AdminService adminService;
	@Autowired
	private SpringClassTest springClassTest;
	private static Logger logger = LoggerFactory.getLogger(AdminServiceTest.class);
	
	@Test
	public void findTasksByUserId() throws Exception {
		
		List<Admin> list = adminService.getAll();
		
		logger.info("ok: " + list.size());
		/*Page<Task> tasks = taskDao.findByUserId(2L, new PageRequest(0, 100, Direction.ASC, "id"));
		assertThat(tasks.getContent()).hasSize(5);
		assertThat(tasks.getContent().get(0).getId()).isEqualTo(1);

		tasks = taskDao.findByUserId(99999L, new PageRequest(0, 100, Direction.ASC, "id"));
		assertThat(tasks.getContent()).isEmpty();
		assertThat(tasks.getContent()).isEmpty();*/
	}
	
	@Test
	public void password(){
		Admin user = new Admin();
		user.setPassword("password");
		
		adminService.entryptPassword(user);
		
		logger.info("password: " + user.getPassword() + " -- "+ user.getSalt());
	}
}
