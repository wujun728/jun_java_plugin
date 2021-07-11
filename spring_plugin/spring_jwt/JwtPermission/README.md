# JwtPermission 

![MavenCentral](https://img.shields.io/maven-central/v/com.github.whvcse/jwtp-spring-boot-starter?style=flat-square)
![Hex.pm](https://img.shields.io/hexpm/l/plug.svg?style=flat-square)


## 更新日志

- 2020-01-14 （v3.1.1）
    - 使用RedisTokenStore不需要jdbc相关的包及配置
    - 增加支持统一身份认证（单点登录）功能
    - 对于排除拦截的接口也提供获取当前用户信息的方法

- 2019-12-16 （v3.0.0）
    - 增加refresh_token机制
    - 增加@Ignore注解(用于忽略验证)
    - 增加url自动匹配权限机制(自带RESTful模式模式和简化模式)
    - 增加自定义查询权限和角色的sql配置


## 1、简介

基于token验证的Java Web权限控制框架，使用jjwt，支持redis和db多种存储方式，可用于前后端分离项目，功能完善、使用简单、易于扩展。
&emsp;[详细开发文档](https://gitee.com/whvse/JwtPermission/wikis/pages)

---

## 2、使用

### 2.1、SpringBoot集成

#### 2.1.1、导入
```xml
<dependency>
  <groupId>com.github.whvcse</groupId>
  <artifactId>jwtp-spring-boot-starter</artifactId>
  <version>3.1.1</version>
</dependency>
```

#### 2.1.2、加注解

在`Application`启动类上面加入`@EnableJwtPermission`注解。

#### 2.1.3、配置

```text
## 0是 redisTokenStore ，1是 jdbcTokenStore ，默认是0
jwtp.store-type=0

## 拦截路径，默认是/**
jwtp.path=/**

## 排除拦截路径，默认无
jwtp.exclude-path=/login

## 单个用户最大token数，默认-1不限制
jwtp.max-token=10

## url自动对应权限方式，0 简易模式，1 RESTful模式
# jwtp.url-perm-type=0

## 自定义查询用户权限的sql
# jwtp.find-permissions-sql=SELECT authority FROM sys_user_authorities WHERE user_id = ?

## 自定义查询用户角色的sql
# jwtp.find-roles-sql=SELECT role_id FROM sys_user_role WHERE user_id = ?

## 日志级别设置debug可以输出详细信息
logging.level.org.wf.jwtp=DEBUG
```

> 如果使用jdbcTokenStore需要导入框架提供的sql脚本，如果使用redisTokenStore，需要集成好redis

---

### 2.2、登录签发token

```java
@RestController
public class LoginController {
    @Autowired
    private TokenStore tokenStore;
    
    @PostMapping("/token")
    public Map token(String account, String password) {
        // 你的验证逻辑
        // ......
        // 签发token
        Token token = tokenStore.createNewToken(userId, permissions, roles, expire);
        System.out.println("access_token：" + token.getAccessToken());
    }
}
```

createNewToken方法参数说明：

- userId   &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&nbsp;&nbsp;   token载体，建议为用户id
- permissions   &emsp;&emsp;&emsp;&emsp;   权限列表
- roles   &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&nbsp;   角色列表
- expire   &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&nbsp;&nbsp;&nbsp;   token过期时间(单位秒)

> 关于createNewToken方法的详细介绍以及refresh_token机制的用法请在详细文档中查看


---

### 2.3、使用注解或代码限制权限

**1.使用注解的方式：**

```text
// 需要有system权限才能访问
@RequiresPermissions("system")

// 需要有system和front权限才能访问,logical可以不写,默认是AND
@RequiresPermissions(value={"system","front"}, logical=Logical.AND)

// 需要有system或front权限才能访问
@RequiresPermissions(value={"system","front"}, logical=Logical.OR)

// 需要有admin或user角色才能访问
@RequiresRoles(value={"admin","user"}, logical=Logical.OR)
```

> 注解加在Controller的方法或类上面。

<br/>

**2.使用代码的方式：**

```text
//是否有system权限
SubjectUtil.hasPermission(request, "system");

//是否有system或者front权限
SubjectUtil.hasPermission(request, new String[]{"system","front"}, Logical.OR);

//是否有admin或者user角色
SubjectUtil.hasRole(request, new String[]{"admin","user"}, Logical.OR)
```

---

## 2.4、异常处理
JwtPermistion在token验证失败和没有权限的时候会抛出异常：
    
| 异常                  | 描述          | 错误信息                          |
|:----------------------|:-------------|:----------------------------------|
| ErrorTokenException   | token验证失败 | 错误信息“身份验证失败”，错误码401 |
| ExpiredTokenException | token已经过期 | 错误信息“登录已过期”，错误码402   |
| UnauthorizedException | 没有权限      | 错误信息“没有访问权限”，错误码403 |

建议使用异常处理器来捕获异常并返回json数据：
```java
@ControllerAdvice
public class ExceptionHandler {

   @ResponseBody
   @ExceptionHandler(Exception.class)
   public Map<String, Object> errorHandler(Exception ex) {
       Map<String, Object> map = new HashMap<>();
       // 根据不同错误获取错误信息
       if (ex instanceof TokenException) {
           map.put("code", ((TokenException) ex).getCode());
           map.put("msg", ex.getMessage());
       } else {
           map.put("code", 500);
           map.put("msg", ex.getMessage());
           ex.printStackTrace();
       }
       return map;
   }
}
```

---

## 2.5、更多用法

### 2.5.1、使用注解忽略验证
在Controller的方法或类上面添加`@Ignore`注解可排除框架拦截，即表示调用接口不用传递access_token了。


### 2.5.2、自定义查询角色和权限的sql

如果是在签发token的时候指定权限和角色，不重新获取token，不主动更新权限，权限和角色不会实时更新，
可以配置自定义查询角色和权限的sql来实时查询用户的权限和角色：

```text
## 自定义查询用户权限的sql
jwtp.find-permissions-sql=SELECT authority FROM sys_user_authorities WHERE user_id = ?

## 自定义查询用户角色的sql
jwtp.find-roles-sql=SELECT role_id FROM sys_user_role WHERE user_id = ?
```


### 2.5.3、url自动匹配权限
如果不想每个接口都加@RequiresPermissions注解来控制权限，可以配置url自动匹配权限：

```text
## url自动对应权限方式，0 简易模式，1 RESTful模式
jwtp.url-perm-type=0
```

RESTful模式(请求方式:url)：post:/api/login

简易模式(url)：/api/login

> 配置了自动匹配也可以同时使用注解，注解优先级高于自动匹配，你还可以借助Swagger自动扫描所有接口生成权限到数据库权限表中


### 2.5.4、获取当前的用户信息
```text
// 正常可以这样获取
Token token = SubjectUtil.getToken(request);

// 对于排除拦截的接口可以这样获取
Token token = SubjectUtil.parseToken(request);
```   


### 2.5.5、主动让token失效：
```text
// 移除用户的某个token
tokenStore.removeToken(userId, access_token);

// 移除用户的全部token
tokenStore.removeTokensByUserId(userId);
```


### 2.5.6、更新角色和权限列表
修改了用户的角色和权限需要同步更新框架中的角色和权限：
```text
// 更新用户的角色列表
tokenStore.updateRolesByUserId(userId, roles);

// 更新用户的权限列表
tokenStore.updatePermissionsByUserId(userId, permissions);
```

---

## 2.6、前端传递token
放在参数里面用`access_token`传递：
```javascript
$.get("/xxx", { access_token: token }, function(data) {

});
```
放在header里面用`Authorization`、`Bearer`传递： 
```javascript
$.ajax({
   url: "/xxx", 
   beforeSend: function(xhr) {
       xhr.setRequestHeader("Authorization", 'Bearer '+ token);
   },
   success: function(data){ }
});
```

---

## 联系方式
前后端分离技术交流群：

![群二维码](https://s2.ax1x.com/2019/07/06/Zw83O1.jpg)

