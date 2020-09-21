/*   
 * Project: OSMP
 * FileName: ExecutorWrapper.java
 * version: V1.0
 */
package com.osmp.web.base.core;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.osmp.web.system.servers.entity.VmData;
import com.osmp.web.user.entity.User;

/**
 * 全局系统初始化。公共方法等
 *
 * @author wangqiang
 *
 */
public class SystemGlobal implements InitializingBean, DisposableBean {

	public static final Logger logger = LoggerFactory.getLogger(SystemGlobal.class);

	public static Map<String, LinkedList<VmData>> vmDataMap = new ConcurrentHashMap<String, LinkedList<VmData>>();

	public static LinkedList<VmData> getVmData(String ip) {
		return vmDataMap.get(ip);
	}

	public static User currentLoginUser() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		if (request != null) {
			return (User) request.getSession().getAttribute(SystemConstant.LOGIN_USER);
		}
		return null;
	}

	public static void setLoginUser(User lu) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		if (request != null) {
			request.getSession().setAttribute(SystemConstant.LOGIN_USER, lu);
		}

	}

	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub

	}

}
