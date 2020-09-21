/*   
 * Project: OSMP
 * FileName: ServiceConfigManager.java
 * version: V1.0
 */
package com.osmp.service.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.osmp.service.bean.DataServiceMappingInfo;
import com.osmp.service.bean.InterceptorMappingInfo;
import com.osmp.service.dao.ServiceRegistDao;
import com.osmp.service.registration.ServiceContainer;

/**
 * 接口服务及拦截器配置变更管理
 * @author heyu
 */
public class ServiceConfigManager implements InitializingBean{
    
    private ServiceRegistDao serviceDao;
    public void setServiceDao(ServiceRegistDao serviceDao) {
        this.serviceDao = serviceDao;
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(serviceDao,"serviceDao未初始化...");
    }
    
    /**
     * 更新dataService接口配置
     */
    public void updateDataServiceMapping(){
        List<DataServiceMappingInfo> dsInfos = serviceDao.findAllDataServiceMapping();
        if(dsInfos != null && !dsInfos.isEmpty()){
            Map<String, String> dataServiceMappingConfig = new HashMap<String, String>();
            for(DataServiceMappingInfo info : dsInfos){
                if(info == null) continue;
                dataServiceMappingConfig.put(info.getInterfaceName(), info.generateServiceName());
            }
            ServiceContainer.getInstance().updateDataServiceMapping(dataServiceMappingConfig);
        }
    }
    
    
    /**
     * 更新dataService接口配置
     */
    public void updateInterceptorMapping(){
        List<InterceptorMappingInfo> icInfos = serviceDao.findAllInterceptorMapping();
        if(icInfos != null && !icInfos.isEmpty()){
            Map<String, List<String>> interceptorMappingConfig = new HashMap<String, List<String>>();
            for(InterceptorMappingInfo info : icInfos){
                if(info == null) continue;
                if(interceptorMappingConfig.get(info.generateServiceName()) == null){
                    interceptorMappingConfig.put(info.generateServiceName(),new ArrayList<String>());
                }
                interceptorMappingConfig.get(info.generateServiceName()).add(info.generateInterceptorName());
            }
            ServiceContainer.getInstance().updateInterceptorMapping(interceptorMappingConfig);
        }
    }
}
