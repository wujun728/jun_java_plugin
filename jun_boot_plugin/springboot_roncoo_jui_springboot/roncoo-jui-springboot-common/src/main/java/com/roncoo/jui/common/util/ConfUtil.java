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

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 配置文件读取工具类
 * 
 * @author Wujun
 */
public final class ConfUtil {
	private static final Logger logger = LoggerFactory.getLogger(ConfUtil.class);

	private ConfUtil() {
	}

	/**
	 * 通过静态代码块读取上传文件的验证格式配置文件,静态代码块只执行一次(单例)
	 */
	private static Properties properties = new Properties();

	// 通过类装载器装载进来
	static {
		try {
			// 从类路径下读取属性文件
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("system.properties"));
		} catch (IOException e) {
			logger.error("读取配置文件出错", e);
		}
	}

	/**
	 * 根据key读取value
	 * 
	 * @param keyName
	 *            key
	 * @return
	 */
	public static String getProperty(String keyName) {
		return getProperty(keyName, "");
	}

	/**
	 * 根据key读取value，key为空，返回默认值
	 * 
	 * @param keyName
	 *            key
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public static String getProperty(String keyName, String defaultValue) {
		return properties.getProperty(keyName, defaultValue);
	}

	public static final String FILEPATH = getProperty("filePath");
	public static final String USER = getProperty("user");

}
