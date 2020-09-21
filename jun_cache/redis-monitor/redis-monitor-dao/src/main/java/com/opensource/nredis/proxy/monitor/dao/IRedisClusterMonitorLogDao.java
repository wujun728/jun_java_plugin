package com.opensource.nredis.proxy.monitor.dao;

import com.opensource.nredis.proxy.monitor.model.RedisClusterMonitorLog;

/**
* dao
*
* @author liubing
* @date 2017/01/11 12:18
* @version v1.0.0
*/
public interface IRedisClusterMonitorLogDao extends IMyBatisRepository<RedisClusterMonitorLog>,IPaginationDao<RedisClusterMonitorLog> {
}
