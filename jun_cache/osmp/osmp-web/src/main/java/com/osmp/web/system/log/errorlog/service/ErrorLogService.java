package com.osmp.web.system.log.errorlog.service;

import java.util.List;

import com.osmp.web.system.log.errorlog.entity.ErrorLog;
import com.osmp.web.utils.Pagination;

/**
 * Description:
 * 
 * @author: zhangjunming
 * @date: 2014年11月13日 下午17:51:35
 */

public interface ErrorLogService {

	/**
	 * 查询所有错误日志
	 * 
	 * @return
	 */
	public List<ErrorLog> getList(Pagination<ErrorLog> p, ErrorLog errorLog, String startTime, String endTime);

	/**
	 * 查询指定错误日志
	 * 
	 * @return
	 */
	public ErrorLog get(ErrorLog p);

	/**
	 * 新增一个错误日志
	 * 
	 * @param p
	 */
	public void insert(ErrorLog p);

	/**
	 * 删除一个错误日志
	 * 
	 * @param p
	 */
	public void delete(ErrorLog p);

	/**
	 * 修改一个错误日志
	 * 
	 * @param p
	 */
	public void update(ErrorLog p);

}
