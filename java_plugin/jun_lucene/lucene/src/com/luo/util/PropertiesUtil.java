package com.luo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Component;

@Component("myPropertiesUtil")
public class PropertiesUtil {
	private static Properties properties=null;
	
	
	static{
		try {
			properties=new Properties();
			File file = new File(PropertiesUtil.class.getResource("/").getPath());
			File[] fs=file.listFiles();
			for (File f : fs) {
				if(f.isFile()&&f.getName().endsWith("properties")){
					properties.load(new FileInputStream(f));
				}
			}
			System.out.println("properties初始化。。。。。。。。");
			System.out.println(properties.get("uploadDir"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String get(String key){
		return properties.getProperty(key);
	}
}
