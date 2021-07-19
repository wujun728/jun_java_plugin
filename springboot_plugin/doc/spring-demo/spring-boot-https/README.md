#spring-boot支持https的步骤
###* 生成SSL认证
#### 命令

keytool -genkey -alias tomcat -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 3650

![keytool](http://git.oschina.net/buxiaoxia/spring-demo/raw/master/spring-boot-https/pic/keytool.png)  

###* Spring Boot启动https支持

![config](http://git.oschina.net/buxiaoxia/spring-demo/raw/master/spring-boot-https/pic/config.png)  

###* 重定向HTTP到HTTPS

#####配置文件:
com.buxiaoxia.system.config.WebConfig.class 指定配置