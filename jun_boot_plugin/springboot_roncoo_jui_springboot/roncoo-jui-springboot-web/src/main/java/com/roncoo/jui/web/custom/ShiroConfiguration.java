package com.roncoo.jui.web.custom;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.roncoo.jui.common.dao.SysUserDao;
import com.roncoo.jui.common.util.ConfUtil;
import com.roncoo.jui.common.util.Constants;
import com.roncoo.jui.web.bean.vo.SysMenuVO;
import com.roncoo.spring.boot.autoconfigure.shiro.ShiroRealm;
import com.xiaoleilu.hutool.util.CollectionUtil;

/**
 * shiro配置类 Created by cdyoue on 2016/10/21.
 */
@Configuration
public class ShiroConfiguration {

	/**
	 * ShiroRealm
	 */
	@Bean(name = "shiroRealm")
	public ShiroRealm shiroRealm() {
		ShiroCustomRealm realm = new ShiroCustomRealm();
		return realm;
	}

}

class ShiroCustomRealm extends ShiroRealm {

	@Autowired
	private SysUserDao sysUserDao;

	/**
	 * 授权认证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		@SuppressWarnings("unchecked")
		List<SysMenuVO> menuVOList = (List<SysMenuVO>) SecurityUtils.getSubject().getSession().getAttribute(Constants.Session.MENU);
		Set<String> menuSet = new HashSet<>();
		// 处理菜单权限
		listMenu(menuSet, menuVOList);
		simpleAuthorizationInfo.setStringPermissions(menuSet);
		return simpleAuthorizationInfo;
	}

	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) arg0;
		// String roncooNo = token.getUsername();
		// String password = token.getPassword().toString();
		
		SecurityUtils.getSubject().getSession().setAttribute(Constants.Session.USER, sysUserDao.getByUserPhone(ConfUtil.USER));
		return new SimpleAuthenticationInfo(token, token.getPassword(), getName());
	}

	/**
	 * @param list
	 * @return
	 */
	private static void listMenu(Set<String> menuSet, List<SysMenuVO> menuVOList) {
		if (CollectionUtil.isNotEmpty(menuVOList)) {
			for (SysMenuVO sm : menuVOList) {
				if (StringUtils.hasText(sm.getMenuUrl())) {
					menuSet.add(sm.getMenuUrl());
				}
				listMenu(menuSet, sm.getList());
			}
		}
	}

}