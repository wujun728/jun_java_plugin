# roncoo-spring-boot（龙果学院Spring Boot集成）

## 说明
1. Spring Boot官方提供了很多第三方的框架集成，但是在国内还有很多优秀的框架没有进行集成，因此龙果学院决定进行对Spring官方没有支持的框架进行集成，这样也方便大家的使用，让大家多点时间去泡女，陪小孩，哈哈！

2. 为了方便使用，我们已经把其发布在Maven的中央库，可以直接引用

3. 如果你有想集成的框架，请和我们反映，我们会择优进行集成共享出来给大家使用

## 更新
* 2019-12-16 升级xxl-job版本到2.1.2
* 2019-12-09 集成xxl-job并升级Spring Boot的版本，移除druid，因为官方已经集成，这里不再维护
* 2019-01-05 升级Spring Boot的版本
* 2017-11-07 集成Shiro并升级Spring Boot的版本
* 2017-04-20 集成国内优秀的数据链接池Druid


## 使用说明
### 方法1：使用maven
```
<dependency>
    <groupId>com.roncoo</groupId>
    <artifactId>roncoo-spring-boot-starter-shiro</artifactId>
    <version>1.0.8</version>
</dependency>

<dependency>
    <groupId>com.roncoo</groupId>
    <artifactId>roncoo-spring-boot-starter-xxl-job</artifactId>
    <version>1.0.8</version>
</dependency>

```
### 方法2：jar下载

### 视频学习：[Spring Boot教程全集](http://www.roncoo.com/course/view/c99516ea604d4053908c1768d6deee3d)
### 视频学习：[Spring Cloud第一季](http://www.roncoo.com/course/view/cc8fbd6749f94f2fa015641ef96b9460)
