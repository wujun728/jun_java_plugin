package org.typroject.tyboot.component.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;

/**
 * 
 * <pre>
 *  Tyrest
 *  File: KeyExpiredListener.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * key过期事件监听器
 *  Notes:
 *  $Id: KeyExpiredListener.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月1日		magintrursh		Initial.
 *
 * </pre>
 */
public class KeyExpiredListener implements MessageListener {

	private static final Logger logger = LoggerFactory.getLogger(KeyExpiredListener.class);

	@Override
	public void onMessage(Message message, byte[] pattern) {

		String messageBody = new String(message.getBody());

		logger.info("recieved key expired message:[{}] from channel [{}] with pattern [{}]", new String(message.getBody()),
				new String(message.getChannel()), new String(pattern));

		if (!ValidationUtil.isEmpty(messageBody)) {
			try {
				KeyExpiredEventHandler.getEventHandler(messageBody).handle(messageBody);
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}
}

/*
 * $Log: av-env.bat,v $
 */