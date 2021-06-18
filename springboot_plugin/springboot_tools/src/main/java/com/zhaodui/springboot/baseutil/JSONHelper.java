package com.zhaodui.springboot.baseutil;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;


public final class JSONHelper {

	/**
	 * json转换为java对象
	 *
	 * <pre>
	 * return JackJson.fromJsonToObject(this.answersJson, JackJson.class);
	 * </pre>
	 *
	 * @param <T>
	 *            要转换的对象
	 * @param json
	 *            字符串
	 * @param valueType
	 *            对象的class
	 * @return 返回对象
	 */
	public static <T> T fromJsonToObject(String json, Class<T> valueType) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(json, valueType);
		} catch (JsonMappingException e) {
 		} catch (IOException e) {
 		}
		return null;
	}

}
