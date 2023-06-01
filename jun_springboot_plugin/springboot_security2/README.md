# simple-security

#### 项目介绍
基于spring boot+spring security+jwt实现的基础auth机制。  

#### 快速开始
在SpringBootApplication上添加@EnableWebSecurityJwt。  
同时需要自己实现一个UserDetailsService。  

登陆接口路径： /auth/token

#### 属性配置项
- jwt.filter.header 请求header内的key
- jwt.filter.tokenHead 请求header内的key对应value 的默认开头 如Bearer
- jwt.filter.exceptUrl 过滤路径 如 /auth/**
- jwt.payload.secret payload秘钥
- jwt.payload.issuer jwt签发者名称
- jwt.payload.audience 接收jwt的一方
- jwt.payload.expirationMinute 过期时间 ( 分钟 ) 默认是一天
- jwt.payload.notBeforeMinute NotBefore ( 分钟 ) 默认是15分钟

#### 常见问题
##### jackson-databind发生冲突？  
```
<dependency>
    <groupId>org.simple</groupId>
    <artifactId>web-security-jwt</artifactId>
    <version>2.0.3.RELEASE</version>
    <exclusions>
        <exclusion>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```