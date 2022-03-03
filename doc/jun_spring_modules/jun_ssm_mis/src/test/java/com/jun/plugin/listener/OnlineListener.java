package com.jun.plugin.listener;

import java.util.Date;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.erp.jee.model.Tonline;
import com.erp.jee.pageModel.SessionInfo;
import com.erp.jee.service.OnlineServiceI;
import com.jun.plugin.utils.ConfigUtil;

/**
 * 监听在线用户上线下线
 * 
 * @author Wujun
 * 
 */
public class OnlineListener implements ServletContextListener, ServletContextAttributeListener, HttpSessionListener, HttpSessionAttributeListener, HttpSessionActivationListener, HttpSessionBindingListener, ServletRequestListener, ServletRequestAttributeListener {

	private static final Logger logger = Logger.getLogger(OnlineListener.class);

	private static ApplicationContext ctx = null;

	public OnlineListener() {
	}

	public void requestDestroyed(ServletRequestEvent arg0) {
	}

	/**
	 * 向session里增加属性时调用(用户成功登陆后会调用)
	 */
	public void attributeAdded(HttpSessionBindingEvent evt) {
		String name = evt.getName();
		logger.debug("向session存入属性：" + name);
		HttpSession session = evt.getSession();
		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		if (sessionInfo != null) {
			OnlineServiceI onlineService = (OnlineServiceI) ctx.getBean("onlineService");
			Tonline online = new Tonline();
			online.setCip(sessionInfo.getIp());
			online.setCdatetime(new Date());
			online.setCname(sessionInfo.getLoginName());
			onlineService.updateOnline(online);
		}
	}

	/**
	 * 服务器初始化时调用
	 */
	public void contextInitialized(ServletContextEvent evt) {
		logger.debug("服务器启动");
		ctx = WebApplicationContextUtils.getWebApplicationContext(evt.getServletContext());
	}

	public void sessionDidActivate(HttpSessionEvent arg0) {
	}

	public void valueBound(HttpSessionBindingEvent arg0) {
	}

	public void attributeAdded(ServletContextAttributeEvent arg0) {
	}

	public void attributeRemoved(ServletContextAttributeEvent arg0) {
	}

	/**
	 * session销毁(用户退出系统时会调用)
	 */
	public void sessionDestroyed(HttpSessionEvent evt) {
		HttpSession session = evt.getSession();
		if (session != null) {
			logger.debug("session销毁：" + session.getId());
			SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
			if (sessionInfo != null) {
				OnlineServiceI onlineService = (OnlineServiceI) ctx.getBean("onlineService");
				onlineService.deleteOnline(sessionInfo.getLoginName(), sessionInfo.getIp());
			}
		}
	}

	public void attributeRemoved(HttpSessionBindingEvent arg0) {
	}

	public void attributeAdded(ServletRequestAttributeEvent evt) {
	}

	public void valueUnbound(HttpSessionBindingEvent arg0) {
	}

	public void sessionWillPassivate(HttpSessionEvent arg0) {
	}

	public void sessionCreated(HttpSessionEvent arg0) {
	}

	public void attributeReplaced(HttpSessionBindingEvent arg0) {
	}

	public void attributeReplaced(ServletContextAttributeEvent arg0) {
	}

	public void attributeRemoved(ServletRequestAttributeEvent arg0) {
	}

	public void contextDestroyed(ServletContextEvent evt) {
		logger.debug("服务器关闭");
	}

	public void attributeReplaced(ServletRequestAttributeEvent arg0) {
	}

	public void requestInitialized(ServletRequestEvent arg0) {
	}

}
