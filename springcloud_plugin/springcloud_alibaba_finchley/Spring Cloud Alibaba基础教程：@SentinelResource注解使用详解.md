# Spring Cloud Alibaba基础教程：@SentinelResource注解使用详解

**原创**

 [2019-06-27](https://blog.didispace.com/spring-cloud-alibaba-sentinel-2-5/)

 翟永超

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

在之前的[《使用Sentinel实现接口限流》](http://blog.didispace.com/spring-cloud-alibaba-sentinel-1/)一文中，我们仅依靠引入Spring Cloud Alibaba对Sentinel的整合封装`spring-cloud-starter-alibaba-sentinel`，就完成了对所有Spring MVC接口的限流控制。然而，在实际应用过程中，我们可能需要限流的层面不仅限于接口。可能对于某个方法的调用限流，对于某个外部资源的调用限流等都希望做到控制。呢么，这个时候我们就不得不手工定义需要限流的资源点，并配置相关的限流策略等内容了。

今天这篇我们就来一起学习一下，如何使用`@SentinelResource`注解灵活的定义控制资源以及如何配置控制策略。

## 自定义资源点

下面的例子基于您已经引入了Spring Cloud Alibaba Sentinel为基础，如果您还不会这些，建议优先阅读[《使用Sentinel实现接口限流》](http://blog.didispace.com/spring-cloud-alibaba-sentinel-1/)。

第一步：在应用主类中增加注解支持的配置：

```
@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    // 注解支持的配置Bean
    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

}
```

第二步：在需要通过Sentinel来控制流量的地方使用`@SentinelResource`注解，比如下面以控制Service逻辑层的某个方法为例：

```
@Slf4j
@Service
public class TestService {

    @SentinelResource(value = "doSomeThing")
    public void doSomeThing(String str) {
        log.info(str);
    }

}
```

到这里一个需要被保护的方法就定义完成了。下面我们分别说说，定义了资源点之后，我们如何实现不同的保护策略，包括：限流、降级等。

## 如何实现限流与熔断降级

在定义了资源点之后，我们就可以通过Dashboard来设置限流和降级策略来对资源点进行保护了。同时，也可以通过`@SentinelResource`来指定出现限流和降级时候的异常处理策略。下面，就来一起分别看看限流和降级都是如何实现的。

### 实现限流控制

第一步：在Web层调用这个被保护的方法：

```
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/hello")
    public String hello() {
        estService.doSomeThing("hello " + new Date());
        return "didispace.com";
    }

}
```

第二步：启动测试应用，启动Sentinel-Dashboard。发一个请求到`/hello`接口上，使得Sentinel-Dashboard上可以看到如下图所示的几个控制点：

[![img](https://blog.didispace.com/images/pasted-237.png)](https://blog.didispace.com/images/pasted-237.png)

可以看到，除了如之前入门实例中那样有`/hello`资源点之外，多了一个`doSomeThing`资源点。可以通过界面为这个资源点设置限流规则，比如将其QPS设置为2。由于`/hello`资源不设置限流规则，所以只要请求`/hello`接口，就可以直接模拟调用`doSomeThing`资源，来观察限流规则是否生效。

下面可以通过任何你喜欢的工具来调用`/hello`接口，只要QPS超过2，那么就会出现如下的错误返回，代表限流策略生效了。

[![img](https://blog.didispace.com/images/pasted-238.png)](https://blog.didispace.com/images/pasted-238.png)

此时，服务端的控制台也会有对应的限流报错日志：

```
2019-06-27 11:30:43.514  INFO 36898 --- [nio-8001-exec-3] c.d.a.sentinel.service.TestService       : aaa
2019-06-27 11:30:43.905 ERROR 36898 --- [nio-8001-exec-4] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is java.lang.reflect.UndeclaredThrowableException] with root cause

com.alibaba.csp.sentinel.slots.block.flow.FlowException: null
```

### 实现限流的异常处理

默认情况下，Sentinel对控制资源的限流处理是直接抛出异常，也就是上一节中贴出的日志内容。在没有合理的业务承接或者前端对接情况下可以这样，但是正常情况为了更好的用户业务，都会实现一些被限流之后的特殊处理，我们不希望展示一个生硬的报错。那么只需要基于上面的例子做一些加工，比如：

```
@Slf4j
@Service
public class TestService {

    @SentinelResource(value = "doSomeThing", blockHandler = "exceptionHandler")
    public void doSomeThing(String str) {
        log.info(str);
    }

    // 限流与阻塞处理
    public void exceptionHandler(String str, BlockException ex) {
        log.error( "blockHandler：" + str, ex);
    }
    
}
```

主要做了两件事：

- 通过`@SentinelResource`注解的`blockHandler`属性制定具体的处理函数
- 实现处理函数，该函数的传参必须与资源点的传参一样，并且最后加上`BlockException`异常参数；同时，返回类型也必须一样。

> 如果熟悉Hystrix的读者应该会发现，这样的设计与HystrixCommand中定义fallback很相似，还是很容易理解的。

完成上面的改动之后，再尝试访问接口（注意限流规则需要配置好），此时前端就不会返回异常信息了，后端会打印`exceptionHandler`中定义的日志输出。而在实际应用的时候，只要根据业务需要对限流请求做缓存或者前端提示等都可以基于此方法来实现。

### 实现熔断降级

`@SentinelResource`注解除了可以用来做限流控制之外，还能实现与Hystrix类似的熔断降级策略。下面就来具体看看如何使用吧。

第一步：与限流控制一样，使用`@SentinelResource`注解标记资源点，比如：

```
@Slf4j
@Service
public class TestService {

    @SentinelResource(value = "doSomeThing2")
    public void doSomeThing2(String str) {
        log.info(str);
        throw new RuntimeException("发生异常");
    }

}
```

这里在`TestService`类中创建了一个新的方法，并使用`@SentinelResource`将该资源命名为`doSomeThing2`。该方法会抛出异常，以配合后续制定基于异常比例的降级策略（类似Hystrix）。Sentinel相比Hystrix更丰富，还有基于响应时间和异常数的降级策略。

第二步：在Web层调用这个被保护的方法：

```
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/hello2")
    public String hello2() {
        testService.doSomeThing2("hello2 " + new Date());
        return "didispace.com";
    }

}
```

第三步：启动测试应用，启动Sentinel-Dashboard。发一个请求到`/hello2`接口上，使得Sentinel-Dashboard上可以看到名为`doSomeThing2`的资源点。然后点击”降级“按钮，为该资源设置降级规则。这里使用异常比例策略，比例设置为0.5（即：50%的异常率），时间窗口设置为2（秒）。

[![img](https://blog.didispace.com/images/pasted-239.png)](https://blog.didispace.com/images/pasted-239.png)

第四步：验证熔断降级，根据上面的降级策略配置，当`doSomeThing2`方法的调用QPS >= 5，如果异常率超过50%，那么后续2秒内的调用将直接出发熔断降级，默认情况会直接抛出`DegradeException`异常，比如：

```
2019-06-27 17:49:58.913 ERROR 99863 --- [nio-8001-exec-2] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is java.lang.reflect.UndeclaredThrowableException] with root cause

com.alibaba.csp.sentinel.slots.block.degrade.DegradeException: null
```

### 熔断的降级处理

在Sentinel中定义熔断的降级处理方法非常简单，与Hystrix非常相似。只需要使用`@SentinelResource`注解的`fallback`属性来指定具体的方法名即可。这里也需要注意传参与返回必须一致。比如：

```
@Slf4j
@Service
public class TestService {

    // 熔断与降级处理
    @SentinelResource(value = "doSomeThing2", fallback = "fallbackHandler")
    public void doSomeThing2(String str) {
        log.info(str);
        throw new RuntimeException("发生异常");
    }

    public void fallbackHandler(String str) {
        log.error("fallbackHandler：" + str);
    }
}
```

完成上面的改造之后，重启应用，并设置`doSomeThing2`资源的熔断降级策略（使用异常百分比），然后频繁的请求`/hello2`接口。在QPS>=5之后，由于这个接口一直在抛出异常，所以一定会满足熔断降级条件，这时候就会执行`fallbackHandler`方法，不断的打印如下日志：

```
2019-06-27 23:44:19.432 ERROR 58471 --- [nio-8001-exec-1] c.d.a.sentinel.service.TestService       : fallbackHandler：hello2 Thu Jun 27 23:44:19 CST 2019
2019-06-27 23:44:19.599 ERROR 58471 --- [nio-8001-exec-2] c.d.a.sentinel.service.TestService       : fallbackHandler：hello2 Thu Jun 27 23:44:19 CST 2019
2019-06-27 23:44:19.791 ERROR 58471 --- [nio-8001-exec-3] c.d.a.sentinel.service.TestService       : fallbackHandler：hello2 Thu Jun 27 23:44:19 CST 2019
2019-06-27 23:44:19.975 ERROR 58471 --- [nio-8001-exec-4] c.d.a.sentinel.service.TestService       : fallbackHandler：hello2 Thu Jun 27 23:44:19 CST 2019
2019-06-27 23:44:20.168 ERROR 58471 --- [nio-8001-exec-5] c.d.a.sentinel.service.TestService       : fallbackHandler：hello2 Thu Jun 27 23:44:20 CST 2019
```

## 更多注解属性说明

关于`@SentinelResource`注解最主要的两个用法：限流控制和熔断降级的具体使用案例介绍完了。另外，该注解还有一些其他更精细化的配置，比如忽略某些异常的配置、默认降级函数等等，具体可见如下说明：

- `value`：资源名称，必需项（不能为空）

- `entryType`：entry 类型，可选项（默认为 `EntryType.OUT`）

- `blockHandler` / `blockHandlerClass`: `blockHandler`对应处理 `BlockException` 的函数名称，可选项。blockHandler 函数访问范围需要是 `public`，返回类型需要与原方法相匹配，参数类型需要和原方法相匹配并且最后加一个额外的参数，类型为 `BlockException`。blockHandler 函数默认需要和原方法在同一个类中。若希望使用其他类的函数，则可以指定 `blockHandlerClass` 为对应的类的 `Class` 对象，注意对应的函数必需为 static 函数，否则无法解析。

- ```
  fallback
  ```

  ：fallback 函数名称，可选项，用于在抛出异常的时候提供 fallback 处理逻辑。fallback 函数可以针对所有类型的异常（除了

  ```
  exceptionsToIgnore
  ```

  里面排除掉的异常类型）进行处理。fallback 函数签名和位置要求：

  - 返回值类型必须与原函数返回值类型一致；
  - 方法参数列表需要和原函数一致，或者可以额外多一个 `Throwable` 类型的参数用于接收对应的异常。
  - fallback 函数默认需要和原方法在同一个类中。若希望使用其他类的函数，则可以指定 `fallbackClass` 为对应的类的 `Class` 对象，注意对应的函数必需为 static 函数，否则无法解析。

- ```
  defaultFallback
  ```

  （since 1.6.0）：默认的 fallback 函数名称，可选项，通常用于通用的 fallback 逻辑（即可以用于很多服务或方法）。默认 fallback 函数可以针对所有类型的异常（除了

  ```
  exceptionsToIgnore
  ```

  里面排除掉的异常类型）进行处理。若同时配置了 fallback 和 defaultFallback，则只有 fallback 会生效。defaultFallback 函数签名要求：

  - 返回值类型必须与原函数返回值类型一致；
  - 方法参数列表需要为空，或者可以额外多一个 `Throwable` 类型的参数用于接收对应的异常。
  - defaultFallback 函数默认需要和原方法在同一个类中。若希望使用其他类的函数，则可以指定 `fallbackClass` 为对应的类的 `Class` 对象，注意对应的函数必需为 static 函数，否则无法解析。

- `exceptionsToIgnore`（since 1.6.0）：用于指定哪些异常被排除掉，不会计入异常统计中，也不会进入 fallback 逻辑中，而是会原样抛出。

> 注：1.6.0 之前的版本 fallback 函数只针对降级异常（`DegradeException`）进行处理，**不能针对业务异常进行处理**。

特别地，若 blockHandler 和 fallback 都进行了配置，则被限流降级而抛出 `BlockException` 时只会进入 `blockHandler` 处理逻辑。若未配置 `blockHandler`、`fallback` 和 `defaultFallback`，则被限流降级时会将 `BlockException` **直接抛出**。

> **参考资料**：[Sentinel官方文档](https://github.com/alibaba/Sentinel/wiki/介绍)
>
> **版本说明**：本文基于spring-cloud-alibaba-dependencies版本为0.2.2，如您遇到特殊问题，请先核对版本是否一致，或直接参考代码示例核对具体案例。

## 代码示例

本文介绍内容的客户端代码，示例读者可以通过查看下面仓库中的`alibaba-sentinel-annotation`项目：

- *Github：*[https://github.com/dyc87112/SpringCloud-Learning/](https://github.com/dyc87112/SpringCloud-Learning/tree/master/4-Finchley)
- *Gitee：*[https://gitee.com/didispace/SpringCloud-Learning/](https://gitee.com/didispace/SpringCloud-Learning/tree/master/4-Finchley)