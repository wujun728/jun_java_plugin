package com.opensource.nredis.proxy.monitor.dao;
import java.util.List;

import com.opensource.nredis.proxy.monitor.model.RedisClusterSlave;

/**
* dao
*
* @author liubing
* @date 2017/01/11 12:18
* @version v1.0.0
*/
public interface IRedisClusterSlaveDao extends IMyBatisRepository<RedisClusterSlave>,IPaginationDao<RedisClusterSlave> {

	/**
	 * 乐观锁
	 * @param systemApplication
	 * @return
	 */
	int updateVersionByIdAndVersion(RedisClusterSlave redisClusterSlave);// 根据主键来更新版本号
	
	public List<RedisClusterSlave> getListByMasterId(int masterId);//查出前50条数据
}
