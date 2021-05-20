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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import cn.ipanel.apps.payment.msg.handler.ConnectException;

/**
 * 
 * @author   zengtao
 * @version  1.0.0
 * @2008-9-22 ÉÏÎç09:44:52
 */
public class ErrorMessage {

	static Properties properties=new Properties();
	
	static{
		load();
	}
	private static void load(){
	  InputStream is= ErrorMessage.class.getResourceAsStream("/errormessage.properties");	
	  try {
		  properties.load(is);
		  is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static  String getProperty(String key,String type){
		if(type.equals("0")){
			if(key.equals("069")){
				return properties.getProperty("069_0");
			}
		}else{
			if(key.equals("069")){
				return properties.getProperty("069_1");
			}
		}
		
		return properties.getProperty(key);
	}
	
	public static  String getProperty(String key){
		//¶ÔÕË
		String current=new SimpleDateFormat("HH:mm:ss").format(new Date());
		if(current.compareTo("22:30:00")>0&&current.compareTo("23:59:59")<0){
			return properties.getProperty("duizhang");
		}

		
		
		
		String value= properties.getProperty(key);
		return value;
	}
	
	public static  String ConnExptionMsg(ConnectException e){
		//¶ÔÕË
		String current=new SimpleDateFormat("HH:mm:ss").format(new Date());
		if(current.compareTo("22:30:00")>0&&current.compareTo("23:59:59")<0){
			return properties.getProperty("duizhang");
		}
		
		if(e.getType()==ConnectException.BOSS_CONN_EXCEPTION){
			System.out.println("boss ÍøÂçÁ¬½Ó¹ÊÕÏ");
		}
		if(e.getType()==ConnectException.ICBC_BANK_CONN_EXCEPTION){
			System.out.println("ICBC ÍøÂçÁ¬½Ó¹ÊÕÏ");
		}
		if(e.getType()==ConnectException.CCB_CONN_EXCEPTION){
			System.out.println("CCB ÍøÂçÁ¬½Ó¹ÊÕÏ");
		}
		return "·şÎñÆ÷ÍøÂç¹ÊÕÏ";
	}
	
	
}

