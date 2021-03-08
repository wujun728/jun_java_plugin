package com.itstyle.web.worker;

import java.util.Map;
import java.util.Queue;
/**
 * Wroker负责处理子任务
 * 创建者 张志朋
 * 创建时间	2017年6月22日
 *
 */
public class Worker implements Runnable {  
    //任务队列  
    protected Queue<Object> workQueue;  
    //子任务处理结果集  
    protected Map<String, Object> resultMap;  
    public void setWorkQueue(Queue<Object> workQueue) {  
        this.workQueue = workQueue;  
    }  
  
    public void setResultMap(Map<String, Object> resultMap) {  
        this.resultMap = resultMap;  
    }  
      
    //子任务处理的逻辑，在这里不作具体实现，由子类实现  
    public Object handle(Object input) {  
        return input;  
    }  
      
    @Override  
    public void run() {  
        while(true) {  
            //获取子任务  
            Object input = workQueue.poll();  
            if(input == null) break;  
            //处理子任务  
            Object re = handle(input);  
            //将处理结果写入结果集  
            resultMap.put(Integer.toString(input.hashCode()), re);  
        }  
    }  
}  
