package com.opensource.nredis.proxy.monitor.service;

import java.util.List;

import com.opensource.nredis.proxy.monitor.model.SystemComponent;

/**
* service interface
*
* @author liubing
* @date 2017/01/02 15:27
* @version v1.0.0
*/
public interface ISystemComponentService extends IBaseService<SystemComponent>,IPaginationService<SystemComponent>  {
	public List<SystemComponent> getListByApplicationId(Integer applicationId);
}

