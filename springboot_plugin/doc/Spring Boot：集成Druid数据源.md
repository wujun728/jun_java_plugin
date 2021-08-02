# [Spring Boot：集成Druid数据源](https://www.cnblogs.com/xifengxiaoma/p/11028248.html)

## 综合概述

数据库连接池负责分配、管理和释放数据库连接，它允许应用程序重复使用一个现有的数据库连接，而不是再重新建立一个；释放空闲时间超过最大空闲时间的数据库连接来避免因为没有释放数据库连接而引起的数据库连接遗漏。通过数据库连接池能明显提高对数据库操作的性能。在Java应用程序开发中，常用的连接池有DBCP、C3P0、Proxool等。

Spring Boot默认提供了若干种可用的连接池，默认的数据源是：org.apache.tomcat.jdbc.pool.DataSource。而Druid是阿里系提供的一个开源连接池，除在连接池之外，Druid还提供了非常优秀的数据库监控和扩展功能。接下来，我们就来讲解如何实现Spring Boot与Druid连接池的集成。

## Druid介绍

Druid是阿里开源的一个JDBC应用组件， 其包括三部分：

- DruidDriver: 代理Driver，能够提供基于Filter－Chain模式的插件体系。
- DruidDataSource: 高效可管理的数据库连接池。
- SQLParser: 实用的SQL语法分析

通过Druid连接池中间件， 我们可以实现：

- 可以监控数据库访问性能，Druid内置提供了一个功能强大的StatFilter插件，能够详细统计SQL的执行性能，这对于线上分析数据库访问性能有帮助。
- 替换传统的DBCP和C3P0连接池中间件。Druid提供了一个高效、功能强大、可扩展性好的数据库连接池。
- 数据库密码加密。直接把数据库密码写在配置文件中，容易导致安全问题。DruidDruiver和DruidDataSource都支持PasswordCallback。
- SQL执行日志，Druid提供了不同的LogFilter，能够支持Common-Logging、Log4j和JdkLog，你可以按需要选择相应的LogFilter，监控你应用的数据库访问情况。
- 扩展JDBC，如果你要对JDBC层有编程的需求，可以通过Druid提供的Filter-Chain机制，很方便编写JDBC层的扩展插件。

更多详细信息参考官方文档：https://github.com/alibaba/druid/wiki

## 实现案例

