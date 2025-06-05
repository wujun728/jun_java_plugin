# dbapi-spring-boot-starter

## 概述

- dbapi-spring-boot-starter 是接口快速开发工具，可以极大的降低代码量，类似于mybatis-plus框架，不需要再编写mapper接口、resultMap、resultType、javaBean(数据库表对应的java实体)
- 通过xml编写sql和数据库配置，可以快速开发接口，支持多数据源，支持动态sql，支持mysql/postgresql/oracle/sqlserver/doris/hive/impala/clickhouse等等
- dbapi-spring-boot-starter 是[DBAPI开源框架](https://github.com/freakchick/db-api) 的spring boot集成

## 对比mybatis优劣

- 如果使用mybatis框架的话，我们要编写 mapper java接口、mapper.xml、数据库表对应的javaBean实体类。
  当join查询的时候还要封装resultMap(xml)和java dto实体类。
- 如果使用本框架，相当于只需要编写mapper.xml中的sql脚本，参数类型返回类型都是自动的。极大的减少代码量。

## 适用场景

- 接口中没有复杂逻辑，都是sql执行，尤其适用于报表类应用
- 需要多种数据源

## 官方文档

[官方文档](https://starter.51dbapi.com)

## 引入依赖

```xml

<dependency>
    <groupId>com.gitee.freakchicken</groupId>
    <artifactId>dbapi-spring-boot-starter</artifactId>
    <version>1.1.0</version>
</dependency>
```

## 代码案例
[dbapi-spring-boot-starter-demo](https://gitee.com/freakchicken/dbapi-spring-boot-starter-demo.git)

## 联系作者：

### wechat：

<div style="text-align: center"> 
<img src="https://freakchicken.gitee.io/images/kafkaui/wechat.jpg" width = "30%" />
</div>

### 捐赠：

如果您喜欢此项目，请给捐助作者一杯咖啡
<div style="text-align: center">
<img align="center" height="200px" src="https://freakchicken.gitee.io/images/alipay.png"/>
<img align="center" height="200px" src="https://freakchicken.gitee.io/images/wechatpay.png"/>
</div>