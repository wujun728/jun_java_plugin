
# SSM 架构后台管理系统

> 该版本为 Spring Mvc 已不再维护

更好的商业版体验移步： https://gitee.com/atcrab/crab

------------------------------------------------
《春风》

春风如贵客，一到便繁华。

来扫千山雪，归留万国花。

[商业版 crab 演示地址： http://crab.kuaituima.com](http://crab.kuaituima.com)
------------------------------------------------

# 其他开源项目 | Other Project

- [基于Cookie的SSO中间件 Kisso](http://git.oschina.net/baomidou/kisso)
- [Mybatis-Plus CRUD 快速开发框架](http://git.oschina.net/baomidou/mybatis-plus)
- [基于Hibernate扩展 Hibernate-Plus](http://git.oschina.net/baomidou/hibernate-plus)

# SpringWind 说明


> 简单介绍

```
该项目为 SSM 核心库 spring-wind-core 演示项目

http://git.oschina.net/juapk/spring-wind

已集成组件：

1、mybatis-plus （mybatis 自动 crud 功能）
2、kisso （单点授权、权限管理、验证码、api 服务、oauth2认证）
3、mail（收发邮件）
4、veloctiy （继承模板支持、环境控制）
5、slf4j-api（日志 logback 管理）
7、fastjson （json 处理）
8、quartz (定时任务)
9、cos （优化、支持头文件字节检查、图片剪切、视频处理、文档处理）
10、Sigar （系统信息收集）
11、pingyin4j（中文转拼音库 ）
12、oauth2.0  weibo  github 登录支持
13、表单重复提交aop 辅助工具类
14、日志组件 logback 动态环境配置支持
15、 等..

```


> 运行项目配置说明

```
1、根据 /SpringWind/src/main/resources/properties/jdbc.properties 配置数据库

2、导入数据库 /SpringWind/src/test/resources/springwind.sql

3、操作系统 host 添加一行设置 127.0.0.1 demo.baomidou.com
    hosts文件存放目录：C:\Windows\System32\drivers\etc
    修改前请查看hosts文件属性是否为只读，如果只读右键单击文件，单击属性菜单，弹出属性对话框，修改去掉只读属性。

4、配置 无项目名称的 访问：http://demo.baomidou.com:8080 登录账户默认：  admin 管理员，密码 123 ，普通会员 test 密码  123

5、必须配置 hosts 重启浏览器，**未配置无项目名称**访问 404 ！！

 /SpringWind/src/main/resources/properties/sso.properties 配置修改为：

sso.login.url=http://demo.baomidou.com:8080/SpringWind/account/login.html 

访问：http://demo.baomidou.com:8080/SpringWind/account/login.html

```


> 测试权限

```
1、test 账户登录系统

2、访问地址：http://demo.baomidou.com:8080/role/list.html
```


> 404 异常

```
1、操作系统 host 需要与 /SpringWind/src/main/resources/properties/sso.properties 配置一致（配置好 host 重启浏览器）

2、注意：访问项目名称问题 ，未配置无项目名称访问携带项目名称访问。
```

更多补充中。。。。。


演示界面
=======

![登录效果](http://git.oschina.net/uploads/images/2016/0423/181624_cd5f4706_12260.png "登录效果")

![echarts演示效果](http://git.oschina.net/uploads/images/2016/0511/222846_6bbded27_12260.png "echarts演示效果")

![权限管理效果](http://git.oschina.net/uploads/images/2016/0423/182040_f9e11f03_12260.png "权限管理效果")

![监控效果](http://git.oschina.net/uploads/images/2016/0423/182059_de36d868_12260.png "监控效果")

![修改头像](http://git.oschina.net/uploads/images/2016/0509/224121_d6f7a3ca_12260.png "修改头像")

![日志管理](http://git.oschina.net/uploads/images/2016/0509/224142_5a4f847e_12260.png "日志管理")

![锁定效果](http://git.oschina.net/uploads/images/2016/0415/233245_dc44f2f9_12260.png "锁定效果")


Features
=======

1、欢迎提出更好的意见，帮助完善 Spring-Wind

Copyright
====================
GPL V3 License