### `jun_java_plugin` 项目  

### 项目说明
jun_java_plugin 整合Java企业级各种开发组件、开箱即用、不写重复代码，包含基础Java基础开发组件，Spring企业级开发组，SpringBoot常用开发组件、SpringCloud开发组件

### 功能清单

1. 集成Java常用开发组件：**java_plugin**，Java常用开发组件，当前**包含60+类各种**lib类库示例与文档。
2. 集成Spring常用开发组件：**spring_plugin**，企业级开发常用组件，当前**集成100+种**lib类库示例与文档。
3. 集成SpringBoot常用开发组件：**springboot_plugin**，当前**集成100+**种类库示例与文档。
4. 集成Maven常用项目模板：**maven_template**，当前集成了**10种模板**，含单体及多模块、SSH、SSM、Boot、Cloud等。
5. 集成SpringCloud常用开发组件：**springcloud_plugin**，含个版本netflix、alibaba、dubbo等，主要为示例与文档。


#### 基础篇：企业级开发组件(开发组件、代码生成、前端组件) [jun_java_plugin]

> Java基础系开发组件-通用篇（jun_plugin） 常用开发组件，基础公共lib包的组件不依赖Spring的组件，主要供原生开发的项目集成：




#### 工程截图(组件较多，随意截了几个，所有组件正常编译运行)

<table>
    <tr>
		<td><img src="doc/java1.png"/> 
     </tr>
    <tr>
		<td><img src="doc/spring1.png"/>
    </tr>
    <tr>
	<td><img src="doc/springboot1.png"/>
</tr>
<tr>
	<td><img src="doc/maven1.png"/>
   </tr>
</table>



#### 模块清单(组件较多，随意写了几个，具体见代码明细)

