# jun_lowcode_api-demo

### 介绍
jun_lowcode_api 集成Demo 

### 引入jar包
- maven引入jar包
```xml
 <dependency>
    <groupId>io.github.wujun728</groupId>
    <artifactId>api-core</artifactId>
    <version>1.0.0</version>
</dependency>

```
### 启动类添加注解 
- 启动类添加注解 @ComponentScan({"io.github.wujun728"})

```java
    
@EnableAsync
@ComponentScan({"io.github.wujun728"})
@SpringBootApplication
public class ApiWebServerApplication{

    public static void main(String[] args) {
        SpringApplication.run(ApiWebServerApplication.class, args);
    }
}
```

### 添加控制器类

- 添加控制器类 [ApiRequestController](src/main/java/io/github/wujun728/web/server/controller/ApiRequestController.java)


### 全局异常处理类
- 如需要可以添加全局异常处理类 [GlobalExceptionHandler](src/main/java/io/github/wujun728/web/server/config/GlobalExceptionHandler.java)， 注解@RestControllerAdvice中basePackages添加包io.github.wujun728.api.core.controller
- 添加io.github.wujun728.api.common.exception.BusinessException类的异常处理

