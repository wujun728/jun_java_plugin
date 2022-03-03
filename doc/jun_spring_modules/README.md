### `jun_spring` 项目

#### 项目说明
基于Spring家族的360度整合，基本覆盖开发用九成以上的场景，即开即用，主要是详尽的了解项目里面的每个stater跟lib组件及功能，为个人及项目实际开发提供助力，帮组项目快速开发，本项目是maven项目，每个组件均可以独立运行且不依赖第三方框架，具体见项目明细。



#### jun_spring  项目module组件功能列表
- 【spring_helloworld】springboot 的一个 helloworld  
- 【spring_properties】springboot 读取配置文件中的内容  

TODO：
1、整cs的login跟index的page，适配Nginx跟ssm_jwt；
2、整ssm_jwt的代码生成器
3、整ssm_jwt的common工具包
4、整ssm_jwt的api跟rbac跟表结构


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



doc\
SpringRainV3.1.0\ 合并到下面的ssm里面
SpringWind\   合并到下面的ssm里面
sshe\    合并到下面的ssm里面
jun_ssh_easyui\ 将前端的easyui调整为html+js放到nginx里面，保留
jun_ssm\
jun_ssm_admin\
jun_ssm_biz\
jun_ssm_captcha\
jun_ssm_common\
jun_ssm_email\
jun_ssm_mis\
jun_ssm_web\
 
jun_ssm_jwt\ 新增
jun_ssm_wechatservice,
jun_ssm_bizservice,
jun_ssm_apiservice,
jun_ssm_easyui,
jun_ssm_bootstrap,
jun_ssm_layadmin
 


WordPress4J  --迁移到jun_website
Springside3   迁移到jun_spring
Springside4   迁移到jun_boot

Jun_ssm
	jun_bootstrap2\   干掉，代码则取重要的合并到ssh_common
	jun_ssm\   干掉，代码迁移main核心111
	jun_ssm_bootstrap\   干掉， 迁移到ssm
	jun_ssm_dubbo\   保留，迁移，代码迁移到ssm
	jun_ssm_easyui\    主代码，ssm，拆分API+WEB
		jun_ssm_api\    主代码，ssm，拆分API+WEB
		jun_ssm_web\    主代码，ssm，拆分API+WEB
	jun_ssm_jsp\   干掉，代码迁移参考111
	jun_ssm_layui\    干掉，代码迁移ssm
	jun_ssm2\
		laycms\     干掉，代码迁移ssm
		spring_admin\   干掉，代码迁移到boot
		spring_springwind2\  干掉，代码ssm
		spring_ssm_cluster\  干掉，代码ssm
		spring_ssm_easyui\  外部跑起来，代码ssm
		spring_wind\    合并到ssm framework
		
		
Code-generator
	doc
		adminlte2-itheima-doc\  迁移到frontend
		codeutil\   合并到maventemplate，跑起来
		dp-generator\     干掉，代码挪走gen
		dp-security\   干掉，代码迁移ssm
		itheima-cli\     迁移到fontend
		roncoo-adminlte-springmvc\   干掉，代码合入ssm
		ssm\  代码合入ssm
		vue-element-admin-api-java-itheima\  干掉，合并到boot
		vue-element-admin-doc-itheima\  迁移到frontend
		vue-element-admin-itheima\    迁移到frontend
Jun_test111
	SSM-Shiro-JWT    合并到jwt
	auto-code\   干掉，代码合入到helper，拼字符串没模板
	auto-code-admin\  干掉，代码迁移到boot
	auto-code-springboot-demo\  干掉，合并boot
	auto-code-ui\  重要，保留，迁移到gen里面
	auto-code-ui-spring-boot-starter\   只是个模板，合并到boot
	auto-code-web-demo\    只是个模板，合并到ssm
	black\   迁移到product，新增产品图片网
	codeGenerator\   重要，保留，合并到gen，新增freemarker的模板
	code-generatorcpp\   干掉，代码合入到gen
	code-template\  合并到跟
	easy-boot\   迁移到boot
	generator\  重要，保持刘，合并到gen，新freemarker的模板
	HbackmanageDemo\   迁移到frontend
	hplus_requirejs_singlePage\  迁移到frontend
	hplus-shiro\   重要，main
0000000000000000000
	ifast\
	ittree.club-mybatis-generator\
	Light-Year-Admin-Template\
	mk-teamwork-server\
	mk-teamwork-ui\
	multi-module-web\
	mybatis-dsc-generator\
	one-deploy\
	redisson-spring-boot-starter\
	renren-generator\
	RuoYi\
	RuoYi-Vue\
	SpringBoot_v2\
	springboot2-integration\
	SpringBootCodeGenerator\
	Springboot-Mybatis-Thymeleaf\
	spring-boot-quartz\
	X-admin\
	xxl-deep\
	xxl-deep2\
	

