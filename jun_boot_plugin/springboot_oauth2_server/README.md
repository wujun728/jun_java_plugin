# Spring Boot Oauth2-master

Oauth2支持的授权方式目前有5类

| **GRANT_TYPE** | **Description** |
|:-----------------|:------------------------------------------------------------------------|
|authorization_code|授权码模式(即先登录获取code，再获取token) [最常用]                           |
|password          |密码模式(将用户名、密码传过去，直接获取token) [适用于移动设备]                 |
|client_credentials|客户端模式(无用户，用户向客户端注册，然后客户端以自己的名义向'服务端'获取资源)   |
|implicit          |简化模式(在redirect_uri的Hash传递token，客户端运行在浏览器中，如JS、Flash)    |
|refresh_token     |更新access_token                                                          |

## 1. authorization_code
* 申请code
```
http://localhost:8080/oauth/authorize?response_type=code&scope=read write&client_id=curl-client&redirect_uri=http://www.baidu.com&state=b375bc2b-25f7-4dce-9b36-5f9e2d20bda1
```
* 登录
* 是否允许
* 返回code
```
http://www.baidu.com/?code=kG4F2N&state=b375bc2b-25f7-4dce-9b36-5f9e2d20bda1
```
* 申请access_token
```
curl -X POST http://localhost:8080/oauth/token -H "Accept: application/json" -d "client_id=curl-client&client_secret=client-secret&grant_type=authorization_code&code=kG4F2N&redirect_uri=http://www.baidu.com"
```
* 返回access_token
```
{"access_token":"30626e87-761f-410c-9497-84b29d310dd7","token_type":"bearer","refresh_token":"0843fbec-20e3-4802-93a0-357488403924","expires_in":29,"scope":"read write"}
```
* 请求资源
```
curl http://localhost:8080/user/ping -H "Authorization: Bearer 30626e87-761f-410c-9497-84b29d310dd7"
```

## 2. password
* 申请access_token
```
curl -X POST http://localhost:8080/oauth/token -H "Accept: application/json" -d "grant_type=password&scope=read%20write&client_id=curl-client&client_secret=client-secret&username=nangzi&password=nangzi"
```
* 返回access_token
```
{"access_token":"9ac3fe0f-f380-4149-8fca-19a72374365d","token_type":"bearer","refresh_token":"0843fbec-20e3-4802-93a0-357488403924","expires_in":29,"scope":"read write"}
```
* 请求资源
```
curl http://localhost:8080/user/ping -H "Authorization: Bearer 9ac3fe0f-f380-4149-8fca-19a72374365d"
```

## 3. client_credentials
* 申请access_token
```
curl -X POST http://localhost:8080/oauth/token -H "Accept: application/json" -d "grant_type=client_credentials&scope=read%20write&client_id=curl-client&client_secret=client-secret"
```
* 返回access_token
```
{"access_token":"904953fc-f446-49f3-9258-06d0f6cfba5b","token_type":"bearer","expires_in":29,"scope":"read write"}
```
* 请求资源
```
curl http://localhost:8080/user/ping -H "Authorization: Bearer 904953fc-f446-49f3-9258-06d0f6cfba5b"
```

## 4. implicit
`是否需要登录待确认`
* 请求access_token
```
http://localhost:8080/oauth/authorize?response_type=token&scope=read write&client_id=curl-client&client_secret=client-secret&redirect_uri=http://www.baidu.com&state=b375bc2b-25f7-4dce-9b36-5f9e2d20bda1
```
* 登录
* 是否允许
* 返回access_token
```
http://www.baidu.com/#access_token=aa7779b0-b98e-4739-b0d7-922a811ed134&token_type=bearer&state=b375bc2b-25f7-4dce-9b36-5f9e2d20bda1&expires_in=29
```
* 请求资源
```
curl http://localhost:8080/user/ping -H "Authorization: Bearer aa7779b0-b98e-4739-b0d7-922a811ed134"
```

## 5. refresh_token
* 请求refresh_token
```
curl -X POST http://localhost:8080/oauth/token -H "Accept: application/json" -d "grant_type=refresh_token&refresh_token=0843fbec-20e3-4802-93a0-357488403924&client_id=curl-client&client_secret=client-secret"
```
* 返回access_token
```
{"access_token":"6aa17ed5-e684-4dfa-90a8-61b2ad7c7615","token_type":"bearer","refresh_token":"0843fbec-20e3-4802-93a0-357488403924","expires_in":29,"scope":"read write"}
```
* 请求资源
```
curl http://localhost:8080/user/ping -H "Authorization: Bearer 6aa17ed5-e684-4dfa-90a8-61b2ad7c7615"
```
