# [Spring Boot：整合JdbcTemplate](https://www.cnblogs.com/xifengxiaoma/p/11046099.html)

## 综合概述

Spring对数据库的操作在jdbc上面做了更深层次的封装，而JdbcTemplate便是Spring提供的一个操作数据库的便捷工具。我们可以借助JdbcTemplate来执行所有数据库操作，例如插入，更新，删除和从数据库中检索数据，并且有效避免直接使用jdbc带来的繁琐编码。

JdbcTemplate主要提供以下五种类型的方法：

- execute方法：可以用于执行任何SQL语句，一般用于执行DDL语句。
- update、batchUpdate方法：用于执行新增、修改、删除等语句。
- query方法及queryForXXX方法：用于执行查询相关的语句。
- call方法：用于执行数据库存储过程和函数相关的语句。

当然，在大部分情况下，我们都会直接使用更加强大的持久化框架来访问数据库，比如MyBatis、Hibernate或者Spring Data JPA，我们这里讲解JdbcTemplate的整合，只是告诉大家有这么一种操作数据库的方式。

## 实现案例

接下来，我们就以一个具体的例子来学习如何利用Spring的JdbcTemplate来进行数据库操作。

### 生成项目模板

为方便我们初始化项目，Spring Boot给我们提供一个项目模板生成网站。

\1. 打开浏览器，访问：https://start.spring.io/

\2. 根据页面提示，选择构建工具，开发语言，项目信息等。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190618151615905-1352802756.png)

\3. 点击 Generate the project，生成项目模板，生成之后会将压缩包下载到本地。

\4. 使用IDE导入项目，我这里使用Eclipse，通过导入Maven项目的方式导入。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190618151633158-1077199795.png)

### 创建数据库表

这里使用MySQL数据库，版本是8.0.16，在项目根目录下新建db目录，然后在其中编写一个数据库脚本文件。

在MySQL数据库新建一个springboot数据库，然后在此数据库中执行下面的提供的脚本创建项目的用户表。

脚本文件

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190618151657412-499998065.png)

SQL脚本内容

springboot.sql

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(50) NOT NULL COMMENT '用户名',
  `nick_name` varchar(150) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(150) DEFAULT NULL COMMENT '头像',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(40) DEFAULT NULL COMMENT '加密盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '机构ID',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `last_update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='用户管理';
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

### 添加相关依赖

添加项目需要的相关依赖，这里需要添加上WEB和Swagger和JDBC和MYSQL的依赖，Swagger的添加是为了方便接口测试。

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
        <!-- jdbc -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <!-- mysql -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
        <!-- 打包时拷贝MyBatis的映射文件 -->
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/sqlmap/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
            <resource>  
                <directory>src/main/resources</directory>  
                    <includes> 
                        <include>**/*.*</include>  
                    </includes> 
                    <filtering>true</filtering>  
            </resource> 
        </resources>
    </build>

</project>
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

### 添加相关配置

1.添加数据源配置

将application.properties文件改名为application.yml ，并在其中添加MySQL数据源连接信息。

注意：

这里需要首先创建一个MySQL数据库，并输入自己的用户名和密码。这里的数据库是springboot。

另外，如果你使用的是MySQL 5.x及以前版本，驱动配置driverClassName是com.mysql.jdbc.Driver。

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
server:
  port: 8080
spring:
  datasource:
    driverClassName: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/springboot?useUnicode=true&zeroDateTimeBehavior=convertToNull&autoReconnect=true&characterEncoding=utf-8
    username: root
    password: 123456
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

\2. 添加swagger 配置

添加一个swagger 配置类，在工程下新建 config 包并添加一个 SwaggerConfig 配置类。

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
                .title("SpringBoot API Doc")
                .description("This is a restful api document of Spring Boot.")
                .version("1.0")
                .build();
    }

}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

### 编写业务代码

新建model包并在其中编写model类SysUser。

SysUser.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.model;

import java.util.Date;

public class SysUser {
    private Long id;

    private String name;

    private String nickName;

    private String avatar;

    private String password;

    private String salt;

    private String email;

    private String mobile;

    private Byte status;

    private Long deptId;

    private String createBy;

    private Date createTime;

    private String lastUpdateBy;

    private Date lastUpdateTime;

    private Byte delFlag;

    // 省略setter和getter
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

新建dao包并在其中编写dao类SysUserDao，dao包含一些基础的操作方法。

SysUserDao.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.dao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.louis.springboot.demo.model.SysUser;

@Repository
public class SysUserDao {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * 保存用户
     * @param user
     */
    public void save(SysUser user) {
        String sql = "insert into sys_user(id, name, nick_name, password, email) values(?,?,?,?,?)";
        jdbcTemplate.update(sql, user.getId(), user.getName(), user.getNickName(), user.getPassword(), user.getEmail());
    }
    
    /**
     * 删除用户
     * @param user
     */
    public void delete(String id) {
        String sql = "delete from sys_user where id=?";
        jdbcTemplate.update(sql, id);
    }
    
    /**
     * 查询全部用户
     * @return
     */
    public List<SysUser> findAll() {
        String sql = "select * from sys_user";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(SysUser.class));
    }
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

