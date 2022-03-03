package net.chenlin.dp.common.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.chenlin.dp.common.dao.SysLogMapper;
import net.chenlin.dp.common.entity.Page;
import net.chenlin.dp.common.entity.Query;
import net.chenlin.dp.common.entity.SysLogEntity;
import net.chenlin.dp.common.manager.SysLogManager;

/**
 * 系统日志
 *
 * @author ZhouChenglin
 * @email yczclcn@163.com
 * @url www.chenlintech.com
 * @date 2017年8月14日 下午8:43:15
 */
@Component("sysLogManager")
public class SysLogManagerImpl implements SysLogManager {

	@Autowired
	private SysLogMapper sysLogMapper;
	
	@Override
	public void saveLog(SysLogEntity log) {
		sysLogMapper.save(log);
	}

	@Override
	public List<SysLogEntity> listLog(Page<SysLogEntity> page, Query query) {
		return sysLogMapper.listForPage(page, query);
	}

	@Override
	public int batchRemove(Long[] id) {
		return sysLogMapper.batchRemove(id);
	}

	@Override
	public int batchRemoveAll() {
		return sysLogMapper.batchRemoveAll();
	}

}
