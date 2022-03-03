# FengYuWisdom

#### 介绍
锋豫智慧平台，是一款基于SSM+Git+LayUI实现的产品线

#### 软件架构
软件架构说明
采用单体的项目架构
源码管理：Git+码云
项目管理：maven
核心框架：SSM+Easyecel+json+Layui+Ajax

![输入图片说明](https://images.gitee.com/uploads/images/2021/0622/122816_01bcc3da_555784.png "屏幕截图.png")


#### 使用说明

模块的开发步骤：
	
	1.分析功能
	
	需要知道自己要做什么
		
	2.设计数据库
	
		功能单一原则
		
	3.搬砖
		实体层---对应数据库的表
		持久层---数据库操作的定义
		映射层---mapper文件实现sql语句的编写
		业务层---实现业务逻辑，接口和实现类
		控制层---对外的接口，可以通过url访问
		页面-----CV  新增、列表等
	4.集成测试




#### 技术栈详解

Maven+SSM+Git+Easyexcel+Layui

    1.各自作用
	Maven:管理项目和依赖jar包
		管理项目：常用的命令：clean complie package
		依赖jar：GAV坐标  避免手动导包
	Spring：框架  模板  工具集
		核心作用：IOC和AOP和Tx
		IOC：控制反转 创建对象并赋值 管理对象（单例）
		AOP：面向切面编程 代理模式 不改变源码的情况下，实现功能的增强
			通知的类型：前置、后置、环绕、成功、失败
			切入点表达式：匹配想要增强处理的内容
		tx：声明式事务 基于AOP实现事务管理
	SpringMVC：框架 代替Servlet 实现接口的快速开发
		核心作用：编写对外访问的接口
		记住各种注解：
		@Controller
		@RequestMapping
		@RequestParam
		@ResponseBody
		@RequestBody
		……
		会去定义 映射方法（外部通过URL访问所调用的方法）
		JSON数据交互---@RequestBody+@ResponseBody
	Mybatis：框架 实现对数据库的操作，简化封装 JDBC
		核心作用：生成接口的实现类，动态sql，结果的自动转换
		记住常用：
		1.mapper文件（常用标签：insert update delete select resultmap ）
		2.动态sql（foreach if where）
		3.优化策略（一级缓存、二级缓存、懒加载）
		4.实现多表关系的查询（resultmap：对象、集合）
	Git+码云：实际的企业开发中，都是团队协作，统一管理源代码
		核心作用：版本控制器 分支 冲突 命令 回滚
	Easyexcel：工具类 操作excel 读取和生成
		核心作用：读取excel表格的内容  生成excel数据
	Layui：前端框架 包含css和js 能写或能改
		核心的内容：表单、表格、导航、Tab、日期、文件上传等
	Ajax:
		是：技术、在网页中请求后端接口、可以为接口传递请求的参数，也可以接收接口的返回数据
		为：网页中，需要请求后端的接口
		
		咋：jQuery的Ajax封装，背格式	
			1.$.get("后端接口地址",function(res){
				结果数据的处理
			})
			get请求的参数传递：url?参数名=值&……
			
			2.$.post("后端接口地址",参数-键值对,function(res){
				结果数据的处理
			})
			
			3.$.ajax({
				url:"",
				method:"请求方式",
				success:function(res){
					
				}
			})
			
			
			
			$.post("url","id="+id的值,function(res){
			
			})


#### 有话要说
    如果感觉项目对你有用，希望可以来个star!

