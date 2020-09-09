#### 使用介绍
参见简书图文教程
## [https://www.jianshu.com/p/89403b1b314d](https://www.jianshu.com/p/89403b1b314d)

### 1启动 NettyServer
```
@Configuration
public class NettyHttpServer implements ApplicationListener<ApplicationStartedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyHttpServer.class);

    @Value("${server.port}")
    private int port;

    @Resource
    private InterceptorHandler interceptorHandler;

    @Resource
    private HttpServerHandler httpServerHandler;

    @Override
    public void onApplicationEvent(@NonNull ApplicationStartedEvent event) {

        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        bootstrap.group(bossGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childOption(NioChannelOption.TCP_NODELAY, true);
        bootstrap.childOption(NioChannelOption.SO_REUSEADDR,true);
        bootstrap.childOption(NioChannelOption.SO_KEEPALIVE,false);
        bootstrap.childOption(NioChannelOption.SO_RCVBUF, 2048);
        bootstrap.childOption(NioChannelOption.SO_SNDBUF, 2048);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) {
                ch.pipeline().addLast("codec", new HttpServerCodec());
                ch.pipeline().addLast("aggregator", new HttpObjectAggregator(512 * 1024));
                ch.pipeline().addLast("logging", new FilterLogginglHandler());
                ch.pipeline().addLast("interceptor", interceptorHandler);
                ch.pipeline().addLast("bizHandler", httpServerHandler);
            }
        })
        ;
        ChannelFuture channelFuture = bootstrap.bind(port).syncUninterruptibly().addListener(future -> {
            String logBanner = "\n\n" +
                    "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n" +
                    "*                                                                                   *\n" +
                    "*                                                                                   *\n" +
                    "*                   Netty Http Server started on port {}.                         *\n" +
                    "*                                                                                   *\n" +
                    "*                                                                                   *\n" +
                    "* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *\n";
            LOGGER.info(logBanner, port);
        });
        channelFuture.channel().closeFuture().addListener(future -> {
            LOGGER.info("Netty Http Server Start Shutdown ............");
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        });
    }

}
```

### 2实现HttpHandler 也就是SpringMvc的Controller
```
@NettyHttpHandler(path = "/hello/world")
public class HelloWorldHandler implements IFunctionHandler<String> {

    @Override
    public Response<String> execute(NettyHttpRequest request) {
         return Response.ok("Hello World");
    }
}


```

PathVariable

```
@NettyHttpHandler(path = "/moment/list/",equal = false)
public class PathVariableHandler implements IFunctionHandler<List<HashMap<String,String>>> {
    @Override
    public Response<List<HashMap<String,String>>> execute(NettyHttpRequest request) {

        /**
         * 通过请求uri获取到path参数
         */
        String id = request.getStringPathValue(3);

        List<HashMap<String,String>> list = new LinkedList<>();
        HashMap<String,String> data = new HashMap<>();
        data.put("id","1");
        data.put("name","Bluesky");
        data.put("text","hello sea!");
        data.put("time","2018-08-08 08:08:08");
        list.add(data);
        return Response.ok(list);
    }
}

```

RequestBody

```
@NettyHttpHandler(path = "/request/body",method = "POST")
public class RequestBodyHandler implements IFunctionHandler<String> {
    @Override
    public Response<String> execute(NettyHttpRequest request) {
        /**
         * 可以在此拿到json转成业务需要的对象
         */
        String json = request.contentText();
        return Response.ok(json);
    }
}

```

### 3执行请求查看效果
![image.png](https://upload-images.jianshu.io/upload_images/2154278-752e5909766228df.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![image.png](https://upload-images.jianshu.io/upload_images/2154278-4132dc17ef929834.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


#### 优缺点
-------------------------------------------------------------------------------------------
优点

1 netty使用多路复用技术大幅提升性能

2 减少web容器依赖，减少jar包体积

3 灵活配置简单，适合所有需要提供restful接口的微服务应用

4 完全按照springmvc的模式开发配置

缺点

1 还没能做到和spirng DispatcherServlet那么强大到支持各种规则的path配置

2 获取各种参数还需要在controller里面通过HttpRequest来获取，没有springmvc自动注入参数方便

有问题欢迎随时提issues. Thanks

