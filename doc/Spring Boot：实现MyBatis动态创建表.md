# [Spring Boot：实现MyBatis动态创建表](https://www.cnblogs.com/xifengxiaoma/p/11041509.html)

## 综合概述

在有些应用场景中，我们会有需要动态创建和操作表的需求。比如因为单表数据存储量太大而采取分表存储的情况，又或者是按日期生成日志表存储系统日志等等。这个时候就需要我们动态的生成和操作数据库表了。而我们都知道，以往我们使用MyBatis是需要提前生成包括Model，Mapper和XML映射文件的，显然因为动态生成和操作表的需求一开始表都是不存在的，所以也就不能直接通过MyBatis连接数据库来生成我们的数据访问层代码并用来访问数据库了。还好MyBatis提供了动态SQL，我们可以通过动态SQL，传入表名等信息然组装成建表和操作语句，接下来，我们就通过一个具体的案例来了解一下。

## 实现案例

先说一下我们要实现的案例，本案例中每个用户都会有一个自己日志表，其中的user_log_config表就是用户名和对应的日志表配置，每次往这个表添加配置的时候，系统就会根据配置信息给该用户生成一个日志存储表，表名是获取日志配置表里配置的表名称，并统一提供对这个日志表的操作接口。本教程案例基于 Spring Boot + Mybatis + MySQL 实现。

### 生成项目模板

为方便我们初始化项目，Spring Boot给我们提供一个项目模板生成网站。

\1. 打开浏览器，访问：https://start.spring.io/

\2. 根据页面提示，选择构建工具，开发语言，项目信息等。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190617165830105-262774991.png)

\3. 点击 Generate the project，生成项目模板，生成之后会将压缩包下载到本地。

\4. 使用IDE导入项目，我这里使用Eclipse，通过导入Maven项目的方式导入。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190617165853787-1674725983.png)

### 创建数据库表

这里使用MySQL数据库，版本是8.0.16，在项目根目录下新建db目录，然后在其中编写一个数据库脚本文件。

在MySQL数据库新建一个springboot数据库，然后在此数据库中执行下面的脚本创建项目相关的表。

脚本文件

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190617172252860-864938279.png)

SQL脚本内容，注意，这里的user_log并不需要用到，事实上，user_log就是我们要生成的表结构，但为了一会儿MyBatis代码的生成，先创建一下，具体后续会讲到。

springboot.sql

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
-- ----------------------------
-- Table structure for user_log_config
-- ----------------------------
DROP TABLE IF EXISTS `user_log_config`;
CREATE TABLE `user_log_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(50) NOT NULL COMMENT '用户名',
  `table_name` varchar(150) DEFAULT NULL COMMENT '用户对应的日志存储表',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='用户日志表配置';
-- ----------------------------
-- Table structure for user_log
-- ----------------------------
DROP TABLE IF EXISTS `user_log`;
CREATE TABLE `user_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2897 DEFAULT CHARSET=utf8 COMMENT='用户操作日志';
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

### 添加相关依赖

需要添加Spring Boot，Mybatis，MySQL，Swagger相关依赖。Swagger方便用来测试接口。

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
        <!-- mybatis -->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.0.0</version>
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

### 添加配置类

\1. 添加swagger 配置

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

2.添加MyBatis配置

添加MyBatis配置类，配置相关扫描路径，包括DAO，Model，XML映射文件的扫描。

在config包下新建一个MyBatis配置类，MybatisConfig.java。

MybatisConfig.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.config;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan("com.louis.springboot.**.dao")    // 扫描DAO
public class MybatisConfig {
  @Autowired
  private DataSource dataSource;

  @Bean
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    sessionFactory.setDataSource(dataSource);
    sessionFactory.setTypeAliasesPackage("com.louis.springboot.**.model");    // 扫描Model
    
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    sessionFactory.setMapperLocations(resolver.getResources("classpath*:**/sqlmap/*.xml"));    // 扫描映射文件
    
