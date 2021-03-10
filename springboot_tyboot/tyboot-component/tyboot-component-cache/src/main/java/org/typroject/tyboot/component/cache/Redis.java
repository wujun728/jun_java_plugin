package org.typroject.tyboot.component.cache;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *
 *  Tyrest
 *  File: Redis.java
 *
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 *
 *  Description:
 *  	如果系统中的缓存只使用redis的实现,则可直接使用此类,
 *  	Redis类的所有方法均为static，调用很方便，
 *  	此类中的三个single标示的方法,可直接在dao层使用
 *  TODO
 *
 *  Notes:
 * 	$Id: Redis.java 72642 2009-01-01 20:01:57Z freeapis\magintursh $
 *
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年7月15日		magintursh		Initial.
 *
 * </pre>
 */
public class Redis {


	public static String VAR_SPLITOR = ":";

	public  static RedisTemplate redisTemplate;

	public static RedisTemplate getRedisTemplate()
	{
		return redisTemplate;
	}


	public static String genKey(String... keyMembers) {
		return StringUtils.join(keyMembers, VAR_SPLITOR).toUpperCase();
	}





}

/*
 * $Log: av-env.bat,v $
 */