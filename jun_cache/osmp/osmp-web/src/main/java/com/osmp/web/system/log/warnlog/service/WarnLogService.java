package com.osmp.web.system.log.warnlog.service;

import java.util.List;

import com.osmp.web.system.log.warnlog.entity.WarnLog;

public interface WarnLogService {

	public List<WarnLog> getList();
	
	public void addWarnLog(WarnLog warnLog);
	
}
