### `jun_plugin` 项目

#### 项目说明
jun_plugin 整合Java企业级各种开发组件、开箱即用、不写重复代码

#### `笔者其他项目` 功能实现&使用
- jun_springboot：[点击查看](https://github.com/wujun728/jun_springboot) 
- jun_springcloud：[点击查看](https://github.com/wujun728/jun_springcloud) 
    
#### 基础篇：企业级开发组件功能列表（jun_plugin）
- 【fastdfs-client-java】[fastdfs的源码，编辑jar，引入maven本地，maven仓库没这个jar包](https://github.com/wujun728/jun_plugin)
- 【jun_activiti】[原生集成activity工作流，完成工作流常用操作](https://github.com/wujun728/jun_plugin)
- 【jun_ajax】[完成ajax操作，前端及后端的ajax](https://github.com/wujun728/jun_plugin)
- 【jun_aliyun_sms】[发送阿里云短信及验证码等](https://github.com/wujun728/jun_plugin)
- 【jun_cron】 [cron表达式的java的实现及调度](https://github.com/wujun728/jun_plugin)
- 【jun_dao_hibernate】[原生集成hibernate及使用](https://github.com/wujun728/jun_plugin)
- 【jun_datasource】[原生集成各种JDBC DataSource数据源](https://github.com/wujun728/jun_plugin)
- 【jun_dbutil】[原生集成Apache 的Dbutils完成单表及多表的增删改查](https://github.com/wujun728/jun_plugin)
- 【jun_designpattern】[23种涉及模式的Java实现](https://github.com/wujun728/jun_plugin)
- 【jun_distributed_session】[分布式Session实现及配置持久化等](https://github.com/wujun728/jun_plugin)
- 【jun_email】[原生邮件发送、纯文本、HTML邮件、带附件的邮件](https://github.com/wujun728/jun_plugin)
- 【jun_executor】[原生的并发多线程demo操作](https://github.com/wujun728/jun_plugin)
- 【jun_fileupload】[原生的文件上传及下载操作的实现基于common fileupload](https://github.com/wujun728/jun_plugin)
- 【jun_freemarker】[原生集成freemarker模板引擎，数据+模板=输出，可输出代码生成器](https://github.com/wujun728/jun_plugin)
- 【jun_httpclient】[原生集成httpclient，发送http请求、下载文件等](https://github.com/wujun728/jun_plugin)
- 【jun_image】[原生图片操作包、包括图片上传、下载、展示、缩略图等](https://github.com/wujun728/jun_plugin)
- 【jun_jdbc】[原生JDBC操作，简单封装，需要的可以看下](https://github.com/wujun728/jun_plugin)
- 【jun_jdk】[JDK原生demo代码，了解的越多才会了解的越深](https://github.com/wujun728/jun_plugin)
- 【jun_json】[集成jackjson及fastjson及jons-lib及Gson等实现JSON生成及序列化&反序列化](https://github.com/wujun728/jun_plugin)
- 【jun_jsoup】[HTM标记语言解析包，完成HTML解析、主要爬虫使用，解析HTML渲染数据](https://github.com/wujun728/jun_plugin)
- 【jun_leetcode】[算法刷题大全](https://github.com/wujun728/jun_plugin)
- 【jun_lucene】[老牌搜索引擎、可以看下](https://github.com/wujun728/jun_plugin)
- 【jun_poi】[原生POI完成Excel文件的导入、解析、导出及持久化等](https://github.com/wujun728/jun_plugin)
- 【jun_qrcode】[二维码生成器](https://github.com/wujun728/jun_plugin)
- 【jun_quartz】[job任务调度](https://github.com/wujun728/jun_plugin)
- 【jun_rpc】[原生RPC调用、客户端及服务端](https://github.com/wujun728/jun_plugin)
- 【jun_servlet】[原生Servlet、WEB开发的基础](https://github.com/wujun728/jun_plugin)
- 【jun_sso】[原生SSO的实现单点登录](https://github.com/wujun728/jun_plugin)
- 【jun_test】[JUNIT框架及TestNG框架](https://github.com/wujun728/jun_plugin)
- 【jun_utils】[常用开发工具集、非常重要！！！](https://github.com/wujun728/jun_plugin)
- 【jun_webservice】[原生的webservice调用、基于apache cxf实现服务调用及发布](https://github.com/wujun728/jun_plugin)
- 【jun_webservlet】[原生Servlet 3.0的实现](https://github.com/wujun728/jun_plugin)
- 【jun_websocket】[原生的WebSocket的实现长链接](https://github.com/wujun728/jun_plugin)
- 【jun_xml】[原生的XML解析及生产XML、提供SAX、DOM、DOM4J解析方式](https://github.com/wujun728/jun_plugin)
- 【jun_zxing】[Google二维码生成器](https://github.com/wujun728/jun_plugin)


合并到plugin
https://github.com/arthaschan/SpringBootBucket
合并到plugin
https://github.com/YunaiV/SpringBoot-Labs
 
 
 
jun_algorithm\    干掉，迁移到jdk，新增jun_designpattern
jun_aliyun_sms\    保留
jun_apache_commons\   保留
jun_api\      调整，切换为apijson
jun_bootsrtap\    干掉
jun_cache\      调整 合并
jun_captcha\      调整，合并
jun_crawler\ 调整
jun_datasource\ 调整
jun_dbutil\ 调整
jun_demo\ 调整
jun_drools\ 调整
jun_email\ 调整
jun_excel\ 调整
jun_fileupload\ 调整
jun_freemarker\ 调整
jun_gzip\ 调整
jun_httpclient\ 调整
jun_image\      调整， 调整
jun_itext\      干掉，删掉，重命名 jun_pdf
jun_jbpm\        调整
jun_jdbc\   干掉，合并到dbutil里面
jun_jdk\     调整
jun_job\   干掉，合并到quartz里面
jun_json\     干掉，合并到util里面
jun_jsoup\      干掉，调整，合并到crawler
jun_multicluster\ 干掉合并到datasource_cluster
jun_mybatisplus\     调整
jun_pay\    调整
jun_qrcode\   调整
jun_quartz\     调整
jun_rpc\   干掉，建spring_dubbo
jun_servlet3\   干掉合并到webservlet里面
jun_shiro\   调整
jun_sso\     调整
jun_templatespider\ 干掉，  合并到crawler
jun_util\     调整
jun_webmagic\   干掉，合并到crawler
jun_webservice\   调整
jun_webservlet\   调整
jun_websocket\   调整
jun_xml\    调整
 
spring_activemq\    干掉，放到mq里面
spring_atomikos\    调整
spring_autowired\    干掉，合并到spring_demos
spring_camel\    调整
spring_cas\    调整
spring_cors\ 调整
spring_demo\      调整
spring_disruptor\    干掉
spring_distributed_config\ 调整
spring_distributed_fastdfs\ 调整
spring_distributed_lock\ 调整
spring_distributed_multidatasource\ 调整
spring_distributed_netty\ 调整
spring_distributed_oss_qiniu\ 调整
spring_distributed_rpc\ 调整
spring_distributed_session\ 调整
spring_distributed_transaction_tcc\   新增seata
spring_drools\ 调整
spring_dubbo\    调整
spring_dynamic_job\   干掉，合并到spring_quartz
spring_elasticsearch\ 调整
spring_email\    合并到jun_excel
spring_excel\   合并到jun_email
spring_generator\ 干掉
spring_hibernate\ 调整一下
spring_jasperreport\ 干掉，新增jimureport
spring_jpa\ 调整一下
spring_jqgrid\   干掉，迁移到spring_demo里面
spring_jsonp\   干掉，迁移到cors里面
spring_jsoup\   干掉，迁移到spring_webmagic里面
spring_jwt\    调整
spring_kafka\   干掉，新增spring_mq
spring_lucene\ 调整
spring_mina\   干掉，迁移到netty里面
spring_mongodb\ 调整
spring_mybatis\ 调整，并新增mybatisplus
spring_netty\ 调整
spring_nutch\ 干掉
spring_oauth\   调整
spring_plupload\ 调整
spring_quartz\ 调整
spring_rabbitmq\   干掉，迁移到mq里面
spring_redis\   调整
spring_s2sh\    干掉，迁移到ssh
spring_session\   调整
spring_shiro_redis\     调整
spring_simplessh14\   干掉，迁移到ssh里面
spring_solr\   调整
spring_spider\    干掉，迁移到spring_webmagic
spring_springbatch\   干掉，迁移到quartz里面
spring_springjdbc\ 调整
spring_springmvc\ 调整
spring_ssh\   调整
spring_ssm_dubbo\ 干掉，迁移到spring_ssml里面
spring_ssm_freemarker\ 干掉，迁移到spring_ssml里面
spring_ssm_layui\ 干掉，迁移到spring_ssml里面
spring_ssm2\ 干掉，迁移到spring_ssml里面
spring_sso\   干掉，迁移到oauth里面
spring_struts2\ 干掉，可以直接干掉，迁移到ssh里面
spring_swagger\    调整
spring_task\   干掉，迁移到quartz里面
spring_thymeleaf\    调整
spring_validator\ 干掉，新增spring_hibernate_validator
spring_velocity\   调整
spring_websocket\
 
https://github.com/ityouknow/spring-boot-examples
https://github.com/yidao620c/SpringBootBucket
https://github.com/rstyro/Springboot


spring_websocket\
 
https://github.com/ityouknow/spring-boot-examples
https://github.com/yidao620c/SpringBootBucket
https://github.com/rstyro/Springboot
https://github.com/yizhiwazi/springboot-socks
 
https://github.com/ityouknow/spring-cloud-examples
 
https://github.com/ityouknow/spring-examples



 
springboot_activiti\
springboot_actuator\ 调整
springboot_admin\
springboot_async\
springboot_batch\   调整
springboot_bootshiro\   调整
springboot_cache\   调整
springboot_codegen\
springboot_data_jpa\   调整
springboot_data_mybatis\
springboot_distributed_seckill\
springboot_docker\
springboot_drools\   调整
springboot_dubbo\
springboot_dubbox\
springboot_dynamic_datasource\
springboot_elastic_job\    调整
springboot_elasticsearch\
springboot_email\
springboot_exception_handler\
springboot_fastdfs\   调整
springboot_filemanager\   调整
springboot_flyway\
springboot_freemarker\   调整
springboot_graylog\
springboot_helloworld\
springboot_hibernate_validator\   调整
springboot_https\
springboot_jackson\ 调整
springboot_jbpm6\   调整
springboot_jms\    调整
springboot_junit\ 调整
springboot_jwt\   调整
springboot_kafka_demo\   调整
springboot_kaptcha_spring_boot_starter\    调整
springboot_kisso_spring_boot\   调整到sso里面
springboot_klock_starter\ 干掉
springboot_klock_starter22\   干掉
springboot_labs\    调整，拆分到boot跟cloud里面
springboot_laydate_theme_bootstrap\
springboot_ldap\
springboot_learning\   调整
springboot_learning_example\   调整
springboot_learning23\ 调整
springboot_leave\
springboot_lhbauth\   调整
springboot_lock_spring_boot_starter\   调整
springboot_lock4j_spring_boot_starter\ 调整
springboot_log\   调整
springboot_log 2\
springboot_log_aop\
springboot_logback\
springboot_mall_boot\    调整
springboot_mongodb\
springboot_mongodb 2\   调整
springboot_mongodb2\
springboot_mongodb23\
springboot_mq_kafka\
springboot_mq_rabbitmq\
springboot_mq_rocketmq\
springboot_ms_office\
springboot_multi_datasource_jpa\
springboot_multi_datasource_mybatis\
springboot_mybatis\
springboot_mybatis_jsp\   调整
springboot_neo4j\
springboot_oauth\
springboot_oauth_server\    调整
springboot_oauth2\   调整
springboot_oauth2_server\   调整
springboot_order_mybaits_activemq\ 调整
springboot_orm_beetlsql\
springboot_orm_jdbctemplate\
springboot_orm_jpa\
springboot_orm_mybatis\
springboot_orm_mybatis_mapper_page\
springboot_orm_mybatis_plus\
springboot_pay\   调整
springboot_pay 2\
springboot_pay222\
springboot_plus\   干掉
springboot_project\   调整，点餐系统
springboot_properties\
springboot_ratelimit_guava\
springboot_ratelimit_redis\
springboot_rbac_security\
springboot_rbac_shiro\
springboot_redis\
springboot_redis_sentinel_example\   调整
springboot_redislock\    调整
springboot_redisson\
springboot_redisson_spring_boot_starter\    调整
springboot_restful\   调整
springboot_restful 2\
springboot_roncoo_jui_springboot\   调整
springboot_roncoo_spring_boot\ 调整
springboot_samples\   调整
springboot_sell\
springboot_session\
springboot_session2\   调整，合并到springboot_session
springboot_session3\   调整
springboot_sharding_jdbc\
springboot_sharding_jdbc_demo\    调整
springboot_sharding_jdbc_mybatis_plus_spring_boot_starter\    调整
springboot_shiro\    调整，shiro
springboot_shiro_spring_boot_starter\   干掉，调整，合并
springboot_smart_boot\
springboot_social\
springboot_sofa_boot\
springboot_springside4\   调整
springboot_springtime\
springboot_ssh2_boot1\    调整
springboot_ssm2\   调整
springboot_starter_drools\   调整
springboot_starter_dubbo\
springboot_starter_dubbox\
springboot_starter_swagger\   调整
springboot_starter_weixin\
springboot_startkit\
springboot_study\   调整
springboot_study 2\
springboot_superboot\   调整，迁移到cloud
springboot_swagger\
springboot_swagger_beauty\
springboot_task\
springboot_task_quartz\
springboot_task_xxl_job\
springboot_task2\   调整
springboot_template_beetl\
springboot_template_enjoy\
springboot_template_freemarker\
springboot_template_thymeleaf\
springboot_templatemaven\   调整
springboot_templates_layui_admin\   调整
springboot_test_examples\   调整
springboot_tio\
springboot_tools\   迁移到jun_boot里面
springboot_transaction\
springboot_tyboot\
springboot_tyboot2\
springboot_tybootdemo\
springboot_uflo\
springboot_uniapp\
springboot_unity\   调整
springboot_unity 2\
springboot_unity23\
springboot_unity23 2\
 





#### 开发环境
1
- **JDK 1.8 
- **Maven 3.5 
- **IDEA 2018.2 + or  STS 4.5 +** (*注意：务必使用 IDEA 开发，同时
 
