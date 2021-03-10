# Spring 5.x版本特性展示项目

作为Java世界首个响应式Web框架，Spring 5最大的亮点莫过于提供了完整的端到端响应式编程的支持。

![](http://emacoo.cn/backend/spring5-overview/webflux-overview.png)

*图片出处：[Spring Framework Reference Documentation](http://docs.spring.io/spring/docs/5.0.0.BUILD-SNAPSHOT/spring-framework-reference/htmlsingle/)*

左侧是传统的基于Servlet的Spring Web MVC框架，右侧是5.0版本新引入的基于Reactive Streams的Spring WebFlux框架，从上到下依次是Router Functions，WebFlux，Reactive Streams三个新组件。

- Router Functions: 对标@Controller，@RequestMapping等标准的Spring MVC注解，提供一套函数式风格的API，用于创建Router，Handler和Filter。
- WebFlux: 核心组件，协调上下游各个组件提供响应式编程支持。
- [Reactive Streams](http://www.reactive-streams.org/): 一种支持背压（Backpressure）的异步数据流处理标准，主流实现有RxJava和Reactor，Spring WebFlux默认集成的是Reactor。

在Web容器的选择上，Spring WebFlux既支持像Tomcat，Jetty这样的的传统容器（前提是支持Servlet 3.1 Non-Blocking IO API），又支持像Netty，Undertow那样的异步容器。不管是何种容器，Spring WebFlux都会将其输入输出流适配成`Flux<DataBuffer>`格式，以便进行统一处理。

值得一提的是，除了新的Router Functions接口，Spring WebFlux同时支持使用老的Spring MVC注解声明Reactive Controller。和传统的MVC Controller不同，Reactive Controller操作的是非阻塞的ServerHttpRequest和ServerHttpResponse，而不再是Spring MVC里的HttpServletRequest和HttpServletResponse。

除了响应式编程支持，Spring 5还包括了很多Java程序员期待已久的特性，包括JDK 9，[Junit 5](http://emacoo.cn/arch/junit5/)，Servlet 4以及HTTP/2支持。目前Spring 5的最新版本是[RC1](https://spring.io/blog/2017/05/08/spring-framework-5-0-goes-rc1)，而Spring Boot也刚刚发布了[2.0.0 M1](https://spring.io/blog/2017/05/16/spring-boot-2-0-0-m1-available-now)版本。根据Spring[官方博客](https://spring.io/blog/2015/12/03/spring-framework-5-0-roadmap-update)，Spring 5将在JDK 9 [GA](http://www.java9countdown.xyz/)之后随即发布，也就是今年的7月底前后。

如果你想进一步了解Spring 5，可以参考我写的Spring 5系列博客：

- [【Spring 5】响应式Web框架前瞻](http://emacoo.cn/backend/spring5-overview/)
- [响应式编程总览](http://emacoo.cn/backend/reactive-overview/)
- [【Spring 5】响应式Web框架实战（上）](http://emacoo.cn/backend/spring5-reactive-tutorial/)
- [【Spring 5】响应式Web框架实战（下）](http://emacoo.cn/backend/spring5-reactive-tutorial2/)
