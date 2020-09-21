/*   
 * Project: OSMP
 * FileName: ServiceStateManager.java
 * version: V1.0
 */
package com.osmp.service.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.osmp.service.bean.DataServiceInfo;
import com.osmp.service.bean.InterceptorInfo;
import com.osmp.service.dao.ServiceRegistDao;

/**
 * 接口服务及拦截器状态变更管理
 * @author heyu
 *
 */
public class ServiceStateManager implements InitializingBean,DisposableBean{
    private Logger logger = LoggerFactory.getLogger(ServiceStateManager.class);
    
    //定时任务线程池
    private ScheduledThreadPoolExecutor poolExecutor;
    
    public void afterPropertiesSet() throws Exception {
        poolExecutor = new ScheduledThreadPoolExecutor(2);
    }
    
    //接口服务更新任务
    private Runnable updateDsTask = new Runnable() {
        @Override
        public void run() {
            logger.info("接口服务更新任务执行...");
            if(!dataServiceQueue.isEmpty()){
                Iterator<DataServiceInfo> dsIter = dataServiceQueue.iterator();
                while(dsIter.hasNext()){
                    DataServiceInfo dsd = dsIter.next();
                    if(serviceDao.updateDataService(dsd)) dsIter.remove();
                }
            }
            startDsTask(false);
        }
    };
    //服务拦截器更新任务
    private Runnable updateIcTask = new Runnable() {
        @Override
        public void run() {
            logger.info("接口服务拦截器更新任务执行...");
            if(!interceptorQueue.isEmpty()){
                Iterator<InterceptorInfo> dsIter = interceptorQueue.iterator();
                while(dsIter.hasNext()){
                    InterceptorInfo itd = dsIter.next();
                    if(serviceDao.updateServiceInterceptor(itd)) dsIter.remove();
                }
            }
            startIcTask(false);
        }
    };
    
    private int taskInSeconds = 60;
    
    //启动或停止接口服务更新线程任务
    private boolean registDsTaskStarted = false;
    private ScheduledFuture<?> registDsFuture = null;
    private void startDsTask(boolean start){
        if(start && registDsTaskStarted) return;
        if(start && !registDsTaskStarted){
            registDsFuture = poolExecutor.scheduleAtFixedRate(updateDsTask, taskInSeconds,taskInSeconds, TimeUnit.SECONDS);
            registDsTaskStarted = true;
        }else{
            if(!registDsTaskStarted) return;
            if(dataServiceQueue.isEmpty()){
                if(registDsFuture != null){
                    registDsTaskStarted = !registDsFuture.cancel(true);
                }
            }
        }
    }
    
    //启动或停止接口服务拦截器更新线程任务
    private boolean registIcTaskStarted = false;
    private ScheduledFuture<?> registIcFuture = null;
    private void startIcTask(boolean start){
        if(start && registIcTaskStarted) return;
        if(start && !registIcTaskStarted){
            registIcFuture = poolExecutor.scheduleAtFixedRate(updateIcTask,taskInSeconds, taskInSeconds, TimeUnit.SECONDS);
            registIcTaskStarted = true;
        }else{
            if(!registIcTaskStarted) return;
            if(interceptorQueue.isEmpty()){
                if(registIcFuture != null){
                    registIcTaskStarted = !registIcFuture.cancel(true);
                }
            }
        }
    }
    
   
    /*****************************以下队列为服务注册或注销时入库操作出错时的服务临时存储***************************/
    
    //需更新的DataService服务队列
    private List<DataServiceInfo> dataServiceQueue = new ArrayList<DataServiceInfo>();
    //需更新的ServiceInterceptor服务队列
    private List<InterceptorInfo> interceptorQueue = new ArrayList<InterceptorInfo>();
    
    //jdbc操作管理类
    private ServiceRegistDao serviceDao;
    
    public void setServiceDao(ServiceRegistDao serviceDao) {
        this.serviceDao = serviceDao;
    }

    public void updateDataService(DataServiceInfo info) {
        if(!serviceDao.updateDataService(info)){
            dataServiceQueue.add(info);
            startDsTask(true);
        }
    }

    public void updateServiceInterceptor(InterceptorInfo info) {
        if(!serviceDao.updateServiceInterceptor(info)){
            interceptorQueue.add(info);
            startIcTask(true);
        }
    }

    public void destroy() throws Exception {
        if(poolExecutor != null){
            poolExecutor.shutdownNow();
            logger.info("停止线程池...");
        }
    }
   
}
