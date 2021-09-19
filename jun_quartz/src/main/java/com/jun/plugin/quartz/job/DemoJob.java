package com.jun.plugin.quartz.job;

/**
 * 定时任务实现类
 */
public class DemoJob {
    /**
     * 定时任务需要依赖的参数
     */
    private String jobParam ;
    private String jobParam1;
    /**
     * 定时任务方法
     */
    public void jobmethod(){
        System.out.println("execute job method:jobParam="+jobParam+",jobParam1="+jobParam);

    }
}
