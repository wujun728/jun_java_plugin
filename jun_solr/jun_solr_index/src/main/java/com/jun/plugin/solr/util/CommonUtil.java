package com.jun.plugin.solr.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {

	public static String getCurrentDatetime()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
}
