## 认证客户端-服务端调用

### 服务端调用

查看spring-boot-oauth-server内的readme.md


### 客户端调用

修改application.yml中的 my 下面的属性


### 问题
#### 问题1

    Possible CSRF detected - state parameter was required but no state could be found
 
出现这个问题的解决请查看：https://github.com/spring-projects/spring-security-oauth/issues/822


## 认证客户端-服务端结合JWT做认证以及携带用户信息

查看spring-boot-oauth-jwt-server内的readme.md