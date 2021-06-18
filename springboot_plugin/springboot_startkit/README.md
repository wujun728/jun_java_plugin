#SpringBoot-StartKit(Sky)
#0.2

* 项目名称定为Sky
* 项目定位,快速开发框架
* 单表,主从表,主外键表,树表,左树右表,ButtonEditor(真是熟悉的名词啊)


#0.1

* SpringBoot初始环境搭建
* 集成Swagger2,用于做Rest接口测试及管理,使用http://127.0.0.1:8080/swagger-ui.html访问即可
* 默认使用Log4J作为日志配置
* 集成Spring Boot打包插件,mvn clean install package 即可打出可执行的Jar包
* JDK默认使用1.8
* 新增IOC演示示例
* 多环境配置示例,启动的时候激活需要使用的配置文件即可:参数为--spring.profiles.active=dev
* 配置加载演示例子GlobalConfig
* 日志配置文件调整为存放于application-dev.properties
* 加入静态资源访问配置,后续会使用WebPack+React的做法来做页面
* 整合Druid,http://localhost:8080/druid
* 整合MyBatis,MyBatis自动生成插件
* 整合React
* 引入Redux。。。这货真是绕
* 受不了React了!!!!换成Vue了,还是挺爽的
