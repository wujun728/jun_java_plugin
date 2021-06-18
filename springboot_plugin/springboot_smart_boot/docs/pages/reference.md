---
layout: page
title: 使用手册 V1.0.2
permalink: /reference/
show_meta: false
# imagefeature path is relative to images/ directory.
imagefeature: ../images/arch.png
imageimportdemo: ../images/import_demo_project.png
imagefacadepom: ../images/facade_pom_modify.png
published: true
description: "About example.com...."
category: views
comments: false
mathjax: false
noindex: false
hide_printmsg: true
sitemap:
    priority: 0.7
    changefreq: 'monthly'
    lastmod: 2016-02-13
# tags will be used as html meta keywords.    
tags:
  - "foo boo"
  - "city tx"
---

* TOC
{:toc}


#第一章：smart-boot简介
`smart boot`是以**spring boot**为原型而构建的一款技术架构模型，致力于为企业的微服务方案实施提供基础服务。`smart boot`的本质就是一个spring boot工程，因此对于熟悉spring boot的开发者而言，掌握smart boot的几乎没什么门槛。即便仅是了解spring，也能在短时间内上手。

##1.1. smart-boot特性
- 模块化编程、面向服务编程、测试框架;
- 遵循“习惯优于配置”的原则，使用smart boot只需要很少的配置，大部分的时候我们直接使用默认的配置即可；
- 项目快速搭建，可以无需配置的自动整合第三方的框架；
- 可以完全不使用XML配置文件，只需要自动配置和Java Config；
- 内嵌Servlet容器，降低了对环境的要求，可以使用命令直接执行项目，应用可用jar包执行：java -jar；
- 提供了starter POM, 能够非常方便的进行包管理, 很大程度上减少了jar hell或者dependency hell；
- 运行中应用状态的监控；
- 微服务架构，支持RPC

---

