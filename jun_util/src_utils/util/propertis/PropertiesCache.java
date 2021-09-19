/**
 * Program  : PropertiesCache.java
 * Author   : apps1_icesx
 * Create   : 2008-11-21 ����12:30:33
 *
 * Copyright 2008 by iPanel Technologies Ltd.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of iPanel Technologies Ltd.("Confidential Information").  
 * You shall not disclose such Confidential Information and shall 
 * use it only in accordance with the terms of the license agreement 
 * you entered into with iPanel Technologies Ltd.
 *
 */

package cn.ipanel.apps.logparser.util.propertis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author apps1_icesx
 * @version 1.0.0
 * @2008-11-21 ����12:30:33
 */
class PropertiesCache extends PropertiesConfigUtil {

	/**
	 * @author apps1_icesx
	 * @create 2008-11-21 ����12:30:31
	 * @since
	 */
	private Properties properties;

	PropertiesCache(String propertiesPath) {
		super(propertiesPath);
		this.load();
	}

	private void load() {
		properties = new Properties();
		InputStream inputStream = null;
		try {
			PropertiesConfigUtil.logger
					.debug("load the proerties :" + this.url);
			inputStream = url.openStream();
			properties.load(inputStream);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("getValue(String)resourse:" + this.url,
					e);
		} catch (IOException e) {
			throw new RuntimeException("getValue(String)", e);
		} finally {
			try {
				if (inputStream == null) {
					PropertiesConfigUtil.logger.error("can not get resourse:"
							+ this.url);
				} else {
					inputStream.close();
				}
			} catch (IOException e) {
				PropertiesConfigUtil.logger.error("getValue(String)", e);
			}
		}
	}

	public synchronized String getValue(String key) {
		return (String) properties.get(key);
	}
}