接下来，我们就通过实际案例来讲解如何集成Druid数据源，为了避免重复篇幅，此篇教程的源码基于《[Spring Boot：整合MyBatis框架](https://www.cnblogs.com/xifengxiaoma/p/11024402.html)》一篇的源码实现，读者请先参考并根据教程链接先行获取基础源码和数据库内容。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190615173951659-1287657418.png)

 

### 添加相关依赖

打开pom文件，添加 druid 相关的 maven 依赖。

pom.xml

```
<!-- druid -->
<dependency>
   <groupId>com.alibaba</groupId>
   <artifactId>druid-spring-boot-starter</artifactId>
   <version>1.1.17</version>
</dependency>
```

Druid Spring Boot Starter 是阿里官方提供的 Spring Boot 插件，用于帮助在Spring Boot项目中轻松集成Druid数据库连接池和监控。

更多资料参考:

Druid: https://github.com/alibaba/druid

Druid Spring Starter: https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter

### 添加相关配置

把原有的数据源配置替换成 druid 数据源并配置数据源相关参数。

application.yml

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
# Tomcat
server:
  tomcat:
    uri-encoding: UTF-8
    max-threads: 1000
    min-spare-threads: 30
  port: 8080
    #context-path: /springboot

# DataSource
spring:
  datasource:
    name: druidDataSource
    type: com.alibaba.druid.pool.DruidDataSource
    druid:
      driver-class-name: com.mysql.cj.jdbc.Driver
      url: jdbc:mysql://localhost:3306/springboot?useUnicode=true&zeroDateTimeBehavior=convertToNull&autoReconnect=true&characterEncoding=utf-8
      username: root
      password: 123456
      filters: stat,wall,log4j,config
      max-active: 100
      initial-size: 1
      max-wait: 60000
      min-idle: 1
      time-between-eviction-runs-millis: 60000
      min-evictable-idle-time-millis: 300000
      validation-query: select 'x'
      test-while-idle: true
      test-on-borrow: false
      test-on-return: false
      pool-prepared-statements: true
      max-open-prepared-statements: 50
      max-pool-prepared-statement-per-connection-size: 20
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

**参数说明：**

\- spring.datasource.druid.max-active 最大连接数 
\- spring.datasource.druid.initial-size 初始化大小 
\- spring.datasource.druid.min-idle 最小连接数 
\- spring.datasource.druid.max-wait 获取连接等待超时时间 
\- spring.datasource.druid.time-between-eviction-runs-millis 间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 
\- spring.datasource.druid.min-evictable-idle-time-millis 一个连接在池中最小生存的时间，单位是毫秒 
\- spring.datasource.druid.filters=config,stat,wall,log4j 配置监控统计拦截的filters，去掉后监控界面SQL无法进行统计，’wall’用于防火墙

**Druid提供以下几种Filter信息：**

**![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190615184513449-1570249789.png)**

#### **自定义配置属性**

如果需要通过定制的配置文件对druid进行自定义属性配置，在config包中添加属性配置类。

DruidDataSourceProperties.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.config;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.datasource.druid")
public class DruidDataSourceProperties {

    // jdbc
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    // jdbc connection pool
    private int initialSize;
    private int minIdle;
    private int maxActive = 100;
    private long maxWait;
    private long timeBetweenEvictionRunsMillis;
    private long minEvictableIdleTimeMillis;
    private String validationQuery;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean poolPreparedStatements;
    private int maxPoolPreparedStatementPerConnectionSize;
    // filter
    private String filters;

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public long getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }

    public long getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public long getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public boolean isPoolPreparedStatements() {
        return poolPreparedStatements;
    }

    public void setPoolPreparedStatements(boolean poolPreparedStatements) {
        this.poolPreparedStatements = poolPreparedStatements;
    }

    public int getMaxPoolPreparedStatementPerConnectionSize() {
        return maxPoolPreparedStatementPerConnectionSize;
    }

    public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
        this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

Druid Spring Starter 简化了很多配置，如果默认配置满足不了你的需求，可以自定义配置。更多配置参考：

Druid Spring Starter: https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter

#### 配置Servlet和Filter

在config包中添加一个DruidConfig配置类。

DruidConfig.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.springboot.demo.config;
import java.sql.SQLException;
import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

@Configuration
@EnableConfigurationProperties({DruidDataSourceProperties.class})
public class DruidConfig {
    @Autowired
    private DruidDataSourceProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public DataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(properties.getDriverClassName());
        druidDataSource.setUrl(properties.getUrl());
        druidDataSource.setUsername(properties.getUsername());
        druidDataSource.setPassword(properties.getPassword());
        druidDataSource.setInitialSize(properties.getInitialSize());
        druidDataSource.setMinIdle(properties.getMinIdle());
        druidDataSource.setMaxActive(properties.getMaxActive());
        druidDataSource.setMaxWait(properties.getMaxWait());
        druidDataSource.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRunsMillis());
        druidDataSource.setMinEvictableIdleTimeMillis(properties.getMinEvictableIdleTimeMillis());
        druidDataSource.setValidationQuery(properties.getValidationQuery());
        druidDataSource.setTestWhileIdle(properties.isTestWhileIdle());
        druidDataSource.setTestOnBorrow(properties.isTestOnBorrow());
        druidDataSource.setTestOnReturn(properties.isTestOnReturn());
        druidDataSource.setPoolPreparedStatements(properties.isPoolPreparedStatements());
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(properties.getMaxPoolPreparedStatementPerConnectionSize());

        try {
            druidDataSource.setFilters(properties.getFilters());
            druidDataSource.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return druidDataSource;
    }

    /**
     * 注册Servlet信息， 配置监控视图
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public ServletRegistrationBean<Servlet> druidServlet() {
        ServletRegistrationBean<Servlet> servletRegistrationBean = new ServletRegistrationBean<Servlet>(new StatViewServlet(), "/druid/*");

        //白名单：
        servletRegistrationBean.addInitParameter("allow","192.168.1.195");
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        servletRegistrationBean.addInitParameter("deny","192.168.1.119");
        //登录查看信息的账号密码, 用于登录Druid监控后台
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "admin");
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable", "true");
        return servletRegistrationBean;

    }

    /**
     * 注册Filter信息, 监控拦截器
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean<Filter> filterRegistrationBean() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<Filter>();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

**说明:**

\- @EnableConfigurationProperties({DruidDataSourceProperties.class}) 用于导入上一步Druid的配置信息 
\- public ServletRegistrationBean druidServlet() 相当于Web Servlet配置 
\- public FilterRegistrationBean filterRegistrationBean() 相当于Web Filter配置

如果不使用上述的Servlet和Filter配置， 也可以通过下述监控器配置实现：

#### **配置监控拦截器（相当于FilterRegistrationBean）**

DruidStatFilter.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.kitty.boot.config;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import com.alibaba.druid.support.http.WebStatFilter;

/**   
 * 配置监控拦截器, druid监控拦截器   
 */    
@WebFilter(filterName="druidWebStatFilter",    
urlPatterns="/*",    
initParams={    
    @WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*"), // 忽略资源    
})   
public class DruidStatFilter extends WebStatFilter {  

} 
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

#### **配置Druid监控视图（相当于ServletRegistrationBean）**

DruidStatViewServlet.java

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
package com.louis.kitty.boot.config;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import com.alibaba.druid.support.http.StatViewServlet;

/**   
 * druid监控视图配置   
 */    
@WebServlet(urlPatterns = "/druid/*", initParams={    
    @WebInitParam(name="allow",value="192.168.6.195"),    // IP白名单 (没有配置或者为空，则允许所有访问)    
    @WebInitParam(name="deny",value="192.168.6.73"),    // IP黑名单 (存在共同时，deny优先于allow)    
    @WebInitParam(name="loginUsername",value="admin"),    // 用户名    
    @WebInitParam(name="loginPassword",value="admin"),    // 密码    
    @WebInitParam(name="resetEnable",value="true")    // 禁用HTML页面上的“Reset All”功能    
})   
public class DruidStatViewServlet extends StatViewServlet {  
    private static final long serialVersionUID = 7359758657306626394L;  
}  
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

### 应用启动问题

到这里Druid的配置就完成了，但是此时启动应用，发现出错了，错误内容大致如下，提示 log4j 相关的类查找不到，然后查看依赖，发现确实没有 log4j 的依赖，有些奇怪，尝试了一下，也没发现其他办法，手动加一下吧。

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'mybatisConfig': Unsatisfied dependency expressed through field 'dataSource'; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'druidDataSource' defined in class path resource [com/louis/kitty/boot/config/DruidConfig.class]: Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [javax.sql.DataSource]: Factory method 'druidDataSource' threw exception; nested exception is java.lang.NoClassDefFoundError: org/apache/log4j/Priority
    at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor$AutowiredFieldElement.inject(AutowiredAnnotationBeanPostProcessor.java:586) ~[spring-beans-5.0.8.RELEASE.jar:5.0.8.RELEASE]
    at org.springframework.beans.factory.annotation.InjectionMetadata.inject(InjectionMetadata.java:91) ~[spring-beans-5.0.8.RELEASE.jar:5.0.8.RELEASE]
    at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor.postProcessPropertyValues(AutowiredAnnotationBeanPostProcessor.java:372) ~[spring-beans-5.0.8.RELEASE.jar:5.0.8.RELEASE]
    at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.populateBean(AbstractAutowireCapableBeanFactory.java:1341) ~[spring-beans-5.0.8.RELEASE.jar:5.0.8.RELEASE]
    at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:572) ~[spring-beans-5.0.8.RELEASE.jar:5.0.8.RELEASE]
    at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:495) ~[spring-beans-5.0.8.RELEASE.jar:5.0.8.RELEASE]
    at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:317) ~[spring-beans-5.0.8.RELEASE.jar:5.0.8.RELEASE]
    at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:222) ~[spring-beans-5.0.8.RELEASE.jar:5.0.8.RELEASE]
    at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:315) ~[spring-beans-5.0.8.RELEASE.jar:5.0.8.RELEASE]
    at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:199) ~[spring-beans-5.0.8.RELEASE.jar:5.0.8.RELEASE]
    at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:759) ~[spring-beans-5.0.8.RELEASE.jar:5.0.8.RELEASE]
    at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:869) ~[spring-context-5.0.8.RELEASE.jar:5.0.8.RELEASE]
    at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:550) ~[spring-context-5.0.8.RELEASE.jar:5.0.8.RELEASE]
    at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:140) ~[spring-boot-2.0.4.RELEASE.jar:2.0.4.RELEASE]
    at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:762) [spring-boot-2.0.4.RELEASE.jar:2.0.4.RELEASE]
    at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:398) [spring-boot-2.0.4.RELEASE.jar:2.0.4.RELEASE]
    at org.springframework.boot.SpringApplication.run(SpringApplication.java:330) [spring-boot-2.0.4.RELEASE.jar:2.0.4.RELEASE]
    at org.springframework.boot.SpringApplication.run(SpringApplication.java:1258) [spring-boot-2.0.4.RELEASE.jar:2.0.4.RELEASE]
    at org.springframework.boot.SpringApplication.run(SpringApplication.java:1246) [spring-boot-2.0.4.RELEASE.jar:2.0.4.RELEASE]
    at com.louis.kitty.boot.KittyApplication.main(KittyApplication.java:10) [classes/:na]
