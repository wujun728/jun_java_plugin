package org.springrain.frame.shiro;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springrain.frame.util.GlobalStatic;

/**
 * 使用 frameSimpleCredentialsMatcher 进行替换,前台MD5加密后传输,后台不再进行加密
 * @author caomei
 *
 */
@Deprecated
//@Component("frameHashedCredentialsMatcher")
public class FrameHashedCredentialsMatcher extends HashedCredentialsMatcher {

	@Resource
	private CacheManager cacheManager;
	
	
	public FrameHashedCredentialsMatcher(){
		 super();
	     setHashAlgorithmName(Md5Hash.ALGORITHM_NAME);
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
