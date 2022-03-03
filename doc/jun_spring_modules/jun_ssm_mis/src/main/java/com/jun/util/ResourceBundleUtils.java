package com.jun.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 资源文件工具类
 * @author Wujun
 *
 */
public class ResourceBundleUtils {
	private static ResourceBundle resourceBundle;

	/**
	 * 实例化ResourceBundle
	 * @param resourceFileName 资源文件名，含包名
	 * @param local 本地化 zh_CN/en_US
	 * @return
	 */
    public static ResourceBundle getInstance(String resourceFileName,String local){
    	resourceBundle = ResourceBundle.getBundle(resourceFileName,new Locale(local));
    	return resourceBundle;
    }
    
    /**
	 * 实例化ResourceBundle
	 * @param resourceFileName 资源文件名，含包名
	 * @param local 本地化 Locale.CHINA / Locale.US
	 * @return ResourceBundle实例
	 */
    public static ResourceBundle getInstance(String resourceFileName,Locale local){
    	resourceBundle = ResourceBundle.getBundle(resourceFileName,local);
    	return resourceBundle;
    }
    /**
     * 根据key获取资源文件里对应的值
     * @param key
     * @return String
     */
    public static String getString(String key){
    	return resourceBundle.getString(key);
    }
    public static void main(String[] args) {
    	ResourceBundleUtils.getInstance("com.yida.common.report.message", Locale.CHINA);
    	String value = ResourceBundleUtils.getString("myReport.pageNumber");
    	System.out.println(value);
	}
}
