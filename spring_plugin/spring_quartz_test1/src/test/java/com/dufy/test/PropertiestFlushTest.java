package com.dufy.test;


import java.util.Enumeration;
import java.util.ResourceBundle;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class PropertiestFlushTest {

	@Test
	public void getPropertiesContent() {
		ResourceBundle.clearCache();//下次也是重新拿配置文件的内容 ！！  会否刷新整个spring容器的所有properties？？
		ResourceBundle resourceBundle = ResourceBundle.getBundle("quartz");//quartz.properties
		Enumeration<String> keys = resourceBundle.getKeys();
		System.out.println("----------------");
	    for (Enumeration<String> e = resourceBundle.getKeys(); e.hasMoreElements();){
	    	 String nextElement = e.nextElement();
	    	 System.out.println(nextElement +" = "+ resourceBundle.getObject(nextElement));
	    }
	      
		
		System.out.println(JSON.toJSONString(resourceBundle));
		
		
	}

	private void nextElement() {
		// TODO Auto-generated method stub
		
	}

}
