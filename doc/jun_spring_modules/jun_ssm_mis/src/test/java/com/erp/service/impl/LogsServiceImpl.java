package com.erp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erp.dao.PublicDao;
import com.erp.model.Log;
import com.erp.service.ILogsService;
import com.jun.plugin.utils.Constants;
import com.jun.plugin.utils.biz.PageUtil;
@Service("logsService")
public class LogsServiceImpl implements ILogsService
{
	private PublicDao<Log> publicDao;
	@Autowired
	public void setPublicDao(PublicDao<Log> publicDao )
	{
		this.publicDao = publicDao;
	}
	/* (非 Javadoc) 
	* <p>Title: findLogsAllList</p> 
	* <p>Description: </p> 
	* @param map
	* @param pageUtil
	* @return 
	* @see com.erp.service.LogsService#findLogsAllList(java.util.Map, com.erp.util.PageUtil) 
	*/
	public List<Log> findLogsAllList(Map<String, Object> map,PageUtil pageUtil)
	{
		String hql="from Log t where 1=1";
		hql+=Constants.getSearchConditionsHQL("t", map);
		hql+=Constants.getGradeSearchConditionsHQL("t", pageUtil);
		hql+=Constants.getLogSortOrderHql("t", "desc");
		return publicDao.find(hql, map, pageUtil.getPage(), pageUtil.getRows());
	}
	
	public Long getCount(Map<String, Object> map,PageUtil pageUtil)
	{
		String hql="select count(*) from Log t where 1=1";
		hql+=Constants.getSearchConditionsHQL("t", map);
		hql+=Constants.getGradeSearchConditionsHQL("t", pageUtil);
		return  publicDao.count(hql, map);
	}
	
	/* (非 Javadoc) 
	* <p>Title: persistenceLogs</p> 
	* <p>Description: </p> 
	* @param l
	* @return 
	* @see com.erp.service.LogsService#persistenceLogs(com.erp.model.Log) 
	*/
	public boolean persistenceLogs(Log l)
	{
		if (null==l.getLogId()||0==l.getLogId()||"".equals(l.getLogId()))
		{
			l.setLogDate(new Date());
			l.setUserId(Constants.getCurrendUser().getUserId());
			publicDao.save(l);
		}else {
			l.setUserId(Constants.getCurrendUser().getUserId());
			publicDao.update(l);
		}
		return true;
	}
	
	/* (非 Javadoc) 
	* <p>Title: delLogs</p> 
	* <p>Description: </p> 
	* @param logId
	* @return 
	* @see com.erp.service.LogsService#delLogs(java.lang.Integer) 
	*/
	public boolean delLogs(Integer logId)
	{
		publicDao.delete(publicDao.get(Log.class, logId));
		return true;
	}
}
