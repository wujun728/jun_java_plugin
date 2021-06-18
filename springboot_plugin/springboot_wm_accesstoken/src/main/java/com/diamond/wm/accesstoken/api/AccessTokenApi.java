package com.diamond.wm.accesstoken.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diamond.wm.accesstoken.manager.AbstractTokenManager;
import com.diamond.wm.accesstoken.manager.AccessTokenManager;

@RestController
@RequestMapping("token")
public class AccessTokenApi extends AbstractTokenController{
	
	@Resource
	AccessTokenManager manager;

	@Override
	AbstractTokenManager getManager() {
		return manager;
	}
	
}
