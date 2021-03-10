package com.diamond.wm.accesstoken.api;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.diamond.wm.accesstoken.entity.AccessToken;
import com.diamond.wm.accesstoken.exception.WechatRequestException;
import com.diamond.wm.accesstoken.manager.AbstractTokenManager;

public abstract class AbstractTokenController {

	abstract AbstractTokenManager getManager();
	
	@RequestMapping("get/{appid}/{secret}")
	public AccessToken getToken(@PathVariable String appid,@PathVariable  String secret) throws WechatRequestException{
		AccessToken token = getManager().getToken(appid,secret);
		return token;
	}
	
	@RequestMapping("getstr/{appid}/{secret}")
	public String getTokenOnly(@PathVariable String appid,@PathVariable  String secret) throws WechatRequestException{
		return getToken(appid, secret).getAccessToken();
	}

	@RequestMapping("remove/{appid}/{secret}")
	public String remove(@PathVariable String appid,@PathVariable  String secret){
		getManager().removeToken(appid, secret);
		return "success";
	}
	
	@RequestMapping("flush/{appid}/{secret}")
	public AccessToken flush(@PathVariable String appid,@PathVariable  String secret) throws WechatRequestException{
		return getManager().flushToken(appid, secret);
	}
	
	@ExceptionHandler(WechatRequestException.class)
	public String wechatRequestExceptionHandler(WechatRequestException ex){
		return ex.getMessage();
	}
}
