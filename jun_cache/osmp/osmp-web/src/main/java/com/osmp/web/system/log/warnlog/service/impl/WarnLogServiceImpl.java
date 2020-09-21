package com.osmp.web.system.log.warnlog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osmp.web.system.log.warnlog.dao.WarnLogMapper;
import com.osmp.web.system.log.warnlog.entity.WarnLog;
import com.osmp.web.system.log.warnlog.service.WarnLogService;

@Service
public class WarnLogServiceImpl implements WarnLogService{

	@Autowired
	private WarnLogMapper warnLogMapper;
	
	@Override
	public List<WarnLog> getList() {
		return warnLogMapper.selectAll(WarnLog.class, "");
	}

	@Override
	public void addWarnLog(WarnLog warnLog) {
		warnLogMapper.insert(warnLog);
		
	}

}
