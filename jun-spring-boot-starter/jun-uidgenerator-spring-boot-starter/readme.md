# jun-uidgenerator-spring-boot-starter

#### 介绍
生成全局唯一ID之UidGenerator，from 百度，原ID生成依赖Spring4.x及ibatis，6年没有更新了，特升级springboot-stater不再依赖ibatis了，改为了jdbctemplate查询插入WORKER_NODE，即插即用，自用

#### 软件架构
软件架构说明
- UidGenerator是Java实现的, 基于Snowflake算法的唯一ID生成器。UidGenerator以组件形式工作在应用项目中, 支持自定义workerId位数和初始化策略, 从而适用于docker等虚拟化环境下实例自动重启、漂移等场景。 在实现上, UidGenerator通过借用未来时间来解决sequence天然存在的并发限制; 采用RingBuffer来缓存已生成的UID, 并行化UID的生产和消费, 同时对CacheLine补齐，避免了由RingBuffer带来的硬件级「伪共享」问题.
 

#### 安装教程

1.  第一次使用需要建数据库表
```sql
DROP TABLE IF EXISTS WORKER_NODE;
CREATE TABLE WORKER_NODE
(
ID BIGINT unsigned NOT NULL AUTO_INCREMENT COMMENT 'auto increment id',
HOST_NAME VARCHAR(64) NOT NULL COMMENT 'host name',
PORT VARCHAR(64) NOT NULL COMMENT 'port',
TYPE INT NOT NULL COMMENT 'node type: ACTUAL or CONTAINER',
LAUNCH_DATE DATE NOT NULL COMMENT 'launch date',
MODIFIED TIMESTAMP NOT NULL COMMENT 'modified time',
CREATED TIMESTAMP NOT NULL COMMENT 'created time',
PRIMARY KEY(ID)
)
COMMENT='DB WorkerID Assigner for UID Generator';
```


#### 使用说明

1.  引入依赖
```xml
<dependency>
    <groupId>io.github.wujun728</groupId>
    <artifactId>jun-uidgenerator-springboot-starter</artifactId>
    <version>1.0.1</version>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
    <scope>compile</scope>
</dependency>
```
2.   增加配置(任选其一)
 
```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/DB_BASE?useSSL=false&characterEncoding=UTF-8&allowMultiQueries=true
    username: root
    password: root
uid:
  gen:
    boostPower: 3
    epochStr: '2022-11-11'
    scheduleInterval: 60
    seqBits: 11
    timeBits: 32
    workerBits: 20 
server:
  tomcat:
    threads:
      max: 200
      min-spare: 100
    accept-count: 100
```
3.   获取id
```java
#启动类无需添加
#@ComponentScan(basePackages = {"io.github.wujun728.uidgenerator","com.jun.plugin"})
#使用
@Resource
private UidGenerator uidGenerator;

Long uid=uidGenerator.getUID()

```
 
