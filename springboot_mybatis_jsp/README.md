# SpringBoot-Mybatis-JSP
&nbsp;&nbsp;&nbsp;&nbsp;SpringBoot入门级别案例，主要涉及技术点有，SpringBoot+Mybatis+JSP。其他内容有:多环境切换，统一异常处理，SpringBoot热部署，阿里数据源监控，SpringBoot中的访问日志等...

## 1.使用sprongloader进行热部署的两种方法

> 缺点： _不支持对“新增方法”和“新增类”情景的热部署_

#### 1.1直接使用maven命令执行
```
D:\20171112Git\SpringBoot-Mybatis-JSP>mvn spring-boot:run -f pom.xml
```
#### 1.2使用run main执行
- 此方法依赖jar包： _**springloaded-1.2.4.RELEASE.jar**_ 

```
-javaagent:.\hotplugin\springloaded-1.2.4.RELEASE.jar -noverify
```
>注意： 项目工程中的run main的默认起始目录为项目工程目录

### 2.添加spring-boot-devtools依赖

#### 2.1直接main方法启动
- 直接run main就可以了

#### 2.2使用maven命令启动
- 需要添加maven插件
```
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>

```
- maven启动命令
```
D:\20171112Git\SpringBoot-Mybatis-JSP>mvn spring-boot:run -f pom.xml
```

#### 2.3原理解析

&nbsp;&nbsp;&nbsp;&nbsp;原理是使用了两个ClassLoader，一个Classloader加载那些不会改变的类（第三方Jar包），另一个ClassLoader加载会更改的类，称为  restart ClassLoader,这样在有代码更改的时候，原来的restart ClassLoader 被丢弃，重新创建一个restart ClassLoader，由于需要加载的类相比较少，所以实现了较快的重启时间
> 特别注意：eclipse中编译发生在save的时候；而在idea中的编译则需要ctr+f9来主动去手工编译project.
