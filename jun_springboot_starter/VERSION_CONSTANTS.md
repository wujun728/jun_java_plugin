# 版本常量参考

此文档记录 jun_springboot_starter 模块中使用的所有版本号，用于统一版本管理和维护。

## 内部模块版本

所有内部模块统一使用版本 **1.0.25**

### 基础模块（Foundation Modules）

- jun-common-base: 1.0.25
- jun-db-activerecord: 1.0.25
- jun-mybatis-sql-engine: 1.0.25

### 功能模块（Feature Modules）

- jun-activerecord: 1.0.25
- jun-db-spring-boot-starter: 1.0.25
- jun-db-activerecord2: 1.0.25
- jun-groovy-api: 1.0.25
- jun-groovy-api-spring-boot-starter: 1.0.25
- jun-swagger2-spring-boot-starter: 1.0.25
- jun-uidgenerator-spring-boot-starter: 1.0.25
- jun-sentinel-spring-boot-starter: 1.0.25
- jun-job-spring-boot-starter: 1.0.25
- jun-snakerflow-spring-boot-starter: 1.0.25
- jun-minio-spring-boot-starter: 1.0.25
- jun-encrypt-body-spring-boot-starter: 1.0.25
- jun-log-spring-boot-starter: 1.0.25
- jun-redis-spring-boot-starter: 1.0.25
- jun-onlineForm-spring-boot-starter: 1.0.25
- jun-security-spring-boot-starter: 1.0.25
- jun-p6spy-spring-boot-starter: 1.0.25
- jun-oss-spring-boot-starter: 1.0.25

## 第三方依赖版本

### Spring Framework 系列

| 依赖名称 | 版本 | 备注 |
|---------|------|------|
| Spring Boot | 2.5.14 | 主要版本 |
| Spring Cloud | 2020.0.6 | 对应 Spring Boot 2.5.x |
| Spring Cloud Alibaba | 2021.1 | 阿里巴巴微服务方案 |
| Spring Data Elasticsearch | 4.2.3 | ES 集成 |

### 数据库和 ORM

| 依赖名称 | 版本 | 备注 |
|---------|------|------|
| MyBatis Plus | 3.5.7 | MyBatis 增强框架 |
| MyBatis Plus Boot Starter | 3.5.7 | - |
| MyBatis Plus Extension | 3.5.7 | - |
| MyBatis Plus Core | 3.5.7 | - |
| MyBatis Plus Generator | 3.5.7 | - |
| Druid | 1.2.6 (starter) / 1.2.16 (direct) | 数据库连接池 |
| Sharding Sphere | 3.1.0 | 分库分表方案 |

### 工具库

| 依赖名称 | 版本 | 备注 |
|---------|------|------|
| Hutool | 5.8.25 (root) / 5.8.40-5.8.41 (modules) | Java 工具类库 |
| Guava | 33.2.1-jre | Google 集合库 |
| Lombok | 1.18.20 | 减少样板代码 |
| FastJSON2 | 2.0.37 / 2.0.57 | 阿里巴巴 JSON 库 |
| Apache Commons Lang3 | 3.10 / 3.18.0 | - |
| Apache Commons IO | 2.7 / 2.11.0 / 2.14.0 | - |
| Apache Commons Collections4 | 4.4 | - |
| Apache Commons Configuration | 1.10 / 2.7 | - |
| Apache Commons BeanUtils | 1.9.4 | - |
| Apache Commons FileUpload | 1.6.0 | 文件上传 |

### 缓存和消息

| 依赖名称 | 版本 | 备注 |
|---------|------|------|
| Redisson | 3.16.1 | Redis 客户端和框架 |
| Spring Cloud Context | 2020.0.6 | 从 Spring Cloud 获取 |

### 序列化和数据格式

| 依赖名称 | 版本 | 备注 |
|---------|------|------|
| Jackson Databind | 2.13.4 | JSON 序列化 |
| Jackson Core | 2.13.4 | - |
| Jackson Annotations | 2.20 | - |
| Freemarker | 2.3.32 | 模板引擎 |
| Velocity | 1.7 | 模板引擎 |
| JimuReport JFINAL ActiveRecord | 5.1.2 | - |
| JimuReport JFINAL Enjoy | 5.1.2 | - |

### API 文档和安全

