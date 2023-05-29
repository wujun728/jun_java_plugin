package com.jun.plugin.commons.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.jun.plugin.commons.util.apiext.IOUtil;

public abstract class Conf {
	public static final Properties utilProperties = IOUtil
			.fileToProperties("/commonsUtil.properties");// 属性配置

	/**
	 * 取到Redis配置服务器配置示例： <br/>
	 * defaultRedisName=redis1<br/>
	 * rjzjh.redisserver.redis1.host=localhost<br/>
	 * rjzjh.redisserver.redis1.port=6379 rjzjh.redisserver.redis1.maxTotal=20<br/>
	 * rjzjh.redisserver.redis1.maxidle=5<br/>
	 * rjzjh.redisserver.redis1.maxWaitMillis=10001<br/>
	 * rjzjh.redisserver.redis1.testonborrow=false<br/>
	 * */
	public static Map<String, String> getRedisServerPropByKey(
			final Properties prop, final String key) {
		Set<Object> propKeys = prop.keySet();
		Map<String, String> retMap = new HashMap<String, String>();
		for (Object object : propKeys) {
			String tempKey = String.valueOf(object);
			String tempStr = String.format("rjzjh.redisserver.%s.", key);
			if (tempKey.startsWith(tempStr)) {
				retMap.put(tempKey.replace(tempStr, ""),
						prop.getProperty(tempKey));
			}
		}
		return retMap;
	}
}
