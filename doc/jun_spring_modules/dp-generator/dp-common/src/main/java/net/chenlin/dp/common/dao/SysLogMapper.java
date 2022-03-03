package net.chenlin.dp.common.dao;

import org.mybatis.spring.annotation.MapperScan;

import net.chenlin.dp.common.entity.SysLogEntity;

/**
 * 系统日志 
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年8月14日 下午8:19:01
 */
@MapperScan
public interface SysLogMapper extends BaseMapper<SysLogEntity> {

	int batchRemoveAll();
	
}
