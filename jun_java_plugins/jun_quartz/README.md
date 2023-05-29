#quartz作业demo

项目介绍：Spring+Quartz集成实现的定时任务调度器，支持集群环境下的定时任务调用！

项目开发环境：jdk1.7+，tomcat7.0+，sql server 2000+，mysql 5.6

项目支持MySQL,SqlServer数据库，Oracle我这里没试，有兴趣的朋友可以自己做下，其实就是sql脚本不一样，具体的sql脚本可以下载quartz官方文档查看，里面有
支持各种数据库的sql脚本

MySQL版本部署步骤：

1.首先根据jdbc.properties文件里面的配置新建对应的数据库

2.执行db文件夹下的sql文件，先执行mysql_db.sql文件，再执行init_db.sql文件

3.部署到tomcat即可查看定时任务的调度情况

SqlServer版本部署步骤：

1.首先根据jdbc.properties文件里面的配置新建对应的数据库

2.执行db文件夹下的sql文件，先执行sqlserver_db.sql文件，再执行init_db.sql文件

3.部署到tomcat即可查看定时任务的调度情况





