package com.baijob.commonTools.net;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baijob.commonTools.Setting;

/**
 * 统一资源定位符相关工具类
 * @author Luxiaolei
 *
 */
public class URLUtil {
	private static Logger logger = LoggerFactory.getLogger(Setting.class);
	
	/**
	 * 获得URL
	 * @param pathBaseClassLoader 相对路径（相对于classes）
	 * @return URL
	 */
	public static URL getURL(String pathBaseClassLoader){
		return URLUtil.class.getClassLoader().getResource(pathBaseClassLoader);
	}
	
	/**
	 * 获得URL
	 * @param path 相对给定 class所在的路径
	 * @param clazz 指定class
	 * @return URL
	 */
	public static URL getURL(String path, Class<?> clazz){
		return clazz.getResource(path);
	}
	
	/**
	 * 获得URL，常用于使用绝对路径时的情况
	 * @param configFile URL对应的文件对象
	 * @return URL
	 */
	public static URL getURL(File configFile){
		try {
			return configFile.toURI().toURL();
		} catch (MalformedURLException e) {
			logger.error("Error occured when get URL!", e);
		}
		return null;
	}
}
