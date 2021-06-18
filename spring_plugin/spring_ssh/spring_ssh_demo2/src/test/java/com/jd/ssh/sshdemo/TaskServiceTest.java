/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.jd.ssh.sshdemo;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.jd.ssh.sshdemo.bean.SpringClassTest;
import com.jd.ssh.sshdemo.entity.Admin;
import com.jd.ssh.sshdemo.entity.Task;
import com.jd.ssh.sshdemo.search.SearchHelper;
import com.jd.ssh.sshdemo.service.AdminService;
import com.jd.ssh.sshdemo.service.TaskService;
import com.jd.ssh.sshdemo.test.spring.SpringTransactionalTestCase;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class TaskServiceTest extends SpringTransactionalTestCase {

	@Autowired
	private AdminService adminService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private SpringClassTest springClassTest;
	
	private static Logger logger = LoggerFactory.getLogger(TaskServiceTest.class);
	
	@Test
	public void createTask() throws Exception {
		
		List<Admin> list = adminService.getAll();
		
		logger.info("ok: " + list.size());
		
		List<Task> tasks = taskService.getAll();
		assertNotNull(tasks);

		String text = "android 刷机";
        long ct = System.currentTimeMillis();
        for(String word : SearchHelper.splitKeywords(text)){
            System.out.println(word);
        }
        System.out.printf("TIME %d\n", (System.currentTimeMillis() - ct));
	}
}
