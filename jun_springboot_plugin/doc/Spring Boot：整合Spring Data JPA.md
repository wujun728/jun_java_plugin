# [Spring Boot：整合Spring Data JPA](https://www.cnblogs.com/xifengxiaoma/p/11047049.html)

## 综合概述

JPA是Java Persistence API的简称，是一套Sun官方提出的Java持久化规范。其设计目标主要是为了简化现有的持久化开发工作和整合ORM技术，它为Java开发人员提供了一种ORM工具来管理Java应用中的关系数据。 简而言之，JPA提供了使用面向对象的方式操作数据库的功能。JPA充分吸收了现有Hibernate，TopLink，JDO等ORM框架的优势，具有易于使用、伸缩性强等优点。

Spring Data JPA是Spring基于Spring Data框架对于JPA规范的一套具体实现方案，使用Spring Data JPA可以极大地简化JPA 的写法，几乎可以在不写具体实现的情况下完成对数据库的操作，并且除了基础的CRUD操作外，Spring Data JPA还提供了诸如分页和排序等常用功能的实现方案。合理的使用Spring Data JPA可以极大的提高我们的日常开发效率和有效的降低项目开发成本。

## 实现案例

接下来，我们就通过实际案例来讲解Spring Data JPA的整合，以及提供JPA相关操作的一些示例。

### 生成项目模板

为方便我们初始化项目，Spring Boot给我们提供一个项目模板生成网站。

\1. 打开浏览器，访问：https://start.spring.io/

\2. 根据页面提示，选择构建工具，开发语言，项目信息等。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190618172409100-1056934082.png)

\3. 点击 Generate the project，生成项目模板，生成之后会将压缩包下载到本地。

\4. 使用IDE导入项目，我这里使用Eclipse，通过导入Maven项目的方式导入。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190618172426244-1427818911.png)

 

### 添加相关依赖

清理掉不需要的测试类及测试依赖，添加 Maven 相关依赖，这里需要添加上WEB和Swagger和JPA的依赖，Swagger的添加是为了方便接口测试。

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
        <!-- jpa -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <!-- mysql -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
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
  jpa:
    show-sql: true # 默认false，在日志里显示执行的sql语句
    database: mysql
    hibernate.ddl-auto: update #指定为update，每次启动项目检测表结构有变化的时候会新增字段，表不存在时会新建，如果指定create，则每次启动项目都会清空数据并删除表，再新建
    properties.hibernate.dialect: org.hibernate.dialect.MySQL5Dialect
    database-platform: org.hibernate.dialect.MySQL5Dialect
    hibernate:
      naming:
        implicit-strategy: org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl #指定jpa的自动表生成策略，驼峰自动映射为下划线格式
        #physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

\2. 添加swagger 配置

添加一个swagger 配置类，在工程下新建 config 包并添加一个 SwaggerConfig 配置类。

SwaggerConfig.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

