package com.osmp.web.system.interceptorMapping.service;

import java.util.List;

import com.osmp.web.system.interceptorMapping.entity.InterceptorMapping;

public interface InterceptorMappingService {
    List<InterceptorMapping> getAllServices();
    List<InterceptorMapping> getAllInterceptors();
    List<InterceptorMapping> getServiceListByInterceptor(String interceptorInfo);
    boolean update(String interceptorInfo,List<String> addServices,List<String> delServices);
}
