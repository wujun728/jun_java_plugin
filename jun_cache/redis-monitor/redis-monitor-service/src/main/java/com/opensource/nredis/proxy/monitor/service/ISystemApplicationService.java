package com.opensource.nredis.proxy.monitor.service;

import com.opensource.nredis.proxy.monitor.model.SystemApplication;

/**
* service interface
*
* @author liubing
* @date 2016/12/31 08:09
* @version v1.0.0
*/
public interface ISystemApplicationService extends IBaseService<SystemApplication>,IPaginationService<SystemApplication>  {

	int updateVersionByIdAndVersion(SystemApplication systemApplication);// 根据主键来更新版本号
	
	/**
	 * 获取唯一信息
	 * @param host
	 * @param port
	 * @return
	 */
	public SystemApplication getSystemApplicationByHostAndPort(String host,Integer port) throws Exception;
}

