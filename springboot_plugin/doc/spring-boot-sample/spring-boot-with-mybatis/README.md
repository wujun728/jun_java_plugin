#spring-boot 整合mybatis
    本系列是spring-boot相关的一些列子，比如spring-boot集成druid，以及druid的动态数据源切换，
    spring-boot 集成mybatis，spring-boot集成定时器等等

1、在pom文件中添加mybatis的依赖
    
       <dependency>
           <groupId>org.mybatis.spring.boot</groupId>
           <artifactId>mybatis-spring-boot-starter</artifactId>
           <version>1.1.1</version>
       </dependency>
       <dependency>
           <groupId>mysql</groupId>
           <artifactId>mysql-connector-java</artifactId>
           <version>5.1.38</version>
       </dependency>
       
2、在配置文件中添加mybatis设置

    mybatis.type-aliases-package=cn.kiiwii.framework.mybatis.model
    mybatis.mapper-locations=classpath:cn/kiiwii/framework/mybatis/mapping/*.xml
    
3、在Application添加扫描注解

    @MapperScan("cn.kiiwii.framework.mybatis.mapper")