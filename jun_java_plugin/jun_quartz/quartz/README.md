# quartz

#### 项目简介
1. 基于quartz的二次集成
2. 支持集群
3. 支持其它web项目进行功能开发
4. 动态控制定时任务启动、暂停、重启、删除、添加、修改
5. 支持多数据库
6. 支持自实现Scheduler、Job、Trigger监听，系统自动注册
7. 数据源使用阿里Druid

#### 使用教程

1. 引入依赖
2. 修改jdbc.properties数据源配置
3. 继承AbstractQuartzTask，实现自己的定时任务
4. 功能开发
5. 任务展示
6. 调用接口控制任务

#### 配置示例
```xml
<bean name="threadPoolTaskExecutor" class="org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor">
    <property name="corePoolSize" value="15"/>
    <property name="maxPoolSize" value="25"/>
    <property name="queueCapacity" value="100"/>
</bean>

<!-- xml方式配置定时任务信息 -->
<!--
<bean id="demoJobDetail" class="org.springframework.scheduling.quartz.JobDetailFactoryBean">
    <property name="jobClass" value="com.xbd.quartz.DefaultQuartzJobBeanbBean"/>
    <property name="durability" value="true"/>
    <property name="description" value="测试job1"/>
</bean>

<bean id="quartzTaskDemo" class="com.xbd.quartz.task.QuartzTaskDemo"></bean>

<bean id="demoJobTrigger" class="org.springframework.scheduling.quartz.CronTriggerFactoryBean">
    <property name="jobDetail" ref="demoJobDetail"/>
    <property name="cronExpression" value="0 * * * * ? *"/>
    <property name="jobDataMap">
        <map>
            <entry key="targetObject" value="quartzTaskDemo"/>
            <entry key="targetMethod" value="execute"/>
        </map>
    </property>
</bean>
-->

<!-- 在JobDetailBean中可以使用自动注入注解的关键性配置 -->
<bean id="autowiredSpringBeanJobFactory" class="com.xbd.quartz.AutowiredSpringBeanJobFactory"></bean>

<!-- 全局作业触发器监听 -->
<bean id="defaultGlobalTriggerListener" class="com.xbd.quartz.listener.trigger.DefaultGlobalTriggerListener"></bean>

<!-- 以下是Quartz定时调度配制 -->
<bean id="scheduler" class="org.springframework.scheduling.quartz.SchedulerFactoryBean">
    <property name="configLocation" value="classpath:quartz.properties"/>
    <property name="dataSource" ref="dataSource"/>
    <property name="schedulerName" value="xbd_scheduler"></property>
    <property name="taskExecutor" ref="threadPoolTaskExecutor"></property>
    <!-- 分布式事务 -->
    <property name="transactionManager" ref="transactionManager"></property>
    <property name="applicationContextSchedulerContextKey" value="applicationContext"></property>
    <!-- 在JobDetailBean中可以使用自动注入注解的关键性配置 -->
    <!-- <property name="jobFactory" ref="autowiredSpringBeanJobFactory"></property> -->
    <!--可选，QuartzScheduler 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了 -->
    <property name="overwriteExistingJobs" value="true"/>
    <!-- 设置自动启动 -->
    <property name="autoStartup" value="true"/>
    <property name="startupDelay" value="5"/>
    <!-- xml方式配置定时任务触发信息 -->
    <!--
    <property name="triggers">
        <list>
            <ref bean="demoJobTrigger"/>
        </list>
    </property>
    -->
    <property name="globalTriggerListeners">
        <array>
            <ref bean="defaultGlobalTriggerListener"/>
        </array>
    </property>
</bean>

<bean id="quartzListenerAware" class="com.xbd.quartz.QuartzListenerAware">
    <property name="scheduler" ref="scheduler"></property>
</bean>
```

#### 使用说明

1. QuartzTaskHandler 任务处理接口，其中有添加、修改、删除、暂停、重启等功能
2. AbstractSchedulerListener Scheduler监听，可自行实现自己需要的Scheduler监听
3. AbstractJobListener Job监听，可自行实现自己需要的Job监听
4. AbstractTriggerListener Trigger监听，可自行实现自己需要的Trigger监听

#### 版权说明
quartz使用 [Apache License 2.0](https://gitee.com/xbd521/quartz/blob/master/LICENSE "Apache License 2.0") 协议



