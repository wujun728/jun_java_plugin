### `jun_plugin` 项目  

### 项目说明
jun_plugin 整合Java企业级各种开发组件、开箱即用、不写重复代码，包含基础Java基础开发组件，Spring企业家开发组，SpringBoot开发组件、SpringCloud开发组件

### 功能清单

1. Java常用开发组件：**java_plugin**，Java常用开发组件，当前**包含47类各种**lib类库示例与文档。
2. Spring常用开发组件：**spring_plugin**，企业级开发常用组件，当前**集成61中类种**lib类库示例与文档。
3. SpringBoot常用开发组件：**springboot_plugin**，当前**集成106中**类种类库示例与文档。
4. Maven常用项目模板：**maven_template**，当前集成了**10种模板**，含单体、SSH、SSM、Boot、Cloud等。
5. SpringCloud常用开发组件：**springcloud_plugin**，含netflix、alibaba、dubbo等，主要为示例与文档。


#### 基础篇：企业级开发组件(开发组件、代码生成、前端组件) [jun_java_plugin]

> Java基础系开发组件-通用篇（jun_plugin） 常用开发组件，基础公共lib包的组件不依赖Spring的组件，主要供原生开发的项目集成：

【jun_activiti】[流程引擎，Activiti工作流，完成工作流常用操作](https://github.com/wujun728/jun_plugin/jun_activiti)  
【jun_ajax】[完成ajax操作，前端及后端的ajax](https://github.com/wujun728/jun_plugin)  
【jun_aliyun_sms】[短信工具,集成阿里云短信、腾讯云短信发送及验证码等功能](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/jun_aliyun_sms)  
【jun_apache_commons】[ Common工具集,集成Apache通用工具集](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/jun_apache_commons)  
【jun_api】[ API中心,API注册测试校验管控鉴权中心](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/jun_api)  
【jun_cron】 [cron表达式的java的实现及调度](https://github.com/wujun728/jun_plugin)  
【jun_cache】[分布式缓存，缓存工具,集成Redis分布式缓存功能](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/jun_cache)  
【jun_camel】[消息路由，ESB服务总线,EIP框架，处理不同系统之间的消息传输](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/jun_camel)  
【jun_captcha】[ 验证码工具,GoogleKaptcha及各种验证码工具](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/jun_captcha)  
【jun_crawler】[ 爬虫引擎,网络爬虫引擎，Xpath解析HTML](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/jun_crawler)  
【jun_datasource_cluster】[原生集成各种JDBC DataSource数据源,分布式数据源,Druid、DBCP等数据源](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/jun_datasource_cluster)  
【jun_dbutil】[原生集成Apache 的Dbutils完成单表及多表的增删改查，原生JDBC操作，简单封装](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/jun_dbutil)  

and so on 

 

> Spring系企业级开发框架组件 Spring常用开发组件，万能粘合剂，企业级J2EE实际标准平台

【spring_activemq】[ Activemq消息队列,Spring集成activemq消息队列工具集demo](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/spring_activemq)  
【spring_atomikos】[ 分布式事务atomikos,分布式事务atomikos](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/spring_atomikos)  
【spring_camel由】[ 消息路由camel,消息路由camel](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/spring_camel)  
【spring_cas】[ 单点登录cas,单点登录cas](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/spring_cas)  
【spring_cors】[ Spring跨域调用,Spring跨域调用](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/spring_cors)  
【spring_demo】[ Spring测试DEMO,Spring测试DEMO](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/spring_demo)  
【spring_distributed_config】[ Spring分布式配置,Spring分布式配置](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/spring_distributed_config)  
【spring_distributed_fastdfs】[ Spring分布式文件,Spring分布式文件](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/spring_distributed_fastdfs)  
【spring_distributed_lock】[ Spring分布式锁,Spring分布式锁](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/spring_distributed_lock)  
【spring_distributed_multidatasource】[ Spring分布式数据源,Spring分布式数据源](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/spring_distributed_multidatasource)  
【spring_distributed_netty】[ Spring分布式Netty服务,Spring分布式Netty服务](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/spring_distributed_netty)  
【spring_distributed_oss_qiniu】[ Spring分布式对象存储,Spring分布式对象存储](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/spring_distributed_oss_qiniu)  
【spring_distributed_rpc】[ Spring分布式RPC框架,Spring分布式RPC框架](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/spring_distributed_rpc)  
【spring_distributed_session】[ 分布式Session实现及配置持久化等,Spring分布式Session](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/spring_distributed_session)  
【spring_distributed_transaction_tcc[ Spring分布式事务TCC,Spring分布式事务TCC](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/spring_distributed_transaction_tcc)  
【spring_drools】[ Spring集成规则引擎,Spring集成规则引擎](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/spring_drools)  
【spring_dubbo】[ Spring集成Dubbo框架,Spring集成Dubbo框架](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/spring_dubbo)  

and so on

> Maven常用项目模板，含maven单体分布式、SSH、SSM、Boot、Cloud等

【maven_javaproject】[Java单体项目模板,Java单体项目模板](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/maven_javaproject\)  
【maven_spring4_multi_modules】[Spring4多模块项目模板,Spring4多模块项目模板](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/maven_spring4_multi_modules\)  
【maven_spring5_multi_modules】[Spring5多模块项目模板,Spring5多模块项目模板](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/maven_spring5_multi_modules\)  
【maven_spring5template】[Spring5微服务项目模板,Spring5微服务项目模板](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/maven_spring5template\)  
【maven_springboot】[SpringBoot微服务项目模板,SpringBoot微服务项目模板](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/maven_springboot_permission_example\)  


> SpringBoot系开发框架组件，基于SpringBoot微服务开发组件，新企业级REST服务

【springboot_actuator】[SpringBoot微服务项目模板,SpringBoot微服务项目模板](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/maven_springboot_permission_example\)  
【springboot_admin】[SpringBoot微服务项目模板,SpringBoot微服务项目模板](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/maven_springboot_permission_example\)  
【springboot_async】[SpringBoot微服务项目模板,SpringBoot微服务项目模板](https://github.com/wujun728/jun_java_plugin/tree/master/spring_plugin/maven_springboot_permission_example\)  


#### 工程截图(组件较多，随意择选了几个)

<table>
    <tr>
		<td><img src=""/>
		<td><img src=""/>
    </tr>
</table>



#### 开发环境
- **JDK 1.8 
- **Maven 3.5 
- **IDEA 2018.2 + or  STS 4.5 +** (*注意：安装lombok插件）

#### 笔者其他项目   
 [俊哥个人技术栈代码库(持续更新)](https://github.com/wujun728)  
TODO PLAN：  
Step1基础篇：  
	jun_java_plugin Java基础框架-Java开发组件、Spring开发组件、SpringBoot开发组件、SpringCloud开发组件、Maven项目模板  
	jun_ssh_parent SSH基础框架-SpringBoot+EasyUI+JSP  
	jun_ssm_springboot SSM基础框架-SpringBoot+MybatisPlus+Boostrap+Shiro+JWT  
	jun_code_generator 代码生成器-SpringBoot+Freemarker+API接口+EasyExcel  
	jun_frontend_ui    前端框架，Bootstrap、AdminLTE、Jquery、EasyUI、LayUI、LayAdmin、Vue、vue-element-admin  
	jun_linux    服务器部署、gitlab部署、Nginx部署、Redis部署、Docker部署、MySQL部署、等等  
 	---审视项目本身，给出待办调整清单，给出下步计划  
Step2微服务&大数据：  
	jun_api_service API接口服务框架，SpringBoot+Rest API  
	jun_springboot_vue 前后端分离框架，SpringBoot+Vue+JWT  
	jun_springcloud 微服务框架，SpringCloud Netflix、SpringCloud Alibaba、Dubbo框架、Quarkus极速框架  
	jun_bigdata 大数据框架，支持数据清理、数据推荐、大数据分析、大数据企业看板、大数据报表等  

Step3产品篇：  
	jun_product_center 产品中心，包含企业官网、企业办公自动化OA系统、企业资源管理ERP系统、项目管理系统、等等  
	jun_website   CMS网站系统，基于WordPress的网站系统、支持博客、企业官网、及各种网站模板  
	wujun728.github.io 个人博客  

Step4产品篇：  
	jun_app    移动APP开发平台、支持Uniapp开发独立APP、小程序、企业办公等  
	jun_weixin   微信开发平台、微信公众号、微信小程序、微信管理后台、微信API接口后台  
	jun_android Android移动开发框架，APP开发模板、后台管理系统、后台API接口平台  


​	
