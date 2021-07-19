## 为什么有这个包

- **目前开源项目中的工具包 `starter` 包层出不穷，为什么不借鉴 Spring Boot 统一管理起来呢，抛开`dubbo`、`nacos`、`sharding-sphere` 这种大型开源项目自己的 `Starter` 包，那么一些小的好用的工具包又能进行复用，何不开源共享**
- **QQ交流：1837307557**

**使用方式：在您的 `POM.XML` 中添加如下内容**

## 已实现模块

- **cors： Spring Boot 的跨域处理**
- **crypto： 加解密处理**
- **i18m： 国际化支持**
- **mybatis： 集成 tk.mapper 与 pagehelper 同时封装了全局赋值插件**
- **oss： 云存储（七牛/阿里/腾讯/MINIO）**
- **redis： 增强 Redis，包含分布式锁和分布式限流**
- **sensitive： 关键字脱敏处理**
- **time： JDK8日期全局格式化**
- **wrapper： 包装 request.body，支持重复读取request.getInputStream();**
- **xss： 接口防XSS攻击**
- **xxl-job： XXL-JOB 的自动装配**




```xml
<dependency>
    <groupId>com.battcn.boot</groupId>
    <artifactId>extend-具体的模块-spring-boot-starter</artifactId>
    <version>${extend-spring-boot.version}</version>
</dependency>
```

**动态控制：可以通过 `enabled` 动态控制，如果依赖了模块，默认开启使用**
``` properties
extend.模块.enabled=false
```

## 遗留问题

- **目前只实现了 `minio` 存储的路径返回，其它三种存储暂时未扩展返回内容（主要没有腾讯云和七牛云存储的测试账号/欢迎朋友提供【提供的账号不会出现在发布的代码中】）**
- **`crypto(加密)` 与 `sensitive(脱敏)` 一起使用会存在冲突（单独使用不会有问题）**