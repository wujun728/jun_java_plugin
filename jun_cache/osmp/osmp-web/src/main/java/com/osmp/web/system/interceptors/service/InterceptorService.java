package com.osmp.web.system.interceptors.service;

import java.util.List;

import com.osmp.web.system.interceptors.entity.Interceptor;
import com.osmp.web.utils.Pagination;

public interface InterceptorService {
	
	public List<Interceptor> getList(Pagination<Interceptor> p, String where);

}
