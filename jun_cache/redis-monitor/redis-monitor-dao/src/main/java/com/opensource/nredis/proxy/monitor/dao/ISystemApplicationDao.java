package com.opensource.nredis.proxy.monitor.dao;

import java.util.List;

import com.opensource.nredis.proxy.monitor.model.SystemApplication;

/**
* dao
*
* @author liubing
* @date 2016/12/31 08:09
* @version v1.0.0
*/
public interface ISystemApplicationDao extends IMyBatisRepository<SystemApplication>,IPaginationDao<SystemApplication> {

	/**
	 * 乐观锁
	 * @param systemApplication
	 * @return
	 */
	int updateVersionByIdAndVersion(SystemApplication systemApplication);// 根据主键来更新版本号
	
	/**
	 * 查询前20条数据
	 * @param status
	 * @return
	 */
	public List<SystemApplication> getListByStatus(int status);
}
