/*   
 * Project: OSMP
 * FileName: ResourceConfig.java
 * version: V1.0
 */
package com.osmp.intf.define.service;

/**
 * 
 * Description:
 * @author: wangkaiping
 * @date: 2016年8月8日 上午11:41:44上午10:51:30
 */
public interface ResourceConfig {
	
	String osmp_PID="com.osmp.config";
	
	public String getProperty(String key);

}
