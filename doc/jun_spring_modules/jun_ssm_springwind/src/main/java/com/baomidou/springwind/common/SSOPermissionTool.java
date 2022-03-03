package com.baomidou.springwind.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.tools.config.DefaultKey;

import com.baomidou.framework.spring.SpringContextHolder;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.Token;
import com.baomidou.springwind.service.IPermissionService;

@DefaultKey("SSOPermission")
public class SSOPermissionTool {

	/**
	 * 按钮级别权限判断
	 */
	public boolean isActionable(String permission) {
		HttpServletRequest request = HttpHelper.getHttpServletRequest();
		Token token = SSOHelper.attrToken(request);
		if ( token == null ) {
			return false;
		}
		//数据库判断按钮权限是否合法，生产环境此处建议加缓存判断逻辑。
		IPermissionService psi = SpringContextHolder.getBean(IPermissionService.class);
		return psi.isActionable(token, permission);
	}
	
}
