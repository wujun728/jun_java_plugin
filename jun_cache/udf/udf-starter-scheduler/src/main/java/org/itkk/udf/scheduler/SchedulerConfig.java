/**
 * SchedulerConfig.java
 * Created at 2017-06-01
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package org.itkk.udf.scheduler;

import lombok.Data;
import org.itkk.udf.scheduler.impl.DefListenerEvent;
import org.itkk.udf.scheduler.impl.DefRmsJobEvent;
import org.itkk.udf.scheduler.job.ClearScheduledLog;
import org.itkk.udf.scheduler.job.ClearScheduledTriggerLog;
import org.itkk.udf.scheduler.listener.JobDetailListener;
import org.itkk.udf.scheduler.listener.SchListener;
import org.itkk.udf.scheduler.listener.TriggerDetailListener;
import org.quartz.*;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.validation.annotation.Validated;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Properties;

/**
 * 描述 : SchedulerConfig
 *
 * @author Administrator
 */
@Configuration
@ConfigurationProperties(prefix = "org.itkk.scheduler.config")
@Validated
@Data
public class SchedulerConfig {

    /**
     * 描述 : DEF_GROUP_NAME
     */
    private static final String DEF_GROUP_NAME = "SystemAutoRun";

    /**
     * 描述 : 是否记录日志
     */
    @NotNull
    private Boolean logFlag = true;

    /**
     * 描述 : 是否记录详细日志
     */
    @NotNull
    private Boolean logDetailFlag = false;

    /**
     * 描述 : 是否自动启动
     */
    @NotNull
    private Boolean autoStartup = true;

    /**
     * 描述 : 是否覆盖已经存在的jobs
     */
    @NotNull
    private Boolean overwriteExistingJobs = true;

    /**
     * 描述 : 延迟启动秒数
     */
    @NotNull
    private Integer startupDelay = 0;

    /**
     * 描述 : Job接受applicationContext的成员变量名
     */
    @NotNull
    private String applicationContextSchedulerContextKey = "applicationContext";

    /**
     * 描述 : quartz配置文件地址
     */
    @NotNull
    private String quartzPropertiesPath = "def_quartz.properties";

    /**
     * 描述 : schedulerProperties
     */
    @Autowired
    private SchedulerProperties schedulerProperties;

