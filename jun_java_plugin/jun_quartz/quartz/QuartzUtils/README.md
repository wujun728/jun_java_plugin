##依赖
依赖jar quartz-2.2.3.jar
下载地址：http://www.quartz-scheduler.org/
已升级支持 spring 4.x

##过期策略
* withMisfireHandlingInstructionDoNothing

不触发立即执行

等待下次Cron触发频率到达时刻开始按照Cron频率依次执行



* withMisfireHandlingInstructionIgnoreMisfires

以错过的第一个频率时间立刻开始执行

重做错过的所有频率周期后

当下一次触发频率发生时间大于当前时间后，再按照正常的Cron频率依次执行



* withMisfireHandlingInstructionFireAndProceed

以当前时间为触发频率立刻触发一次执行

然后按照Cron频率依次执行


这个工具，与 tomcat 的定时认为实现不同，tomcat 中的定时器，同过实现 ServletContextListener 获取上下文，随tomcat 服务一同启动。