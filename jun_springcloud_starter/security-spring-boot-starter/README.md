# spring-boot-starter-security

#### 项目介绍
Spring Boot权限框架，对开发者更友好的分布式权限验证框架，极大的提高验证效率。

[![Maven metadata URI](https://img.shields.io/maven-central/v/cn.antcore/spring-boot-starter-security.svg?style=flat-square)](https://mvnrepository.com/artifact/cn.antcore/spring-boot-starter-security)
[![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)](https://mvnrepository.com/artifact/cn.antcore/spring-boot-starter-security)
[![jdk](https://img.shields.io/badge/JDK-1.8+-green.svg)](https://mvnrepository.com/artifact/cn.antcore/spring-boot-starter-security)

#### 使用教程

进入教程[例子](https://gitee.com/hong_ej/ant-security-example.git)

#### Maven仓库坐标
```xml
<dependency>
    <groupId>cn.antcore</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
    <version>${Maven仓库最新版本}</version>
    <scope>compile</scope>
</dependency>
```

#### 基础能力
    
##### 登录授权
* UserDetails
    > 用户数据; 保存用户的唯一id、用户名、已拥有的权限集合和已拥有的角色集合
* LoginSession
    > 登录Session; 提供基础的登录能力和退出能力。登录能力调用后会返回用户唯一token标志
* 例子如下：
```java
    @PostRest(value = "login", login = false)
    public ReturnT<String> login(HttpSession session, String username, String password) {
        // TODO 自己实现username和password的登录验证逻辑
        UserDetails details = new UserDetails(10010L, "Admin");
        details.setAuthority(Arrays.asList("index", "user"));
        details.setRoles(Arrays.asList("admin"));
        String token = ((LoginSession) session).loginSuccess(details);
        return ReturnT.success(token);
    }
```

##### 退出登录
* 例子如下：
```java
    @GetRest(value = "logout")
    public ReturnT<String> logout(HttpSession session) {
        session.invalidate();
        return ReturnT.success("success");
    }
```
```java
    @GetRest(value = "logout")
    public ReturnT<String> logout(HttpSession session) {
        LoginSession loginSession = (LoginSession) session;
        loginSession.logout();
        return ReturnT.success("success");
    }
```

##### 获取登录用户信息
* UserSession
    > 用户Session; 提供登录用户的基础信息查询方法，如用户id，用户名，用户数据等
* 例子如下：
```java
    @GetRest(authority = {"index"}, roles = "admin", condition = "#session.getUserId() == 10010L")
    public ReturnT<Serializable> index(HttpSession session) {
        UserSession userSession = (UserSession) session;
        return ReturnT.success(userSession.getUserId());
    }
```

##### 接口登录验证
* 功能概述
    > 为了让一个接口必须登录后才能访问而增加的拦截
* @Login
    > 该注解是拦截接口登录权限而产生的; 提供一个value参数，true表示接口需要登录才能访问，false表示接口无需登录即可访问
* 例子如下：
```java
    @Login(value = false)
    @PostMapping(value = "login")
    public ReturnT<String> login(HttpSession session, String username, String password) {
        // TODO 登录逻辑
    }
```

##### 接口权限、角色和自定义规则拦截验证
* 功能概述
    > 一个接口必须由拥有指定权限的用户才能访问
* @ApiAuthorize
    > 该注解的作用是校验接口权限、角色访问限制和接口自定义规则拦截; value和authority参数，需要的权限集合。roles参数，需要的角色集合。condition参数，自定义规则，支持SpEL表达式。
* 例子如下：
```java
    @ApiAuthorize(authority = {"index"}, roles = "admin", condition = "#session.getUserId() == 10010L")
    public ReturnT<Serializable> index(HttpSession session) {
        // 此接口表达的意思是：用户需要要拥有index权限才能访问，而且该用户必须是admin角色，最后该用户的userId必须是10010.
        // 三个条件是并且的关系
    }
```

##### Rest接口增强
* 功能概述
    > 将@RequestMapping、@Login和@ApiAuthorize三个注解融合在一个注解当中；为了减少接口上的注解数量。
* 例子如下：
```java
    import cn.antcore.security.web.GetRest;
    import cn.antcore.security.web.PostRest;
    import cn.antcore.security.web.PutRest;
    import cn.antcore.security.web.DeleteRest;
    import cn.antcore.security.web.OptionsRest;
    import cn.antcore.security.web.PatchRest;
    import cn.antcore.security.web.HeadRest;
    import cn.antcore.security.web.TraceRest;

    @GetRest(value = {"", "/index"}, authority = {"index"}, roles = "admin", condition = "#session.getUserId() == 10010L")
    public ReturnT<Serializable> index(HttpSession session) {
        // TODO 自定义逻辑
    }
```

#### 配置文件
```yaml
spring:
  security:
    session-time: 24h # Token失效时间
    automatic-renewal: true # Token是否自动续签
    max-live: 1 # 同一个用户最大同时在线数
  redis: # Redis配置
    host: 127.0.0.1 
    port: 6379
```

#### 基础扩展能力
##### Token生成 / 读取策略
* 功能概述
    > 登录Session返回的Token生成策略以及前端Token传递到后端，后端在什么位置获取到Token等
* 接口和默认实现
    > interface: cn.antcore.security.session.SessionIdStrategy<br/>class impl: cn.antcore.security.session.strategy.HeaderSessionIdStrategy
* 默认实现类说明
    > Token生成策略是UUID；前端通过Header头传递Token，header头的名字叫做"X-Auth-Token"
* 自定义实现
    > 实现SessionIdStrategy接口，并将实现类交给Spring BeanFactory管理即可

##### Session生成策略
* 功能概述
    > 分布式Session的生成策略，默认使用Redis作为数据存储仓库
* 接口和默认实现
    > interface: cn.antcore.security.session.SessionStrategy<br/>class impl: cn.antcore.security.session.strategy.RedisSessionStrategy
* 默认实现类说明
    > Session中的数据保存使用Redis作为存储媒介
* 自定义实现
    > 实现SessionStrategy接口，并将实现类交给Spring BeanFactory管理即可

##### Token自动续期管理
* 功能概述
    > 服务器一旦监测到Token正在使用，则会自动续期该Token
* 接口和默认实现
    > interface: cn.antcore.security.session.refresh.SessionTimeoutRefresh<br/>class impl: cn.antcore.security.session.refresh.RedisSessionTimeoutRefresh
* 默认实现类说明
    > SessionIdStrategy类的getSessionId行为会触发Token续期
* 自定义实现
    > 实现SessionTimeoutRefresh接口，并将实现类交给Spring BeanFactory管理即可

##### 用户存活数管理
* 功能概述
    > 用户一旦创建新的Token，则会触发用户存活数检测，超出最大存活数限制就会剔除旧的会话
* 接口和默认实现
    > interface: cn.antcore.security.session.max.MaxSession<br/>class impl: cn.antcore.security.session.max.MaxSessionManager
* 默认实现类说明
    > LoginSession类的loginSuccess行为会触发用户存活数管理
* 自定义实现
  > 实现MaxSession接口，并将实现类交给Spring BeanFactory管理即可

#### 高级扩展能力
##### 登录状态拦截
* 功能概述
  > @Login触发的拦截器
* 接口和默认实现
  > interface: cn.antcore.security.filter.LoginStatusFilter<br/>class impl: cn.antcore.security.filter.RedisLoginStatusFilter
* 默认实现类说明
  > 拿到UserSession，通过isLogin行为判断用户是否登录
* 自定义实现
  > 实现LoginStatusFilter接口，并将实现类交给Spring BeanFactory管理即可

##### 权限拦截器
* 功能概述
  > @ApiAuthorize触发的拦截器
* 接口和默认实现
  > interface: cn.antcore.security.filter.AuthorizeFilter<br/>class impl: cn.antcore.security.filter.AuthorizeFilterImpl
* 默认实现类说明
  > 拿到UserDetails中的权限进行拦截放行
* 自定义实现
  > 实现AuthorizeFilter接口，并将实现类交给Spring BeanFactory管理即可
