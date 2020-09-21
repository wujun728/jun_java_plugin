package com.osmp.web.system.interceptorMapping.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.osmp.web.core.mybatis.BaseMapper;
import com.osmp.web.system.interceptorMapping.entity.InterceptorMapping;

public interface InterceptorMappingMapper extends BaseMapper<InterceptorMapping> {
    List<InterceptorMapping> getAllServiceList();
    List<InterceptorMapping> getAllInterceptorList();
    List<InterceptorMapping> getServiceListByInterceptor(String interceptorInfo);
    void deleteMapping(@Param("interceptor")String interceptorInfo,@Param("services")List<String> services);
    void insertMapping(List<InterceptorMapping> ims);
}