    return sessionFactory.getObject();
  }
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

3.添加数据源配置

将application.properties文件改名为application.yml ，并在其中添加MySQL数据源连接信息。

注意：

这里需要首先创建一个MySQL数据库，并输入自己的用户名和密码。这里的数据库是springboot。

另外，如果你使用的是MySQL 5.x及以前版本，驱动配置driverClassName是com.mysql.jdbc.Driver。

application.yml 

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

### 生成MyBatis模块

由于手动编写MyBatis的Model、DAO、XML映射文件比较繁琐，通常都会通过一些生成工具来生成。MyBatis官方也提供了生成工具（MyBaits Generator），另外还有一些基于官方基础上改进的第三方工具，比如MyBatis Plus就是国内提供的一款非常优秀的开源工具，网上相关教程比较多，这里就不再赘述了。

这里提供一些资料作为参考。

Mybatis Generator 官网：http://www.mybatis.org/generator/index.html

Mybatis Generator 教程：https://blog.csdn.net/testcs_dn/article/details/77881776

MyBatis Plus 官网: http://mp.baomidou.com/#/

MyBatis Plus 官网: http://mp.baomidou.com/#/quick-start

代码生成好之后，分別将MODEL、DAO、XML映射文件拷贝到相应的包里。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190617174028962-1758476314.png)

生成的Model，如下是user_log_config表对应的Model对象UserLogConfig。

UserLogConfig.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.model;

public class UserLogConfig {
    private Long id;

    private String name;

    private String tableName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

打开Mapper，我们看到MyBatis Generator给我们默认生成了一些增删改查的方法，另外添加一个查询全部的方法。

UserLogConfigMapper.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.dao;

import java.util.List;

import com.louis.springboot.demo.model.UserLogConfig;

public interface UserLogConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserLogConfig record);

    int insertSelective(UserLogConfig record);

    UserLogConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserLogConfig record);

    int updateByPrimaryKey(UserLogConfig record);
    
    public List<UserLogConfig> selectAll();
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

在UserLogConfigMapper.xml中编写selectAll的SQL语句。

UserLogConfigMapper.xml

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.louis.springboot.demo.dao.UserLogConfigMapper">
  <resultMap id="BaseResultMap" type="com.louis.springboot.demo.model.UserLogConfig">
    <id column="id" jdbcType="BIGINT" property="id" />
    <result column="name" jdbcType="VARCHAR" property="name" />
    <result column="table_name" jdbcType="VARCHAR" property="tableName" />
  </resultMap>
  <sql id="Base_Column_List">
    id, name, table_name
  </sql>
  <select id="selectByPrimaryKey" parameterType="java.lang.Long" resultMap="BaseResultMap">
    select 
    <include refid="Base_Column_List" />
    from user_log_config
    where id = #{id,jdbcType=BIGINT}
  </select>
  <delete id="deleteByPrimaryKey" parameterType="java.lang.Long">
    delete from user_log_config
    where id = #{id,jdbcType=BIGINT}
  </delete>
  <insert id="insert" parameterType="com.louis.springboot.demo.model.UserLogConfig">
    insert into user_log_config (id, name, table_name
      )
    values (#{id,jdbcType=BIGINT}, #{name,jdbcType=VARCHAR}, #{tableName,jdbcType=VARCHAR}
      )
  </insert>
  <insert id="insertSelective" parameterType="com.louis.springboot.demo.model.UserLogConfig">
    insert into user_log_config
    <trim prefix="(" suffix=")" suffixOverrides=",">
      <if test="id != null">
        id,
      </if>
      <if test="name != null">
        name,
      </if>
      <if test="tableName != null">
        table_name,
      </if>
    </trim>
    <trim prefix="values (" suffix=")" suffixOverrides=",">
      <if test="id != null">
        #{id,jdbcType=BIGINT},
      </if>
      <if test="name != null">
        #{name,jdbcType=VARCHAR},
      </if>
      <if test="tableName != null">
        #{tableName,jdbcType=VARCHAR},
      </if>
    </trim>
  </insert>
  <update id="updateByPrimaryKeySelective" parameterType="com.louis.springboot.demo.model.UserLogConfig">
    update user_log_config
    <set>
      <if test="name != null">
        name = #{name,jdbcType=VARCHAR},
      </if>
      <if test="tableName != null">
        table_name = #{tableName,jdbcType=VARCHAR},
      </if>
    </set>
    where id = #{id,jdbcType=BIGINT}
  </update>
  <update id="updateByPrimaryKey" parameterType="com.louis.springboot.demo.model.UserLogConfig">
    update user_log_config
    set name = #{name,jdbcType=VARCHAR},
      table_name = #{tableName,jdbcType=VARCHAR}
    where id = #{id,jdbcType=BIGINT}
  </update>
  <select id="selectAll" resultMap="BaseResultMap">
    select 
    <include refid="Base_Column_List" />
    from user_log_config
  </select>
