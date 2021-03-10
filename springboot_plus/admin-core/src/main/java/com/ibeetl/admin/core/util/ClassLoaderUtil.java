package com.ibeetl.admin.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibeetl.admin.core.conf.RbacAnnotationConfig;

public class ClassLoaderUtil {
	private ClassLoaderUtil(){
		
	}
	private static final Logger log = LoggerFactory.getLogger(RbacAnnotationConfig.class);
	public static Class loadClass(String clsName){
		Class cls = null;
		try {
			cls = ClassLoaderUtil.class.getClassLoader().loadClass(clsName);
		} catch (ClassNotFoundException e) {
			log.info(e.getMessage());
			ClassLoader loader =  Thread.currentThread().getContextClassLoader();
			try {
				return loader.loadClass(clsName);
			} catch (ClassNotFoundException e1) {
				log.info(e1.getMessage());
			}
		}
		
		if(cls==null){
			log.error("params:{},message:{}",clsName,"无法加载类");
			throw new IllegalArgumentException("不能加载"+clsName);
		}
		
		return cls;
		
	}
}