新建service包并在其中编写服务接口SysUserService。

SysUserService.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.service;
import java.util.List;
import com.louis.springboot.demo.model.SysUser;

public interface SysUserService {

    /**
     * 保存用户
     * @param user
     */
    public void save(SysUser user);
    
    /**
     * 删除用户
     * @param id
     */
    public void delete(String id);
    
    /**
     * 查询全部用户
     * @return
     */
    public List<SysUser> findAll();

}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

新建service.impl包并在其中编写服务实现类SysUserServiceImpl并调用DAO实现相关方法。

SysUserServiceImpl.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.louis.springboot.demo.dao.SysUserDao;
import com.louis.springboot.demo.model.SysUser;
import com.louis.springboot.demo.service.SysUserService;

@Service
public class SysUserServiceImpl implements SysUserService {
    
    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public void save(SysUser user) {
        sysUserDao.save(user);
    }

    @Override
    public void delete(String id) {
        sysUserDao.delete(id);
    }

    @Override
    public List<SysUser> findAll() {
        return sysUserDao.findAll();
    }
    
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

新建controller包并在其中编写用户控制器SysUserController，并调用相关服务。

SysUserController.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.louis.springboot.demo.model.SysUser;
import com.louis.springboot.demo.service.SysUserService;

@RestController
@RequestMapping("user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    
    @PostMapping(value="/save")
    public Object save(@RequestBody SysUser user) {
        sysUserService.save(user);
        return 1;
    }
    
    @GetMapping(value="/delete")
    public Object delete(@RequestParam("id") String id) {
        sysUserService.delete(id);
        return 1;
    }
    
    @GetMapping(value="/findAll")
    public Object findAll() {
        return sysUserService.findAll();
    }
    
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

 

### 编译测试运行

\1. 右键项目 -> Run as -> Maven install，开始执行Maven构建，第一次会下载Maven依赖，可能需要点时间，如果出现如下信息，就说明项目编译打包成功了。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190618155103152-1373082597.png)

\2. 右键文件 DemoApplication.java -> Run as -> Java Application，开始启动应用，当出现如下信息的时候，就说明应用启动成功了，默认启动端口是8080。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190618155124821-739024769.png)

\3. 打开浏览器，访问：http://localhost:8080/swagger-ui.html，进入swagger接口文档界面。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190618155852528-1308267543.png)

\4. 首先执行以下findAll接口，此时因为没有数据，所以没有返回记录。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190618160312790-365770766.png)

接着执行两次save方法，分别保存下面两条记录。

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
{
  "id": 1,
  "name": "iverson",
  "nickName": "ai",
  "password": "123",
  "email": "iverson@qq.com"
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
{
  "id": 2,
  "name": "kobe",
  "nickName": "kobe",
  "password": "123",
  "email": "kobe@qq.com"
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

然后再次执行findAll接口，这时我们可以看到，成功返回了我们上面插入的两条记录。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190618163502760-1917658644.png)

接着执行一次delete方法，输入id为1，删除插入的第一条记录。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190618163818053-310740592.png)

然后再次执行findAll接口，这时我们可以看到，我们插入的第一条记录已经成功被删除。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190618163845044-1596284547.png)

 

## 相关导航

[Spring Boot 系列教程目录导航](https://www.cnblogs.com/xifengxiaoma/p/11116330.html)

[Spring Boot：快速入门教程](https://www.cnblogs.com/xifengxiaoma/p/11019240.html)

[Spring Boot：整合Swagger文档](https://www.cnblogs.com/xifengxiaoma/p/11022146.html)

[Spring Boot：整合MyBatis框架](https://www.cnblogs.com/xifengxiaoma/p/11024402.html)

[Spring Boot：实现MyBatis分页](https://www.cnblogs.com/xifengxiaoma/p/11027551.html)

## 源码下载

码云：https://gitee.com/liuge1988/spring-boot-demo.git