【jun_activiti】[流程引擎，Activiti工作流，完成工作流常用操作]<br/>
【jun_ajax】[完成ajax操作，前端及后端的ajax]<br/>
【jun_aliyun_sms】[短信工具,集成阿里云短信、腾讯云短信发送及验证码等功能]<br/>
【jun_apache_commons】[ Common工具集,集成Apache通用工具集] <br/>
【jun_api】[ API中心,API注册测试校验管控鉴权中心] <br/>
【jun_cron】 [cron表达式的java的实现及调度] <br/>
【jun_cache】[分布式缓存，缓存工具,集成Redis分布式缓存功能] <br/>
【jun_camel】[消息路由，ESB服务总线,EIP框架，处理不同系统之间的消息传输] <br/>
【jun_captcha】[ 验证码工具,GoogleKaptcha及各种验证码工具] <br/>
【jun_crawler】[ 爬虫引擎,网络爬虫引擎，Xpath解析HTML] <br/>
【jun_datasource_cluster】[原生集成各种JDBC DataSource数据源,分布式数据源,Druid、DBCP等数据源] <br/>
【jun_dbutil】[原生集成Apache 的Dbutils完成单表及多表的增删改查，原生JDBC操作，简单封装] <br/>
【jun_demo】[ DEMO测试,DEMO测试工具集] <br/>
【jun_designpattern】[23种涉及模式的Java实现] <br/>
【jun_drools】[ 规则引擎,规则引擎的各种demo及工具] <br/>
【jun_dubbo】[ Dubbo,阿里巴巴Dubbo RPC demo及工具集] <br/>
【jun_executor】[原生的并发多线程demo操作] <br/>
【jun_email】[原生邮件发送、纯文本、HTML邮件、带附件的邮件] <br/>
【jun_excel】[ Excel工具集,Excel导入导出工具集] <br/>
【jun_fileupload】[原生的文件上传及下载操作的实现基于common fileupload，OSS上传下载] <br/>
【jun_freemarker】[原生集成freemarker模板引擎，数据+模板=输出，可输出代码生成器] <br/>  
【jun_guava】[ GoogleGuava,谷歌工具集，集合工具，缓存工具] <br/>
【jun_guice】[ GoogleGuice,谷歌依赖注入框架(配合Servlet3.0+Dbutil)] <br/>
【jun_gzip】[ Gzip压缩,Gzip压缩及解压缩] <br/>
【jun_hibernate】[原生集成hibernate及使用,Hibernate,JPA框架，持久化框架] <br/>
【jun_httpclient】[原生集成httpclient，发送http请求、下载文件等，配合Postman发起各种HTTP请求] <br/>
【jun_image】[原生图片操作包、包括图片上传、下载、展示、转码、压缩转Base64、缩略图等] <br/>
【jun_jdk】[JDK原生demo代码，了解的越多才会了解的越深] <br/>
【jun_jdbc】[原生JDBC操作，简单封装，需要的可以看下] <br/>
【jun_j2cache】[ 缓存j2cache,缓存工具，OSCHACHE使用的缓存工具] <br/>
【jun_jar2maven】[ Jar转Maven,Jar转Maven工具集，老web项目转maven项目] <br/>
【jun_jbpm】[ 流程引擎JBPM,流程引擎JBPM，老牌流程引擎] <br/>
【jun_jdk】[ JDK核心,JDK核心工具，常用Demo等] <br/>
【jun_jsoup】[HTM标记语言解析包，完成HTML解析、主要爬虫使用，解析HTML渲染数据] <br/>
【jun_lucene】[老牌搜索引擎、可以看下,搜索框架,搜索框架，全文检索工具] <br/>
【jun_leetcode】[算法刷题大全] <br/>
【jun_mybatisplus】[ MybatisPlus,MybatisPlus数据持久化映射框架] <br/>
【jun_pay】[ 支付工具,支付工具，集成微信及阿里支付] <br/>
【jun_pdf】[ PDF工具,PDF工具，生成PDF及PDF文件] <br/>
【jun_poi】[原生POI完成Excel文件的导入、解析、导出及持久化等] <br/>
【jun_qrcode】[二维码生成器,二维码生成工具，WEB二维码服务] <br/>
【jun_quartz】[job任务调度,任务调度框架，分布式任务调度] <br/>
【jun_redis】[ Redis缓存,Redis缓存，分布式缓存，分布式锁] <br/>
【jun_rpc】[原生RPC调用、客户端及服务端] <br/>
【jun_restlet】[REST工具，轻量级REST框架,不依赖Servlet的REST的工具] <br/>
【jun_serializable】[ 序列化,序列化与反序列化工具集] <br/>
【jun_shiro】[ 认证与鉴权,认证与鉴权，URL过滤，用户任务，角色功能鉴权] <br/>
【jun_sso】[原生SSO的实现单点登录,SSO单点登录工具，提供单点登录服务] <br/>
【jun_servlet】[原生Servlet、WEB开发的基础] <br/>
【jun_templatespider】[ 模板爬虫,模板爬虫工具，直接下载url到html文件] <br/>
【jun_test】[JUNIT框架及TestNG框架] <br/>  
【jun_util】[开发工具util，常用开发工具集、非常重要！！！] <br/>
【jun_webmagic】[ 爬虫引擎Webmagic,爬虫引擎Webmagic，爬取整个网站] <br/>
【jun_webservice】[原生的webservice调用、基于apache cxf实现服务调用及发布,CXF，Axis等] <br/>
【jun_webservlet】[原生Servlet 3.0的实现,Servlet3.0新特性demo，原生注解Servlet] <br/>
【jun_websocket】[原生的WebSocket的实现长链接,Websocket长链接demo，消息推送及异步工具] <br/>
【jun_xml】[原生的XML解析及生产XML、提供SAX、DOM、DOM4J解析生成XML] <br/>


> **Maven常用项目模板，含maven单体分布式、SSH、SSM、SpringBoot、SpringCloud等**

【maven_javaproject】[Java单体项目模板,Java单体项目模板] <br/>
【maven_spring4_multi_modules】[Spring4多模块项目模板,Spring4多模块项目模板] <br/>
【maven_spring5_multi_modules】[Spring5多模块项目模板,Spring5多模块项目模板] <br/>
【maven_spring5template】[Spring5微服务项目模板,Spring5微服务项目模板] <br/>
【maven_springboot】[SpringBoot微服务项目模板,SpringBoot微服务项目模板] <br/>
【maven_project_template】[Java单体项目模板,Java单体项目模板] <br/>
【maven_springboot_jpa_jsp】[Java单体项目模板,Java单体项目模板] <br/>
【maven_springboot_mybatis】[Java单体项目模板,Java单体项目模板] <br/>
【maven_springboot_permission_example】[Java单体项目模板,Java单体项目模板] <br/>
【maven_springboot_template】[Java单体项目模板,Java单体项目模板] <br/>



> **Spring系企业级开发框架组件 Spring常用开发组件100+，万能粘合剂**

