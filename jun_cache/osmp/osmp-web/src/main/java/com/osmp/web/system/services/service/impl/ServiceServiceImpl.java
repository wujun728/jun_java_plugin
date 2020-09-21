package com.osmp.web.system.services.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osmp.web.system.services.dao.ServiceMapper;
import com.osmp.web.system.services.service.ServiceService;
import com.osmp.web.utils.Pagination;

@Service
public class ServiceServiceImpl implements ServiceService {

	@Autowired
	private ServiceMapper serviceMapper;
	
	@Override
	public List<com.osmp.web.system.services.entity.Service> getList(
			Pagination<com.osmp.web.system.services.entity.Service> p,
			String where) {
		return serviceMapper.selectByPage(p, com.osmp.web.system.services.entity.Service.class, where);
	}
	
}
