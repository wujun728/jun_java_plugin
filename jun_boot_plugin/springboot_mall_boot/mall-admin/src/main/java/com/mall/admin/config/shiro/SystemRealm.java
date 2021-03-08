package com.mall.admin.config.shiro;

import com.mall.admin.bean.SpringBeanManager;
import com.mall.admin.service.AdminService;
import com.mall.admin.service.MenuService;
import com.mall.common.utils.Md5Utils;
import com.mall.common.utils.StrKit;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.List;
import java.util.Map;


public class SystemRealm extends AuthorizingRealm {

	/**
	 * 用户授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("doGetAuthorizationInfo");
		Map userMap = (Map)principals.getPrimaryPrincipal();
		if (StrKit.isNotEmpty(userMap)) {
			MenuService menuService = SpringBeanManager.getBean(MenuService.class);
			List<Map> menuList = menuService.getMenuByUser((Integer)userMap.get("id"));
			SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
			for (Map menu : menuList) {
				if (StrKit.isNotEmpty(menu.get("permission"))) {
					simpleAuthorizationInfo.addStringPermission((String)menu.get("permission"));
				}
			}
			return simpleAuthorizationInfo;
		}
		return null;
	}

	
	/**
	 * 用户登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
		String userName = usernamePasswordToken.getUsername();
		Map userMap = getAdminService().getAdminByLoginName(userName);
		if (userMap == null) {
			throw new UnknownAccountException("用户不存在");
		}
		String password = Md5Utils.getMd5(new String(usernamePasswordToken.getPassword()), (String)userMap.get("encrypt"));
		usernamePasswordToken.setPassword(password.toCharArray());
		//以下数据属于数据库中的用户名密
		return new SimpleAuthenticationInfo(userMap, userMap.get("password"), getName());
	}

	public AdminService getAdminService(){
		return SpringBeanManager.getBean(AdminService.class);
	}
}