##1.2. smart-boot依赖
1. JDK 7+
2. Maven 3
1. Mysql 默认对接mysql数据库，可根据实际项目需要重新进行配置
2. Redis 默认使用redis提供缓存服务，可根据实际项目需要重新进行配置
3. [smartboot-sosa](https://git.oschina.net/smartboot/smartboot-sosa) 提供底层RPC服务
4. [maven-mydalgen-plugin](http://git.oschina.net/smartboot/maven-mydalgen-plugin) 数据层采用了mybatis框架，通过该maven-mydalgen-plugin插件可以方便的生成DAL层的代码以及配置文件。

---

##1.3. 工程结构

- smart-assembly   
集中管理smart-boot工程依赖的所有配置文件(.properties、.yml、.xml等)
- smart-dal  
数据访问层,实现对数据库的CRUD操作
- smart-service-integration  
与第三方系统对接的module，以供smart-boot调用第三方服务
- smart-component  
组件module，遵循单一职责原则，向下对接smart-dal、smart-service-integration，向上为业务层smart-service-impl提供各组件式服务
- smart-service-facade  
定义smart-boot的服务接口，一个独立的module,不依赖其他模块。第三方系统可通过该module提供的接口调用服务
- smart-service-impl  
该module通过引用smart-componet提供的各组件用于实现smart-service-facade中定义的接口。对于私有服务可直接在本module中定义接口，无需放置在smart-service-facade中.  

>为方便使用，也可直接调用smart-service-integration中提供的服务

- smart-shared  
该module完全独立于业务，主要用于提供一些工具类，可被任一module引用
- smart-restful  
Web层，仅负责前后端的数据交互，不建议在该module中进行复杂的业务处理，应统一交由smart-service-impl处理

![系统结构图]({{ page.imagefeature }})

---

#第二章：创建smart-boot工程
在采用`smart boot`进行业务系统开发时，我们无需去下载`smart boot`源码。可通过Maven脚手架[smartboot-archetype](http://mvnrepository.com/artifact/org.smartboot.maven.archetype/smartboot-archetype)去创建工程。

	mvn -X archetype:generate -DarchetypeGroupId=org.smartboot.maven.archetype -DarchetypeArtifactId=smartboot-archetype -DarchetypeVersion=1.0.2 -DarchetypeCatalog=remote -DgroupId=com.test -DartifactId=hello-world -Dversion=1.0.0 -Dpackage=com.test

参数说明:

- -DarchetypeGroupId：脚手架groupId,固定为`org.smartboot.maven.archetype` 
- -DarchetypeArtifactId：脚手架artifactID，固定为`smartboot-archetype` 
- -DarchetypeVersion：脚手架版本号，设置为`1.0.2` 
- -DarchetypeCatalog：默认值:remote、local，标识脚手架来源为加载远程中央仓库或者调用本地仓库，首次执行请设置为**remote**。		
- -DgroupId：即将创建的工程groupId，例如：`com.test`
- -DartifactId：即将创建的工程artifactId，例如：`hello-world`
- -Dversion：即将创建的工程版本号，例如：`1.0.0`
- -Dpackage：即将创建的工程package,通常与groupId一致

>工程创建完成后，请打开facade模块下的pom.xml文件，删除\<parent>标签。

1. 将创建的`hello-world`工程导入IDE
<img src="{{ page.imageimportdemo }}" style='width:30rem'>
2. 删除facade模块下pom.xml文件的\<parent>标签
<img src="{{ page.imagefacadepom }}" style='width:30rem'>
3. dbapi-restful模块中运行BootStrap.java启动工程。因为smart-boot默认依赖数据库和redis，所以在未做任何配置的情况下启动会报异常。
- 若项目本身需要依赖数据库,可在`smart-assembly`模块的`application-dev.properties`文件中修改数据配置,否则参照《无数据库运行》章节去除数据库依赖；
- 若项目需要启用分布式Session或缓存服务,可在`smart-assembly`模块的`application-dev.properties`文件中修改redis配置，否则去除`applicaton.properties`中`spring.profiles.include`的配置值`redis`并参照《无分布式会话(Session)》章节关闭分布式会话；


#第三章：应用

##打包运行
`smart boot`本身即是一个Maven工程，所以也可通过maven进行打包。在工程主目录下执行`mvn install -Dmaven.test.skip=true`，执行无报错后，会在smart-restful/target目录下生成一个可执行的jar文件，可通过命令`java -jar XX.jar`启动项目。

---

##多环境配置(开发、测试、正式)
`smart boot`支持在一个工程内定义多套环境配置，该功能完全来源于[Spring Profiles](http://docs.spring.io/spring-boot/docs/1.4.3.RELEASE/reference/htmlsingle/#boot-features-profiles)的特性。例如：开发环境(application-dev.properties)、正式环境(application-stable.properties)，不同环境的差异化配置项定义在不同的属性文件中。
至于运行时使用哪一套环境配置，有两种生效方式：

1. 在application.properties中设置属性`spring.profiles.active`，若环境配置文件为`application-dev.properties`,则`spring.profiles.active=dev`
2. 通过设置jar包的启动参数动态设置。`java -jar -Dspring.profiles.active=dev XX.jar`

---

##无数据库运行
作为一套微服务项目，可能自身并不直接访问数据库，此时需要去除工程中的数据库配置项。

1. 修改smart-component模块的pom.xml，将其中的`smart-dal`修改成`smart-assembly`
2. 修改smart-assembly模块中的`application.properties`,去除`spring.profiles.include`中的`database`配置值

>当工程不依赖数据库时，意味着业务层的事务管理功能将失效。此时在`smart-service-impl`层继承`AbstractService`的类中再调用`operateTemplate.operateWithTransaction`将等同于`operateTemplate.operateWithoutTransaction`的效果

---

##无分布式会话(Session)
因为smart-boot是针对企业应用，所以默认启用了分布式会话。对于个人学习而言在使用上略显不便，因此需要修改一下配置关闭分布式会话。修改`smart-assembly`中的`application.properties`文件，在该文件内添加一条配置项`spring.session.store-type=none`即可。

---

##单元测试
在smart-boot中进行单元测试的开发非常方便，目前默认支持在`smart-dal`、`smart-component`、`smart-service-integration`、`smart-service-imp`中进行单元测试的编写。需要要在各模块中的`src/test/java`中定义**AbstractUnitTest**的子类，并在测试类中添加注解`@EnableAutoConfiguration`,`@SpringBootConfiguration`即可。例如：

	@SpringBootConfiguration
	@EnableAutoConfiguration
	public class CacheClientTest extends AbstractUnitTest {
	
		@Autowired
		private CacheClient cacheClient;
	
		@Test
		public void testA() {
			int[] a = new int[1024];
			for (int i = 0; i < a.length; i++) {
				a[i] = i;
			}
			cacheClient.putObject("1111111", a, 60);
			int[] q = cacheClient.getObject("1111111");
			for (int i = 0; i < q.length; i++) {
				System.out.println(q[i]);
			}
		}
	}
	
>注：若项目不依赖数据库,需要去除AbstractUnitTest中的注解`@Rollback`、`@Transactional`

---

##日志系统log4j2
1. Apache Log4j 2 is an upgrade to Log4j that provides significant improvements over its predecessor, Log4j 1.x, and provides many of the improvements available in Logback while fixing some inherent problems in Logback's architecture. 一句话总结，官方号称log4j2比log4j和logback都牛逼.
2. 配置简单集中，修改smart-assembly中的log4j2.xml即可实现整个工程的日志管理。

*[volutpat]: Tooltip for abbreviation.

---

## RESTful API 开发

---

## RPC服务

###服务发布

###服务调用
