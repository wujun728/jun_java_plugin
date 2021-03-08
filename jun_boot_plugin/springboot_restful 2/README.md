## Spring Boot Example

This is a basic example of how to use Kotlin in a Spring Boot application. See the [accompanying tutorial](http://kotlinlang.org/docs/tutorials/spring-boot-restful.html)
for more information.

To run:

```
$ gradle bootRun

```

## Api request flow 
对验证接口发起一个post请求,header 带上Username和Password
```
POST http://localhost:8080/api/v1/authenticate

add header
X-Auth-Username:user
X-Auth-Password:password
```
 
得到token,这里的message
```
{
"status": 200,
"message": "MTUwMDYzMjE2OTM0NGY0ZjQwMDk5NzMwYzQwNTdhNGZhM2U3ZTY5ZDIxNWJk"
}
```
最后请求其他接口的时候,在header带上这个token

```
GET  http://localhost:8080/api/v1/users/1?page=1
add header
X-Authorization:MTUwMDYzMjE2OTM0NGY0ZjQwMDk5NzMwYzQwNTdhNGZhM2U3ZTY5ZDIxNWJk

```


## Features

* restful 风格api举例
* Token 身份验证,token过期处理,使用过滤器
* 方法级别权限控制api接口
* 处理异常自定返回结果 
* servlet异步响应
* model数据处理 



