/*   
 * Project: OSMP
 * FileName: JSONUtils.java
 * version: V1.0
 */
package com.osmp.utils.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class JSONUtils {

	public static Map<String, String> jsonString2Map(String jsonStr) {
		JSONObject json_obj = null;
		try {
			json_obj = JSONObject.fromObject(jsonStr);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (json_obj == null)
			return null;
		Set<String> obj_keys = json_obj.keySet();
		if (obj_keys == null)
			return null;
		Map<String, String> map = new HashMap<String, String>();
		for (String item_key : obj_keys) {
			map.put(item_key, json_obj.getString(item_key));
		}
		return map;
	}

	/**
	 * 
	 * @param jsonStr
	 *            需转换的标准的json字符串
	 * @param clz
	 *            转换对象
	 * @param classMap
	 *            转换对象中包含复杂对象，key对应jsonStr中key,value对应需转换为的复杂对象
	 * @return
	 */
	public static <T> T jsonString2Bean(String jsonStr, Class<T> clz, Map<String, Class<?>> classMap) {
		JSONObject json_obj = null;
		try {
			json_obj = JSONObject.fromObject(jsonStr);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (json_obj == null)
			return null;
		JsonConfig config = new JsonConfig();
		config.setRootClass(clz);
		config.setClassMap(classMap);
		config.setHandleJettisonEmptyElement(true);
		return (T) JSONObject.toBean(json_obj, config);
	}

}
