package com.itstyle.web.worker;

import java.util.HashMap;  
import java.util.Map;  
import java.util.Queue;  
import java.util.concurrent.ConcurrentHashMap;  
import java.util.concurrent.ConcurrentLinkedQueue;  
/**
 * Master负责接收和分配任务
 * 创建者 张志朋
 * 创建时间	2017年6月22日
 *
 */
public class Master {  
    //任务队列  
    protected Queue<Object> workQueue =   
            new ConcurrentLinkedQueue<Object>();  
    //Worker进程队列  
    protected Map<String, Thread> threadMap =   
            new HashMap<String, Thread>();  
    //子任务处理结果集  
    protected Map<String, Object> resultMap =  
            new ConcurrentHashMap<String, Object>();  
      
    public Master(Worker worker, int countWorker) {  
        worker.setWorkQueue(workQueue);  
        worker.setResultMap(resultMap);  
        for(int i=0; i<countWorker; i++) {  
            threadMap.put(Integer.toString(i),   
                    new Thread(worker, Integer.toString(i)));  
        }  
    }  
      
    //是否所有的子任务都介绍了  
    public boolean isComplete() {  
        for(Map.Entry<String, Thread> entry : threadMap.entrySet()) {  
            if(entry.getValue().getState() != Thread.State.TERMINATED)  
                //存在为完成的线程  
                return false;  
        }  
        return true;  
    }  
      
    //提交一个子任务  
    public void submit(Object job) {  
        workQueue.add(job);  
    }  
      
    //返回子任务结果集  
    public Map<String, Object> getResultMap() {  
        return resultMap;  
    }  
      
    //执行所有Worker进程，进行处理  
    public void execute() {  
        for(Map.Entry<String, Thread> entry : threadMap.entrySet()) {  
            entry.getValue().start();  
        }  
    }  
}  
