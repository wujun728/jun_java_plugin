### 缓存（Cache）

缓存模块是以EhCache作为默认JVM进程内缓存服务，通过整合外部Redis服务实现多级缓存（MultiLevel）的轻量级缓存框架，并与YMP框架深度集成(支持针对类方法的缓存，可以根据方法参数值进行缓存)，灵活的配置、易于使用和扩展；

#### Maven包依赖

    <dependency>
        <groupId>net.ymate.platform</groupId>
        <artifactId>ymate-platform-cache</artifactId>
        <version>2.0.2</version>
    </dependency>

> **注**：
> - 在项目的pom.xml中添加上述配置，该模块已经默认引入核心包依赖，无需重复配置。
> - 若需要启用redis作为缓存服务，请添加以下依赖配置：
>
>         <dependency>
>             <groupId>net.ymate.platform</groupId>
>             <artifactId>ymate-platform-persistence-redis</artifactId>
>             <version>2.0.2</version>
>         </dependency>

#### 基础接口概念

开发者可以根据以下接口完成对缓存模块的自定义扩展实现；

- 缓存服务提供者(ICacheProvider)接口：

    + DefaultCacheProvider - 基于EhCache缓存服务的默认缓存服务提供者接口实现类；
    + RedisCacheProvider - 基于Redis数据库的缓存服务提供者接口实现类；
    + MultievelCacheProvider - 融合EhCache和Redis两者的缓存服务提供者接口实现类，通过MultilevelKey决定缓存对象的获取方式；

- 缓存Key生成器(IKeyGenerator)接口：

    + DefaultKeyGenerator - 根据提供的类方法和参数对象生成缓存Key，默认是将方法和参数对象进行序列化后取其MD5值；

- 序列化服务(ISerializer)接口：

    + DefaultSerializer - 默认序列化服务采用JDK自带的对象序列化技术实现；

- 缓存事件监听(ICacheEventListener)接口：用于监听被缓存对象发生变化时的事件处理，需开发者实现接口；

- 缓存作用域处理器(ICacheScopeProcessor)接口：用于处理@Cacheable注解的Scope参数设置为非DEFAULT作用域的缓存对象，需开发者实现接口；

#### 模块配置

##### 初始化参数配置

    #-------------------------------------
    # 缓存模块初始化参数
    #-------------------------------------

    # 缓存提供者，可选参数，默认值为default，目前支持[default|redis|multilevel]或自定义类名称
    ymp.configs.cache.provider_class=

    # 缓存对象事件监听器，可选参数，默认值为空
    ymp.configs.cache.event_listener_class=

    # 缓存作用域处理器，可选参数，默认值为空
    ymp.configs.cache.scope_processor_class=

    # 缓存Key生成器，可选参数，默认值为空则采用框架默认net.ymate.platform.cache.impl.DefaultKeyGenerator
    ymp.configs.cache.key_generator_class=

    # 对象序列化接口实现，可选参数，默认值为net.ymate.platform.cache.impl.DefaultSerializer
    ymp.configs.cache.serializer_class=

    # 默认缓存名称，可选参数，默认值为default，对应于Ehcache配置文件中设置name="__DEFAULT__"
    ymp.configs.cache.default_cache_name=

    # 缓存数据超时时间，可选参数，数值必须大于等于0，为0表示默认缓存300秒
    ymp.configs.cache.default_cache_timeout=

##### EhCache配置示例

请将以下内容保存在ehcache.xml文件中，并放置在classpath根路径下；

    <ehcache updateCheck="false" dynamicConfig="false">

        <diskStore path="java.io.tmpdir"/>

        <cacheManagerEventListenerFactory class="" properties=""/>

        <defaultCache
                maxElementsInMemory="1000"
                eternal="false"
                timeToIdleSeconds="60"
                timeToLiveSeconds="120"
                overflowToDisk="true">
        </defaultCache>

        <cache name="__DEFAULT__"
               maxElementsInMemory="5000"
               eternal="false"
               timeToIdleSeconds="1800"
               timeToLiveSeconds="1800"
               overflowToDisk="false"
        />
    </ehcache>

#### 模块使用

##### 示例一：直接通过缓存模块操作缓存数据

    public static void main(String[] args) throws Exception {
        YMP.get().init();
        try {
            // 操作默认缓存
            Caches.get().put("key1", "value1");
            System.out.println(Caches.get().get("key1"));
            // 操作指定名称的缓存
            Caches.get().put("default", "key2", "value2");
            System.out.println(Caches.get().get("default", "key2"));
        } finally {
            YMP.get().destroy();
        }
    }

**注**：当指定缓存名称时，请确认与名称对应的配置是否已存在；

执行结果：

    value1
    value2

##### 示例二：基于注解完成类方法的缓存

这里用到了@Cacheable注解，作用是标识类中方法的执行结果是否进行缓存，需要注意的是：

> 首先@Cacheable注解必须在已注册到YMP类对象管理器的类上声明，表示该类支持缓存；
> 
> 其次，在需要缓存执行结果的方法上添加@Cacheable注解；

@Cacheable注解参数说明：

> cacheName：缓存名称, 默认值为default；
> 
> key：缓存Key, 若未设置则使用keyGenerator自动生成；
> 
> generator：Key生成器接口实现类，默认为DefaultKeyGenerator.class；
> 
> scope：缓存作用域，可选值为APPLICATION、SESSION和DEFAULT，默认为DEFAULT，非DEFAULT设置需要缓存作用域处理器(ICacheScopeProcessor)接口配合；
> 
> timeout：缓存数据超时时间, 可选参数，数值必须大于等于0，为0表示默认缓存300秒；

示例代码：

    @Bean
    @Cacheable
    public class CacheDemo {

        @Cacheable
        public String sayHi(String name) {
            System.out.println("No Cached");
            return "Hi, " + name;
        }

        public static void main(String[] args) throws Exception {
            YMP.get().init();
            try {
                CacheDemo _demo = YMP.get().getBean(CacheDemo.class);
                System.out.println(_demo.sayHi("YMP"));
                System.out.println(_demo.sayHi("YMP"));
                //
                System.out.println("--------");
                //
                System.out.println(_demo.sayHi("YMPer"));
                System.out.println(_demo.sayHi("YMP"));
                System.out.println(_demo.sayHi("YMPer"));
            } finally {
                YMP.get().destroy();
            }
        }
    }

执行结果：

    No Cached
    Hi, YMP
    Hi, YMP
    --------
    No Cached
    Hi, YMPer
    Hi, YMP
    Hi, YMPer

以上结果输出可以看出，sayHi方法相同参数首次被调用时将输出“No Cached”字符串，说明它没有使用缓存，再次调用时直接从缓存中返回值；