    /**
     * 描述 : jobFactory
     *
     * @param applicationContext applicationContext
     * @return JobFactory
     */
    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    /**
     * 描述 : schedulerFactoryBean
     *
     * @param dataSource                      dataSource
     * @param jobFactory                      jobFactory
     * @param quartzProperties                quartzProperties
     * @param schListener                     schListener
     * @param jobDetailListener               jobDetailListener
     * @param triggerDetailListener           triggerDetailListener
     * @param clearScheduledLogTrigger        clearScheduledLogTrigger
     * @param clearScheduledTriggerLogTrigger clearScheduledTriggerLogTrigger
     * @return SchedulerFactoryBean
     */
    @Bean("clusterQuartzScheduler")
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource, JobFactory jobFactory, //NOSONAR
                                                     Properties quartzProperties, SchedulerListener schListener, JobListener jobDetailListener,
                                                     TriggerListener triggerDetailListener,
                                                     @Qualifier("clearScheduledLogTrigger") Trigger clearScheduledLogTrigger,
                                                     @Qualifier("clearScheduledTriggerLogTrigger") Trigger clearScheduledTriggerLogTrigger) { //NOSONAR
        //实例化
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setJobFactory(jobFactory);
        //属性设置
        factory.setQuartzProperties(quartzProperties);
        factory.setOverwriteExistingJobs(getOverwriteExistingJobs());
        factory.setAutoStartup(getAutoStartup());
        factory.setStartupDelay(getStartupDelay());
        factory.setApplicationContextSchedulerContextKey(getApplicationContextSchedulerContextKey());
        //添加监听器
        factory.setSchedulerListeners(schListener);
        factory.setGlobalJobListeners(jobDetailListener);
        factory.setGlobalTriggerListeners(triggerDetailListener);
        //添加默认的job
        factory.setTriggers(clearScheduledLogTrigger, clearScheduledTriggerLogTrigger);
        //返回
        return factory;
    }

    /**
     * 描述 : quartzProperties
     *
     * @return Properties
     * @throws IOException IOException
     */
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource(getQuartzPropertiesPath()));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    /**
     * 描述 : schListener
     *
     * @return SchedulerListener
     */
    @Bean(name = "schListener")
    public SchedulerListener schListener() {
        return new SchListener();
    }

    /**
     * 描述 : jobDetailListener
     *
     * @return JobListener
     */
    @Bean(name = "jobDetailListener")
    public JobListener jobDetailListener() {
        return new JobDetailListener();
    }

    /**
     * 描述 : triggerDetailListener
     *
     * @return TriggerListener
     */
    @Bean(name = "triggerDetailListener")
    public TriggerListener triggerDetailListener() {
        return new TriggerDetailListener();
    }

    /**
     * 描述 : clearScheduledLogTrigger
     *
     * @param jobDetail jobDetail
     * @return CronTriggerFactoryBean
     */
    @Bean(name = "clearScheduledLogTrigger")
    public CronTriggerFactoryBean clearScheduledLogTrigger(
            @Qualifier("clearScheduledLogJobDetail") JobDetail jobDetail) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setDescription("清理计划任务日志");
        factoryBean.setName("clearScheduledLogTrigger");
        factoryBean.setGroup(DEF_GROUP_NAME);
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setCronExpression("0 0 1 * * ? ");
        return factoryBean;
    }

    /**
     * 描述 : clearScheduledLogJobDetail
     *
     * @return JobDetailFactoryBean
     */
    @Bean("clearScheduledLogJobDetail")
    public JobDetailFactoryBean clearScheduledLogJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setDescription("清理计划任务日志");
        factoryBean.setName("clearScheduledLogJobDetail");
        factoryBean.setGroup(DEF_GROUP_NAME);
        factoryBean.setJobClass(ClearScheduledLog.class);
        factoryBean.setDurability(true);
        return factoryBean;
    }

    /**
     * 描述 : clearScheduledTriggerLogTrigger
     *
     * @param jobDetail jobDetail
     * @return CronTriggerFactoryBean
     */
    @Bean(name = "clearScheduledTriggerLogTrigger")
    public CronTriggerFactoryBean clearScheduledTriggerLogTrigger(
            @Qualifier("clearScheduledTriggerLogJobDetail") JobDetail jobDetail) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setDescription("清理计划任务执行日志");
        factoryBean.setName("clearScheduledTriggerLogTrigger");
        factoryBean.setGroup(DEF_GROUP_NAME);
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setCronExpression("0 0 2 * * ? ");
        return factoryBean;
    }

    /**
     * 描述 : clearScheduledTriggerLogJobDetail
     *
     * @return JobDetailFactoryBean
     */
    @Bean("clearScheduledTriggerLogJobDetail")
    public JobDetailFactoryBean clearScheduledTriggerLogJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setDescription("清理计划任务执行日志");
        factoryBean.setName("clearScheduledTriggerLogJobDetail");
        factoryBean.setGroup(DEF_GROUP_NAME);
        factoryBean.setJobClass(ClearScheduledTriggerLog.class);
        factoryBean.setDurability(true);
        return factoryBean;
    }

    /**
     * rmsJobEvent
     *
     * @return IRmsJobEvent
     */
    @Bean
    @ConditionalOnMissingBean(value = IRmsJobEvent.class, search = SearchStrategy.CURRENT)
    public IRmsJobEvent rmsJobEvent() {
        return new DefRmsJobEvent();
    }

    /**
     * listenerEvent
     *
     * @return IListenerEvent
     */
    @Bean
    @ConditionalOnMissingBean(value = IListenerEvent.class, search = SearchStrategy.CURRENT)
    public IListenerEvent listenerEvent() {
        return new DefListenerEvent();
    }

}
