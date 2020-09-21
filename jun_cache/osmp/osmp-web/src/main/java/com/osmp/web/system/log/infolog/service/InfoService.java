package com.osmp.web.system.log.infolog.service;

import java.util.List;

import com.osmp.web.system.log.infolog.entity.InfoLog;
import com.osmp.web.utils.Pagination;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年11月24日 上午10:13:02
 */
public interface InfoService {

	/**
	 * 查询所有错误日志
	 * 
	 * @return
	 */
	public List<InfoLog> getList(Pagination<InfoLog> p, InfoLog infoLog, String startTime, String endTime);

	/**
	 * 查询指定错误日志
	 * 
	 * @return
	 */
	public InfoLog get(InfoLog p);

	/**
	 * 删除一个错误日志
	 * 
	 * @param p
	 */
	public void delete(InfoLog p);

}
