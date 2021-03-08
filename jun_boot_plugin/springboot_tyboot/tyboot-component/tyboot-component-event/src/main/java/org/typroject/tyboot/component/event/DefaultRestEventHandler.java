package org.typroject.tyboot.component.event;

import org.springframework.stereotype.Component;

/**
 * 
 * <pre>
 * 
 *  Tyrest
 *  File: DefaultEventHandler.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2015
 * 
 *  Description:默认的事件处理器实现
 * 
 *  Notes:
 *  $Id: DefaultEventHandler.java 31101200-9 2014-10-14 16:43:51Z Tyrest\magintursh $
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2015年8月10日		magintursh		Initial.
 *
 * </pre>
 */
@Component(value = "defaultEventHandler")
public class DefaultRestEventHandler extends RestEventHandler{

	@Override
	protected void handleEvent(RestEvent restEvent) throws Exception {
		System.out.println(restEvent.getRestEventName() + " event has occured!");
	}


}

/*
*$Log: av-env.bat,v $
*/