*模块太多，描述写太麻烦了，直接贴编译清单吧，具体如下：*<br/>
` [INFO] --------------------< com.jun.plugin:spring_plugin >--------------------` <br/>
` [INFO] Building spring_plugin 1.0                                     [111/111]` <br/>
` [INFO] --------------------------------[ pom ]---------------------------------` <br/>
` [INFO]` <br/> <br/>
` [INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ spring_plugin ---` <br/><br/>
` [INFO] ------------------------------------------------------------------------` <br/>
` [INFO] Reactor Summary:` <br/>
` [INFO]` <br/>
` [INFO] spring_activemq 1.0 ................................ SUCCESS [  1.337 s]` <br/>
` [INFO] spring_atomikos 1.0 ................................ SUCCESS [  1.070 s]` <br/>
` [INFO] spring_autowired 1.0 ............................... SUCCESS [  0.649 s]` <br/>
` [INFO] spring_camel 1.0 ................................... SUCCESS [  0.444 s]` <br/>
` [INFO] spring_captcha 1.1.9 ............................... SUCCESS [  0.601 s]` <br/>
` [INFO] spring_data_jpa 1.0 ................................ SUCCESS [  0.526 s]` <br/>
` [INFO] spring_demo 1.0 .................................... SUCCESS [  0.341 s]` <br/>
` [INFO] spring_distributed_datasource 1.0 .................. SUCCESS [  0.291 s]` <br/>
` [INFO] spring_distributed_fastdfs 1.0 ..................... SUCCESS [  0.030 s]` <br/>
` [INFO] fastdfs_client 1.0 ................................. SUCCESS [  0.391 s]` <br/>
` [INFO] fastdfs_app 1.0 .................................... SUCCESS [  0.646 s]` <br/>
` [INFO] fastdfs_core 1.0 ................................... SUCCESS [  1.811 s]` <br/>
` [INFO] spring_distributed_lock 1.0 ........................ SUCCESS [  0.224 s]` <br/>
` [INFO] spring_distributed_multidatasource Maven Webapp 1.0  SUCCESS [  0.594 s]` <br/>
` [INFO] spring_distributed_netty 1.0 ....................... SUCCESS [  0.008 s]` <br/>
` [INFO] spring_netty_common 1.0 ............................ SUCCESS [  0.086 s]` <br/>
` [INFO] spring_netty_server 1.0 ............................ SUCCESS [  0.223 s]` <br/>
` [INFO] spring_distributed_oss_qiniu Maven Webapp 1.0 ...... SUCCESS [  0.544 s]` <br/>
` [INFO] spring_distributed_rpc 1.0 ......................... SUCCESS [  0.004 s]` <br/>
` [INFO] fastrpc_core 1.0 ................................... SUCCESS [  0.178 s]` <br/>
` [INFO] fastrpc_server 1.0 ................................. SUCCESS [  0.195 s]` <br/>
` [INFO] fastrpc_client 1.0 ................................. SUCCESS [  0.174 s]` <br/>
` [INFO] spring_distributed_session 1.0.0 ................... SUCCESS [  0.238 s]` <br/>
` [INFO] spring_distributed_transaction_tcc 1.2.12 .......... SUCCESS [  0.037 s]` <br/>
` [INFO] tcc-transaction-api 1.2.12 ......................... SUCCESS [  0.642 s]` <br/>
` [INFO] tcc-transaction-core 1.2.12 ........................ SUCCESS [  1.058 s]` <br/>
` [INFO] tcc-transaction-spring 1.2.12 ...................... SUCCESS [  0.741 s]` <br/>
` [INFO] tcc-transaction-unit-test 1.2.12 ................... SUCCESS [  0.858 s]` <br/>
` [INFO] tcc-transaction-server 1.2.12 ...................... SUCCESS [  0.826 s]` <br/>
` [INFO] tcc-transaction-dubbo 1.2.12 ....................... SUCCESS [  0.831 s]` <br/>
` [INFO] tcc-transaction-bom 1.2.12 ......................... SUCCESS [  0.003 s]` <br/>
` [INFO] spring_drools 1.0 .................................. SUCCESS [  0.573 s]` <br/>
` [INFO] spring_dynamic_datasource 1.0 ...................... SUCCESS [  0.259 s]` <br/>
` [INFO] spring_dynamic_job 1.0 ............................. SUCCESS [  0.644 s]` <br/>
` [INFO] easy-excel-util 1.0 ................................ SUCCESS [  0.952 s]` <br/>
` [INFO] spring_easyexcel 1.0 ............................... SUCCESS [  0.004 s]` <br/>
` [INFO] my-base-common 1.0-SNAPSHOT ........................ SUCCESS [  1.416 s]` <br/>
` [INFO] my-web 1.0-SNAPSHOT ................................ SUCCESS [  2.728 s]` <br/>
` [INFO] spring_elasticsearch Maven Webapp 1.0 .............. SUCCESS [  0.637 s]` <br/>
` [INFO] spring_email 1.0 ................................... SUCCESS [  0.107 s]` <br/>
` [INFO] spring_fastdfs 1.0 ................................. SUCCESS [  0.214 s]` <br/>
` [INFO] spring_fastdfs_test 1.0 ............................ SUCCESS [  0.501 s]` <br/>
` [INFO] spring_fileupload 1.0 .............................. SUCCESS [  1.736 s]` <br/>
` [INFO] spring_hibernate 1.0.0-SNAPSHOT .................... SUCCESS [  0.666 s]` <br/>
` [INFO] spring_hibernate_plus 1.0 .......................... SUCCESS [  1.278 s]` <br/>
` [INFO] spring_hibernate_validator 1.0 ..................... SUCCESS [  0.010 s]` <br/>
` [INFO] spring_images 1.0 .................................. SUCCESS [  0.720 s]` <br/>
` [INFO] spring_ioc_aop 1.0 ................................. SUCCESS [  0.414 s]` <br/>
` [INFO] spring_jedis_cluster 1.0 ........................... SUCCESS [  0.310 s]` <br/>
` [INFO] spring_jedis_simple 1.0 ............................ SUCCESS [  0.217 s]` <br/>
` [INFO] spring_jpa 1.0 ..................................... SUCCESS [  2.663 s]` <br/>
` [INFO] spring_jqgrid 1.0 .................................. SUCCESS [  0.244 s]` <br/>
` [INFO] spring_json_view 1.0 ............................... SUCCESS [  0.238 s]` <br/>
` [INFO] spring_jsonp 1.0 ................................... SUCCESS [  0.192 s]` <br/>
` [INFO] spring_jsoup 1.0 ................................... SUCCESS [  0.165 s]` <br/>
` [INFO] spring_jwt_ssm Maven Webapp 1.0 .................... SUCCESS [  0.673 s]` <br/>
` [INFO] spring_lucene 1.0 .................................. SUCCESS [  0.551 s]` <br/>
` [INFO] spring_mina 1.0 .................................... SUCCESS [  0.470 s]` <br/>
` [INFO] spring_mongodb 1.0 ................................. SUCCESS [  0.688 s]` <br/>
` [INFO] spring_mybatis 1.0 ................................. SUCCESS [  0.717 s]` <br/>
` [INFO] spring_mybatis_generator 1.0 ....................... SUCCESS [  1.976 s]` <br/>
` [INFO] spring_mybatis_generator_gui 0.7.2 ................. SUCCESS [  0.557 s]` <br/>
` [INFO] spring_mybatis_mulidatasource 1.0 .................. SUCCESS [  0.351 s]` <br/>
` [INFO] spring_mybatis_mulidatasource_atomikos 1.0 ......... SUCCESS [  0.270 s]` <br/>
` [INFO] spring_mybatis_plus_generator 1.0 .................. SUCCESS [  0.480 s]` <br/>
` [INFO] spring_mybatis3_simple 1.0 ......................... SUCCESS [  0.272 s]` <br/>
` [INFO] spring_mybatisplus 1.0 ............................. SUCCESS [  0.428 s]` <br/>
` [INFO] spring_netty 1.0 ................................... SUCCESS [  0.662 s]` <br/>
` [INFO] spring_oauth_client 1.1 ............................ SUCCESS [  0.806 s]` <br/>
` [INFO] spring_oauth_server 1.0 ............................ SUCCESS [  0.751 s]` <br/>
` [INFO] spring_oauth_server2 1.01 .......................... SUCCESS [  0.663 s]` <br/>
` [INFO] spring_oauth 1.0 ................................... SUCCESS [  0.004 s]` <br/>
` [INFO] spring_oschina 1.0 ................................. SUCCESS [  0.174 s]` <br/>
` [INFO] spring_plupload 1.0 ................................ SUCCESS [  0.188 s]` <br/>
` [INFO] spring_projects 1.0 ................................ SUCCESS [  0.003 s]` <br/>
` [INFO] spring-mvc-session-with-redis Maven Webapp 1.0 ..... SUCCESS [  0.331 s]` <br/>
` [INFO] spring-mvc-with-redis Maven Webapp 1.0 ............. SUCCESS [  0.306 s]` <br/>
` [INFO] web-native-jar Maven Webapp 1.0 .................... SUCCESS [  0.212 s]` <br/>
` [INFO] spring-with-ehcache Maven Webapp 1.0 ............... SUCCESS [  0.304 s]` <br/>
` [INFO] spring-mvc-with-redis-rank Maven Webapp 1.0 ........ SUCCESS [  0.298 s]` <br/>
` [INFO] spring_quartz 1.0 .................................. SUCCESS [  0.478 s]` <br/>
` [INFO] spring_quartz_task 1.0 ............................. SUCCESS [  1.270 s]` <br/>
` [INFO] spring_rabbitmq 1.0 ................................ SUCCESS [  0.360 s]` <br/>
` [INFO] spring_redis 1.0 ................................... SUCCESS [  0.556 s]` <br/>
` [INFO] redis-demo-publisher 1.0 ........................... SUCCESS [  0.533 s]` <br/>
` [INFO] redis-demo-subscriber1 1.0 ......................... SUCCESS [  0.310 s]` <br/>
` [INFO] redis-demo-subscriber2 1.0 ......................... SUCCESS [  0.303 s]` <br/>
` [INFO] spring_redis_queue 1.0 ............................. SUCCESS [  0.003 s]` <br/>
` [INFO] spring_rest_client 1.0 ............................. SUCCESS [  0.237 s]` <br/>
` [INFO] spring_rest_server 1.0 ............................. SUCCESS [  0.326 s]` <br/>
` [INFO] spring_rest 1.0 .................................... SUCCESS [  0.004 s]` <br/>
` [INFO] spring-session-demo.javaconfig Maven Webapp 1.0 .... SUCCESS [  0.343 s]` <br/>
` [INFO] spring-session-demo.xml Maven Webapp 1.0 ........... SUCCESS [  0.223 s]` <br/>
` [INFO] spring_session 1.0 ................................. SUCCESS [  0.004 s]` <br/>
` [INFO] spring_shiro_redis Maven Webapp 1.0 ................ SUCCESS [  0.658 s]` <br/>
` [INFO] spring_solr 1.0 .................................... SUCCESS [  0.282 s]` <br/>
` [INFO] spring_springbatch 1.0 ............................. SUCCESS [  0.967 s]` <br/>
` [INFO] spring_springjdbc 1.0 .............................. SUCCESS [  0.250 s]` <br/>
` [INFO] spring_springmv_bootstrap Maven Webapp 1.0 ......... SUCCESS [  2.355 s]` <br/>
` [INFO] spring_springmvc 1.0 ............................... SUCCESS [  0.863 s]` <br/>
` [INFO] spring_springtest 1.0 .............................. SUCCESS [  0.453 s]` <br/>
` [INFO] spring_ssh 1.0 ..................................... SUCCESS [  0.351 s]` <br/>
` [INFO] spring_ssh2 1.0 .................................... SUCCESS [  0.389 s]` <br/>
` [INFO] spring_ssm_layui 1.0 ............................... SUCCESS [  1.909 s]` <br/>
` [INFO] spring_ssm2 1.0 .................................... SUCCESS [  0.422 s]` <br/>
` [INFO] spring_swagger Maven Webapp 1.0 .................... SUCCESS [  1.483 s]` <br/>
` [INFO] spring_task 1.0 .................................... SUCCESS [  0.236 s]` <br/>
` [INFO] spring_thymeleaf 1.0 ............................... SUCCESS [  0.154 s]` <br/>
` [INFO] spring_transaction 1.0 ............................. SUCCESS [  1.995 s]` <br/>
` [INFO] spring_websocket 1.0 ............................... SUCCESS [  0.796 s]` <br/>
` [INFO] spring_plugin 1.0 .................................. SUCCESS [  0.003 s]` <br/>
` [INFO] ------------------------------------------------------------------------` <br/>
` [INFO] BUILD SUCCESS` <br/>
` [INFO] ------------------------------------------------------------------------` <br/>
` [INFO] Total time:  01:04 min` <br/>
` [INFO] Finished at: 2023-06-01T11:25:59+08:00` <br/>
` [INFO] ------------------------------------------------------------------------` <br/>

