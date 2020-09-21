/*   
 * Project: OSMP
 * FileName: ServiceFactory.java
 * version: V1.0
 */
package com.osmp.intf.define.factory;

import java.util.Map;

import com.osmp.intf.define.model.InvocationDefine;

/**
 * Description:服务工厂
 * @author: wangkaiping
 * @date: 2016年8月9日 上午9:26:03上午10:51:30
 */
public interface ServiceFactory {
	// 根据服务名获取服务相关定义
	public InvocationDefine getInvocationDefine(String name);

	// 获取所有服务
	public Map<String, String> getAllService();
}
