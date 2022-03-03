package org.springrain.frame.shiro;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springrain.frame.util.GlobalStatic;
import org.springrain.system.entity.User;
import org.springrain.system.service.IUserRoleMenuService;

/**
 * 数据库认证及权限查询
 * @author caomei
 *
 */
@Component("shiroDbRealm")
public class ShiroDbRealm extends AuthorizingRealm {
	private final  Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	IUserRoleMenuService userRoleMenuService;

	@Resource
	private CacheManager cacheManager;
	
	
	@Resource
	private CredentialsMatcher frameSimpleCredentialsMatcher;

	//public static final String HASH_ALGORITHM = "MD5";
	//public static final int HASH_INTERATIONS = 1;
	
	
	public ShiroDbRealm() {
		// 认证
		// super.setAuthenticationCacheName(GlobalStatic.authenticationCacheName);
		super.setAuthenticationCachingEnabled(false);
		
		
		// 授权
		super.setAuthorizationCachingEnabled(false);
		// 授权不在缓存,统一有spring cache提供授权结果
		//super.setAuthorizationCacheName(GlobalStatic.authorizationCacheName);
		
		
		super.setName(GlobalStatic.authorizingRealmName);
		
		//设置密码匹配方式
		//super.setCredentialsMatcher(frameHashedCredentialsMatcher);
	}

	// 授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principalCollection) {

		// 因为非正常退出，即没有显式调用 SecurityUtils.getSubject().logout()
		// (可能是关闭浏览器，或超时)，但此时缓存依旧存在(principals)，所以会自己跑到授权方法里。
		if (!SecurityUtils.getSubject().isAuthenticated()) {
			doClearCache(principalCollection);
			SecurityUtils.getSubject().logout();
			return null;
		}

		ShiroUser shiroUser = (ShiroUser) principalCollection
				.getPrimaryPrincipal();
		// String userId = (String)
		// principalCollection.fromRealm(getName()).iterator().next();
		String userId = shiroUser.getId();
		if (StringUtils.isBlank(userId)) {
			return null;
		}
		// 添加角色及权限信息
		SimpleAuthorizationInfo sazi = new SimpleAuthorizationInfo();
		try {
			//sazi.addRoles(userRoleMenuService.getRolesAsString(userId));
			sazi.addStringPermissions(userRoleMenuService
					.getPermissionsAsString(userId));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}

		return sazi;
	}

	// 认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		FrameAuthenticationToken upToken = (FrameAuthenticationToken) token;
		/*
		 * String pwd = new String(upToken.getPassword()); if
		 * (StringUtils.isNotBlank(pwd)) { pwd = DigestUtils.md5Hex(pwd); }
		 */
		// 调用业务方法
		User user = null;
		String userName = upToken.getUsername();
		
		if(StringUtils.isBlank(userName)){//账号为空
			return null;
		}
		
		//处理密码错误缓存
		 Cache cache = cacheManager.getCache(GlobalStatic.springrainloginCacheKey);
		 Integer errorLogincount=cache.get(userName, Integer.class);
		 if(errorLogincount!=null&&errorLogincount>=GlobalStatic.ERROR_LOGIN_COUNT){//密码连续错误10次以上
			 
			 String errorMessage="密码连续错误超过"+GlobalStatic.ERROR_LOGIN_COUNT+"次,账号被锁定,请"+GlobalStatic.ERROR_LOGIN_LOCK_MINUTE+"分钟之后再尝试登录!";
			 
			 Long endDateLong = cache.get(userName+"_endDateLong", Long.class);
			 Long now=System.currentTimeMillis()/1000;//秒
			 if(endDateLong==null){
				 endDateLong=now+GlobalStatic.ERROR_LOGIN_LOCK_MINUTE*60;//秒
				 cache.put(userName+"_endDateLong", endDateLong);
				 throw new LockedAccountException(errorMessage);
			 }else if(now>endDateLong){//过了失效时间
				 cache.evict(userName);
				 cache.evict(userName+"_endDateLong");
				 
			 }else{
				 throw new LockedAccountException(errorMessage); 
			 }
			 
			 
			
		 }
		try {
			user = userRoleMenuService.findLoginUser(userName, null,upToken.getUserType());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw  new AuthenticationException(e);
		}

		if (user != null) {
			
			Integer active=user.getActive();
			if(active==null){
				active=0;
			}
			
			if(1-user.getActive()!=0){
				throw new LockedAccountException("您的账号正在审核中,暂无法登陆."); 
			}
			
			
			
			// 要放在作用域中的东西，请在这里进行操作
			// SecurityUtils.getSubject().getSession().setAttribute("c_user",
			// user);
			// byte[] salt = EncodeUtils.decodeHex(user.getSalt());

			//Session session = SecurityUtils.getSubject().getSession(false);
			AuthenticationInfo authinfo = new SimpleAuthenticationInfo(
					new ShiroUser(user), user.getPassword(), getName());
			// Cache<Object, Object> cache =
			// cacheManager.getCache(GlobalStatic.authenticationCacheName);
			// cache.put(GlobalStatic.authenticationCacheName+"-"+userName,
			// session.getId());
			
			
			
			
			return authinfo;
		}
		// 认证没有通过
		return null;
	}
	
	
	
	/**

	 * 设定Password校验的Hash算法与迭代次数.

	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		/*
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(
				HASH_ALGORITHM);
		matcher.setHashIterations(HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
		*/
		setCredentialsMatcher(frameSimpleCredentialsMatcher);
	}
	
	
}