```

　　　 ...

Caused by: java.lang.ClassNotFoundException: org.apache.log4j.Priority
at java.net.URLClassLoader.findClass(URLClassLoader.java:381) ~[na:1.8.0_131]
at java.lang.ClassLoader.loadClass(ClassLoader.java:424) ~[na:1.8.0_131]
at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:335) ~[na:1.8.0_131]
at java.lang.ClassLoader.loadClass(ClassLoader.java:357) ~[na:1.8.0_131]
... 49 common frames omitted

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

#### **添加 log4j 依赖**

pom.xml 添加 log4j 依赖，最新版本是 1.2.17。

```
<!-- log4j -->
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
```

#### **添加 log4j 配置**

在 resources 目录下，新建一个 log4j.properties 参数配置文件，并键入如下内容：

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

```
### set log levels ###    
log4j.rootLogger = INFO,DEBUG, console, infoFile, errorFile ,debugfile,mail 
LocationInfo=true    

log4j.appender.console = org.apache.log4j.ConsoleAppender  
log4j.appender.console.Target = System.out  
log4j.appender.console.layout = org.apache.log4j.PatternLayout 

log4j.appender.console.layout.ConversionPattern =[%d{yyyy-MM-dd HH:mm:ss,SSS}]-[%p]:%m   %x %n 

