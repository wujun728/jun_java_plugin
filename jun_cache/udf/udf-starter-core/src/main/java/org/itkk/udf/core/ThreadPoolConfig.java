/**
 * ThreadPoolConfig.java
 * Created at 2017-05-03
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 描述 : ThreadPoolConfig
 *
 * @author Administrator
 */
@Configuration
public class ThreadPoolConfig {

    /**
     * 描述 : applicationConfig
     */
    @Autowired
    private ApplicationConfig applicationConfig;

    /**
     * 描述 : 默认线程池
     *
     * @return Executor
     */
    @Bean
    public Executor defaultThreadPool() {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        //设置线程池
        executor.setPoolSize(applicationConfig.getThreadPool().getPoolSize());
        executor.setThreadPriority(applicationConfig.getThreadPool().getThreadPriority());
        executor.setThreadNamePrefix(applicationConfig.getThreadPool().getThreadNamePrefix());
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}