![复制代码](https://common.cnblogs.com/images/copycode.gif)

```
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

首先，编写一个实体类，并添加相关注解，具体注解说明参见代码。

SysUser.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity    // @Entity: 实体类, 必须
// @Table: 对应数据库中的表, 必须, name=表名, Indexes是声明表里的索引, columnList是索引的列, 同时声明此索引列是否唯一, 默认false
@Table(name = "sys_user", indexes = {@Index(name = "id", columnList = "id", unique = true), @Index(name = "name", columnList = "name", unique = true)})
public class SysUser {
    
    @Id // @Id: 指明id列, 必须
    @GeneratedValue(strategy = GenerationType.IDENTITY) // @GeneratedValue： 表明是否自动生成, 必须, strategy也是必写, 指明主键生成策略, 默认是Oracle
    private Long id;
  
    @Column(name = "name", nullable = false) // @Column： 对应数据库列名,可选, nullable 是否可以为空, 默认true
    private String name; 

    private String password;

    private String email;

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
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

然后，编写一个SysUserDao并继承JpaRepository，由此我们已经继承了大部分可用的CURD操作，针对基础操作，DAO完全不用写任何方法。

SysUserDao.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.louis.springboot.demo.model.SysUser;

public interface SysUserDao extends JpaRepository<SysUser, Long>, Serializable {
    
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

使用Spring Data JPA，可以通过两种方式使用 JPA 进行数据持久化。

方式一：使用Spring Data JPA 提供的接口默认实现，如上面我们的DAO实现。

方式二：自定义符合Spring Data JPA规则的查询方法，由框架将其自动解析为SQL。

Spring Data JPA提供了一些实现了基本的数据库操作的接口类，这些接口和类的关系如下。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190618191527312-1017211224.png)

其中CrudRepository是顶层CURD接口，提供了一些简单的增删查改功能，接口定义如下。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190618191737253-1891400393.png)

CrudRepository.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package org.springframework.data.repository;

import java.util.Optional;

/**
 * Interface for generic CRUD operations on a repository for a specific type.
 * @author Oliver Gierke
 * @author Eberhard Wolff
 */
@NoRepositoryBean
public interface CrudRepository<T, ID> extends Repository<T, ID> {

    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation might have changed the
     * entity instance completely.
     *
     * @param entity must not be {@literal null}.
     * @return the saved entity will never be {@literal null}.
     */
    <S extends T> S save(S entity);

    /**
     * Saves all given entities.
     *
     * @param entities must not be {@literal null}.
     * @return the saved entities will never be {@literal null}.
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    <S extends T> Iterable<S> saveAll(Iterable<S> entities);

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    Optional<T> findById(ID id);

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    boolean existsById(ID id);

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    Iterable<T> findAll();

    /**
     * Returns all instances of the type with the given IDs.
     *
     * @param ids
     * @return
     */
    Iterable<T> findAllById(Iterable<ID> ids);

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities
     */
    long count();

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    void deleteById(ID id);

    /**
     * Deletes a given entity.
     *
     * @param entity
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    void delete(T entity);

    /**
     * Deletes the given entities.
     *
     * @param entities
     * @throws IllegalArgumentException in case the given {@link Iterable} is {@literal null}.
     */
    void deleteAll(Iterable<? extends T> entities);

    /**
     * Deletes all entities managed by the repository.
     */
    void deleteAll();
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

PagingAndSortingRepository在继承了CrudRepository基础上实现了排序和分页的方法。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190618192215877-1675747407.png)

PagingAndSortingRepository.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package org.springframework.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Extension of {@link CrudRepository} to provide additional methods to retrieve entities using the pagination and
 * sorting abstraction.
 *
 * @author Oliver Gierke
 * @see Sort
 * @see Pageable
 * @see Page
 */
@NoRepositoryBean
public interface PagingAndSortingRepository<T, ID> extends CrudRepository<T, ID> {

    /**
     * Returns all entities sorted by the given options.
     *
     * @param sort
     * @return all entities sorted by the given options
     */
    Iterable<T> findAll(Sort sort);

    /**
     * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param pageable
     * @return a page of entities
     */
    Page<T> findAll(Pageable pageable);
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

JpaRepository又在继承PagingAndSortingRepository的基础上，同时继承了QueryByExampleExecutor接口，使其拥有了匹配指定样例查询的能力。 

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190618192618336-825294584.png)

JpaRepository.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package org.springframework.data.jpa.repository;

import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/**
 * JPA specific extension of {@link org.springframework.data.repository.Repository}.
 *
 * @author Oliver Gierke
 * @author Christoph Strobl
 * @author Mark Paluch
 */
@NoRepositoryBean
public interface JpaRepository<T, ID> extends PagingAndSortingRepository<T, ID>, QueryByExampleExecutor<T> {

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#findAll()
     */
    List<T> findAll();

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.PagingAndSortingRepository#findAll(org.springframework.data.domain.Sort)
     */
    List<T> findAll(Sort sort);

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#findAll(java.lang.Iterable)
     */
    List<T> findAllById(Iterable<ID> ids);

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#save(java.lang.Iterable)
     */
    <S extends T> List<S> saveAll(Iterable<S> entities);

    /**
     * Flushes all pending changes to the database.
     */
    void flush();

    /**
     * Saves an entity and flushes changes instantly.
     *
     * @param entity
     * @return the saved entity
     */
    <S extends T> S saveAndFlush(S entity);

    /**
     * Deletes the given entities in a batch which means it will create a single {@link Query}. Assume that we will clear
     * the {@link javax.persistence.EntityManager} after the call.
     *
     * @param entities
     */
    void deleteInBatch(Iterable<T> entities);

    /**
     * Deletes all entities in a batch call.
     */
    void deleteAllInBatch();

    /**
     * Returns a reference to the entity with the given identifier. Depending on how the JPA persistence provider is
     * implemented this is very likely to always return an instance and throw an
     * {@link javax.persistence.EntityNotFoundException} on first access. Some of them will reject invalid identifiers
     * immediately.
     *
     * @param id must not be {@literal null}.
     * @return a reference to the entity with the given identifier.
     * @see EntityManager#getReference(Class, Object) for details on when an exception is thrown.
     */
    T getOne(ID id);

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.query.QueryByExampleExecutor#findAll(org.springframework.data.domain.Example)
     */
    @Override
    <S extends T> List<S> findAll(Example<S> example);

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.query.QueryByExampleExecutor#findAll(org.springframework.data.domain.Example, org.springframework.data.domain.Sort)
     */
    @Override
    <S extends T> List<S> findAll(Example<S> example, Sort sort);
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

上面因为我们的SysUserDao直接继承了JpaRepository，所以上述所有的接口SysUserDao都是可以直接使用的，当然，除了可以直接使用默认提供的基础接口外，Spring Data JPA还允许我们自定义查询方法，对于符合以下命名规则的方法，Spring Data JPA能够根据其方法名为其自动生成SQL，除了使用示例中的 find 关键字，还支持的关键字有：query、get、read、count、delete等。 

只要按照以下命名规范的定义的方法，Spring Data JPA都能够帮我们自动生成SQL，无需自己实现。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190618193056781-1690498804.png)

接着编写一个服务接口，添加用户保存、删除、查询全部和分页查询的方法。

SysUserService.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.service;
import java.util.List;
import com.louis.springboot.demo.model.SysUser;
import com.louis.springboot.demo.util.PageQuery;

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
    public void delete(SysUser user);
    
    /**
     * 查询全部用户
     * @return
     */
    public List<SysUser> findAll();
    
    /**
     * 查询分页数据
     * @return
     */
    public Object findPage(PageQuery pageQuery);

}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

继续编写服务实现类并调用DAO实现相应功能，以下DAO方法都是继承而来的，除此之后，JPA还提供了大量的API可用。

SysUserServiceImpl.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.service.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.louis.springboot.demo.dao.SysUserDao;
import com.louis.springboot.demo.model.SysUser;
import com.louis.springboot.demo.service.SysUserService;
import com.louis.springboot.demo.util.PageQuery;

@Service
public class SysUserServiceImpl implements SysUserService {
    
    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public void save(SysUser user) {
        sysUserDao.save(user);
    }

    @Override
    public void delete(SysUser user) {
        sysUserDao.delete(user);
    }

    @Override
    public List<SysUser> findAll() {
        return sysUserDao.findAll();
    }

    @Override
    public Object findPage(PageQuery pageQuery) {
        return sysUserDao.findAll(PageRequest.of(pageQuery.getPage(), pageQuery.getSize()));
    }

}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

接着编写一个用户控制器，调用服务接口实现对应功能。

SysUserController.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.louis.springboot.demo.model.SysUser;
import com.louis.springboot.demo.service.SysUserService;
import com.louis.springboot.demo.util.PageQuery;

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
    
    @PostMapping(value="/delete")
    public Object delete(@RequestBody SysUser user) {
        sysUserService.delete(user);
        return 1;
    }
    
    @GetMapping(value="/findAll")
    public Object findAll() {
        return sysUserService.findAll();
    }
    
    @PostMapping(value="/findPage")
    public Object findPage(@RequestBody PageQuery pageQuery) {
        return sysUserService.findPage(pageQuery);
    }
    
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

上面对分页请求进行了简单的封装，主要包含查询页码和每页数量两个属性。

PageQuery.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.util;

public class PageQuery {

    private int page;
    private int size;
    
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

### 编译测试运行

\1. 右键项目 -> Run as -> Maven install，开始执行Maven构建，第一次会下载Maven依赖，可能需要点时间，如果出现如下信息，就说明项目编译打包成功了。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190618184111508-1154289443.png)

\2. 打开数据库，创建一个springboot数据库，然后右键文件 DemoApplication.java -> Run as -> Java Application，开始启动应用，如果一开始数据库没有对应的表，在应用启动时会创建，我们可以通过控制台查看到对应的SQL语句。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190618183152131-310822474.png)

\3. 打开浏览器，访问：http://localhost:8080/swagger-ui.html，进入swagger接口文档界面。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190618184419778-1198117534.png)

\4. 首先访问findAll接口，此时并没有数据，所以返回结果为空。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190618184600814-128201550.png)

然后调用save接口，分别插入以下三条数据。

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
{
  
  "id": 1,
  "name": "111",
  "email": "111@qq.com",
  "password": "111"
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
{
  
  "id": 2,
  "name": "222",
  "email": "222@qq.com",
  "password": "222"
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
{
  
  "id": 3,
  "name": "333",
  "email": "333@qq.com",
  "password": "333"
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

接着回来继续调用findAll接口，可以看到我们已经成功的插入了三条数据。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190618184917235-1807654552.png)

接着测试分页查询接口findPage，输入{ "page": 0, "size": 2 }，标识查询第一页，每页显示两条记录，下面返回正确的分页查询数据。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190618185107027-721962541.png)

最后我们测试一下删除接口delete，删除掉id为1的数据，再次调用findAll接口，我们发现目标记录已经成功被删除。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190618185444390-1489003153.png)

 

## 参考资料

项目主页：https://spring.io/projects/spring-data-jpa

参考文档：https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

网上资料：http://www.360doc.com/content/17/0801/09/16915_675758662.shtml

网上资料：https://www.ibm.com/developerworks/cn/opensource/os-cn-spring-jpa/index.html

## 相关导航

[Spring Boot 系列教程目录导航](https://www.cnblogs.com/xifengxiaoma/p/11116330.html)

[Spring Boot：快速入门教程](https://www.cnblogs.com/xifengxiaoma/p/11019240.html)

[Spring Boot：整合Swagger文档](https://www.cnblogs.com/xifengxiaoma/p/11022146.html)

[Spring Boot：整合MyBatis框架](https://www.cnblogs.com/xifengxiaoma/p/11024402.html)

[Spring Boot：实现MyBatis分页](https://www.cnblogs.com/xifengxiaoma/p/11027551.html)

## 源码下载

码云：https://gitee.com/liuge1988/spring-boot-demo.git