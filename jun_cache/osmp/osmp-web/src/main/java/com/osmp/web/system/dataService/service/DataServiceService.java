package com.osmp.web.system.dataService.service;

import java.util.List;

import com.osmp.web.system.dataService.entity.DataService;
import com.osmp.web.utils.Pagination;

/**
 * Description:
 *
 * @author: wangkaiping
 * @date: 2014年11月19日 下午2:36:04
 */
public interface DataServiceService {

	public List<DataService> getList(Pagination<DataService> p, String where);
	
	/**
	 * 得到所有满足条件的服务
	 * @param where
	 * @return
	 */
	public List<DataService> getByWhere(String where);

}
