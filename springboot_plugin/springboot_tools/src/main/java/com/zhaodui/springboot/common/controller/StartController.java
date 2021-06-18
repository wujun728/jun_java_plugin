package com.zhaodui.springboot.common.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Controller;

/**
 * 容器启动后执行的方法
 *
 * @author Wujun
 */
@Controller
public class StartController implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	private SecurityManager manager;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
			SecurityUtils.setSecurityManager(manager);
		}
	}
}
