###简介
Spring Boot全栈式入门学习资料

###启动方式

 1. 下载该项目并修改application.properties文件，将MySQL和Redis的信息修改为自己的配置
 2. 打开db/init.sql文件，将其中的sql语句在MySQL中运行
 3. 双击 bin/springboot-run.bat 启动项目，如果日志输出Started Application in 8.112 seconds (JVM running for 14.491)说明启动成功
 4. 浏览器打开localhost:8080，可以看到swagger-ui的主页
 5. 演示登录：在该页面打开POST tokens/，在username项输入admin、password项输入password，点击Try it out！，    
    查看返回结果得到userId和token
 6. 演示退出登录：在该页面打开DELETE tokens/，在authorization中填写用userId和token以"_"拼接得到的字符串，点击Try it out！，  
    如果返回码为200则成功。重复一次操作，返回码将变为401

###可能会遇到的问题：

**java.lang.ClassNotFoundException: org.jboss.jandex.IndexView **

原因是缺少``org.jboss:jandex:1.1.0Final``依赖，可能需要您手动在`pom.xml`中依赖中添加以下内容：

``<dependency>
    <groupId>org.jboss</groupId>
    <artifactId>jandex</artifactId>
    <version>1.1.0.Final</version>
</dependency>
``
###其他

参考项目   
http://www.scienjus.com/restful-token-authorization/  
https://github.com/ScienJus/spring-authorization-manager