log4j.appender.infoFile = org.apache.log4j.DailyRollingFileAppender  
log4j.appender.infoFile.Threshold = INFO  
log4j.appender.infoFile.File = D:/logs/log
log4j.appender.infoFile.DatePattern = '.'yyyy-MM-dd'.log'  
log4j.appender.infoFile.Append=true
log4j.appender.infoFile.layout = org.apache.log4j.PatternLayout  
log4j.appender.infoFile.layout.ConversionPattern =[%d{yyyy-MM-dd HH:mm:ss,SSS}]-[%p]:%m  %x %n 

log4j.appender.errorFile = org.apache.log4j.DailyRollingFileAppender  
log4j.appender.errorFile.Threshold = ERROR  
log4j.appender.errorFile.File = D:/logs/error  
log4j.appender.errorFile.DatePattern = '.'yyyy-MM-dd'.log'  
log4j.appender.errorFile.Append=true  
log4j.appender.errorFile.layout = org.apache.log4j.PatternLayout  
log4j.appender.errorFile.layout.ConversionPattern =[%d{yyyy-MM-dd HH:mm:ss,SSS}]-[%p]:%m  %x %n

#log4j.appender.debugfile = org.apache.log4j.DailyRollingFileAppender  
#log4j.appender.debugfile.Threshold = DEBUG  
#log4j.appender.debugfile.File = D:/logs/debug  
#log4j.appender.debugfile.DatePattern = '.'yyyy-MM-dd'.log'  
#log4j.appender.debugfile.Append=true  
#log4j.appender.debugfile.layout = org.apache.log4j.PatternLayout  
#log4j.appender.debugfile.layout.ConversionPattern =[%d{yyyy-MM-dd HH:mm:ss,SSS}]-[%p]:%m  %x %n
```

[![复制代码](https://common.cnblogs.com/images/copycode.gif)](javascript:void(0);)

配置完成后，重新编译启动，发现已经可以启动了。按理说，Spring Boot 已经集成了 log4j， 这个问题出现的有点奇怪，有知道答案的朋友，欢迎赐教，感激不尽。

### 查看监控视图

#### 登录界面

启动应用，访问： http://localhost:8080/druid/login.html， 进入Druid监控后台页面。

注意：登录用户名和密码，就是在DruidConfig配置类中配置的，查看并进行登录。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190615180105400-1247147475.png)

#### 登录首页

首页信息。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190615180424189-2097764896.png)

#### 数据源

显示连接数据源的相关信息。

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190615180517920-1220030196.png)

#### SQL监控

分别访问下面两个接口之后，SQL监控的记录结果。

http://localhost:8080/user/findByUserId?userId=1

http://localhost:8080/user/findAll

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190615180714056-1784002774.png)

#### URI监控

分别访问下面两个接口之后，URI监控的记录结果。

http://localhost:8080/user/findByUserId?userId=1

http://localhost:8080/user/findAll

![img](https://img2018.cnblogs.com/blog/616891/201906/616891-20190615180830753-1962496497.png)

 

## 胡言乱语

要想数据库性能好，数据库调优少不了。

服务器配置需要弄，SQL调优也得搞。

要问数据源哪家好，阿里DRUID准没跑。

SQL监控做得好，语句调优没烦恼。

## 参考资料

官方网站：https://druid.apache.org/

官方文档：https://github.com/alibaba/druid/wiki

在线文档：http://tool.oschina.net/apidocs/apidoc?api=druid0.26