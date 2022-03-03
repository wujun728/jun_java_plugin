package org.springrain.frame.common;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springrain.frame.shiro.ShiroUser;

/**
 * 当前登录用户信息,可以在bean中调用获取当前登录用户信息,例如 SessionUser.getUserId()获取当前登录人的userId
 * 
 * @copyright {@link weicms.net}
 * @author springrain<9iuorg@gmail.com>
 * @version 2013-03-19 11:08:15
 * @see org.springrain.frame.common.SessionUser
 */
public class SessionUser {
	private static final Logger logger = LoggerFactory.getLogger(SessionUser.class);
	private SessionUser() {
		throw new IllegalAccessError("工具类不能实例化");
	}

	public static ShiroUser getShiroUser() {

		try {

			Subject user = SecurityUtils.getSubject();
			if (user == null) {
				return null;
			}
			ShiroUser shiroUser = (ShiroUser) user.getPrincipal();
			if (shiroUser == null) {
				return null;
			}
			return shiroUser;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return null;
		}
	}

	public static Session getSession() {
		try {
			Subject user = SecurityUtils.getSubject();
			if (user == null) {
				return null;
			}
			Session session = user.getSession();
			return session;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return null;
		}
	}

	public static String getUserId() {
		try {
			ShiroUser shiroUser = getShiroUser();
			if (shiroUser == null) {
				return null;
			}
			return shiroUser.getId();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return null;
		}
	}

	public static Integer getUserType() {
		ShiroUser shiroUser = getShiroUser();
		if (shiroUser == null) {
			return null;
		}
		return shiroUser.getUserType();
	}

	public static String getUserCode() {
		ShiroUser shiroUser = getShiroUser();
		if (shiroUser == null) {
			return null;
		}
		return shiroUser.getAccount();

	}

	public static String getUserName() {
		ShiroUser shiroUser = getShiroUser();
		if (shiroUser == null) {
			return null;
		}
		return shiroUser.getName();
	}

	public static String getEmail() {
		ShiroUser shiroUser = getShiroUser();
		if (shiroUser == null) {
			return null;
		}
		return shiroUser.getEmail();
	}

}
