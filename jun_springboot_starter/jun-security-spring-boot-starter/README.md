<h1 align="center">jun-security-boot-starter</h1>


 

## 1、简介

jun-security-boot-starter是一个基于SpringBoot开发的轻量级权限控制框架，支持登录认证、权限认证；同时支持token验证和cookie验证；
支持redis、jdbc和单机session多种会话存储方式（亦可自行扩展存储方式）；前后端分离项目、不分离项目均可使用，功能完善、使用简单，文档清晰，让认证鉴权这件事变得更加简单！

---

## 2、使用

### 2.1、SpringBoot集成

#### 2.1.1、引入依赖
```xml
<dependency>
    <groupId>com.jun.plugin</groupId>
    <artifactId>jun-security-spirng-boot-starter</artifactId>
    <version>1.0.8</version>
</dependency>
```

#### 2.1.2、yml参数配置项

```yaml
jun-security:
  # 存储类型，目前支持jdbc和redis和单机内存三种(redis,jdbc,single)，如不配置，则默认为single
  store-type: redis
  # token名称 (同时也是cookie名称，适配前后端不分离的模式)
  token-name: token
  # token有效期 (即会话时长)，单位秒 默认1800秒(30分钟)
  timeout: 1800
  # token风格，可配置uuid (默认风格)，snowflake (纯数字风格)，objectid (变种uuid)，random128 (随机128位字符串)，nanoid
  token-style: uuid
  # 当配置为jdbc时，存储token的表名字，默认为b_auth_token
  table-name: b_auth_token
```

1. 如果使用jdbcAuthStore，需要导入框架提供的sql脚本并集成好jdbcTemplate，
   导入依赖 `spring-boot-starter-jdbc`，在yml里进行相应配置即可
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
```
2. 如果使用redisAuthStore，需要集成好redisTemplate，
   导入依赖 `spring-boot-starter-data-redis` ，在yml里进行相应配置即可
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

#### 2.1.3、其他自定义配置
1. 配置会话拦截器和权限角色拦截器，以`SpringBoot2.0`为例, 新建配置类`WebMvcConfig.java`，两个拦截器的拦截路由规则可自行配置
```java
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AuthenticeInterceptor authenticeInterceptor;

    // 按需要来，如果不需要角色权限控制，可以不配置此拦截器
    @Autowired
    private PermissionInterceptor permissionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        
        // 注册会话拦截器
        registry.addInterceptor(authenticeInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login");

        // 注册权限拦截器
        registry.addInterceptor(permissionInterceptor)
                .addPathPatterns("/**");
    }
}
```
2. 如需权限角色拦截器进行权限控制的话，则需要实现`PermissionInfoInterface`接口，重写权限和角色编码列表获取的业务逻辑（框架没有对权限和角色编码进行缓存，如需缓存请自行处理），例如以下代码：
```java
@Component
public class PermissionInfoInterfaceImpl implements PermissionInfoInterface {
    private final static Logger logger = LoggerFactory.getLogger(PermissionInfoInterfaceImpl.class);


    /**
     * 返回一个账号所拥有的权限码集合
     * @param loginId，账号id，即你在调用 authProvider.login(id) 时写入的标识值。
     */
    @Override
    public Set<String> getPermissionSet(Object loginId) {
        if (logger.isInfoEnabled()) {
            logger.info("PermissionInfoInterfaceImpl -- getPermissionSet -- loginId = {}", loginId);
        }
        // 自定义权限编码列表获取逻辑，下面的只是示例
        Set<String> permissionSet = new HashSet<String>() {{
            add("权限1");
            add("权限2");
        }};

        return permissionSet;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     * @param loginId，账号id，即你在调用 authProvider.login(id) 时写入的标识值。
     */
    @Override
    public Set<String> getRoleSet(Object loginId) {
        if (logger.isInfoEnabled()) {
            logger.info("PermissionInfoInterfaceImpl -- getRoleSet -- loginId = {}", loginId);
        }
        // 自定义角色编码列表获取逻辑，下面的只是示例
        Set<String> roleSet = new HashSet<String>() {{
            add("角色1");
            add("角色2");
        }};
        return roleSet;
    }
}
```

---

### 2.2、登录签发token，创建会话

```java
@Controller
public class IndexController {
    final static Logger logger = LoggerFactory.getLogger(IndexController.class);
    
    @Autowired
    private AuthProvider authProvider;

    @ResponseBody
    @PostMapping("/login")
    public Result<Object> login(@ApiParam(name = "username", required = true, value = "用户名")
                                @RequestParam("username") String username,
                                @ApiParam(name = "password", required = true, value = "用户密码")
                                @RequestParam("password") String password) {
        // 你的登录验证逻辑
        // ......
        // 签发token
        String token = authProvider.login(username);

        return Result.ok("登录成功！", token);
    }
}
```
login方法参数说明：
- loginId  登录的账号id，建议的数据类型：long | int | String，建议为用户id，不可以传入复杂类型，如：User、Admin 等等

---


### 2.3、退出登录，注销会话
```java
@Controller
public class IndexController {
    final static Logger logger = LoggerFactory.getLogger(IndexController.class);
   
    @Autowired
    private AuthProvider authProvider;

