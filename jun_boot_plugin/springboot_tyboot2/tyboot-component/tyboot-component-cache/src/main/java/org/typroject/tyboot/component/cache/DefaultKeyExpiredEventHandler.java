package org.typroject.tyboot.component.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * <pre>
 *  Tyrest
 *  File: DefaultKeyExpiredEventHandler.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  默认的key过期事件处理器
 * 
 *  Notes:
 *  $Id: DefaultKeyExpiredEventHandler.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月1日		magintrursh		Initial.
 *
 * </pre>
 */
@Component(value = "defaultKeyExpiredEventHandler")
public class DefaultKeyExpiredEventHandler extends KeyExpiredEventHandler {

	private static final Logger logger = LoggerFactory.getLogger(DefaultKeyExpiredEventHandler.class);
	
	@Override
	protected void handle(String expiredKey) throws Exception {
		logger.info("recieved expired key [{}]!", expiredKey);
	}
}

/*
*$Log: av-env.bat,v $
*/