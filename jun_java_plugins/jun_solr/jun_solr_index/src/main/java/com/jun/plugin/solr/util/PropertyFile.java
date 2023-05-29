package com.jun.plugin.solr.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFile {
	private static Properties configProp;
	
	private static synchronized void init_prop(){
		configProp = new Properties();	
		String path = PropertyFile.class.getClassLoader().getResource("").getPath();
		path = path.replaceAll("%20", " ");
		InputStream fileinputstream = null;
		try {
			fileinputstream = new FileInputStream(path+"config.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			configProp.load(fileinputstream);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("不能读取配置文件, 请确保config.properties在CLASSPATH指定的路径中!");
		}
	}
	
	public static Properties getProps(){
		if(configProp==null)
			init_prop();
		return configProp;
	}
	
	public static void main(String[] args) {
		System.out.println(PropertyFile.getProps().getProperty("solr_url"));

	}
}
