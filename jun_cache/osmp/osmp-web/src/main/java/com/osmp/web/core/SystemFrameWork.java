/*   
 * Project: OSMP
 * FileName: ExecutorWrapper.java
 * version: V1.0
 */
package com.osmp.web.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.osmp.web.system.dict.entity.DictItem;
import com.osmp.web.system.dict.service.DictService;
import com.osmp.web.system.properties.service.PropertiesService;
import com.osmp.web.system.servers.entity.Servers;
import com.osmp.web.system.servers.service.ServersService;
import com.osmp.web.system.strategy.entity.StrategyCondition;
import com.osmp.web.system.strategy.service.StrategyService;

/**
 * Description: 系统初始化数据
 * 
 * @author: wangkaiping
 * @date: 2014年11月24日 下午2:13:57
 */
public class SystemFrameWork implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {

	private static ApplicationContext applicationContext; // Spring应用上下文环境

	/**
	 * 服务器列表
	 */
	public static List<Servers> servers = new ArrayList<Servers>();

	/**
	 * 服务器心跳
	 */
	public static Map<String, Long> xtmsg = new ConcurrentHashMap<String, Long>();

	/**
	 * 字典数据
	 */
	public static Map<String, List<DictItem>> dictMap = new ConcurrentHashMap<String, List<DictItem>>();
	
	/**
	 *分发策略
	 */
	public static Map<String, List<StrategyCondition>> strategyMap = new ConcurrentHashMap<String, List<StrategyCondition>>();

	/**
	 * 系统参数
	 */
	public static Map<String, String> proMap = new ConcurrentHashMap<String, String>();

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (null == event.getApplicationContext().getParent()) {
			refreshDict();// 刷新字典表
			refreshStrategy();//刷新分发策略
			refreshPro();// 刷新系统参数
			refreshServer(); // 刷新负载信息
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		setAppContext(context);
	}

	/**
	 * 刷新服务器心跳
	 * 
	 * @param ip
	 * @param lastTime
	 */
	public static void refreshXtMsg(String ip, Long lastTime) {
		SystemFrameWork.xtmsg.put(ip, lastTime);
	}

	/**
	 * 检查服务器当前状态
	 * 
	 * @param ip
	 * @return
	 */
	public static boolean checkServerState(String ip) {
		Long lastTime = SystemFrameWork.xtmsg.get(ip);
		if (null != lastTime) {
			if (System.currentTimeMillis() - lastTime >= 60 * 1000) {
				SystemFrameWork.xtmsg.remove(ip);
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 * 刷新负载信息
	 */
	public static void refreshServer() {
		servers.clear();
		ServersService serverService = getBean(ServersService.class);
		servers = serverService.getAllServers("state=" + Servers.STATE_UP);
	}

	/**
	 * 刷新字典数据
	 */
	public static void refreshDict() {
		dictMap.clear();
		DictService dictService = getBean(DictService.class);
		dictMap = dictService.getDictMap();
	}
	
	/**
	 * 刷新分发策略
	 */
	public static void refreshStrategy() {
		strategyMap.clear();
		StrategyService strategyService = getBean(StrategyService.class);
		strategyMap = strategyService.getStrategyMap();
	}

	/**
	 * 刷新系统参数
	 */
	public static void refreshPro() {
		proMap.clear();
		PropertiesService proService = getBean(PropertiesService.class);
		proMap = proService.getProMap();
	}

	private static void setAppContext(ApplicationContext context) {
		applicationContext = context;
	}

	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	public static String[] getAliases(String name) {
		return applicationContext.getAliases(name);
	}

	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean(name);
	}

	public static <T> T getBean(Class<T> requiredType) throws BeansException {
		return applicationContext.getBean(requiredType);
	}

	public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
		return applicationContext.getBeansOfType(clazz);
	}

	public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
		return applicationContext.getBean(name, requiredType);
	}

	public static Object getBean(String name, Object... args) throws BeansException {
		return applicationContext.getBean(name, args);
	}

	public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.getType(name);
	}

	public static boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.isPrototype(name);
	}

	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.isSingleton(name);
	}

	public static boolean isTypeMatch(String name, Class<?> targetType) throws NoSuchBeanDefinitionException {
		return applicationContext.isTypeMatch(name, targetType);
	}

}
