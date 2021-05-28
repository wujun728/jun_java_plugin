package com.diamond.wm.accesstoken.manager;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.diamond.wm.accesstoken.config.AutoTokenConfig;
import com.diamond.wm.accesstoken.entity.AccessToken;
import com.diamond.wm.accesstoken.enums.AccessTokenStatus;
import com.diamond.wm.accesstoken.exception.WechatRequestException;

public abstract class AbstractTokenManager {
	
	@Resource
	AutoTokenConfig autoTokenConfig;
	Logger log = LoggerFactory.getLogger(getClass());

	
	Map<String,AccessToken> accessTokenMap = new HashMap<String,AccessToken>(); 
	Map<String,Thread> accessFlushThreadMap = new HashMap<String,Thread>();
	
	public AccessToken getToken(String appid, String secret) throws WechatRequestException {
		String key = getKey(appid,secret);
		AccessToken token = accessTokenMap.get(key);
		if(token!=null){
			token.setStatus(AccessTokenStatus.CACHE);
		}else{
			token = getAccessToken(appid,secret);
			accessTokenMap.put(key, token);
			accessFlushThreadMap.put(key,getFlushThread(appid,secret));
		}
		return token;
	}
	
	public void removeToken(String appid,String secret){
		String key = getKey(appid, secret);
		if(accessFlushThreadMap.containsKey(key)){
			accessFlushThreadMap.get(key).interrupt();
			accessFlushThreadMap.remove(key);
		}
		if(accessTokenMap.containsKey(key)){
			accessTokenMap.remove(key);
		}
	}
	
	public AccessToken flushToken(String appid,String secret) throws WechatRequestException{
		removeToken(appid, secret);
		return getToken(appid, secret);
	}
	
	public Thread getFlushThread(final String appid,final String secret){
		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
				String key = getKey(appid, secret);
				while(!Thread.interrupted()){
						AccessToken token = accessTokenMap.get(key);
						long expries = token.getExpires();
						long now = System.currentTimeMillis();
						long remain = expries-now;
						log.info(key+" expires:"+token.getExpires()+"  remaining:"+remain);
						if(autoTokenConfig.getFlushTime()>=remain){
							try {
								log.info("is expires,flush now");
								flushToken(appid,secret);
								log.info("flush success");
							} catch (WechatRequestException e) {
								log.info("flush failed:"+ e.getMessage());
							}
						}
					try {
						Thread.sleep(autoTokenConfig.getRate());
					} catch (InterruptedException e) {
						log.info("Thread "+Thread.currentThread().getName()+" interrupted");
						break;
					}
				}
			}
		});
		th.start();
		return th;
	}

	public String getKey(String appid, String secret) {
		return appid+"@"+secret;
	}
	
	public String getAppid(String key){
		return key.split("@")[0];
	}
	
	public String getSecret(String key){
		return key.split("@")[1];
	}
	
	protected abstract AccessToken getAccessToken(String appid,String secret) throws WechatRequestException;
	
	public int getAccessSize() {
		return accessTokenMap.size();
	}

}
