package com.erp.shiro;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.erp.model.Users;
import com.jun.plugin.utils.Constants;

/**
 * 类功能说明 TODO:自定义Realm 类修改者 修改日期 修改说明
 * <p>
 * Title: MyShiroRealm.java
 * </p>
 * <p>
 * Description:福产流通科技
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company:福产流通科技有限公司
 * </p>
 * 
 * @author Wujun
 * @date 2013-5-29 上午11:32:29
 * @version V1.0
 */

public class MyShiroRealm extends AuthorizingRealm {
	// 用于获取用户信息及用户权限信息的业务接口
	private SessionFactory hibernateSessionFactory;

	public SessionFactory getSessionFactory() {
		return hibernateSessionFactory;
	}

	public void setHibernateSessionFactory(SessionFactory hibernateSessionFactory) {
		this.hibernateSessionFactory = hibernateSessionFactory;
	}

	@SuppressWarnings("unused")
	private Session getCurrentSession() {
		return hibernateSessionFactory.getCurrentSession();
	}

	@SuppressWarnings("rawtypes")
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//		 String username = (String) principals.fromRealm(getName()).iterator().next();
		ShiroUser shiroUser = (ShiroUser) principals.fromRealm(getName()).iterator().next();
		String username = shiroUser.getAccount();
		if (username != null) {
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			// 查询用户授权信息
			// info.addRole("admin");
			String sql = null;
			// 超级管理员默认拥有所有操作权限
			if (Constants.SYSTEM_ADMINISTRATOR.equals(username)) {
				sql = "SELECT p.PERMISSION_ID,p.MYID FROM SYS_PERMISSION AS p\n" + "where p.STATUS='A' and p.TYPE='O' and p.ISUSED='Y'";
			} else {
				sql = "SELECT DISTINCT rp.PERMISSION_ID,p.MYID FROM\n" + "SYS_ROLE_PERMISSION AS rp\n" + "INNER JOIN SYS_ROLE AS r ON rp.ROLE_ID = r.ROLE_ID\n"
						+ "INNER JOIN SYS_USER_ROLE AS ur ON rp.ROLE_ID = ur.ROLE_ID\n" + "INNER JOIN SYS_USERS AS u ON u.USER_ID = ur.USER_ID\n"
						+ "INNER JOIN SYS_PERMISSION AS p ON rp.PERMISSION_ID = p.PERMISSION_ID\n"
						+ "WHERE rp.STATUS='A' and r.STATUS='A' and ur.STATUS='A' and u.STATUS='A' and p.STATUS='A' and p.TYPE='O' and p.ISUSED='Y'\n" + "and u.NAME ='" + username
						+ "'";
			}
			List perList = this.getSessionFactory().getCurrentSession().createSQLQuery(sql).list();
			if (perList != null && perList.size() != 0) {
				for (Object object : perList) {
					Object[] obj = (Object[]) object;
					info.addStringPermission(obj[1].toString());
				}
				return info;
			}
		}
		return null;
	}

	// 获取认证信息
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		CaptchaUsernamePasswordToken token = (CaptchaUsernamePasswordToken) authcToken;
		// 通过表单接收的用户名
		String username = token.getUsername();
//		if (username != null && !"".equals(username) && doCaptchaValidate(token)) {
		if (username != null && !"".equals(username)) {
			SessionFactory s = this.getSessionFactory();
			String hql = "from Users t where t.status='A' and t.name=:name";
			System.err.println("info");
			System.err.println(s.getCurrentSession());
			Users users = (Users) s.getCurrentSession().createQuery(hql).setParameter("name", username).uniqueResult();
//			Users users=new Users();
			users.setUserId(1);
			users.setAccount("admin");
			users.setPassword("admin");
			if (users != null) {
				Subject subject = SecurityUtils.getSubject();
				subject.getSession().setAttribute(Constants.SHIRO_USER, new ShiroUser(users.getUserId(), users.getAccount()));
				return new SimpleAuthenticationInfo(new ShiroUser(users.getUserId(), users.getAccount()), users.getPassword(), getName());
			}
		}
		return null;
	}

	/**
	 * 更新用户授权信息缓存.
	 */

	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清除所有用户授权信息缓存.
	 */

	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}

	// 验证码校验
/*	protected boolean doCaptchaValidate(CaptchaUsernamePasswordToken token) {
		String captcha = (String) token.getRequest().getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		if (captcha != null && !captcha.equalsIgnoreCase(token.getCaptcha())) {
			throw new IncorrectCaptchaException("验证码错误！");
		}
		return true;
	}*/
}
