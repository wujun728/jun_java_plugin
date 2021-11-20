# Spring Cloud Config采用数据库存储配置内容【Edgware+】

**原创**

 [2018-06-11](https://blog.didispace.com/spring-cloud-starter-edgware-3-1/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

在之前的[《Spring Cloud构建微服务架构：分布式配置中心》](http://blog.didispace.com/spring-cloud-starter-dalston-3/)一文中，我们介绍的Spring Cloud Server配置中心采用了Git的方式进行配置信息存储。这一设计巧妙的利用Git自身机制以及其他具有丰富功能的Git服务端产品，让Spring Cloud Server在配置存储和管理的上避开了很多与管理相关的复杂实现，使其具备了配置中心存储配置和读取配置的基本能力；而更上层的管理机制，由于不具备普遍适用性，所以Spring Cloud Server并没有自己去实现这部分内容，而是通过Git服务端产品来提供一部分实现，如果还需要更复杂的功能也能自己实现与定义。即便如此，对于Spring Cloud Server默认使用Git来存储配置的方案一直以来还是饱受争议。所以，本文将介绍一下Spring Cloud Config从Edgware版本开始新增的一种配置方式：采用数据库存储配置信息。

## 构建配置中心服务端

第一步：创建一个基础的Spring Boot项目，在pom.xml中引入几个主要依赖：

- `spring-cloud-config-server`：配置中心的基础依赖
- `spring-boot-starter-jdbc`：由于需要访问数据库，所以需要加载jdbc的依赖
- `mysql-connector-java`：MySQL数据库的连接包
- `flyway-core`：该内容非强制，主要用来管理schema（如果您不了解可以看一下[这篇文章](http://blog.didispace.com/spring-boot-flyway-db-version/)）

```
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>1.5.11.RELEASE</version>
    <relativePath/>
</parent>

<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-config-server</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>
    <dependency>
        <groupId>org.flywaydb</groupId>
        <artifactId>flyway-core</artifactId>
        <version>5.0.3</version>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.21</version>
    </dependency>
</dependencies>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>Edgware.SR3</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

第二步：准备schema创建文件。在`resources`下创建`schema`目录，并加入`V1__Base_version.sql`文件，具体内容如下：

```
CREATE TABLE `properties` (
  `id` int(11) NOT NULL,
  `key` varchar(50) NOT NULL,
  `value` varchar(500) NOT NULL,
  `application` varchar(50) NOT NULL,
  `profile` varchar(50) NOT NULL,
  `label` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

> 该脚本会在程序运行时由flyway自动执行

第三步：创建应用主类，具体如下：

```
@EnableConfigServer
@SpringBootApplication
public class ConfigServerBootstrap {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ConfigServerBootstrap.class);

        // 测试用数据，仅用于本文测试使用
        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
        jdbcTemplate.execute("delete from properties");
        jdbcTemplate.execute("INSERT INTO properties VALUES(1, 'com.didispace.message', 'test-stage-master', 'config-client', 'stage', 'master')");
        jdbcTemplate.execute("INSERT INTO properties VALUES(2, 'com.didispace.message', 'test-online-master', 'config-client', 'online', 'master')");
        jdbcTemplate.execute("INSERT INTO properties VALUES(3, 'com.didispace.message', 'test-online-develop', 'config-client', 'online', 'develop')");
        jdbcTemplate.execute("INSERT INTO properties VALUES(4, 'com.didispace.message', 'hello-online-master', 'hello-service', 'online', 'master')");
        jdbcTemplate.execute("INSERT INTO properties VALUES(5, 'com.didispace.message', 'hello-online-develop', 'hello-service', 'online', 'develop')");
    }

}
```

> 这里增加了一些测试用数据，以便于后续的配置读取验证。

第四步：配置`application.properties`，具体内容如下：

```
spring.application.name=config-server-db
server.port=10020

spring.profiles.active=jdbc

spring.cloud.config.server.jdbc.sql=SELECT `KEY`, `VALUE` from PROPERTIES where APPLICATION=? and PROFILE=? and LABEL=?

spring.datasource.url=jdbc:mysql://localhost:3306/config-server-db
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.jdbc.Driver

flyway.locations=/schema
```

这里主要涉及几个配置：

- `spring.profiles.active=jdbc`：必须设置，将配置中心的存储实现切换到jdbc的方式
- `spring.cloud.config.server.jdbc.sql`：非必须，这里由于采用mysql数据源，`key`、`value`是保留关键词，原生的实现语句会报错，所以需要重写一下这句查询语句（如果存储的表结构设计不同于上面准备的内容，也可以通过这个属性的配置来修改配置的获取逻辑）
- `spring.datasource.*`：存储配置信息的数据源配置，这里采用mysql，开发者根据自己实际情况修改
- `flyway.locations`：flyway加载schema创建sql的位置

## 服务端配置验证

完成了上一节内容之后，我们就已经构建一个通过数据酷来存储配置内容的配置中心了，下面我们可以通过配置中心暴露的端点来尝试读取配置。

第一步：先将上面构建的配置中心启动起来。

第二步：验证配置信息获取：

- `curl http://localhost:10020/config-client/stage/`，获取信息`config-client`服务`stage`环境的配置内容，根据上面的数据准备，我们会获得如下返回内容：

```
{
    "name": "config-client",
    "profiles": [
    "stage"
    ],
    "label": null,
    "version": null,
    "state": null,
    "propertySources": [
        {
            "name": "config-client-stage",
            "source": {
            "com.didispace.message": "test-stage-master"
            }
        }
    ]
}
```

- `curl http://localhost:10020/hello-service/stage/develop`，获取信息`hello-service`服务，`stage`环境，`develop`标签的配置内容，根据上面的数据准备，我们会获得如下返回内容：

```
{
    "name": "hello-service",
    "profiles": [
        "online"
    ],
    "label": "develop",
    "version": null,
    "state": null,
    "propertySources": [
        {
            "name": "hello-service-online",
            "source": {
                "com.didispace.message": "hello-online-develop"
            }
        }
    ]
}
```

关于如何访问Spring Cloud Config构建配置中心获取配置信息的详细内容
，可以查看前文：[《Spring Cloud构建微服务架构：分布式配置中心》](http://blog.didispace.com/spring-cloud-starter-dalston-3/)，本文不做详细介绍。

## 总结

本文主要具体介绍了在Spring Cloud Config在Edgware版本开始新增的JDBC存储的使用思路，具体使用实际上还有很多可以优化的空间，比如：索引的优化、查询语句的优化；如果还需要进一步定制管理，对于表结构的优化也是很有必要的。

最后，安利一个基于**Spring Cloud Config的配置管理项目：https://github.com/dyc87112/spring-cloud-config-admin**，正在紧锣密鼓的开发中，尽情期待！

## 本文示例

读者可以根据喜好选择下面的两个仓库中查看`config-server-db`和`config-client`两个项目：

- [Github：https://github.com/dyc87112/SpringCloud-Learning/](https://github.com/dyc87112/SpringCloud-Learning/tree/master/3-Edgware)
- [Gitee：https://gitee.com/didispace/SpringCloud-Learning/](https://gitee.com/didispace/SpringCloud-Learning/tree/master/3-Edgware)