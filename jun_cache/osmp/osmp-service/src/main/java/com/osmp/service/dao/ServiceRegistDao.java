/*   
 * Project: OSMP
 * FileName: ServiceRegistDao.java
 * version: V1.0
 */
package com.osmp.service.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.osmp.intf.define.config.FrameConst;
import com.osmp.jdbc.support.JdbcDao;
import com.osmp.service.bean.DataServiceInfo;
import com.osmp.service.bean.DataServiceMappingInfo;
import com.osmp.service.bean.InterceptorInfo;
import com.osmp.service.bean.InterceptorMappingInfo;
import com.osmp.service.util.ServiceUtil;

public class ServiceRegistDao implements InitializingBean{
    private Logger logger = LoggerFactory.getLogger(ServiceRegistDao.class);
    private String catalog = "osmp";
    
    //jdbc操作管理类
    private JdbcDao jdbcDao;
    
    public void setJdbcDao(JdbcDao jdbcDao) {
        this.jdbcDao = jdbcDao;
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(jdbcDao, "JdbcDao未注入");
    }
    
    //注册接口服务
    public boolean updateDataService(DataServiceInfo info){
        if(info == null) return true;
        String ip = FrameConst.getLoadIp();
        
        try{
            List<DataServiceInfo> dd = jdbcDao.queryForList("osmp.service.findDataServiceByName",catalog, DataServiceInfo.class,info.getBundle(),
                    info.getVersion(),info.getName(),ip);
            
            if(dd == null || dd.isEmpty()){
                jdbcDao.update("osmp.service.insertDataServiceByName",catalog,info.getBundle(),info.getVersion(),info.getName(),info.getState(),
                        info.getUpdateTime(),info.getMark(),ip);
            }else{
                jdbcDao.update("osmp.service.updateDataServiceByName",catalog,info.getState(),info.getUpdateTime(),info.getMark(),
                        info.getBundle(),info.getVersion(),info.getName(),info.getUpdateTime(),ip);
            }
            logger.info("更新接口服务:"+ServiceUtil.generateServiceName(info.getBundle(), info.getVersion(), info.getName()) + "状态为:"+(info.getState() == 0?"停止":"启用"));
            return true;
        }catch(Throwable e){
            logger.error(e.getMessage());
        }
        return false;
    } 
    
    //注册接口服务拦截器
    public boolean updateServiceInterceptor(InterceptorInfo info){
        if(info == null) return true;
        String ip = FrameConst.getLoadIp();
        try{
            List<InterceptorInfo> ii = jdbcDao.queryForList("osmp.service.findInterceptorByName",catalog, InterceptorInfo.class,info.getBundle(),
                    info.getVersion(),info.getName(),ip);
            
            if(ii == null || ii.isEmpty()){
                jdbcDao.update("osmp.service.insertInterceptor",catalog,info.getBundle(),info.getVersion(),info.getName(),info.getState(),info.getUpdateTime(),info.getMark(),ip);
                
            }else{
                jdbcDao.update("osmp.service.updateInterceptorByName",catalog,info.getState(),info.getMark(),
                        info.getUpdateTime(),info.getBundle(),info.getVersion(),info.getName(),info.getUpdateTime(),ip);
            }
            logger.info("更改接口服务拦截器:"+ServiceUtil.generateServiceName(info.getBundle(), info.getVersion(), info.getName())+"状态为:"+(info.getState() == 0?"停止":"启用"));
            return true;
        }catch(Throwable e){
            logger.error(e.getMessage());
        }
        return false;
    } 
    
    
    public List<DataServiceMappingInfo> findAllDataServiceMapping(){
        try{
            return jdbcDao.queryForList("osmp.service.findAllDataServiceMapping",catalog, DataServiceMappingInfo.class);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }
    
    public List<InterceptorMappingInfo> findAllInterceptorMapping(){
        try{
            return jdbcDao.queryForList("osmp.service.findAllInterceptorMapping",catalog, InterceptorMappingInfo.class);
        }catch(Exception e){
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    
}
