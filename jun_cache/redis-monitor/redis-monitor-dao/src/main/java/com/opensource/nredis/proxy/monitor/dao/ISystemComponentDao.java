package com.opensource.nredis.proxy.monitor.dao;

import java.util.List;

import com.opensource.nredis.proxy.monitor.model.SystemComponent;

/**
* dao
*
* @author liubing
* @date 2017/01/02 15:27
* @version v1.0.0
*/
public interface ISystemComponentDao extends IMyBatisRepository<SystemComponent>,IPaginationDao<SystemComponent> {

	public List<SystemComponent> getListByApplicationId(Integer applicationId);
}
