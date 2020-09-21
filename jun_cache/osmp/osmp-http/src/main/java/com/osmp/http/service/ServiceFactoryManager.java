/*   
 * Project: OSMP
 * FileName: ServiceFactoryManager.java
 * version: V1.0
 */
package com.osmp.http.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.osmp.intf.define.factory.ServiceFactory;
import com.osmp.intf.define.model.InvocationDefine;

/**
 * Description:服务工厂管理
 * @author: wangkaiping
 * @date: 2016年8月9日 上午9:12:53上午10:51:30
 */
public class ServiceFactoryManager {
    
    private Logger logger = LoggerFactory.getLogger(ServiceFactoryManager.class);
    //存储所有dataService服务
    private ServiceFactory serviceFactory;
    //获取服务
    public InvocationDefine getInvocationDefine(String serviceName){
        if(serviceFactory == null){
            logger.error("未绑定服务工厂");
            return null;
        }
        return serviceFactory.getInvocationDefine(serviceName);
    }
    
    //监听服务工厂
    public void bind(ServiceFactory serviceFactory,Map<Object,Object> props){
        if(serviceFactory == null) return;
        this.serviceFactory = serviceFactory;
        logger.info("绑定服务工厂");
    }
    
    //监听服务工厂移除
    public void unbind(ServiceFactory serviceFactory,Map<Object,Object> props){
        if(serviceFactory == null) return;
        this.serviceFactory = null;
        logger.info("解绑服务工厂");
    }
}
