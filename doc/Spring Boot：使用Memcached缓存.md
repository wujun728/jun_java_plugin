# [Spring Boot：使用Memcached缓存](https://www.cnblogs.com/xifengxiaoma/p/11115130.html)

## 综合概述

Memcached是一个自由开源的，高性能，分布式内存对象缓存系统。Memcached基于内存的key-value存储，用来存储小块的任意数据，这些数据可以是数据库调用、API调用或者是页面渲染的结果。通过Memcached缓存数据库查询结果，可以有效地减少数据库访问次数，进而提高动态Web应用的速度。虽然Memcached的守护进程是用C写的，但是客户端可以用任何语言来编写，并通过Memcached协议与守护进程进行通信。

因为Spring Boot暂时还没有提供 Memcached相关的支持包，因此需要我们通过集成第三方提供的Memcached客户端来实现。Spymemcached是官方推出的一个Memcached Java客户端，使用NIO实现，异步、单线程，在性能上表现出色，广泛应用于Java + Memcached项目中。

## 实现案例

接下来，我们就用一个简单的案例来说明在Spring Boot中如何使用Memcached缓存技术。

首先，需要安装Memcached，教程很多，这里不再赘述。可以参考：[Memcached安装教程](https://www.runoob.com/memcached/window-install-memcached.html)。

### 生成项目模板

为方便我们初始化项目，Spring Boot给我们提供一个项目模板生成网站。

\1. 打开浏览器，访问：https://start.spring.io/

\2. 根据页面提示，选择构建工具，开发语言，项目信息等。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190620170755294-565515384.png)

\3. 点击 Generate the project，生成项目模板，生成之后会将压缩包下载到本地。

\4. 使用IDE导入项目，我这里使用Eclipse，通过导入Maven项目的方式导入。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190620170815047-443889129.png)

### 添加相关依赖

清理掉不需要的测试类及测试依赖，添加 Maven 相关依赖，这里需要添加上web、swagger和spymemcached的依赖，Swagger是为了方便接口测试。

对于spymemcached的支持，其实只要如下这个依赖包就可以了。

```
<!-- spymemcached -->
<dependency>
  <groupId>net.spy</groupId>
  <artifactId>spymemcached</artifactId>
  <version>2.12.3</version>
</dependency>
```

完整的POM文件内容如下。

pom.xml

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.5.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.louis.springboot</groupId>
    <artifactId>demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>demo</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
        <!-- web -->
        <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
         </dependency>
        <!-- swagger -->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.9.2</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.9.2</version>
        </dependency>
        <!-- spymemcached -->
        <dependency>
          <groupId>net.spy</groupId>
          <artifactId>spymemcached</artifactId>
          <version>2.12.3</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

### 添加相关配置

1.添加swagger 配置

添加一个swagger 配置类，在工程下新建 config 包并添加一个 SwaggerConfig 配置类，除了常规配置外，加了一个令牌属性，可以在接口调用的时候传递令牌。

SwaggerConfig.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Swagger API Doc")
                .description("This is a restful api document of Swagger.")
                .version("1.0")
                .build();
    }

}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

2.在配置文件添加memcache的主机端口信息

application.properties

```
memcache.ip=127.0.0.1
memcache.port=11211
```

3.添加一个MemcacheConfig配置类，读取主机端口并构造一个MemcachedClient。

MemcacheConfig.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.config;

import java.io.IOException;
import java.net.InetSocketAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import net.spy.memcached.MemcachedClient;

@Configuration
public class MemcacheConfig {

    @Value("${memcache.ip}")
    private String ip;

    @Value("${memcache.port}")
    private int port;


    @Bean
    public MemcachedClient getClient() {
        MemcachedClient memcachedClient = null;
        try {
            memcachedClient  = new MemcachedClient(new InetSocketAddress(ip, port));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return memcachedClient;
    }
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

### 编写业务接口

编写一个业务控制器，通过MemcachedClient实现对缓存的设置和读取。

MemcacheController.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;

@RestController
public class MemcacheController {

    @Autowired
    private MemcachedClient memcachedClient;

    @GetMapping("/memcache")
    public String memcache() throws InterruptedException {
        // 放入缓存, 如下参数key为name，值为louis，过期时间为5000，单位为毫秒
        OperationFuture<Boolean> flag = memcachedClient.set("name", 5000, "louis");
        // 取出缓存
        Object value = memcachedClient.get("name");
        System.out.println(value);
        // 多线程睡眠5秒,让
        Thread.sleep(5000);
        value = memcachedClient.get("name");
        System.out.println(value);
        return "success";
    }
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

### 编译运行测试

\1. 右键项目 -> Run as -> Maven install，开始执行Maven构建，第一次会下载Maven依赖，可能需要点时间，如果出现如下信息，就说明项目编译打包成功了。

 ![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190614112732476-1611778259.png)

\2. 右键文件 DemoApplication.java -> Run as -> Java Application，开始启动应用，当出现如下信息的时候，就说明应用启动成功了，默认启动端口是8080。

 ![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190614112810201-1915006672.png)

\3. 打开浏览器，访问：http://localhost:8080/swagger-ui.html，进入swagger接口文档界面。

![img](https://img2018.cnblogs.com/blog/616891/201907/616891-20190701164127844-895205940.png)

4.调用memcache接口，测试缓存存取操作，查看控制台输出结果。

```
louis
null
```

写入数据时设置name=louis，过期时间为5秒，第一次获取name结果为louis，在睡眠5秒之后第二次获取name时，因为过期返回null。

 

## 相关导航

[Spring Boot 系列教程目录导航](https://www.cnblogs.com/xifengxiaoma/p/11116330.html)

[Spring Boot：快速入门教程](https://www.cnblogs.com/xifengxiaoma/p/11019240.html)

[Spring Boot：整合Swagger文档](https://www.cnblogs.com/xifengxiaoma/p/11022146.html)

[Spring Boot：整合MyBatis框架](https://www.cnblogs.com/xifengxiaoma/p/11024402.html)

[Spring Boot：实现MyBatis分页](https://www.cnblogs.com/xifengxiaoma/p/11027551.html)

## 源码下载

码云：https://gitee.com/liuge1988/spring-boot-demo.git