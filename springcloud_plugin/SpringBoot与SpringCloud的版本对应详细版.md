# SpringBoot与SpringCloud的版本对应详细版



**缘起**

初学spring cloud的朋友可能不知道，其实SpringBoot与SpringCloud需要版本对应，否则可能会造成很多意料之外的错误，比如eureka注册了结果找不到服务类啊，比如某些jar导入不进来啊，等等这些错误。下面列出来springBoot和spring cloud的版本对应关系，需要配套使用，才不会出现各种奇怪的错误。

**关于maven仓库的版本列表**

spring-cloud-dependencies 版本列表可查看：
https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-dependencies
spring-boot-starter-parent 版本列表可查看：
https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-parent

**版本对应关系**

大版本对应：



| Spring Cloud             | Spring Boot                                    |
| :----------------------- | :--------------------------------------------- |
| Angel版本                | 兼容Spring Boot 1.2.x                          |
| Brixton版本              | 兼容Spring Boot 1.3.x，也兼容Spring Boot 1.4.x |
| Camden版本               | 兼容Spring Boot 1.4.x，也兼容Spring Boot 1.5.x |
| Dalston版本、Edgware版本 | 兼容Spring Boot 1.5.x，不兼容Spring Boot 2.0.x |
| Finchley版本             | 兼容Spring Boot 2.0.x，不兼容Spring Boot 1.5.x |
| Greenwich版本            | 兼容Spring Boot 2.1.x                          |
| Hoxtonl版本              | 兼容Spring Boot 2.2.x                          |



在实际开发过程中，我们需要更详细的版本对应：



| *Spring Boot*                | *Spring Cloud*          |
| :--------------------------- | :---------------------- |
| 1.5.2.RELEASE                | Dalston.RC1             |
| **1.5.9.RELEASE**            | **Edgware.RELEASE**     |
| 2.0.2.RELEASE                | Finchley.BUILD-SNAPSHOT |
| **2.0.3.RELEASE**            | **Finchley.RELEASE**    |
| 2.1.0.RELEASE-2.1.14.RELEASE | Greenwich.SR5           |
| **2.2.0.M4**                 | **Hoxton.SR4**          |



**关于spring cloud1.x版本和2.x版本区别**

spring cloud各个版本之间是有所区别的，比如在SpringCloud中，1.X和2.X版本在pom.xml中引入的jar包名字都不一样，比如有的叫spirng-cloud-starter-hystrix 有的叫spring-cloud-netflix-hystrix，维护起来会比较困难。

1.x版本pom.xml里几个基本用到的jar长这样：

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
      <modelVersion>4.0.0</modelVersion>
      <groupId>com.joyce</groupId>
      <artifactId>joyce-test</artifactId>
      <version>1.0</version>
      <packaging>jar</packaging>
     
      <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.9.RELEASE</version>
        <relativePath /> 
      </parent>
       
      <dependencyManagement>
        <dependencies>
          <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>Edgware.RELEASE</version>
            <type>pom</type>
            <scope>import</scope>
          </dependency>
        </dependencies>
      </dependencyManagement>
       
      <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
      </properties>
     
      <dependencies>
        <dependency>
          <groupId>org.springframework.cloud</groupId>
          <artifactId>spring-cloud-starter-feign</artifactId>
        </dependency>
        <dependency>
          <groupId>org.springframework.cloud</groupId>
          <artifactId>spring-cloud-starter-hystrix</artifactId>
        </dependency>
        <dependency>
          <groupId>org.springframework.cloud</groupId>
          <artifactId>spring-cloud-starter-zipkin</artifactId>
        </dependency>
        <dependency>
          <groupId>org.springframework.cloud</groupId>
          <artifactId>spring-cloud-starter-eureka</artifactId>
        </dependency>
        <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-web</artifactId>
          <exclusions>
            <!-- 排除spring boot默认使用的tomcat，使用jetty -->
            <exclusion>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-tomcat</artifactId>
            </exclusion>
          </exclusions>
        </dependency>
        <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-jetty</artifactId>
        </dependency>
        <dependency>
          <groupId>org.springframework.cloud</groupId>
          <artifactId>spring-cloud-starter-ribbon</artifactId>
        </dependency>
        <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-test</artifactId>
          <scope>test</scope>
        </dependency>
      </dependencies>
    </project>
```

而在2.x版本中，比如我们需要eureka,去maven仓库中可能会看到deprecated, please use spring-cloud-starter-netflix-eureka-client这类提示，包括使用ribbon也会有

![在这里插入图片描述](https://img.jbzj.com/file_images/article/202009/2020091609493528.png)

![在这里插入图片描述](https://img.jbzj.com/file_images/article/202009/2020091609493529.png)

所以个人猜测2.x中统一用
spring-cloud-starter-netflix-xx 替换了原有的 spring-cloud-starter-xx(此处如有不正确请指出)
所以2.x的版本pom.xml类似如下这样

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
 
  <groupId>com.forezp</groupId>
  <artifactId>service-feign</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <packaging>jar</packaging>
 
  <name>service-feign</name>
  <description>Demo project for Spring Boot</description>
 
 
  <parent>
    <groupId>com.forezp</groupId>
    <artifactId>sc-f-chapter3</artifactId>
    <version>0.0.1-SNAPSHOT</version>
  </parent>
 
  <dependencies>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>
  </dependencies>
   
  </project>
```



![img](https://img2018.cnblogs.com/blog/1097754/201909/1097754-20190907205906233-2071872814.png)

 

 手动记录一些经本人实际验证可行的版本对应：

| 序号 | 版本对应                                                     |
| ---- | ------------------------------------------------------------ |
| 1    | <spring-boot.version>2.4.2</spring-boot.version> <spring-cloud.version>2020.0.0</spring-cloud.version> |
| 2    | <spring-boot.version>2.4.1</spring-boot.version> <spring-cloud.version>2020.0.0-M6</spring-cloud.version> |
| 3    | <spring-boot.version>2.4.0</spring-boot.version> <spring-cloud.version>2020.0.0-M6</spring-cloud.version> |
| 4    | <spring-boot.version>2.3.2.RELEASE</spring-boot.version> <spring-cloud.version>Greenwich.SR2</spring-cloud.version> |

**spring-cloud-dependencies** 版本列表可查看： 

https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-dependencies

**spring-boot-starter-parent** 版本列表可查看：

https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-parent

 

 到spring官方下载最新的springboot 与 springcloud版本对应代码example:  https://start.spring.io/

 end!

