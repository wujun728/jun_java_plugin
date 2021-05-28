package com.diamond.wm.accesstoken.api;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diamond.wm.accesstoken.exception.WechatRequestException;
import com.diamond.wm.accesstoken.manager.AccessTokenManager;

@RestController
public class IndexController {
	
	@Resource
	AccessTokenManager manager;
	
	@RequestMapping("/")
	public String getToken(String appid,String secret) throws WechatRequestException{
		return "Wechat accesstoken manager center; Accesstoken size: "+manager.getAccessSize()+
				"<br/>" +
				"Date:"+new Date();
	}
}
