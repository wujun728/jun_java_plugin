package com.opensource.nredis.proxy.monitor.dao;

import com.opensource.nredis.proxy.monitor.model.SystemApplicationMonitor;

/**
* dao
*
* @author liubing
* @date 2016/12/31 08:09
* @version v1.0.0
*/
public interface ISystemApplicationMonitorDao extends IMyBatisRepository<SystemApplicationMonitor>,IPaginationDao<SystemApplicationMonitor> {
}
