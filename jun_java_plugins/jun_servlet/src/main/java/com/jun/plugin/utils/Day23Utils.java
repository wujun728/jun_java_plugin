package com.jun.plugin.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Day23Utils {
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String getDate(){
		return sdf.format(new Date());
	}
}
