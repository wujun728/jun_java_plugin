# Spring Cloud实战小贴士：Zuul统一异常处理（二）

**原创**

 [2017-05-18](https://blog.didispace.com/spring-cloud-zuul-exception-2/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

在前几天发布的[《Spring Cloud实战小贴士：Zuul统一异常处理（一）》](http://blog.didispace.com/spring-cloud-zuul-exception/)一文中，我们详细说明了当Zuul的过滤器中抛出异常时会发生客户端没有返回任何内容的问题以及针对这个问题的两种解决方案：一种是通过在各个阶段的过滤器中增加`try-catch`块，实现过滤器内部的异常处理；另一种是利用`error`类型过滤器的生命周期特性，集中地处理`pre`、`route`、`post`阶段抛出的异常信息。通常情况下，我们可以将这两种手段同时使用，其中第一种是对开发人员的基本要求；而第二种是对第一种处理方式的补充，以防止一些意外情况的发生。这样的异常处理机制看似已经完美，但是如果在多一些应用实践或源码分析之后，我们会发现依然存在一些不足。

## 不足之处

下面，我们不妨跟着源码来看看，到底上面的方案还有哪些不足之处需要我们注意和进一步优化的。先来看看外部请求到达API网关服务之后，各个阶段的过滤器是如何进行调度的：

```
try {
    preRoute();
} catch (ZuulException e) {
    error(e);
    postRoute();
    return;
}
try {
    route();
} catch (ZuulException e) {
    error(e);
    postRoute();
    return;
}
try {
    postRoute();
} catch (ZuulException e) {
    error(e);
    return;
}
```

上面代码源自`com.netflix.zuul.http.ZuulServlet`的`service`方法实现，它定义了Zuul处理外部请求过程时，各个类型过滤器的执行逻辑。从代码中我们可以看到三个`try-catch`块，它们依次分别代表了`pre`、`route`、`post`三个阶段的过滤器调用，在`catch`的异常处理中我们可以看到它们都会被`error`类型的过滤器进行处理（之前使用`error`过滤器来定义统一的异常处理也正是利用了这个特性）；`error`类型的过滤器处理完毕之后，除了来自`post`阶段的异常之外，都会再被`post`过滤器进行处理。而对于从`post`过滤器中抛出异常的情况，在经过了`error`过滤器处理之后，就没有其他类型的过滤器来接手了，这就是使用之前所述方案存在不足之处的根源。

## 问题分析与进一步优化

回想一下之前实现的两种异常处理方法，其中非常核心的一点，这两种处理方法都在异常处理时候往请求上下文中添加了一系列的`error.*`参数，而这些参数真正起作用的地方是在`post`阶段的`SendErrorFilter`，在该过滤器中会使用这些参数来组织内容返回给客户端。而对于`post`阶段抛出异常的情况下，由`error`过滤器处理之后并不会在调用`post`阶段的请求，自然这些`error.*`参数也就不会被`SendErrorFilter`消费输出。所以，如果我们在自定义`post`过滤器的时候，没有正确的处理异常，就依然有可能出现日志中没有异常并且请求响应内容为空的问题。我们可以通过修改之前`ThrowExceptionFilter`的`filterType`修改为`post`来验证这个问题的存在，注意去掉`try-catch`块的处理，让它能够抛出异常。

解决上述问题的方法有很多种，比如最直接的我们可以在实现`error`过滤器的时候，直接来组织结果返回就能实现效果，但是这样的缺点也很明显，对于错误信息组织和返回的代码实现就会存在多份，这样非常不易于我们日后的代码维护工作。所以为了保持对异常返回处理逻辑的一致，我们还是希望将`post`过滤器抛出的异常能够交给`SendErrorFilter`来处理。

在前文中，我们已经实现了一个`ErrorFilter`来捕获`pre`、`route`、`post`过滤器抛出的异常，并组织`error.*`参数保存到请求的上下文中。由于我们的目标是沿用`SendErrorFilter`，这些`error.*`参数依然对我们有用，所以我们可以继续沿用该过滤器，让它在`post`过滤器抛出异常的时候，继续组织`error.*`参数，只是这里我们已经无法将这些`error.*`参数再传递给`SendErrorFitler`过滤器来处理了。所以，我们需要在`ErrorFilter`过滤器之后再定义一个`error`类型的过滤器，让它来实现`SendErrorFilter`的功能，但是这个`error`过滤器并不需要处理所有出现异常的情况，它仅对`post`过滤器抛出的异常才有效。根据上面的思路，我们完全可以创建一个继承自`SendErrorFilter`的过滤器，就能复用它的`run`方法，然后重写它的类型、顺序以及执行条件，实现对原有逻辑的复用，具体实现如下：

```
@Component
public class ErrorExtFilter extends SendErrorFilter {

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 30;	// 大于ErrorFilter的值
    }

    @Override
    public boolean shouldFilter() {
        // TODO 判断：仅处理来自post过滤器引起的异常
        return true;
    }

}
```

到这里，我们在过滤器调度上的实现思路已经很清晰了，但是又有一个问题出现在我们面前：怎么判断引起异常的过滤器是来自什么阶段呢？（`shouldFilter`方法该如何实现）对于这个问题，我们第一反应会寄希望于请求上下文`RequestContext`对象，可是在查阅文档和源码后发现其中并没有存储异常来源的内容，所以我们不得不扩展原来的过滤器处理逻辑，当有异常抛出的时候，记录下抛出异常的过滤器，这样我们就可以在`ErrorExtFilter`过滤器的`shouldFilter`方法中获取并以此判断异常是否来自`post`阶段的过滤器了。

为了扩展过滤器的处理逻辑，为请求上下文增加一些自定义属性，我们需要深入了解一下Zuul过滤器的核心处理器：`com.netflix.zuul.FilterProcessor`。该类中定义了下面过滤器调用和处理相关的核心方法：

- `getInstance()`：该方法用来获取当前处理器的实例
- `setProcessor(FilterProcessor processor)`：该方法用来设置处理器实例，可以使用此方法来设置自定义的处理器
- `processZuulFilter(ZuulFilter filter)`：该方法定义了用来执行`filter`的具体逻辑，包括对请求上下文的设置，判断是否应该执行，执行时一些异常处理等
- `getFiltersByType(String filterType)`：该方法用来根据传入的`filterType`获取API网关中对应类型的过滤器，并根据这些过滤器的`filterOrder`从小到大排序，组织成一个列表返回
- `runFilters(String sType)`：该方法会根据传入的`filterType`来调用`getFiltersByType(String filterType)`获取排序后的过滤器列表，然后轮询这些过滤器，并调用`processZuulFilter(ZuulFilter filter)`来依次执行它们
- `preRoute()`：调用`runFilters("pre")`来执行所有`pre`类型的过滤器
- `route()`：调用`runFilters("route")`来执行所有`route`类型的过滤器
- `postRoute()`：调用`runFilters("post")`来执行所有`post`类型的过滤器
- `error()`：调用`runFilters("error")`来执行所有`error`类型的过滤器

根据我们之前的设计，我们可以直接通过扩展`processZuulFilter(ZuulFilter filter)`方法，当过滤器执行抛出异常的时候，我们捕获它，并往请求上下中记录一些信息。比如下面的具体实现：

```
public class DidiFilterProcessor extends FilterProcessor {

    @Override
    public Object processZuulFilter(ZuulFilter filter) throws ZuulException {
        try {
            return super.processZuulFilter(filter);
        } catch (ZuulException e) {
            RequestContext ctx = RequestContext.getCurrentContext();
            ctx.set("failed.filter", filter);
            throw e;
        }
    }

}
```

在上面代码的实现中，我们创建了一个`FilterProcessor`的子类，并重写了`processZuulFilter(ZuulFilter filter)`，虽然主逻辑依然使用了父类的实现，但是在最外层，我们为其增加了异常捕获，并在异常处理中为请求上下文添加了`failed.filter`属性，以存储抛出异常的过滤器实例。在实现了这个扩展之后，我们也就可以完善之前`ErrorExtFilter`中的`shouldFilter()`方法，通过从请求上下文中获取该信息作出正确的判断，具体实现如下：

```
@Component
public class ErrorExtFilter extends SendErrorFilter {

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 30;	// 大于ErrorFilter的值
    }

    @Override
    public boolean shouldFilter() {
        // 判断：仅处理来自post过滤器引起的异常
        RequestContext ctx = RequestContext.getCurrentContext();
        ZuulFilter failedFilter = (ZuulFilter) ctx.get("failed.filter");
        if(failedFilter != null && failedFilter.filterType().equals("post")) {
            return true;
        }
        return false;
    }

}
```

到这里，我们的优化任务还没有完成，因为扩展的过滤器处理类并还没有生效。最后，我们需要在应用主类中，通过调用`FilterProcessor.setProcessor(new DidiFilterProcessor());`方法来启用自定义的核心处理器以完成我们的优化目标。