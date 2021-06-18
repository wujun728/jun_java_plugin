/**
 * Copyright 2015-2016 广州市领课网络科技有限公司
 */
package com.roncoo.jui.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Wujun
 */
public final class JSONUtil {
	private static final Logger logger = LoggerFactory.getLogger(JSONUtil.class);

	private JSONUtil() {
	}

	public static String toJSONString(Object o) {
		ObjectMapper m = new ObjectMapper();
		try {
			return m.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			logger.error("json解析出错", e);
			return "";
		}
	}

}
