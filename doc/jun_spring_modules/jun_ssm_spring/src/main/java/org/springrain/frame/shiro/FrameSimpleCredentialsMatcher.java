package org.springrain.frame.shiro;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springrain.frame.util.GlobalStatic;


@Component("frameSimpleCredentialsMatcher")
public class FrameSimpleCredentialsMatcher extends SimpleCredentialsMatcher {

	@Resource
	private CacheManager cacheManager;
	
	
	public FrameSimpleCredentialsMatcher(){
		 super();
	}
	
	
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		//匹配结果
		boolean doCredentialsMatch = super.doCredentialsMatch(token, info);
		
		//处理密码错误缓存
		Cache cache = cacheManager.getCache(GlobalStatic.springrainloginCacheKey);
		FrameAuthenticationToken _token=(FrameAuthenticationToken) token;
		String userName=_token.getUsername();
		
		if(doCredentialsMatch){//如果登录成功
			cache.evict(userName);
			 cache.evict(userName+"_endDateLong");
		}else{
			Integer errorLogincount=cache.get(userName, Integer.class);
			if(errorLogincount==null){
				errorLogincount=0;
			}
			errorLogincount=errorLogincount+1;
			cache.put(userName,errorLogincount);
		}
		 
		
		return doCredentialsMatch;
	}
	
	
	
	
	
	
	
}