` Process finished with exit code 0` <br/>




> **SpringBoot系开发框架组件，基于SpringBoot微服务开发组件100+，**

*模块太多，描述写太麻烦了，直接贴编译清单吧，具体如下（编译内存溢出可调整JVM参数）：*<br/>
`[INFO] ----------------< com.jun.plugin:springboot_zookeeper >-----------------` <br/>
` [INFO] Building springboot_zookeeper 1.0                              [107/107]` <br/>
` [INFO] ------------------------------------------------------------------------` <br/>
` [INFO] Reactor Summary:` <br/>
` [INFO]` <br/>
` [INFO] springboot_a_test 1.0 .............................. SUCCESS [  3.311 s]` <br/>
` [INFO] springboot_actuator 1.0 ............................ SUCCESS [  0.624 s]` <br/>
` [INFO] springboot_aop 1.0 ................................. SUCCESS [  0.638 s]` <br/>
` [INFO] springboot_async 1.0 ............................... SUCCESS [  1.988 s]` <br/>
` [INFO] springboot_batch 1.0 ............................... SUCCESS [  1.620 s]` <br/>
` [INFO] springboot_cache 1.0 ............................... SUCCESS [  0.974 s]` <br/>
` [INFO] springboot_canal 1.0 ............................... SUCCESS [  3.105 s]` <br/>
` [INFO] jun_springboot_plugin 1.0 .......................... SUCCESS [  0.047 s]` <br/>
` [INFO] springboot_codegen 1.0.0-SNAPSHOT .................. SUCCESS [  3.042 s]` <br/>
` [INFO] springboot_content_negotiation 1.0 ................. SUCCESS [  0.355 s]` <br/>
` [INFO] springboot_cxf 1.0 ................................. SUCCESS [  0.601 s]` <br/>
` [INFO] springboot_data_jpa 1.0 ............................ SUCCESS [  1.028 s]` <br/>
` [INFO] springboot_distributed_seckill 1.0 ................. SUCCESS [  2.698 s]` <br/>
` [INFO] springboot_docker 1.0.0-SNAPSHOT ................... SUCCESS [  0.209 s]` <br/>
` [INFO] springboot_drools 1.0 .............................. SUCCESS [  1.185 s]` <br/>
` [INFO] springboot_dubbo_zookeeper 1.0 ..................... SUCCESS [  0.004 s]` <br/>
` [INFO] common-api 1.0 ..................................... SUCCESS [  0.289 s]` <br/>
` [INFO] server-provider 1.0 ................................ SUCCESS [  0.283 s]` <br/>
` [INFO] server-consumer 1.0 ................................ SUCCESS [  0.284 s]` <br/>
` [INFO] springboot_dynamic_datasource 1.0.0-SNAPSHOT ....... SUCCESS [  2.692 s]` <br/>
` [INFO] springboot_echarts 1.0 ............................. SUCCESS [  2.748 s]` <br/>
` [INFO] springboot_ehcache_cache 1.0 ....................... SUCCESS [  0.591 s]` <br/>
` [INFO] springboot_elastic_job 1.0 ......................... SUCCESS [  0.769 s]` <br/>
` [INFO] springboot_email 1.0 ............................... SUCCESS [  0.332 s]` <br/>
` [INFO] springboot_excel 1.0 ............................... SUCCESS [  3.125 s]` <br/>
` [INFO] springboot_fastdfs 1.0 ............................. SUCCESS [  0.418 s]` <br/>
` [INFO] springboot_file_upload 1.0 ......................... SUCCESS [  0.359 s]` <br/>
` [INFO] springboot_file_uploaderr 1.0 ...................... SUCCESS [  0.993 s]` <br/>
` [INFO] springboot_flowable 1.0 ............................ SUCCESS [  2.156 s]` <br/>
` [INFO] springboot_flyway 1.0 .............................. SUCCESS [  0.318 s]` <br/>
` [INFO] springboot_freemarker 1.0 .......................... SUCCESS [  0.318 s]` <br/>
` [INFO] springboot_graphql_mongodb 1.0 ..................... SUCCESS [  2.263 s]` <br/>
` [INFO] springboot_graylog 1.0 ............................. SUCCESS [  0.194 s]` <br/>
` [INFO] springboot_groovy 1.0 .............................. SUCCESS [  1.795 s]` <br/>
` [INFO] springboot_hibernate 1.0 ........................... SUCCESS [  0.381 s]` <br/>
` [INFO] springboot_hibernate_validator 1.0 ................. SUCCESS [  1.741 s]` <br/>
` [INFO] springboot_https 1.0 ............................... SUCCESS [  0.272 s]` <br/>
` [INFO] springboot_upload_h2db 1.0 ......................... SUCCESS [  1.197 s]` <br/>
` [INFO] springboot_jasypt 1.0 .............................. SUCCESS [  2.108 s]` <br/>
` [INFO] springboot_jackson2 1.0 ............................ SUCCESS [  0.265 s]` <br/>
` [INFO] springboot_jdbctemplate_multidatasource 1.0 ........ SUCCESS [  0.374 s]` <br/>
` [INFO] springboot_jpa_thymeleaf_curd 1.0 .................. SUCCESS [  0.529 s]` <br/>
` [INFO] springboot_jsp 1.0 ................................. SUCCESS [  2.043 s]` <br/>
` [INFO] springboot_junit 1.0 ............................... SUCCESS [  0.358 s]` <br/>
` [INFO] springboot_jwt 1.0 ................................. SUCCESS [  0.735 s]` <br/>
` [INFO] springboot_kafka 1.0 ............................... SUCCESS [  0.549 s]` <br/>
` [INFO] springboot_kisso 1.0 ............................... SUCCESS [  0.385 s]` <br/>
` [INFO] springboot_ldap 1.0 ................................ SUCCESS [  1.751 s]` <br/>
` [INFO] springboot_log4jmonitor 1.0 ........................ SUCCESS [  0.338 s]` <br/>
` [INFO] springboot_logback 1.0 ............................. SUCCESS [  1.237 s]` <br/>
` [INFO] springboot_mapper_pagehelper 1.0 ................... SUCCESS [  0.506 s]` <br/>
` [INFO] springboot_minio 1.0 ............................... SUCCESS [  3.098 s]` <br/>
` [INFO] springboot_mongodb 1.0 ............................. SUCCESS [  2.908 s]` <br/>
` [INFO] springboot_mq_rabbitmq 1.0 ......................... SUCCESS [  0.011 s]` <br/>
` [INFO] springboot_mq_rocketmq 1.0 ......................... SUCCESS [  0.007 s]` <br/>
` [INFO] springboot_multi_datasource_jpa 1.0 ................ SUCCESS [  2.141 s]` <br/>
` [INFO] springboot_multi_datasource_mybatis 1.0 ............ SUCCESS [  1.369 s]` <br/>
` [INFO] springboot_multi_threading 1.0 ..................... SUCCESS [  1.262 s]` <br/>
` [INFO] springboot_multisource 1.0 ......................... SUCCESS [  0.780 s]` <br/>
` [INFO] springboot_mybatis 1.0 ............................. SUCCESS [  0.301 s]` <br/>
` [INFO] springboot_mybatis_jsp 1.0 ......................... SUCCESS [  0.581 s]` <br/>
` [INFO] springboot_mybatisplus 1.0 ......................... SUCCESS [  0.789 s]` <br/>
` [INFO] springboot_netty_websocket 1.0 ..................... SUCCESS [  4.037 s]` <br/>
` [INFO] springboot_oauth2 1.0 .............................. SUCCESS [  0.017 s]` <br/>
` [INFO] authorization-server 1.0 ........................... SUCCESS [  2.646 s]` <br/>
` [INFO] resource-server 1.0 ................................ SUCCESS [  0.734 s]` <br/>
` [INFO] springboot_orm_jdbctemplate 1.0 .................... SUCCESS [  2.003 s]` <br/>
` [INFO] springboot_oss_aliyun 1.0 .......................... SUCCESS [  0.695 s]` <br/>
` [INFO] springboot_oss_qiniu 1.0 ........................... SUCCESS [  0.583 s]` <br/>
` [INFO] springboot_pay 1.0 ................................. SUCCESS [  2.203 s]` <br/>
` [INFO] springboot_quartz 1.0 .............................. SUCCESS [  1.343 s]` <br/>
` [INFO] springboot_ratelimit_guava 1.0 ..................... SUCCESS [  1.538 s]` <br/>
` [INFO] springboot_ratelimit_redis 1.0 ..................... SUCCESS [  2.114 s]` <br/>
` [INFO] springboot_rbac_security 1.0 ....................... SUCCESS [  4.486 s]` <br/>
` [INFO] springboot_rbac_shiro 1.0 .......................... SUCCESS [  1.549 s]` <br/>
` [INFO] springboot_redis 1.0 ............................... SUCCESS [  0.845 s]` <br/>
` [INFO] springboot_redis_cluster 1.0 ....................... SUCCESS [  0.519 s]` <br/>
` [INFO] springboot_redis_sentinel 1.0 ...................... SUCCESS [  0.386 s]` <br/>
` [INFO] springboot_redislock 1.0 ........................... SUCCESS [  0.550 s]` <br/>
` [INFO] springboot_resttemplate 1.0 ........................ SUCCESS [  0.960 s]` <br/>
` [INFO] springboot_schedule 1.0 ............................ SUCCESS [  0.203 s]` <br/>
` [INFO] simple-security-jwt 1.0.0 .......................... SUCCESS [  1.704 s]` <br/>
` [INFO] simple-security-jwt-demo 1.0.0 ..................... SUCCESS [  0.300 s]` <br/>
` [INFO] springboot_security2 1.0 ........................... SUCCESS [  0.005 s]` <br/>
` [INFO] springboot_session 1.0 ............................. SUCCESS [  0.424 s]` <br/>
` [INFO] springboot_shardingsphere-mybatisplus 1.0 .......... SUCCESS [  2.885 s]` <br/>
` [INFO] springboot_shiro 1.0 ............................... SUCCESS [  1.735 s]` <br/>
` [INFO] springboot_snaker 1.0 .............................. SUCCESS [  0.990 s]` <br/>
` [INFO] springboot_justauth 1.0 ............................ SUCCESS [  1.590 s]` <br/>
` [INFO] springboot_socketio 1.0 ............................ SUCCESS [  0.373 s]` <br/>
` [INFO] springboot_starter 1.0 ............................. SUCCESS [  0.270 s]` <br/>
` [INFO] springboot_swagger_beauty 1.0 ...................... SUCCESS [  1.668 s]` <br/>
` [INFO] springboot_task_xxl_job 1.0 ........................ SUCCESS [  2.488 s]` <br/>
` [INFO] springboot_template_freemarker 1.0 ................. SUCCESS [  1.326 s]` <br/>
` [INFO] springboot_template_thymeleaf 1.0 .................. SUCCESS [  1.287 s]` <br/>
` [INFO] springboot_testing 1.0 ............................. SUCCESS [  0.435 s]` <br/>
` [INFO] springboot_thumbnailator 0.0.1-SNAPSHOT ............ SUCCESS [  0.809 s]` <br/>
` [INFO] springboot_thymeleaf 1.0 ........................... SUCCESS [  0.447 s]` <br/>
` [INFO] springboot_transaction 1.0 ......................... SUCCESS [  0.922 s]` <br/>
` [INFO] springboot_upload 1.0 .............................. SUCCESS [  2.881 s]` <br/>
` [INFO] springboot_validation 1.0 .......................... SUCCESS [  1.393 s]` <br/>
` [INFO] springboot_war 1.0 ................................. SUCCESS [  0.204 s]` <br/>
` [INFO] springboot_webflux 1.0 ............................. SUCCESS [  0.879 s]` <br/>
` [INFO] springboot_webservice 0.0.1-SNAPSHOT ............... SUCCESS [  0.741 s]` <br/>
` [INFO] springboot_websocket 1.0 ........................... SUCCESS [  0.487 s]` <br/>
` [INFO] springboot_websocket_socketio 1.0 .................. SUCCESS [  2.226 s]` <br/>
` [INFO] springboot_zookeeper 1.0 ........................... SUCCESS [  2.068 s]` <br/>
` [INFO] ------------------------------------------------------------------------` <br/>
` [INFO] BUILD SUCCESS` <br/>
` [INFO] ------------------------------------------------------------------------` <br/>
` [INFO] Total time:  02:09 min` <br/>
` [INFO] Finished at: 2023-06-01T11:29:50+08:00` <br/>
` [INFO] ------------------------------------------------------------------------` <br/>

