package org.typroject.tyboot.component.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 
 * <pre>
 * 
 *  Tyrest
 *  File: TyrestEventListener.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2015
 * 
 *  Description:系统中的业务事件监听器，有系统业务事件触发，进行相关的操作
 *  TODO
 * 
 *  Notes:
 *  $Id: TyrestEventListener.java 31101200-9 2014-10-14 16:43:51Z Tyrest\magintursh $
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2015年6月23日		magintursh		Initial.
 *
 * </pre>
 */
@Component
public class RestEventListener implements SmartApplicationListener
{
	private static final Logger logger = LoggerFactory.getLogger(RestEventHandler.class);
	/**
	 * 事件被触发时执行的方法
	 */
	@Async
	@Override
	public void onApplicationEvent(ApplicationEvent event)
	{
		//先将事件转换为系统业务事件
		RestEvent currentEvent = (RestEvent)event;
		try {
			currentEvent.getRequestContextModel();
			RestEventHandler.getEventHandler(currentEvent).handleEvent(currentEvent);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	/**
	 * 定义事件监听器的优先级,返回值越小，优先级越高
	 */
	@Override
	public int getOrder()
	{
		return 0;
	}

	/**
	 * 该Listener只支持restEvent事件
	 */
	@Override
	public boolean supportsEventType(Class<? extends ApplicationEvent> eventType)
	{
		return eventType == RestEvent.class;
	}
	/**
	 * 定义事件支持的事件源类型
	 */
	@Override
	public boolean supportsSourceType(Class<?> sourceType)
	{
		return true;
	}

}

/*
*$Log: av-env.bat,v $
*/