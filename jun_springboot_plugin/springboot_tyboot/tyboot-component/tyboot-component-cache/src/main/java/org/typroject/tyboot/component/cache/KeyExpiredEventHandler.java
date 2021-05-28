package org.typroject.tyboot.component.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.typroject.tyboot.core.foundation.context.SpringContextHelper;

/**
 * 
 * <pre>
 *  Tyrest
 *  File: KeyExpiredEventHandler.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *   	redis key过期事件处理器抽象类,如果业务模块中需要处理key过期事件,
 *  	需要继承此类,并实现handle方法
 *  TODO	
 *  	eg:如果redis key值为:CacheType.AUTO_CLOSED_ORDER + 2016071511462062542 
 *  	则需要在order模块中定义处理器
 *  
 *  <code>@Component(value = "autoClosedOrderEventHandler")
 *  public class AutoClosedOrderEventHandler extends KeyExpiredEventHandler{
 *       &nbsp;@override
 *  	public void handle(String expiredKey){
 *  		//再次处理需要自动关闭的订单
 *  	}
 *  }
 * 
 *  Notes:
 *  $Id: KeyExpiredEventHandler.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月1日		magintrursh		Initial.
 *
 * </pre>
 */
public abstract class KeyExpiredEventHandler {

	private static final Logger logger = LoggerFactory.getLogger(KeyExpiredEventHandler.class);

	private static final String DEFAULT_EVENT_HANDLER_BEAN_NAME = "defaultKeyExpiredEventHandler";
	
	private static final String DEFAULT_BEAN_NAME_SUFIX = "EventHandler";
	
	protected abstract void handle(String expiredKey) throws Exception;

	public static KeyExpiredEventHandler getEventHandler(String expiredKey) {
		String eventHandlerBeanName = determainHandlerName(expiredKey);
		KeyExpiredEventHandler handler = null;
		try {
			handler = (KeyExpiredEventHandler) SpringContextHelper.getBean(eventHandlerBeanName);
		} catch (NoSuchBeanDefinitionException e) {
			handler = (KeyExpiredEventHandler) SpringContextHelper.getBean(DEFAULT_EVENT_HANDLER_BEAN_NAME);
			logger.warn(
					"has detected expired key [{}],but the expiredKeyEventHandler with name [{}] not found,[{}] willbe used!",
					expiredKey, eventHandlerBeanName, DEFAULT_EVENT_HANDLER_BEAN_NAME);
		}
		return handler;
	}

	private static String determainHandlerName(String expiredKey) {
		StringBuilder handlerName = new StringBuilder();
		String[] nameUnits = expiredKey.split(Redis.VAR_SPLITOR)[0].split("_");
		for (int i = 0; i < nameUnits.length; i++) {
			nameUnits[i] = nameUnits[i].toLowerCase();
			for (int j = 0; j < nameUnits[i].length(); j++) {
				handlerName.append(
						(i != 0 && j == 0) ? Character.toUpperCase(nameUnits[i].charAt(j)) : nameUnits[i].charAt(j));
			}
		}
		return handlerName.append(DEFAULT_BEAN_NAME_SUFIX).toString();
	}

	public static void main(String[] args) {
		System.out.println(determainHandlerName("WAIT_COMPLETE_ORDER"));
	}
}

/*
 * $Log: av-env.bat,v $
 */