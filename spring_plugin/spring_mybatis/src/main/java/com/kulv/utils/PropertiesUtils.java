package com.kulv.utils;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {

	private static Properties props; 
	
	private static final String smsProp = "smsProp.properties";
	private static final String sysProp = "sysProp.properties";
	//QQ登录
	private static final String qqloginProp = "qqconnectconfig.properties";
	//sina登录
	private static final String sinaloginProp = "sinaweibo.properties";
	
	static {
		try {
			props = new Properties();
			props.load(PropertiesUtils.class.getClassLoader().getResourceAsStream(smsProp));
			props.load(PropertiesUtils.class.getClassLoader().getResourceAsStream(sysProp));
			props.load(PropertiesUtils.class.getClassLoader().getResourceAsStream(qqloginProp));
			props.load(PropertiesUtils.class.getClassLoader().getResourceAsStream(sinaloginProp));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String getValue(String proKey){ 
		
		return props.getProperty(proKey);
	}
}
