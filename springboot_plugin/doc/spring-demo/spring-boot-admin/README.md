## spirng boot admin server

### 基本功能

* 基于服务发现 eureka 

* httpBasic 简单账号密码认证

* 使用SBA登入页面



### 客户端使用

pom.xml 添加


``` xml
<dependency>
	<groupId>de.codecentric</groupId>
	<artifactId>spring-boot-admin-starter-client</artifactId>
	<version>1.5.7</version>
</dependency>
``` 

application.yml 添加

    management.security.enabled: false



> 安全性可以进一步添加management.security相关认证