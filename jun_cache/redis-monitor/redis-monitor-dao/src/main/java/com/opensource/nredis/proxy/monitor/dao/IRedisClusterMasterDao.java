package com.opensource.nredis.proxy.monitor.dao;

import java.util.List;
import com.opensource.nredis.proxy.monitor.model.RedisClusterMaster;

/**
* dao
*
* @author liubing
* @date 2017/01/11 12:18
* @version v1.0.0
*/
public interface IRedisClusterMasterDao extends IMyBatisRepository<RedisClusterMaster>,IPaginationDao<RedisClusterMaster> {

	/**
	 * 乐观锁
	 * @param systemApplication
	 * @return
	 */
	int updateVersionByIdAndVersion(RedisClusterMaster redisClusterMaster);// 根据主键来更新版本号
	
	/**
	 * 查询前50条数据
	 * @param status
	 * @return
	 */
	public List<RedisClusterMaster> getListByStatus();//查出前50条数据
}
