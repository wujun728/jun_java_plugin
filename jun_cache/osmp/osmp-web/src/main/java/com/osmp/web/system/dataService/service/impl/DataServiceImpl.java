package com.osmp.web.system.dataService.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osmp.web.system.dataService.dao.DataServiceMapper;
import com.osmp.web.system.dataService.entity.DataService;
import com.osmp.web.system.dataService.service.DataServiceService;
import com.osmp.web.utils.Pagination;

/**
 * Description:
 *
 * @author: wangkaiping
 * @date: 2014年11月19日 下午2:54:34
 */
@Service
public class DataServiceImpl implements DataServiceService {
	
	@Autowired
	private DataServiceMapper dataServiceMapper;
	@Override
	public List<DataService> getList(Pagination<DataService> p, String where) {
		return dataServiceMapper.selectByPage(p, DataService.class, where);
	}
	
	@Override
	public List<DataService> getByWhere(String where) {
		return dataServiceMapper.selectAll(DataService.class, where);
	}

	
}
