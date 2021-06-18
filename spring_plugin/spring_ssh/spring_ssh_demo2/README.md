# 简介

本项目为个人学习MAVEN构建SpringMVC Spring Hibernate框架原型学习演示

# 框架版本

* Spring: **3.2.5.RELEASE**

* Hibernate: **4.2.5.Final**

# 其他
Shiro
Ehcatch for shiro

# 下载安装说明
1、git到本地
2、使用eclipse m2e插件 
3、执行
----------------------------------------------------
1、mvn clean compile    清除class后再进行编译
2、mvn clean test            清除test内部class再编译执行
3、mvn clean package    打包
4、mvn clean install        编译并生成结果，并把结果打包如本地仓库
------------------------------------------------------
4、web容器 本地测试采用jetty
	mvn jetty:run
	mvn tomcat:run
5、localhost:8080/sshdemo


# 笔记
@MAVEN工程搭建顺序

1、新建工程 sshdemo
2、新建Web相关文件夹
    MAVEN web工程的包结构如下：
    src/main/java    -- compile 编译源码
    src/main/resources    -- 资源配置文件
    src/test/java -- 单元测试源码 test
    src/test/resources -- 单元测试部分配置文件
    src包main/webapp -- 存放前台页面文件及部分配置 WEB-INF
3、填写POM文件几个重要结构，具体见pom.xml注解，如：
    <packaging>war</packaging>
    <properties>
        
    </properties>
    <dependencies>
    </dependencies>
    <build>
    </build>

4、新建基本的demo层 entity dao service web
    其他辅助包 util、test等
    
5、新建spring配置文件
    springcontext

6、新建测试类，测试配置文件和其中的方法

7、引入shiro及配置文件
    新建spring-shiro配置文件
    pom引入shiro depedence jar
    
    
8、引入springmvc页面并对web层进行测试
    在springmvc中配置默认登陆跳转页面 redirect:/users/
    
9、测试shiro相关内容
* anon
org.apache.shiro.web.filter.authc.AnonymousFilter
* authc
org.apache.shiro.web.filter.authc.FormAuthenticationFilter
* authcBasic
org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
* perms
org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter
* port
org.apache.shiro.web.filter.authz.PortFilter
* rest
org.apache.shiro.web.filter.authz.HttpMethodPermissionFilter
* roles
org.apache.shiro.web.filter.authz.RolesAuthorizationFilter
* ssl
org.apache.shiro.web.filter.authz.SslFilter
* user
org.apache.shiro.web.filter.authc.UserFilter

* rest:例子/admins/user/**=rest[user],根据请求的方法，相当于/admins/user/**=perms[user:method] ,其中method为post，get，delete等。
* port：例子/admins/user/**=port[8081],当请求的url的端口不是8081是跳转到schemal://serverName:8081?queryString,其中schmal是协议http或https等，serverName是你访问的host,8081是url配置里port的端口，queryString
是你访问的url里的？后面的参数。
* perms：例子/admins/user/**=perms[user:add:*],perms参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，例如/admins/user/**=perms["user:add:*,user:modify:*"]，当有多个参数时必须每个参数都通过才通过，想当于
isPermitedAll()方法。
* roles：例子/admins/user/**=roles[admin],参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，例如/admins/user/**=roles["admin,guest"],每个参数通过才算通过，相当于hasAllRoles()方法。
* anon:例子/admins/**=anon 没有参数，表示可以匿名使用。
* authc:例如/admins/user/**=authc表示需要认证才能使用，没有参数
* authcBasic：例如/admins/user/**=authcBasic没有参数表示httpBasic认证
* ssl:例子/admins/user/**=ssl没有参数，表示安全的url请求，协议为https
* user:例如/admins/user/**=user没有参数表示必须存在用户，当登入操作时不做检查
 
这些过滤器分为两组，一组是认证过滤器，一组是授权过滤器。其中anon，authcBasic，auchc，user是第一组，
perms，roles，ssl，rest，port是第二组

#spring profile active 说明

#新增lucene全文索引，支持annotation字段索引，类似于hibernate-search
maven本地jar到本地仓库，jar包见doc文件夹,jar放置到d盘
mvn install:install-file -Dfile=d:\paoding-analysis.jar -DgroupId=net.paoding -DartifactId=paoding-analysis -Dversion=2.0.4 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true
mvn install:install-file -Dfile=d:\IKAnalyzer.jar -DgroupId=org.wltea -DartifactId=IKAnalyzer -Dversion=2012FF_u1 -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true

