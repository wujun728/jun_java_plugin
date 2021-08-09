# Spring Cloud实战小贴士：Zuul统一异常处理（一）

**原创**

 [2017-05-14](https://blog.didispace.com/spring-cloud-zuul-exception/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

在上一篇[《Spring Cloud源码分析（四）Zuul：核心过滤器》](http://blog.didispace.com/spring-cloud-source-zuul/)一文中，我们详细介绍了Spring Cloud Zuul中自己实现的一些核心过滤器，以及这些过滤器在请求生命周期中的不同作用。我们会发现在这些核心过滤器中并没有实现error阶段的过滤器。那么这些过滤器可以用来做什么呢？接下来，本文将介绍如何利用error过滤器来实现统一的异常处理。

## 过滤器中抛出异常的问题

首先，我们可以来看看默认情况下，过滤器中抛出异常Spring Cloud Zuul会发生什么现象。我们创建一个pre类型的过滤器，并在该过滤器的run方法实现中抛出一个异常。比如下面的实现，在run方法中调用的`doSomething`方法将抛出`RuntimeException`异常。

```
public class ThrowExceptionFilter extends ZuulFilter  {

    private static Logger log = LoggerFactory.getLogger(ThrowExceptionFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        log.info("This is a pre filter, it will throw a RuntimeException");
        doSomething();
        return null;
    }

    private void doSomething() {
        throw new RuntimeException("Exist some errors...");
    }
  
}
```

运行网关程序并访问某个路由请求，此时我们会发现：在API网关服务的控制台中输出了`ThrowExceptionFilter`的过滤逻辑中的日志信息，但是并没有输出任何异常信息，同时发起的请求也没有获得任何响应结果。为什么会出现这样的情况呢？我们又该如何在过滤器中处理异常呢？

## 解决方案一：严格的try-catch处理

回想一下，我们在上一节中介绍的所有核心过滤器，是否还记得有一个`post`过滤器`SendErrorFilter`是用来处理异常信息的？根据正常的处理流程，该过滤器会处理异常信息，那么这里没有出现任何异常信息说明很有可能就是这个过滤器没有被执行。所以，我们不妨来详细看看`SendErrorFilter`的`shouldFilter`函数：

```
public boolean shouldFilter() {
	RequestContext ctx = RequestContext.getCurrentContext();
	return ctx.containsKey("error.status_code") && !ctx.getBoolean(SEND_ERROR_FILTER_RAN, false);
}
```

可以看到该方法的返回值中有一个重要的判断依据`ctx.containsKey("error.status_code")`，也就是说请求上下文中必须有`error.status_code`参数，我们实现的`ThrowExceptionFilter`中并没有设置这个参数，所以自然不会进入`SendErrorFilter`过滤器的处理逻辑。那么我们要如何用这个参数呢？我们可以看一下`route`类型的几个过滤器，由于这些过滤器会对外发起请求，所以肯定会有异常需要处理，比如`RibbonRoutingFilter`的`run`方法实现如下：

```
public Object run() {
	RequestContext context = RequestContext.getCurrentContext();
	this.helper.addIgnoredHeaders();
	try {
		RibbonCommandContext commandContext = buildCommandContext(context);
		ClientHttpResponse response = forward(commandContext);
		setResponse(response);
		return response;
	}
	catch (ZuulException ex) {
		context.set(ERROR_STATUS_CODE, ex.nStatusCode);
		context.set("error.message", ex.errorCause);
		context.set("error.exception", ex);
	}
	catch (Exception ex) {
		context.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		context.set("error.exception", ex);
	}
	return null;
}
```

可以看到，整个发起请求的逻辑都采用了`try-catch`块处理。在`catch`异常的处理逻辑中并没有做任何输出操作，而是往请求上下文中添加一些`error`相关的参数，主要有下面三个参数：

- `error.status_code`：错误编码
- `error.exception`：`Exception`异常对象
- `error.message`：错误信息

其中，`error.status_code`参数就是`SendErrorFilter`过滤器用来判断是否需要执行的重要参数。分析到这里，实现异常处理的大致思路就开始明朗了，我们可以参考`RibbonRoutingFilter`的实现对`ThrowExceptionFilter`的`run`方法做一些异常处理的改造，具体如下：

```
public Object run() {
    log.info("This is a pre filter, it will throw a RuntimeException");
    RequestContext ctx = RequestContext.getCurrentContext();
    try {
        doSomething();
    } catch (Exception e) {
        ctx.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        ctx.set("error.exception", e);
    }
  	return null;
}
```

通过上面的改造之后，我们再尝试访问之前的接口，这个时候我们可以得到如下响应内容：

```
{
  "timestamp": 1481674980376,
  "status": 500,
  "error": "Internal Server Error",
  "exception": "java.lang.RuntimeException",
  "message": "Exist some errors..."
}
```

此时，我们的异常信息已经被`SendErrorFilter`过滤器正常处理并返回给客户端了，同时在网关的控制台中也输出了异常信息。从返回的响应信息中，我们可以看到几个我们之前设置在请求上下文中的内容，它们的对应关系如下：

- `status`：对应`error.status_code`参数的值
- `exception`：对应`error.exception`参数中`Exception`的类型
- `message`：对应`error.exception`参数中`Exception`的`message`信息。对于`message`的信息，我们在过滤器中还可以通过`ctx.set("error.message", "自定义异常消息");`来定义更友好的错误信息。`SendErrorFilter`会优先取`error.message`来作为返回的`message`内容，如果没有的话才会使用`Exception`中的`message`信息

## 解决方案二：ErrorFilter处理

通过上面的分析与实验，我们已经知道如何在过滤器中正确的处理异常，让错误信息能够顺利地流转到后续的`SendErrorFilter`过滤器来组织和输出。但是，即使我们不断强调要在过滤器中使用`try-catch`来处理业务逻辑并往请求上下文添加异常信息，但是不可控的人为因素、意料之外的程序因素等，依然会使得一些异常从过滤器中抛出，对于意外抛出的异常又会导致没有控制台输出也没有任何响应信息的情况出现，那么是否有什么好的方法来为这些异常做一个统一的处理呢？

这个时候，我们就可以用到`error`类型的过滤器了。由于在请求生命周期的`pre`、`route`、`post`三个阶段中有异常抛出的时候都会进入`error`阶段的处理，所以我们可以通过创建一个`error`类型的过滤器来捕获这些异常信息，并根据这些异常信息在请求上下文中注入需要返回给客户端的错误描述，这里我们可以直接沿用在`try-catch`处理异常信息时用的那些error参数，这样就可以让这些信息被`SendErrorFilter`捕获并组织成消息响应返回给客户端。比如，下面的代码就实现了这里所描述的一个过滤器：

```
public class ErrorFilter extends ZuulFilter {

    Logger log = LoggerFactory.getLogger(ErrorFilter.class);

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        Throwable throwable = ctx.getThrowable();
        log.error("this is a ErrorFilter : {}", throwable.getCause().getMessage());
        ctx.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        ctx.set("error.exception", throwable.getCause());
        return null;
    }

}
```

在将该过滤器加入到我们的API网关服务之后，我们可以尝试使用之前介绍`try-catch`处理时实现的`ThrowExceptionFilter`（不包含异常处理机制的代码），让该过滤器能够抛出异常。这个时候我们再通过API网关来访问服务接口。此时，我们就可以在控制台中看到`ThrowExceptionFilter`过滤器抛出的异常信息，并且请求响应中也能获得如下的错误信息内容，而不是什么信息都没有的情况了。

```
{
  "timestamp": 1481674993561,
  "status": 500,
  "error": "Internal Server Error",
  "exception": "java.lang.RuntimeException",
  "message": "Exist some errors..."
}
```

**本文节选自《Spring Cloud微服务实战》，部分稍做加工，转载请注明出处**

------

**相关阅读**

- [《Spring Cloud实战小贴士：Zuul统一异常处理（二）》](http://blog.didispace.com/spring-cloud-zuul-exception-2/)
- [《Spring Cloud源码分析（四）Zuul：核心过滤器》](http://blog.didispace.com/spring-cloud-source-zuul/)
- [《Spring Cloud实战小贴士：Zuul处理Cookie和重定向》](http://blog.didispace.com/spring-cloud-zuul-cookie-redirect/)
- [《Spring Cloud构建微服务架构（五）服务网关》](http://blog.didispace.com/springcloud5/)