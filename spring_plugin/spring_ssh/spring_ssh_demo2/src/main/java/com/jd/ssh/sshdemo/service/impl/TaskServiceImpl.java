package com.jd.ssh.sshdemo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jd.ssh.sshdemo.dao.TaskDao;
import com.jd.ssh.sshdemo.entity.Task;
import com.jd.ssh.sshdemo.service.TaskService;

/**
 * Service实现类 - 管理员
 * ----------------------------------------------------------------------------
 * 版权所有 2013 qshihua。
 * ----------------------------------------------------------------------------
 * 
 * @author qshihua
 * 
 * @version 0.1 2013-01-06
 */

@Service
public class TaskServiceImpl extends BaseServiceImpl<Task, String> implements TaskService {

	@Resource
	private TaskDao taskDao;
	@Resource
	public void setBaseDao(TaskDao taskDao) {
		super.setBaseDao(taskDao);
	}

}