## 认证服务器

主要用于提供token，第三方认证服务器主体

### 注解

    @EnableResourceServer

用于启动认证服务器，用在配置文件
com.buxiaoxia.system.config.OAuth2AuthorizationServerConfig 上


### 几个默认配置
* 初始化schema的sql，初始化init.sql
* 插入oauth_client_details表，初始化几个clientId
* com.buxiaoxia.system.WebSecurityConfig 类中初始化了两个账户

### 认证token获取
#### code 获取

    http://localhost:8081/oauth/authorize?client_id=barClientIdPassword&response_type=code&redirect_uri=http://www.baidu.com

* client_id:数据库中的client_id
* response_type:响应类型
* redirect_uri:重定向的url

    返回： https://www.baidu.com/?code=d9EDuj

#### token获取

    http://localhost:8081/oauth/token?grant_type=authorization_code&code=d9EDuj&redirect_uri=http://www.baidu.com&client_id=barClientIdPassword&client_secret=secret


* grant_type:授权类型 
* code:code码
* redirect_uri:重定向的url
* client_id:数据库中的client_id
* client_secret:数据库中的client_secret
 
    返回：
    
    
    {
      "access_token": "7d153056-34ca-471a-822c-a0d585dd360f",
      "token_type": "bearer",
      "refresh_token": "3294eebc-22dd-4f29-a591-48552eacad46",
      "expires_in": 35085,
      "scope": "bar read write",
      "organization": "john"
    }

### 结合用户信息存储数据库

* 自定义用户信息User实现UserDetails接口
* 自定义CustomUserService实现UserDetailsService接口
* WebSecurityConfigurerAdapter复写globalUserDetails方法指定用户信息读取方式

 
### 认证资源服务器

主要用于校验token,以及用户信息的获取

### 注解

    @EnableResourceServer

用于启动认证服务器，用在配置文件
com.buxiaoxia.system.config.ResourceServerConfiguration 上

### 用户接口实现

查看Application类