jun_2021
jun_framework
jun_temp1
jun_temp2
jun_test\




jun_product
	123\
		BlogHtTemplate\  迁移到jun_frontend/jun_layui
		blogv20180113\  干掉 ，代码
		inspinia_admin_java_ssm\    干掉，代码
		layuiAdmin\   迁移到jun_frontend/jun_layui
		LuGenerate\   干掉，代码搞spring_plugin
		manager-system\  迁移到jun_boot rename jun_boot_layadmin
		noteblogv5\   干掉，源码迁移，没有脚本
		simple-spring-jdbc\  迁移到spring_jdbc
		snaker-springmvc\  干掉，代码
		spring-Boot_templates_layui-Admin\   直接干掉
		spring-shiro-training\  迁移到easyui
		sypro\  迁移到ssh里面
		zb-shiro\   合并到ruoyi
		
	jun_administrative\
	jun_ask_discuss\
	jun_blog\
	jun_bos\
	jun_crm\
	jun_edu\  外网调试
	jun_erp\
	jun_finance\
	jun_flybbs\
	jun_hr\
	jun_itselfservice\
	jun_mis\
	jun_music\
	jun_oa\
	jun_op\
	jun_portal\
	jun_prj\
	jun_resume_java\
	jun_resume_pm\
	jun_spring\
	jun_wms\
	项目1111111\
		Jar包下载网视频教程\
		百度云爬虫视频教程\
		百度云搜索视频教程\
		博客采集系统视频教程\
		博客系统视频教程\
		客户关系视频教程\
		请假系统视频教程\
		设备系统视频教程\
		实用cms系统视频教程\
		支付系统视频教程\
		mindskip-uexam-master.zip
		project.zip
		project(1).zip
		project(1)(1).zip
		
	pom.xml
	
	
	1、父pom.xml jun_system --> jun_product
	https://github.com/xuxueli/xxl-deep
	https://github.com/oneboat/ssm-lay
	https://github.com/search?q=lay++ssm&type=Repositories
	https://github.com/lcw2004/one
	https://github.com/thinkgem/jeesite
	https://github.com/thinkgem/jeesite4
	https://github.com/thinkgem/jeesite4-cloud
	
	
	https://github.com/febsteam/FEBS-Cloud
	https://github.com/febsteam/FEBS-Shiro
	
	ssm
	https://github.com/Mandelo/ssm_shiro_blog
	
	
	boot
	https://github.com/yzcheng90/X-SpringBoot
	https://github.com/Heeexy/SpringBoot-Shiro-Vue
	
	cloud
	https://github.com/yzcheng90/ms
	https://github.com/yzcheng90/ms-ui
	
	
	CMS
	https://github.com/xujeff/tianti
	
	*****
	https://github.com/hope-for/hope-boot  

https://github.com/moshowgame/SpringBootCMS


#### 开发环境

- **JDK 1.8 +**
- **Maven 3.5 +**
- **IDEA 2018.2 + or  STS 4.5 +** (*注意：务必使用 IDEA 开发，同时保证安装 `lombok` 插件*)
- **Mysql 5.7 +** (*尽量保证使用 5.7 版本以上，因为 5.7 版本加了一些新特性，同时不向下兼容。本 demo 里会尽量避免这种不兼容的地方，但还是建议尽量保证 5.7 版本以上*)

#### 运行方式

> 提示：如果是 fork 的朋友，同步代码的请参考：
1. `git clone https://github.com/wujun728/jun_xxx.git`
2. 使用 IDEA 打开 clone 下来的项目
3. 在 IDEA 中 Maven Projects 的面板导入项目根目录下 的 `pom.xml` 文件
4. Maven Projects 找不到的童鞋，可以勾上 IDEA 顶部工具栏的 View -> Tool Buttons ，然后 Maven Projects 的面板就会出现在 IDEA 的右侧
5. 找到各个 Module 的 Application 类就可以运行各个 demo 了
6. 注意：每个 demo 均有详细的 README 配套，用 demo 前记得先看看
7. 注意：运行各个 demo 之前，有些是需要事先初始化数据库数据的

#### 已办&待办列表
- [ ] spring_boot_demo_urule（集成  urule 实现规则引擎）
- [x] spring_boot_demo_activiti（集成 Activiti 实现流程控制引擎）
- [x] ~~spring_boot_demo_async（Spring boot 实现异步调用）~~

