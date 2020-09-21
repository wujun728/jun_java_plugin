/*   
 * Project: OSMP
 * FileName: LogPool.java
 * version: V1.0
 */
package com.osmp.log.pool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.osgi.service.log.LogEntry;
import org.springframework.beans.factory.DisposableBean;

import com.osmp.log.task.LogTask;

public class LogPool implements DisposableBean{
    public static boolean canLog = true;
    private static final Object lock = new Object();
    private ThreadPoolExecutor poolExecutor = null;
    private LinkedBlockingQueue<Runnable> logQueue = new LinkedBlockingQueue<Runnable>();
    private int corePoolSize = 3;
    private int maximumPoolSize = 5;
    private int keepAliveTime = 20;
    private final TimeUnit timeUtil = TimeUnit.MINUTES;
    
    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }
    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }
    public void setKeepAliveTime(int keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    //获取线程池
    private ThreadPoolExecutor getPoolExecutor(){
        if(poolExecutor == null){
            synchronized (lock) {
                if(poolExecutor == null){
                    poolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 
                            keepAliveTime, timeUtil, logQueue);
                }
            }
        }
        return poolExecutor;
    }
    
    //添加日志
    public void addLog(final LogEntry log){
        if(log == null) return;
        if(!LogPool.canLog) return;
        getPoolExecutor().execute(new LogTask(log));
    }
    @Override
    public void destroy() throws Exception {
        if(poolExecutor != null){
            poolExecutor.shutdown();
        }
    }
    
}
