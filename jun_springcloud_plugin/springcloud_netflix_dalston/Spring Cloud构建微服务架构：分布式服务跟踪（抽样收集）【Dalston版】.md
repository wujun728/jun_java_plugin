# Spring Cloud构建微服务架构：分布式服务跟踪（抽样收集）【Dalston版】

**原创**

 [2018-02-24](https://blog.didispace.com/spring-cloud-starter-dalston-8-6/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

通过`Trace ID`和`Span ID`已经实现了对分布式系统中的请求跟踪，而这些记录的跟踪信息最终会被分析系统收集起来，并用来实现对分布式系统的监控和分析功能，比如：预警延迟过长的请求链路、查询请求链路的调用明细等。此时，我们在对接分析系统时就会碰到一个问题：分析系统在收集跟踪信息的时候，需要收集多少量的跟踪信息才合适呢？

理论上来说，我们收集的跟踪信息越多就可以更好的反映出系统的实际运行情况，并给出更精准的预警和分析，但是在高并发的分布式系统运行时，大量的请求调用会产生海量的跟踪日志信息，如果我们收集过多的跟踪信息将会对我们整个分布式系统的性能造成一定的影响，同时保存大量的日志信息也需要不少的存储开销。所以，在Sleuth中采用了抽象收集的方式来为跟踪信息打上收集标记，也就是我们之前在日志信息中看到的第四个boolean类型的值，它代表了该信息是否要被后续的跟踪信息收集器获取和存储。

在Sleuth中的抽样收集策略是通过`Sampler`接口实现的，它的定义如下：

```
public interface Sampler {
    /**
     * @return true if the span is not null and should be exported to the tracing system
    */
    boolean isSampled(Span span);
}
```

通过实现`isSampled`方法，Spring Cloud Sleuth会在产生跟踪信息的时候调用它来为跟踪信息生成是否要被收集的标志。需要注意的是，即使`isSampled`返回了`false`，它仅代表该跟踪信息不被输出到后续对接的远程分析系统（比如：Zipkin），对于请求的跟踪活动依然会进行，所以我们在日志中还是能看到收集标识为`false`的记录。

默认情况下，Sleuth会使用`PercentageBasedSampler`实现的抽样策略，以请求百分比的方式配置和收集跟踪信息，我们可以通过在`application.properties`中配置下面的参数对其百分比值进行设置，它的默认值为`0.1`，代表收集10%的请求跟踪信息。

```
spring.sleuth.sampler.percentage=0.1
```

在开发调试期间，通常会收集全部跟踪信息输出到远程仓库，我们可以将其值设置为`1`，或者也可以通过创建`AlwaysSampler`的Bean（它实现的`isSampled`方法始终返回`true`）来覆盖默认的`PercentageBasedSampler`策略，比如：

```
@Bean
public AlwaysSampler defaultSampler() {
    return new AlwaysSampler();
}
```

在实际使用时，通过与Span对象中存储信息的配合，我们可以根据实际情况做出更贴近需求的抽样策略，比如实现一个仅对包含指定Tag的抽样策略：

```
public class TagSampler implements Sampler {

    private String tag;

    public TagSampler(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean isSampled(Span span) {
        return span.tags().get(tag) != null;
    }
}
```

由于跟踪日志信息的数据价值往往仅在最近的一段时间内非常有用，比如：一周。那么我们在设计抽样策略时，主要考虑在不对系统造成明显性能影响的情况下，以在日志保留时间窗内充分利用存储空间的原则来实现抽样策略。

### 完整示例：

读者可以根据喜好选择下面的两个仓库中查看`trace-1`和`trace-2`两个项目：

- [Github：https://github.com/dyc87112/SpringCloud-Learning/](https://github.com/dyc87112/SpringCloud-Learning/tree/master/2-Dalston版教程示例/)
- [Gitee：https://gitee.com/didispace/SpringCloud-Learning/](https://gitee.com/didispace/SpringCloud-Learning/tree/master/2-Dalston版教程示例)