</mapper>
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

编写UserLogConfig的服务接口。

UserLogConfigService.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.service;
import java.util.List;

import com.louis.springboot.demo.model.UserLogConfig;

public interface UserLogConfigService {

    /**
     * 保存用户日志配置
     * @return
     */
    void save(UserLogConfig userLogConfig);
    
    /**
     * 查找全部用户日志配置
     * @return
     */
    List<UserLogConfig> findAll();

}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

编写UserLogConfig的服务实现类。

UserLogConfigServiceImpl.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.louis.springboot.demo.dao.UserLogConfigMapper;
import com.louis.springboot.demo.model.UserLogConfig;
import com.louis.springboot.demo.service.UserLogConfigService;

@Service
public class UserLogConfigServiceImpl implements UserLogConfigService {
    
    @Autowired
    private UserLogConfigMapper userLogConfigMapper;
    
    @Override
    public void save(UserLogConfig userLogConfig) {
        if(userLogConfig.getId() != null && !"".equals(userLogConfig.getId())) {
            // 更新
            userLogConfigMapper.updateByPrimaryKeySelective(userLogConfig);
        } else {
            // 插入
            userLogConfigMapper.insertSelective(userLogConfig);
        }
    }
    
    @Override
    public List<UserLogConfig> findAll() {
        return userLogConfigMapper.selectAll();
    }
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

编写UserLogConfig的控制器。

UserLogConfigController.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.louis.springboot.demo.model.UserLogConfig;
import com.louis.springboot.demo.service.UserLogConfigService;

@RestController
@RequestMapping("user/log/config")
public class UserLogConfigController {

    @Autowired
    private UserLogConfigService userLogConfigService;
    
    @PostMapping(value="/save")
    public Object save(@RequestBody UserLogConfig userLogConfig) {
        userLogConfigService.save(userLogConfig);
        return 1;
    }
    
    @GetMapping(value="/findAll")
    public Object findAll() {
        return userLogConfigService.findAll();
    }
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

现在我们来讲解如何动态实现动态生成用户日志存储表（user_log的表结构），之前我们通过MyBatis生成了user_log的服务访问层代码，下面是Model类UserLog，你可以直接用或改个名称都行，我们这里就不改了。

UserLog.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.model;

public class UserLog {
    private Long id;

    private String userName;

    private String operation;

    private String method;

    private String params;

    private Long time;

    private String ip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

修改UserLogMapper，将原有的接口都加上tableName参数，传入表名以确定要操作的表。另外额外添加了三个跟建表有关的方法。

UserLogMapper.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.louis.springboot.demo.model.UserLog;

public interface UserLogMapper {
    int deleteByPrimaryKey(@Param("tableName")String tableName, @Param("id")Long id);

    int insert(@Param("tableName")String tableName, @Param("userLog") UserLog userLog);

    int insertSelective(@Param("tableName")String tableName, @Param("userLog") UserLog record);

    UserLog selectByPrimaryKey(@Param("tableName")String tableName, @Param("id")Long id);