` Process finished with exit code 0` <br/>



#### 开发环境
- **JDK 1.8 
- **Maven 3.5 
- **IDEA 2018.2 + or  STS 4.5 +** (*注意：安装lombok插件）
- **TOKEN  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Abc
#### 笔者其他项目   
 [俊哥个人技术栈代码库(持续更新)](https://github.com/wujun728)  
TODO PLAN：  
【Step1基础篇】：  
	jun_java_plugin  Java基础框架-Java开发组件、Spring开发组件、SpringBoot开发组件、SpringCloud开发组件、Maven项目模板  
	jun_ssh_parent  SSH基础框架-SpringBoot+EasyUI+JSP  
	jun_ssm_springboot SSM基础框架-SpringBoot+MybatisPlus+Boostrap+Shiro+JWT  
	jun_code_generator 代码生成器-SpringBoot+Freemarker+API接口  
	jun_frontend_ui    前端框架，Bootstrap、LayUI、Vue、Vue-element-admin  
	jun_linux    服务器部署、gitlab部署、Nginx部署、Redis部署、Docker部署、MySQL部署、等等  
 	---TODO审视项目本身，给出待办调整清单，给出下步计划  
【Step2微服务&大数据】：  
	jun_api_service API接口服务框架，SpringBoot+Rest API  
	jun_springboot_vue 前后端分离框架，SpringBoot+Vue+JWT  
	jun_springcloud 微服务框架，SpringCloud Netflix、SpringCloud Alibaba、Dubbo框架  
	jun_bigdata 大数据框架，支持数据清理、数据推荐、大数据分析、大数据企业看板、大数据报表等  

【Step3产品篇】：  
	jun_product_center 产品中心，包含企业官网、企业办公自动化系统、企业资源管理ERP系统等  
	jun_website   CMS网站系统，基于WordPress的网站系统、支持博客、企业官网、及各种网站模板  
	wujun728.github.io 个人博客  

【Step4移动产品篇】：  
	jun_app    移动APP开发平台、支持Uniapp开发独立APP、小程序、企业办公等  
	jun_weixin   微信开发平台、微信公众号、微信小程序、微信管理后台、微信API接口后台  
	jun_android Android移动开发框架，APP开发模板、后台管理系统、后台API接口平台  