    @ResponseBody
    @GetMapping("/logout")
    public Result<Object> logout(HttpServletRequest request) {
        // 退出登录，注销会话
        authProvider.logout(request);

        return Result.ok("退出登录成功！");
    }
}
```
---

### 2.4、使用注解控制权限

**1.注解解释：**

```text
// 需要有system权限才能访问
@RequiresPermissions("system")

// 需要有system和front权限才能访问, logical可以不写,默认是AND
@RequiresPermissions(value={"system","front"}, logical=Logical.AND)

// 需要有system或front权限才能访问
@RequiresPermissions(value={"system","front"}, logical=Logical.OR)

// 需要有user角色才能访问
@RequiresRoles(value="user")

// 需要有admin和user角色才能访问
@RequiresRoles(value={"admin","user"}, logical=Logical.AND)

// 需要有admin或user角色才能访问
@RequiresRoles(value={"admin","user"}, logical=Logical.OR)
```

> 注解加在Controller的方法或类上面。

**2.代码示例：**

```java
@Controller
public class IndexController {
    final static Logger logger = LoggerFactory.getLogger(IndexController.class);
    
    @Autowired
    private AuthProvider authProvider;

    @RequiresPermissions("权限3")
    @ResponseBody
    @GetMapping("/testPermission3")
    public Result<Object> testPermission3() {

        return Result.ok("testPermission3测试成功！");
    }

    @RequiresPermissions("权限2")
    @ResponseBody
    @GetMapping("/testPermission2")
    public Result<Object> testPermission2() {
        logger.info("IndexController - testPermission3 - authProvider.getLoginId() = {}", authProvider.getLoginId());
        logger.info("IndexController - testPermission3 - AuthUtil.getLoginId() = {}", AuthUtil.getLoginId());
       logger.info("IndexController - testPermission3 - token = {}", authProvider.getToken());
        
        return Result.ok("testPermission2测试成功！", authProvider.getLoginId());
    }
}
```

---

### 2.5、使用代码控制权限
**1.代码示例：** 

```java

// 判断：当前账号是否含有指定角色, 返回 true 或 false
AuthUtil.hasRole("role1");

// 判断：当前账号是否含有指定角色 [指定多个，必须全部验证通过]
AuthUtil.hasAllRole("role1", "role2");

// 判断：当前账号是否含有指定角色 [指定多个，只要其一验证通过即可]
AuthUtil.hasAnyRole("role1", "role2");

// 判断：当前账号是否含有指定权限, 返回 true 或 false
AuthUtil.hasPermission("permission1");

// 判断：当前账号是否含有指定权限 [指定多个，必须全部验证通过]
AuthUtil.hasAllPermission("permission1", "permission2");

// 判断：当前账号是否含有指定权限 [指定多个，只要其一验证通过即可]
AuthUtil.hasAnyPermission("permission1", "permission2");

```

---

### 2.6、获取当前登录用户编码
```java
// 注入authProvider
@Autowired
private AuthProvider authProvider;

authProvider.getLoginId()
        
或者直接调用静态方法
        
AuthUtil.getLoginId()
```

---

### 2.7、获取当前登录用户token
```java
// 注入authProvider
@Autowired
private AuthProvider authProvider;

authProvider.getToken()
或者
authProvider.getToken(HttpServletRequest request);
```
---

### 2.8、异常处理
bluewind-auth-client在会话验证失败和权限验证失败的时候会抛出自定义异常：

| 自定义异常                  | 描述          | 错误信息                          |
|:----------------------|:-------------|:----------------------------------|
| UnAuthorizedException | 未登录或会话已失效 | 错误信息“未登录或会话已失效！”，错误码401 |
| NoPermissionException | 无权限访问（角色或者资源不匹配）  | 错误信息“无权限访问！”，错误码403   |

建议使用全局异常处理器来捕获异常并进行处理：
```java
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 捕捉运行时异常
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public Result<Object> handleRuntimeException(Exception e) {
        logger.error("GlobalExceptionHandler -- RuntimeException = {e}", e);
        return Result.create(HttpStatus.ERROR, e.getMessage());
    }

    // 缺少权限异常
    @ResponseBody
    @ExceptionHandler(value = NoPermissionException.class)
    public Result<Object> handleAuthorizationException() {
        return Result.create(HttpStatus.FORBIDDEN, "接口无权限，请联系系统管理员", null);
    }
    
    // 未登陆异常
    @ResponseBody
    @ExceptionHandler(value = UnAuthorizedException.class)
    public Result<Object> handleAuthenticationException() {
        return Result.create(HttpStatus.UNAUTHORIZED, "会话已失效，请重新登录", null);
    }
}
```

---

### 2.9、更多用法

#### 2.9.1、使用注解忽略会话验证`@Ignore`
在Controller的方法或类上面添加`@Ignore`注解可排除框架会话拦截，即表示调用接口不用传递token了。


#### 2.9.2、主动让token失效
```java
// 注入authProvider
@Autowired
private AuthProvider authProvider;

// 根据token，使token失效
authProvider.deleteToken(token);

// 根据用户loginId，使该用户的全部token都失效
authProvider.deleteTokenByLoginId(loginId);
```

---

### 2.10、前端传递token
1. 放在参数里面用`token`传递：
```javascript
$.get("/xxx", { "token": token }, function(data) {

});
```
2. 放在header里面用`token`传递：
```javascript
$.ajax({
   url: "/xxx", 
   beforeSend: function(xhr) {
       xhr.setRequestHeader("token", token);
   },
   success: function(data){ }
});
```
3. 前后端不分离的项目会自动从cookie里获取`token`

---

### 2.11、自定义AuthProvider
框架内置了JdbcAuthProvider、RedisAuthProvider和SingleAuthProvider三种会话实现，
如果仍然无法满足你的需求，或者你想存在其他什么地方，比如存在磁盘、MongoDB中，只需以下三步即可：
- 实现org.bluewind.authclient.provider.AuthProvider接口，或者继承org.bluewind.authclient.provider.AbstractAuthProvider抽象类， 实现里面的抽象方法，
- 注入bean，如下
```java
   @Component
   public class MongoAuthProvider extends AbstractAuthProvider {
        // ...
   }
```
- 删除store-type的配置