    int updateByPrimaryKeySelective(@Param("tableName")String tableName, @Param("userLog") UserLog record);

    int updateByPrimaryKey(@Param("tableName")String tableName, @Param("userLog") UserLog record);
    
    /**
     * 查找全部
     * @param tableName
     * @return
     */
    List<UserLog> selectAll(@Param("tableName")String tableName);
    
    /**
     * 是否存在表
     * @param tableName
     * @return
     */
    int existTable(@Param("tableName")String tableName);
    /**
     * 删除表
     * @param tableName
     * @return
     */
    int dropTable(@Param("tableName")String tableName);
    /**
     * 创建表
     * @param tableName
     * @return
     */
    int createTable(@Param("tableName")String tableName);
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

修改UserLogMapper.xml，将原来用表名user_log的地方替换成${tableName}，表示表名由外部方法传入。另外编写另外三个建表相关的语句，检查表是否存在和删除表的语句比较简单，创建表的只要把建表语句拷贝过来，然后把表名替换成${tableName}就行了。

UserLogMapper.xml

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.louis.springboot.demo.dao.UserLogMapper">
  <resultMap id="BaseResultMap" type="com.louis.springboot.demo.model.UserLog">
    <id column="id" jdbcType="BIGINT" property="id" />
    <result column="user_name" jdbcType="VARCHAR" property="userName" />
    <result column="operation" jdbcType="VARCHAR" property="operation" />
    <result column="method" jdbcType="VARCHAR" property="method" />
    <result column="params" jdbcType="VARCHAR" property="params" />
    <result column="time" jdbcType="BIGINT" property="time" />
    <result column="ip" jdbcType="VARCHAR" property="ip" />
  </resultMap>
  <sql id="Base_Column_List">
    id, user_name, operation, method, params, time, ip
  </sql>
  <select id="selectByPrimaryKey" parameterType="java.lang.Long" resultMap="BaseResultMap">
    select 
    <include refid="Base_Column_List" />
    from ${tableName}
    where id = #{id,jdbcType=BIGINT}
  </select>
  <delete id="deleteByPrimaryKey" parameterType="java.lang.Long">
    delete from ${tableName}
    where id = #{id,jdbcType=BIGINT}
  </delete>
  <insert id="insert" parameterType="com.louis.springboot.demo.model.UserLog">
    insert into ${tableName} (id, user_name, operation, 
      method, params, time, 
      ip)
    values (#{id,jdbcType=BIGINT}, #{userName,jdbcType=VARCHAR}, #{operation,jdbcType=VARCHAR}, 
      #{method,jdbcType=VARCHAR}, #{params,jdbcType=VARCHAR}, #{time,jdbcType=BIGINT}, 
      #{ip,jdbcType=VARCHAR})
  </insert>
  <insert id="insertSelective" parameterType="com.louis.springboot.demo.model.UserLog">
    insert into ${tableName}
    <trim prefix="(" suffix=")" suffixOverrides=",">
      <if test="id != null">
        id,
      </if>
      <if test="userName != null">
        user_name,
      </if>
      <if test="operation != null">
        operation,
      </if>
      <if test="method != null">
        method,
      </if>
      <if test="params != null">
        params,
      </if>
      <if test="time != null">
        time,
      </if>
      <if test="ip != null">
        ip,
      </if>
    </trim>
    <trim prefix="values (" suffix=")" suffixOverrides=",">
      <if test="id != null">
        #{id,jdbcType=BIGINT},
      </if>
      <if test="userName != null">
        #{userName,jdbcType=VARCHAR},
      </if>
      <if test="operation != null">
        #{operation,jdbcType=VARCHAR},
      </if>
      <if test="method != null">
        #{method,jdbcType=VARCHAR},
      </if>
      <if test="params != null">
        #{params,jdbcType=VARCHAR},
      </if>
      <if test="time != null">
        #{time,jdbcType=BIGINT},
      </if>
      <if test="ip != null">
        #{ip,jdbcType=VARCHAR},
      </if>
    </trim>
  </insert>
  <update id="updateByPrimaryKeySelective" parameterType="com.louis.springboot.demo.model.UserLog">
    update ${tableName}
    <set>
      <if test="userName != null">
        user_name = #{userName,jdbcType=VARCHAR},
      </if>
      <if test="operation != null">
        operation = #{operation,jdbcType=VARCHAR},
      </if>
      <if test="method != null">
        method = #{method,jdbcType=VARCHAR},
      </if>
      <if test="params != null">
        params = #{params,jdbcType=VARCHAR},
      </if>
      <if test="time != null">
        time = #{time,jdbcType=BIGINT},
      </if>
      <if test="ip != null">
        ip = #{ip,jdbcType=VARCHAR},
      </if>
    </set>
    where id = #{id,jdbcType=BIGINT}
  </update>
  <update id="updateByPrimaryKey" parameterType="com.louis.springboot.demo.model.UserLog">
    update ${tableName}
    set user_name = #{userName,jdbcType=VARCHAR},
      operation = #{operation,jdbcType=VARCHAR},
      method = #{method,jdbcType=VARCHAR},
      params = #{params,jdbcType=VARCHAR},
      time = #{time,jdbcType=BIGINT},
      ip = #{ip,jdbcType=VARCHAR}
    where id = #{id,jdbcType=BIGINT}
  </update>
  
  <select id="selectAll" resultMap="BaseResultMap">
    select 
    <include refid="Base_Column_List" />
    from ${tableName}
  </select>
  
  <select id="existTable" parameterType="String" resultType="Integer">  
    select count(*)  
    from information_schema.TABLES  
    where table_name=#{tableName} 
  </select>
  
  <update id="dropTable">  
    DROP TABLE IF EXISTS ${tableName} 
  </update>  
  
  <update id="createTable" parameterType="String">  
    CREATE TABLE ${tableName} (
      `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
      `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
      `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
      `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
      `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
      `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
      `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=2897 DEFAULT CHARSET=utf8 COMMENT='用户操作日志';
  </update> 
</mapper>
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

编写用户日志的服务接口，包含一个保存方法和一个查询方法。

UserLogService.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.service;
import java.util.List;

import com.louis.springboot.demo.model.UserLog;

public interface UserLogService {

    /**
     * 保存用户日志
     * @return
     */
    void save(String tableName, UserLog userLog);
    
    /**
     * 查找全部用户日志
     * @return
     */
    List<UserLog> findAll(String tableName);

}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

编写用户日志的服务实现类，包含保存方法和查询方法的实现。

UserLogServiceImpl.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.louis.springboot.demo.dao.UserLogMapper;
import com.louis.springboot.demo.model.UserLog;
import com.louis.springboot.demo.service.UserLogService;

@Service
public class UserLogServiceImpl implements UserLogService {
    
    @Autowired
    private UserLogMapper userLogMapper;
    
    @Override
    public void save(String tableName, UserLog userLog) {
        // 插入
        userLogMapper.insertSelective(tableName, userLog);
    }
    
    @Override
    public List<UserLog> findAll(String tableName) {
        return userLogMapper.selectAll(tableName);
    }
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

为了接口传表名方便，我们这里在UserLog类里加入一个tableName属性，用来给接口传入表名。

UserLog.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.model;

public class UserLog {
    private Long id;

    private String userName;

    private String operation;

    private String method;

    private String params;

    private Long time;

    private String ip;
    
    private String tableName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

编写服务控制器UserLogController并调用服务的相关接口。

UserLogController.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.louis.springboot.demo.model.UserLog;
import com.louis.springboot.demo.service.UserLogService;

@RestController
@RequestMapping("user/log")
public class UserLogController {

    @Autowired
    private UserLogService userLogService;
    
    @PostMapping(value="/save")
    public Object save(@RequestBody UserLog userLog) {
        String tableName = userLog.getTableName();
        userLogService.save(tableName, userLog);
        return 1;
    }
    
    @GetMapping(value="/findAll")
    public Object findAll(String tableName) {
        return userLogService.findAll(tableName);
    }
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

修改UserLogConfigServiceImpl的save方法，实现在添加配置的时候生成对应的表。

UserLogConfigServiceImpl.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.louis.springboot.demo.dao.UserLogConfigMapper;
import com.louis.springboot.demo.dao.UserLogMapper;
import com.louis.springboot.demo.model.UserLogConfig;
import com.louis.springboot.demo.service.UserLogConfigService;

@Service
public class UserLogConfigServiceImpl implements UserLogConfigService {
    
    @Autowired
    private UserLogConfigMapper userLogConfigMapper;
    @Autowired
    private UserLogMapper userLogMapper;
    
    @Override
    public void save(UserLogConfig userLogConfig) {
        // 插入
        userLogConfigMapper.insertSelective(userLogConfig);
        // 添加配置时，创建日志存储表
        String tableName = userLogConfig.getTableName();
        if(userLogMapper.existTable(tableName) > 0) {
            userLogMapper.dropTable(tableName);
        }
        userLogMapper.createTable(tableName);
    }
    
    @Override
    public List<UserLogConfig> findAll() {
        return userLogConfigMapper.selectAll();
    }
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

 

### 编译测试运行

\1. 右键项目 -> Run as -> Maven install，开始执行Maven构建，第一次会下载Maven依赖，可能需要点时间，如果出现如下信息，就说明项目编译打包成功了。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190617184752311-528856142.png)

\2. 右键文件 DemoApplication.java -> Run as -> Java Application，开始启动应用，当出现如下信息的时候，就说明应用启动成功了，默认启动端口是8080。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190617184928116-1850883939.png)

\3. 打开浏览器，访问：http://localhost:8080/swagger-ui.html，进入swagger接口文档界面。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190617185057070-2034323920.png)

\4. 测试UserLogConfigMapper的save保存接口，输入以下参数进行测试。

```
{
  "id": 1,
  "name": "xiaoming",
  "tableName": "xiaoming"
}
```

成功之后调UserLogConfigMapper的findAll接口，可以看到配置信息已经成功插入。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190617185857963-1424474403.png)

并且我们可以通过MySQL客户端查看到，在配置生成的同时生成了一个表名为xiaoming的数据库表。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190617190309017-1777143878.png)

\5. 测试UserLogController的save保存接口，输入以下参数进行测试。

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
{
  "id": 1,
  "ip": "139.123.123.100",
  "method": "save",
  "operation": "save",
  "params": "string name",
  "tableName": "xiaoming",
  "time": 0,
  "userName": "xiaoming"
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

成功之后调UserLogController的findAll接口，可以看到配置信息已经成功插入。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190617191755611-305541391.png)

测试到此，我们成功的保存了配置信息，并且动态创建了一个表，然后成功的往表里插入了一点数据，并通过接口查询出了插入的数据。

 

## 参考资料

MyBatis 官网：http://www.mybatis.org/mybatis-3/zh/index.html

MyBatis Generator 官网：http://www.mybatis.org/generator/index.html

MyBatis Plus 官网: http://mp.baomidou.com/#/quick-start

## 相关导航

[Spring Boot 系列教程目录导航](https://www.cnblogs.com/xifengxiaoma/p/11116330.html)

[Spring Boot：快速入门教程](https://www.cnblogs.com/xifengxiaoma/p/11019240.html)

[Spring Boot：整合Swagger文档](https://www.cnblogs.com/xifengxiaoma/p/11022146.html)

[Spring Boot：整合MyBatis框架](https://www.cnblogs.com/xifengxiaoma/p/11024402.html)

[Spring Boot：实现MyBatis分页](https://www.cnblogs.com/xifengxiaoma/p/11027551.html)

## 源码下载

码云：https://gitee.com/liuge1988/spring-boot-demo.git