package com.jun.plugin.common.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @ClassName: IGatRedisKey
 * @Description:
 * @author: renkai721
 * @date: 2018年6月25日 下午4:54:42
 */
public interface IGatRedisKey {
	public static Map<String, String> register_map = new HashMap<String, String>();
	public final String NO = "no";
	public final String OK = "ok";
	public final String HTTP = "http://";
	public final String F = ";";
	public final String _ = "_";
}
