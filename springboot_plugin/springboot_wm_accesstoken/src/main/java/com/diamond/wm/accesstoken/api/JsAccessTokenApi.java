package com.diamond.wm.accesstoken.api;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diamond.wm.accesstoken.manager.AbstractTokenManager;
import com.diamond.wm.accesstoken.manager.JSAccessTokenManager;

@RestController
@RequestMapping("jstoken")
public class JsAccessTokenApi extends AbstractTokenController{
	
	@Resource
	JSAccessTokenManager manager;

	@Override
	AbstractTokenManager getManager() {
		return manager;
	}
	
 
}