| 依赖名称 | 版本 | 备注 |
|---------|------|------|
| Knife4j | 2.0.5 | Swagger UI 增强 |
| Swagger Butler Core | 2.0.1 | - |
| Spring Security OAuth2 | 2.3.8.RELEASE | OAuth2 实现 |
| Spring Security JWT | 1.1.0.RELEASE | JWT 支持 |
| JJWT | 0.9.1 | JWT 处理 |
| Hibernate Validator | 6.2.0.Final | JSR-380 验证 |
| Spring Social Security | 1.1.6.RELEASE | 社交登录 |

### 中间件和云服务

| 依赖名称 | 版本 | 备注 |
|---------|------|------|
| Aliyun OSS SDK | 3.8.1 | 阿里云对象存储 |
| Qiniu SDK | 7.2.28 | 七牛云存储 |
| AWS S3 SDK | 1.12.40 | 亚马逊 S3 |
| Dubbo | 2.7.8 | RPC 框架 |
| Curator | 5.1.0 | ZooKeeper 客户端 |
| FastDFS Client | 1.27.2 | 文件系统 |
| UserAgentUtils | 1.21 | 用户代理解析 |
| Transmittable Thread Local | 2.12.1 | 线程上下文传递 |
| TXLcn | 5.0.2.RELEASE | 分布式事务 |

### Office 和 POI

| 依赖名称 | 版本 | 备注 |
|---------|------|------|
| Apache POI | 4.1.1 | Office 文件处理 |
| Apache POI OOXML | 4.1.1 | - |
| EasyPOI | 4.1.3 | POI 简化库 |

### 其他

| 依赖名称 | 版本 | 备注 |
|---------|------|------|
| Spring Boot Admin | 2.5.6 | Spring Boot 应用监控 |
| Elasticsearch | 7.13.4 | 搜索引擎 |
| Elasticsearch REST Client | 7.13.4 | - |
| Elasticsearch X-Content | 7.13.4 | - |
| Easy Captcha | 1.6.2 | 验证码生成 |
| Banner | 1.0.2 | 启动横幅 |
| javax.annotation-api | 1.3.2 | JSR-250 注解 |
| javax.servlet-api | 4.0.1 | Servlet API |
| Spring Zuul | 2.2.9.RELEASE | API 网关 |
| Spring Cloud Starter OAuth2 | 2.2.5.RELEASE | - |
| Docker Maven Plugin | 1.2.2 | Docker 构建 |
| Mica Auto | 2.3.2 / 2.3.3 | 自动配置 |
| MySQL Connector | 8.0.33 | MySQL 驱动 |
| H2 Database | 1.4.200 | 测试数据库 |
| Mockito | 3.12.4 | 单元测试 Mock |
| JUnit | 4.12 / 4.13.2 | 单元测试框架 |

## 属性配置模板

每个模块的 `pom.xml` 应包含以下 `<properties>` 部分（至少包括核心版本）：

```xml
<properties>
    <java.version>1.8</java.version>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>

    <!-- 内部版本 -->
    <jun.version>1.0.25</jun.version>

    <!-- Spring 版本 -->
    <spring-boot.version>2.5.14</spring-boot.version>
    <spring-cloud.version>2020.0.6</spring-cloud.version>
    <spring-cloud-alibaba.version>2021.1</spring-cloud-alibaba.version>

    <!-- 核心依赖版本 -->
    <mybatis-plus.version>3.5.7</mybatis-plus.version>
    <hutool.version>5.8.25</hutool.version>
    <jackson.version>2.13.4</jackson.version>
    <druid.version>1.2.6</druid.version>
    <redisson.version>3.16.1</redisson.version>
    <knife4j.version>2.0.5</knife4j.version>
    <guava.version>33.2.1-jre</guava.version>
    <lombok.version>1.18.20</lombok.version>

    <!-- 其他常用依赖版本 -->
    <commons-lang3.version>3.10</commons-lang3.version>
    <commons-io.version>2.7</commons-io.version>
    <fastjson.version>2.0.37</fastjson.version>
    <freemarker.version>2.3.32</freemarker.version>
    <mica-auto.version>2.3.2</mica-auto.version>
</properties>
```

## dependencyManagement 模板

每个模块应包含以下 dependencyManagement，确保与 Spring Boot 版本兼容：

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>2.5.14</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>2020.0.6</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-alibaba-dependencies</artifactId>
            <version>2021.1</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

## 更新日志

- 2024-02-28: 初始版本，记录所有核心版本号和依赖关系
