# Spring cloud alibaba之Nacos 系统版本兼容性

# 组件版本关系

由于Spring cloud alibaba已经完成了孵化，因此不建议使用孵化期版本的Spring cloud alibaba版本。

spring boot 2.3.x的版本已经发布建议不要使用最新版，尽量的使用兼容版。

> 经过测试 spirng cloud alibaba 2.2.1.RELEASE版本是兼容spring boot 2.9.1版本的

| Spring Cloud Alibaba Version                    | Sentinel Version | Nacos Version | RocketMQ Version | Dubbo Version | Seata Version |
| ----------------------------------------------- | ---------------- | ------------- | ---------------- | ------------- | ------------- |
| 2.2.1.RELEASE                                   | 1.7.1            | 1.2.1         | 4.4.0            | 2.7.6         | 1.1.0         |
| 2.2.0.RELEASE                                   | 1.7.1            | 1.1.4         | 4.4.0            | 2.7.4.1       | 1.0.0         |
| 2.1.2.RELEASE or 2.0.2.RELEASE                  | 1.7.1            | 1.2.1         | 4.4.0            | 2.7.6         | 1.1.0         |
| 2.1.1.RELEASE or 2.0.1.RELEASE or 1.5.1.RELEASE | 1.7.0            | 1.1.4         | 4.4.0            | 2.7.3         | 0.9.0         |
| 2.1.0.RELEASE or 2.0.0.RELEASE or 1.5.0.RELEASE | 1.6.3            | 1.1.1         | 4.4.0            | 2.7.3         | 0.7.1         |

## 毕业版本依赖关系(推荐使用)

| Spring Cloud Version        | Spring Cloud Alibaba Version | Spring Boot Version |
| --------------------------- | ---------------------------- | ------------------- |
| Spring Cloud Hoxton.SR3     | 2.2.1.RELEASE                | 2.2.5.RELEASE       |
| Spring Cloud Hoxton.RELEASE | 2.2.0.RELEASE                | 2.2.X.RELEASE       |
| Spring Cloud Greenwich      | 2.1.2.RELEASE                | 2.1.X.RELEASE       |
| Spring Cloud Finchley       | 2.0.2.RELEASE                | 2.0.X.RELEASE       |
| Spring Cloud Edgware        | 1.5.1.RELEASE                | 1.5.X.RELEASE       |

## 依赖管理

Spring Cloud Alibaba BOM 包含了它所使用的所有依赖的版本。

### RELEASE 版本

#### Spring Cloud Hoxton

如果需要使用 Spring Cloud Hoxton 版本，请在 dependencyManagement 中添加如下内容



```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-alibaba-dependencies</artifactId>
    <version>2.2.1.RELEASE</version>
    <type>pom</type>
    <scope>import</scope>
</dependency>
```

#### Spring Cloud Greenwich

如果需要使用 Spring Cloud Greenwich 版本，请在 dependencyManagement 中添加如下内容



```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-alibaba-dependencies</artifactId>
    <version>2.1.2.RELEASE</version>
    <type>pom</type>
    <scope>import</scope>
</dependency>
```

#### Spring Cloud Finchley

如果需要使用 Spring Cloud Finchley 版本，请在 dependencyManagement 中添加如下内容



```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-alibaba-dependencies</artifactId>
    <version>2.0.2.RELEASE</version>
    <type>pom</type>
    <scope>import</scope>
</dependency>
```

#### Spring Cloud Edgware

如果需要使用 Spring Cloud Edgware 版本，请在 dependencyManagement 中添加如下内容



```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-alibaba-dependencies</artifactId>
    <version>1.5.1.RELEASE</version>
    <type>pom</type>
    <scope>import</scope>
</dependency>
```

| name                          | description                     | option                                 |
| ----------------------------- | ------------------------------- | -------------------------------------- |
| MODE                          | cluster模式/standalone模式      | cluster/standalone default **cluster** |
| NACOS_SERVERS                 | nacos cluster地址               | eg. ip1,ip2,ip3                        |
| PREFER_HOST_MODE              | 是否支持hostname                | hostname/ip default **ip**             |
| NACOS_SERVER_PORT             | nacos服务器端口                 | default **8848**                       |
| NACOS_SERVER_IP               | 多网卡下的自定义nacos服务器IP   |                                        |
| SPRING_DATASOURCE_PLATFORM    | standalone 支持 mysql           | mysql / empty default empty            |
| MYSQL_MASTER_SERVICE_HOST     | mysql 主节点host                |                                        |
| MYSQL_MASTER_SERVICE_PORT     | mysql 主节点端口                | default : **3306**                     |
| MYSQL_MASTER_SERVICE_DB_NAME  | mysql 主节点数据库              |                                        |
| MYSQL_MASTER_SERVICE_USER     | 数据库用户名                    |                                        |
| MYSQL_MASTER_SERVICE_PASSWORD | 数据库密码                      |                                        |
| MYSQL_SLAVE_SERVICE_HOST      | mysql从节点host                 |                                        |
| MYSQL_SLAVE_SERVICE_PORT      | mysql从节点端口                 | default :3306                          |
| MYSQL_DATABASE_NUM            | 数据库数量                      | default :2                             |
| JVM_XMS                       | -Xms                            | default :2g                            |
| JVM_XMX                       | -Xmx                            | default :2g                            |
| JVM_XMN                       | -Xmn                            | default :1g                            |
| JVM_MS                        | -XX:MetaspaceSize               | default :128m                          |
| JVM_MMS                       | -XX:MaxMetaspaceSize            | default :320m                          |
| NACOS_DEBUG                   | 开启远程调试                    | y/n default :n                         |
| TOMCAT_ACCESSLOG_ENABLED      | server.tomcat.accesslog.enabled | default :false                         |

