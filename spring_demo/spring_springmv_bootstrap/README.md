# springmvc 介绍 #

<pre>它是一个典型的MVC三层框架示例工程，快速简单的上手。</pre>

#### 涉及技术组件

+ SpringMVC
+ MyBaits
+ Apache Shiro
+ Bootstrap3
+ Sitemesh3
+ Activiti
+ log4j2；

### 集成示例
+ 用户登录
+ 文件上传下载
+ 文件压缩
+ Excel导入
+ JQuery联想搜索
+ Activiti工作流

#### 如何跑起来？
1. 初始化数据库以MySQL为例<br>
	a. 创建数据库demo<br>
	b. 创建activiti相关表，执行[ddl/activiti/mysql](ddl/activiti/mysql)目录下所有SQL脚本<br>
	c. 创建用户相关的基础表，执行[ddl/mysl/ddl.sql](ddl/mysql/ddl.sql)脚本<br>
	d. 导入测试数据，执行[ddl/mysl/init.sql](ddl/mysql/init.sql)脚本<br>
	e. 初始化一份测试用户数据，运行cn.springmvc.mybatis.init.InitServiceTest.[testInit()](src/test/java/cn/springmvc/mybatis/init/InitServiceTest.java)方法
	
2. 启动工程，访问: [http://ip:port/]( )


#### 用户体系授权？

> 用户组织权限相关表关系很简历，表之间的关联关系，可以看cn.springmvc.mybatis.init.InitServiceTest.[testInit()](src/test/java/cn/springmvc/mybatis/init/InitServiceTest.java)方法执行的步骤，这块就没有单独写页面来操作了


[GitHub](https://github.com/wangxinforme) [issues](https://github.com/wangxinforme/springmvc/issues)

![Markdown](http://wx4.sinaimg.cn/mw690/005OXyHfgy1fh6evxykwhj30ag0as3zv.jpg)