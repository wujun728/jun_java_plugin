package com.osmp.web.system.interceptorMapping.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.osmp.web.system.interceptorMapping.dao.InterceptorMappingMapper;
import com.osmp.web.system.interceptorMapping.entity.InterceptorMapping;
import com.osmp.web.system.interceptorMapping.service.InterceptorMappingService;

@Service
public class InterceptorMappingServiceImpl implements InterceptorMappingService {
    
    @Resource
    private InterceptorMappingMapper mapper;

    @Override
    public List<InterceptorMapping> getAllServices() {
        return mapper.getAllServiceList();
    }

    @Override
    public List<InterceptorMapping> getAllInterceptors() {
        return mapper.getAllInterceptorList();
    }

    @Override
    public List<InterceptorMapping> getServiceListByInterceptor(String interceptorInfo) {
        return mapper.getServiceListByInterceptor(interceptorInfo);
    }

    @Override
    public boolean update(String interceptorInfo,List<String> addServices,List<String> delServices) {
        if(interceptorInfo == null || "".equals(interceptorInfo)) return false;
        if(delServices != null && delServices.size() > 0){
            mapper.deleteMapping(interceptorInfo, delServices);
        }
        if(addServices != null && addServices.size() > 0){
            List<InterceptorMapping> icms = parseAddServices(interceptorInfo,addServices);
            mapper.insertMapping(icms);
        }
        return true;
    }
    
    private List<InterceptorMapping> parseAddServices(String interceptorInfo,List<String> addServices){
        List<InterceptorMapping> list = new ArrayList<InterceptorMapping>();
        String[] inter = interceptorInfo.split("\\(|(\\)-)", 3);
        if(inter == null || inter.length < 3) return null;
        for(String item : addServices){
            if(item == null || "".equals(item)) continue;
            String[] ds = item.split("\\(|(\\)-)", 3);
            if(ds == null || ds.length < 3) continue;
            InterceptorMapping icMapping = new InterceptorMapping();
            icMapping.setInterceptorBundle(inter[0]);
            icMapping.setInterceptorVersion(inter[1]);
            icMapping.setInterceptorName(inter[2]);
            icMapping.setServiceBundle(ds[0]);
            icMapping.setServiceVersion(ds[1]);
            icMapping.setServiceName(ds[2]);
            list.add(icMapping);
        }
        return list;
    }
    
}
