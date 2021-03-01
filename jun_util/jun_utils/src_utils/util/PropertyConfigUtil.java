/**
 * Program  : PropertyConfigUtil.java<br/>
 * Author   : Ices<br/>
 * Create   : 2007-7-24 ÉÏÎç10:46:57<br/>
 *
 * Copyright 1997-2006 by Embedded Internet Solutions Inc.,
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Embedded Internet Solutions Inc.("Confidential Information").
 * You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement
 * you entered into with Embedded Internet Solutions Inc.
 *
 */
package cn.ipanel.apps.payment.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertyConfigUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(PropertyConfigUtil.class);

	private String propertiesPath;

	
	public PropertyConfigUtil(String propertiesPath) {
		this.propertiesPath = propertiesPath;
	}

	public String getValue(String key) {
		Properties properties = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = PropertyConfigUtil.class
					.getResourceAsStream(this.propertiesPath);
			properties.load(inputStream);
			//check(key, properties);
			return properties.getProperty(key);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.warn("getValue(String) - exception ignored", e);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("getValue(String)", e);
		} finally {
			try {
				if (inputStream == null) {
					logger.error("can not get resourse:" + propertiesPath);
				} else {
					inputStream.close();
				}
			} catch (IOException e) {
				logger.error("getValue(String)", e);
				e.printStackTrace();
			}
		}
		return null;
	}

	protected boolean check(String key, Properties properties) {
		if (properties.containsKey(key)) {
			return true;
		} else {
			throw new IllegalArgumentException("the property file["
					+ this.propertiesPath + "] do not have the key:" + key);
		}
	}
}
