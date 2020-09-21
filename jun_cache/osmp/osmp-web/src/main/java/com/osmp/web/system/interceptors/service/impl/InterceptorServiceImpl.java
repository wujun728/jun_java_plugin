package com.osmp.web.system.interceptors.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.osmp.web.system.interceptors.dao.InterceptorMapper;
import com.osmp.web.system.interceptors.entity.Interceptor;
import com.osmp.web.system.interceptors.service.InterceptorService;
import com.osmp.web.utils.Pagination;

@Service
public class InterceptorServiceImpl implements InterceptorService {

	@Autowired
	InterceptorMapper interceptorMapper;
	
	@Override
	public List<Interceptor> getList(Pagination<Interceptor> p, String where) {
		return interceptorMapper.selectByPage(p, Interceptor.class, where);
	}

}
