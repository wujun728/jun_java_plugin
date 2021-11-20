/**
 * Program  : ConfigUtil.java
 * Author   : zengtao
 * Create   : 2008-9-22 ÉÏÎç09:44:52
 *
 * Copyright 2006 by Embedded Internet Solutions Inc.,
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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author   zengtao
 * @version  1.0.0
 * @2008-9-22 ÉÏÎç09:44:52
 */
public class ConfigUtil {

	static Properties properties=new Properties();
	
	private static void load(){
	  InputStream is= ConfigUtil.class.getResourceAsStream("/config.properties");	
	  try {
		  properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static  String getProperty(String key){
		String value= properties.getProperty(key);
		if(value==null){
			load();
			value= properties.getProperty(key);
		}
		return value;
	}
	
	public static int getIntProperty(String key){
		String value=getProperty(key);
		if(value==null)return 0;
		return Integer.parseInt(value.trim());
	}
	
	
	public static void main(String[] args) {
		System.out.println(ConfigUtil.getProperty("bank.host"));
	}
}

