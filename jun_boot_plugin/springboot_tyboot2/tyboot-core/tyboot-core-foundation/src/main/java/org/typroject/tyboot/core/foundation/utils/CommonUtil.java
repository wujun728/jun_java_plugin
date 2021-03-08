package org.typroject.tyboot.core.foundation.utils;

import java.util.UUID;

/**
 * 
 * <pre>
 *  Tyrest
 *  File: CommonUtil.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 *  $Id: CommonUtil.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月1日		magintrursh		Initial.
 *
 * </pre>
 */
public class CommonUtil
{
	public static String getUUID()
	{
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
}
