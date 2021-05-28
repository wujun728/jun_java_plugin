/*
 * Copyright 2015-2016 RonCoo(http://www.roncoo.com) Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.roncoo.jui.common.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Restful 调用工具类
 * 
 * @author Wujun
 */
public final class HttpUtil {
	private HttpUtil() {
	}

	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	private static SimpleClientHttpRequestFactory requestFactory = null;
	static {
		requestFactory = new SimpleClientHttpRequestFactory();
		requestFactory.setConnectTimeout(60000); // 连接超时时间，单位=毫秒
		requestFactory.setReadTimeout(60000); // 读取超时时间，单位=毫秒
	}
	private static RestTemplate restTemplate = new RestTemplate(requestFactory);

	public static JsonNode postForObject(String url, Map<String, Object> map) {
		logger.info("POST 请求， url={}，map={}", url, map.toString());
		return restTemplate.postForObject(url, map, JsonNode.class);
	}

}
