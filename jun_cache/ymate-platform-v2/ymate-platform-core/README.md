### 框架核心（Core）

YMP框架主要是由核心(Core)和若干模块(Modules)组成，核心主要负责框架的初始化和模块的生命周期管理。

#### 主要核心功能

- Beans：类对象管理器（微型的Spring容器），提供包类的自动扫描（AutoScan）以及Bean生命周期管理、依赖注入（IoC）和方法拦截（AOP）等特性。

- Event：事件服务，通过事件注册和广播的方式触发和监听事件动作，并支持同步和异步两种模式执行事件队列。

- Module：模块，是YMP框架所有功能特性封装的基础形式，负责模块的生命周期管理，模块将在框架初始化时自动加载并初始化，在框架销毁时自动销毁。

- I18N：国际化资源管理器，提供统一的资源文件加载、销毁和内容读取，支持自定义资源加载和语言变化的事件监听。

- Lang：提供了一组自定义的数据结构，它们在部分模块中起到了重要的作用，包括：
    + BlurObject：用于解决常用数据类型间转换的模糊对象。
    + PairObject：用于将两个独立的对象捆绑在一起的结对对象。
    + TreeObject：使用级联方式存储各种数据类型，不限层级深度的树型对象。

- Util：提供框架中需要的各种工具类。

#### Maven包依赖

    <dependency>
        <groupId>net.ymate.platform</groupId>
        <artifactId>ymate-platform-core</artifactId>
        <version>2.0.2</version>
    </dependency>

> **注**：若想单独使用YMP核心包时需要在pom.xml中添加上述配置，其它模块已经默认引入核心包依赖，无需重复配置。

#### 框架初始化

YMP框架的初始化是从加载ymp-conf.properties文件开始的，该文件必须被放置在classpath的根路径下；

- 根据程序运行环境的不同，YMP框架初始化时将根据当前操作系统优先级加载配置：

    + 优先加载 ymp-conf_DEV.properties (若加载成功则强制设置ymp.dev_mode=true)
    + Unix/Linux环境下，优先加载 ymp-conf_UNIX.properties；
    + Windows环境下，优先加载 ymp-conf_WIN.properties；
    + 若以上配置文件未找到，则加载默认配置 ymp-conf.properties；

- 框架初始化基本配置参数：

		#-------------------------------------
		# 框架基本配置参数
		#-------------------------------------
		
		# 是否为开发模式，默认为false
		ymp.dev_mode=
		
		# 框架自动扫描的包路径集合，多个包名之间用'|'分隔，默认已包含net.ymate.platform包，其子包也将被扫描
		ymp.autoscan_packages=
		
		# 包文件排除列表，多个文件名称之间用'|'分隔，被包含的JAR或ZIP文件在扫描过程中将被忽略
        ymp.excluded_files=
        
		# 模块排除列表，多个模块名称或类名之间用'|'分隔，被包含的模块在加载过程中将被忽略
		ymp.excluded_modules=
		
		# 国际化资源默认语言设置，可选参数，默认采用系统环境语言
		ymp.i18n_default_locale=zh_CN
		
		# 国际化资源管理器事件监听处理器，可选参数，默认为空
		ymp.i18n_event_handler_class=
		
		# 框架全局自定义参数，xxx表示自定义参数名称，vvv表示参数值
		ymp.params.xxx=vvv
		
		# 本文测试使用的自定义参数
		ymp.params.helloworld=Hello, YMP!


- 测试代码，完成框架的启动和销毁：

        public static void main(String[] args) throws Exception {
            YMP.get().init();
            try {
                // 输出自定义参数值：Hello, YMP!
                System.out.println(YMP.get().getConfig().getParam("helloworld"));
            } finally {
                YMP.get().destroy();
            }
        }

#### Beans

##### 包类的自动扫描（AutoScan）

YMP框架初始化时将自动扫描由`autoscan_packages`参数配置的包路径下所有声明了`@Bean`注解的类文件，首先分析被加载的类所有已实现接口并注册到Bean容器中，然后执行类成员的依赖注入和方法拦截代理的绑定；

> 说明：
>
> - 相同接口的多个实现类被同时注册到Bean容器时，通过接口获取的实现类将是最后被注册到容器的那个，此时只能通过实例对象类型才能正确获取；
>
> - 若不希望某个类被自动扫描，只需在该类上声明`@Ignored`注解，自动扫描程序都忽略它；

- 示例一：

        // 业务接口
        public interface IDemo {
            String sayHi();
        }

        // 业务接口实现类，单例模式
        @Bean
        public class DemoBean implements IDemo {
            public String sayHi() {
                return "Hello, YMP!";
            }
        }

- 示例二：

        // 示例一中的业务接口实现类，非单例模式
        @Bean(singleton = false)
        public class DemoBean implements IDemo {
            public String sayHi() {
                return "Hello, YMP!";
            }
        }

- 测试代码：

        public static void main(String[] args) throws Exception {
            YMP.get().init();
            try {
                // 1. 通过接口获取实例对象
                IDemo _demo = YMP.get().getBean(IDemo.class);
                System.out.println(_demo.sayHi());

                // 2. 直接获取实例对象
                _demo = YMP.get().getBean(DemoBean.class);
                System.out.println(_demo.sayHi());
            } finally {
                YMP.get().destroy();
            }
        }

##### 依赖注入（IoC）

通过在类成员属性上声明`@Inject`和`@By`注解来完成依赖注入的设置，且只有被Bean容器管理的类对象才支持依赖注入，下面举例说明：

- 示例：

        // 业务接口
        public interface IDemo {
            String sayHi();
        }

        // 业务接口实现类1
        @Bean
        public class DemoOne implements IDemo {
            public String sayHi() {
                return "Hello, YMP! I'm DemoOne.";
            }
        }

        // 业务接口实现类2
        @Bean
        public class DemoTwo implements IDemo {
            public String sayHi() {
                return "Hello, YMP! I'm DemoTwo.";
            }
        }

- 测试代码：

        @Bean
        public class TestDemo {

            @Inject
            private IDemo __demo1;

            @Inject
            @By(DemoOne.class)
            private IDemo __demo2;

            public void sayHi() {
                // _demo1注入的将是最后被注册到容器的IDemo接口实现类
                System.out.println(__demo1.sayHi());
                // _demo2注入的是由@By注解指定的DemoOne类
                System.out.println(__demo2.sayHi());
            }

            public static void main(String[] args) throws Exception {
                YMP.get().init();
                try {
                    TestDemo _demo = YMP.get().getBean(TestDemo.class);
                    _demo.sayHi();
                } finally {
                    YMP.get().destroy();
                }
            }
        }

也可以通过`@Injector`注解声明一个`IBeanInjector`接口实现类向框架注册自定义的注入处理逻辑，下面举例说明如何为注入对象添加包装器：

- 示例：

        // 定义一个业务接口
        
        public interface IInjectBean {
        
            String getName();
        
            void setName(String name);
        }
        
        // 业务接口实现类
        
        @Bean
        public class InjectBeanImpl implements IInjectBean {
        
            private String name;
        
            public String getName() {
                return name;
            }
        
            public void setName(String name) {
                this.name = name;
            }
        }
        
        // 业务对象包装器类
        
        public class InjectBeanWrapper implements IInjectBean {
        
            private IInjectBean __targetBean;
        
            public InjectBeanWrapper(IInjectBean targetBean) {
                __targetBean = targetBean;
            }
        
            public String getName() {
                return __targetBean.getName();
            }
        
            public void setName(String name) {
                __targetBean.setName(name);
            }
        }
        
        // 自定义一个注解
                
        @Target({ElementType.FIELD})
        @Retention(RetentionPolicy.RUNTIME)
        @Documented
        public @interface Demo {
        
            String value();
        }
        
        // 为注解编写自定义注入逻辑
        
        @Injector(Demo.class)
        public class DemoBeanInjector implements IBeanInjector {
        
            public Object inject(IBeanFactory beanFactory, Annotation annotation, Class<?> targetClass, Field field, Object originInject) {
                // 为从自定义注解取值做准备
                Demo _anno = (Demo) annotation;
                if (originInject == null) {
                    // 若通过@Inject注入的对象不为空则为其赋值
                    IInjectBean _bean = new InjectBeanImpl();
                    _bean.setName(_anno.value());
                    // 创建包装器
                    originInject = new InjectBeanWrapper(_bean);
                } else {
                    // 直接创建包装器并赋值
                    InjectBeanWrapper _wrapper = new InjectBeanWrapper((IInjectBean) originInject);
                    _wrapper.setName(_anno.value());
                    //
                    originInject = _wrapper;
                }
                return originInject;
            }
        }

- 测试代码：

        @Bean
        public class App {
        
            @Inject
            @Demo("demo")
            private IInjectBean __bean;
        
            public IInjectBean getBean() {
                return __bean;
            }
        
            public static void main(String[] args) throws Exception {
                try {
                    YMP.get().init();
                    //
                    App _app = YMP.get().getBean(App.class);
                    IInjectBean _bean = _app.getBean();
                    System.out.println(_bean.getName());
                } finally {
                    YMP.get().destroy();
                }
            }
        }

> 说明：
>
> - 当使用自定义注解进行依赖注入操作时可以忽略`@Inject`注解，若存在则优先执行`@Inject`注入并将此对象当作`IBeanInjector`接口方法参数传入；
> - 当成员变量被声明多个自定义注入规则注解时（不推荐），根据框架加载顺序，仅执行首个注入规则；

##### 方法拦截（AOP）

YMP框架的AOP是基于CGLIB的MethodInterceptor实现的拦截，通过以下注解进行配置：

- @Before：用于设置一个类或方法的前置拦截器，声明在类上的前置拦截器将被应用到该类所有方法上；

- @After：用于设置一个类或方法的后置拦截器，声明在类上的后置拦截器将被应用到该类所有方法上；

- @Around：用于同时配置一个类或方法的前置和后置拦截器；

- @Clean：用于清理类上全部或指定的拦截器，被清理的拦截器将不会被执行；

- @ContextParam：用于设置上下文参数，主要用于向拦截器传递参数配置；

- @Ignored：声明一个方法将忽略一切拦截器配置；

> 说明:
>
> 声明`@Ignored`注解的方法、非公有方法和Object类方法及Object类重载方法将不被拦截器处理。

示例一：

        // 创建自定义拦截器
        public class DemoInterceptor implements IInterceptor {
            public Object intercept(InterceptContext context) throws Exception {
                // 判断当前拦截器执行方向
                switch (context.getDirection()) {
                    // 前置
                    case BEFORE:
                        System.out.println("before intercept...");
                        // 获取拦截器参数
                        String _param = context.getContextParams().get("param");
                        if (StringUtils.isNotBlank(_param)) {
                            System.out.println(_param);
                        }
                        break;
                    // 后置
                    case AFTER:
                        System.out.println("after intercept...");
                }
                return null;
            }
        }

        @Bean
        public class TestDemo {

            @Before(DemoInterceptor.class)
            public String beforeTest() {
                return "前置拦截测试";
            }

            @After(DemoInterceptor.class)
            public String afterTest() {
                return "后置拦截测试";
            }

            @Around(DemoInterceptor.class)
            @ContextParam({
                    @ParamItem(key = "param", value = "helloworld")
                })
            public String allTest() {
                return "拦截器参数传递";
            }

            public static void main(String[] args) throws Exception {
                YMP.get().init();
                try {
                    TestDemo _demo = YMP.get().getBean(TestDemo.class);
                    _demo.beforeTest();
                    _demo.afterTest();
                    _demo.allTest();
                } finally {
                    YMP.get().destroy();
                }
            }
        }

示例二：

        @Bean
        @Before(DemoInterceptor.class)
        @ContextParam({
                @ParamItem(key = "param", value = "helloworld")
            })
        public class TestDemo {

            public String beforeTest() {
                return "默认前置拦截测试";
            }

            @After(DemoInterceptor.class)
            public String afterTest() {
                return "后置拦截测试";
            }

            @Clean
            public String cleanTest() {
                return "清理拦截器测试";
            }

            public static void main(String[] args) throws Exception {
                YMP.get().init();
                try {
                    TestDemo _demo = YMP.get().getBean(TestDemo.class);
                    _demo.beforeTest();
                    _demo.afterTest();
                    _demo.cleanTest();
                } finally {
                    YMP.get().destroy();
                }
            }
        }

**注**：`@ContextParam`注解的value属性允许通过$xxx的格式支持从框架全局参数中获取xxx的值

##### 包拦截器配置

YMP框架支持将`@Before`、`@After`、`@Around`和`@ContextParam`注解在`package-info.java`类中声明，声明后该拦截器配置将作用于其所在包下所有类(子包将继承父级包配置)。

拦截器的执行顺序: `package` \> `class` \> `method`

通过`@Packages`注解让框架自动扫描`package-info.java`类并完成配置注册。

示例: 

> 本例将为`net.ymate.demo.controller`包指定拦截器配置，其`package-info.java`内容如下：

    @Packages
    @Before(DemoInterceptor.class)
    @ContextParam(@ParamItem(key = "param", value = "helloworld"))
    package net.ymate.demo.controller;
    
    import net.ymate.demo.intercept.DemoInterceptor;
    import net.ymate.platform.core.beans.annotation.Before;
    import net.ymate.platform.core.beans.annotation.ContextParam;
    import net.ymate.platform.core.beans.annotation.Packages;
    import net.ymate.platform.core.beans.annotation.ParamItem;

##### 拦截器全局规则设置

有些时候，我们需要对指定的拦截器或某些类和方法的拦截器配置进行调整，往往我们要修改代码、编译打包并重新部署，这样做显然很麻烦！

现在我们可以通过配置文件来完成此项工作，配置格式及说明如下：

    #-------------------------------------
    # 框架拦截器全局规则设置参数
    #-------------------------------------
    
    # 是否开启拦截器全局规则设置, 默认为false
    ymp.intercept_settings_enabled=true
    
    # 为指定包配置拦截器, 格式: ymp.intercept.packages.<包名>=<[before:|after:]拦截器类名> (通过'|'分隔多个拦截器)
    ymp.intercept.packages.net.ymate.demo.controller=before:net.ymate.demo.intercept.UserSessionInterceptor
    
    # 全局设置指定的拦截器状态为禁止执行, 仅当取值为disabled时生效, 格式: ymp.intercept.globals.<拦截器类名>=disabled
    ymp.intercept.globals.net.ymate.framework.webmvc.intercept.UserSessionAlreadyInterceptor=disabled
    
    # 为目标类配置拦截器执行规则:
    #
    # -- 格式: ymp.intercept.settings.<目标类名>#[方法名称]=<[*|before:*|after:*]或[before:|after:]interceptor_class_name[+|-]]>
    # -- 假设目标类名称为: net.ymate.demo.controller.DemoController
    #
    # -- 方式一: 指定目标类所有方法禁止所有拦截器(*表示全部, 即包括前置和后置拦截器)
    ymp.intercept.settings.net.ymate.demo.controller.DemoController#=*
    
    # -- 方式二: 指定目标类的doLogin方法禁止所有前置拦截器(before:表示规则限定为前置拦截器, after:表示规则限定为后置拦截器)
    ymp.intercept.settings.net.ymate.demo.controller.DemoController#doLogin=before:*
    
    # -- 方式三: 指定目标类的doLogout方法禁止某个前置拦截器并增加一个新的后置拦截器(多个执行规则通过'|'分隔, 增加拦截器的'+'可以省略)
    ymp.intercept.settings.net.ymate.demo.controller.DemoController#__doLogout=before:net.ymate.demo.intercept.UserSessionInterceptor-|after:net.ymate.demo.intercept.UserStatusUpdateInterceptor+

##### 记录类属性状态 (PropertyState)

通过在类成员变量上声明`@PropertyState`注解，并使用`PropertyStateSupport`工具类配合，便可以轻松实现对类成员属性的变化情况进行监控。

- @PropertyState注解：声明记录类成员属性值的变化；

    > propertyName：成员属性名称，默认为空则采用当前成员名称；
    >
    > aliasName：自定义别名，默认为空；
    >
    > setterName：成员属性SET方法名称，默认为空；

- 示例代码：

        public class PropertyStateTest {
        
            @PropertyState(propertyName = "user_name")
            private String username;
        
            @PropertyState(aliasName = "年龄")
            private int age;
        
            public String getUsername() {
                return username;
            }
        
            public void setUsername(String username) {
                this.username = username;
            }
        
            public int getAge() {
                return age;
            }
        
            public void setAge(int age) {
                this.age = age;
            }
        
            public static void main(String[] args) throws Exception {
                PropertyStateTest _original = new PropertyStateTest();
                _original.setUsername("123456");
                _original.setAge(20);
                //
                PropertyStateSupport<PropertyStateTest> _support = PropertyStateSupport.create(_original);
                PropertyStateTest _new = _support.bind();
                _new.setUsername("YMPer");
                _new.setAge(30);
                //
                System.out.println("发生变更的字段名集合: " + Arrays.asList(_support.getChangedPropertyNames()));
                for (PropertyStateSupport.PropertyStateMeta _meta : _support.getChangedProperties()) {
                    System.out.println("已将" + StringUtils.defaultIfBlank(_meta.getAliasName(), _meta.getPropertyName()) + "由" + _meta.getOriginalValue() + "变更为" + _meta.getNewValue());
                }
            }
        }

- 执行结果：

        发生变更的字段名集合: [user_name, age]
        已将user_name由123456变更为YMPer
        已将年龄由20变更为30

#### Event

事件服务，通过事件的注册、订阅和广播完成事件消息的处理，目的是为了减少代码侵入，降低模块之间的业务耦合度，事件消息采用队列存储，采用多线程接口回调实现消息及消息上下文对象的传输，支持同步和异步两种处理模式；

##### 框架事件初始化配置参数

    #-------------------------------------
    # 框架事件初始化参数
    #-------------------------------------

    # 默认事件触发模式(不区分大小写)，取值范围：NORMAL-同步执行，ASYNC-异步执行，默认为ASYNC
    ymp.event.default_mode=

    # 事件管理提供者接口实现，默认为net.ymate.platform.core.event.impl.DefaultEventProvider
    ymp.event.provider_class=

    # 事件线程池初始化大小，默认为Runtime.getRuntime().availableProcessors()
    ymp.event.thread_pool_size=

    # 事件配置扩展参数，xxx表示自定义参数名称，vvv表示参数值
    ymp.event.params.xxx=vvv

##### YMP核心事件对象

- ApplicationEvent：框架事件

        APPLICATION_INITED - 框架初始化
        APPLICATION_DESTROYED - 框架销毁

- ModuleEvent：模块事件

        MODULE_INITED - 模块初始化
        MODULE_DESTROYED - 模块销毁

**注**：以上只是YMP框架核心中包含的事件对象，其它模块中包含的事件对象将在其相应的文档描述中阐述；

##### 事件的订阅

- 方式一：通过代码手动完成事件的订阅

        public static void main(String[] args) throws Exception {
            YMP.get().init();
            try {
                // 订阅模块事件
                YMP.get().getEvents().registerListener(ModuleEvent.class, new IEventListener<ModuleEvent>() {
                    @Override
                    public boolean handle(ModuleEvent context) {
                        switch (context.getEventName()) {
                            case MODULE_INITED:
                                // 注意：这段代码是不会被执行的，因为在我们进行事件订阅时，模块的初始化动作已经完成
                                System.out.println("Inited :" + context.getSource().getName());
                                break;
                            case MODULE_DESTROYED:
                                System.out.println("Destroyed :" + context.getSource().getName());
                                break;
                        }
                        return false;
                    }
                });
            } finally {
                YMP.get().destroy();
            }
        }

- 方式二：通过`@EventRegister`注解和IEventRegister接口实现事件的订阅

        // 首先创建事件注册类，通过实现IEventRegister接口完成事件的订阅
        // 通过@EventRegister注解，该类将在YMP框架初始化时被自动加载
        @EventRegister
        public class DemoEventRegister implements IEventRegister {
            public void register(Events events) throws Exception {
                // 订阅模块事件
                events.registerListener(ModuleEvent.class, new IEventListener<ModuleEvent>() {
                    @Override
                    public boolean handle(ModuleEvent context) {
                        switch (context.getEventName()) {
                            case MODULE_INITED:
                                System.out.println("Inited :" + context.getSource().getName());
                                break;
                            case MODULE_DESTROYED:
                                System.out.println("Destroyed :" + context.getSource().getName());
                                break;
                        }
                        return false;
                    }
                });
                //
                // ... 还可以添加更多的事件订阅代码
            }
        }

        // 框架启动测试
        public static void main(String[] args) throws Exception {
            YMP.get().init();
            try {
                // Do Nothing...
            } finally {
                YMP.get().destroy();
            }
        }

##### 自定义事件

YMP的事件对象必须实现IEvent接口的同时需要继承EventContext对象，下面的代码就是一个自定义事件对象：

- 创建自定义事件对象

        public class DemoEvent extends EventContext<Object, DemoEvent.EVENT> implements IEvent {

            public enum EVENT {
                CUSTOM_EVENT_ONE, CUSTOM_EVENT_TWO
            }

            public DemoEvent(Object owner, Class<? extends IEvent> eventClass, EVENT eventName) {
                super(owner, eventClass, eventName);
            }
        }

    说明：EventContext的注解中的第一个参数代表事件源对象类型，第二个参数是指定用于事件监听事件名称的枚举类型；

- 注册自定义事件

        YMP.get().getEvents().registerEvent(DemoEvent.class);

- 订阅自定义事件

    事件订阅（或监听）需实现IEventListener接口，该接口的handle方法返回值在同步触发模式下将影响事件监听队列是否终止执行，异步触发模式下请忽略此返回值；

        // 采用默认模式执行事件监听器
        YMP.get().getEvents().registerListener(DemoEvent.class, new IEventListener<DemoEvent>() {

            public boolean handle(DemoEvent context) {
                switch (context.getEventName()) {
                    case CUSTOM_EVENT_ONE:
                        System.out.println("CUSTOM_EVENT_ONE");
                        break;
                    case CUSTOM_EVENT_TWO:
                        System.out.println("CUSTOM_EVENT_TWO");
                        break;
                }
                return false;
            }
        });
        
        // 采用异步模式执行事件监听器
        YMP.get().getEvents().registerListener(Events.MODE.ASYNC, DemoEvent.class, new IEventListener<DemoEvent>() {

            public boolean handle(DemoEvent context) {
                ......
            }
        });

    当然，也可以通过`@EventRegister`注解和IEventRegister接口实现自定义事件的订阅；

    **注**：当某个事件被触发后，订阅（或监听）该事件的接口被回调执行的顺序是不能被保证的；

- 触发自定义事件

        YMP.get().getEvents().fireEvent(new DemoEvent(YMP.get(), DemoEvent.class, DemoEvent.EVENT.CUSTOM_EVENT_ONE));
        //
        YMP.get().getEvents().fireEvent(new DemoEvent(YMP.get(), DemoEvent.class, DemoEvent.EVENT.CUSTOM_EVENT_TWO));

- 示例测试代码：

        public static void main(String[] args) throws Exception {
            YMP.get().init();
            try {
                // 注册自定义事件对象
                YMP.get().getEvents().registerEvent(DemoEvent.class);
                // 注册自定义事件监听
                YMP.get().getEvents().registerListener(DemoEvent.class, new IEventListener<DemoEvent>() {

                    public boolean handle(DemoEvent context) {
                        switch (context.getEventName()) {
                            case CUSTOM_EVENT_ONE:
                                System.out.println("CUSTOM_EVENT_ONE");
                                break;
                            case CUSTOM_EVENT_TWO:
                                System.out.println("CUSTOM_EVENT_TWO");
                                break;
                        }
                        return false;
                    }
                });
                // 触发事件
                YMP.get().getEvents().fireEvent(new DemoEvent(YMP.get(), DemoEvent.class, DemoEvent.EVENT.CUSTOM_EVENT_ONE));
                YMP.get().getEvents().fireEvent(new DemoEvent(YMP.get(), DemoEvent.class, DemoEvent.EVENT.CUSTOM_EVENT_TWO));
            } finally {
                YMP.get().destroy();
            }
        }


#### Module

##### 创建自定义模块

- 步骤一：根据业务需求创建需要对外暴露的业务接口

        public interface IDemoModule {

            // 为方便引用，定义模块名称常量
            String MODULE_NAME = "demomodule";

            // 返回自定义模块的参数配置接口对象
            IDemoModuleCfg getModuleCfg();

            // 对外暴露的业务方法
            String sayHi();
        }

- 步骤二：处理自定义模块的配置参数，下列代码假定测试模块有两个自定义参数

        // 定义模块配置接口
        public interface IDemoModuleCfg {

            String getModuleParamOne();

            String getModuleParamTwo();
        }

        // 实现模块配置接口
        public class DemoModuleCfg implements IDemoModuleCfg {

            private String __moduleParamOne;

            private String __moduleParamTwo;

            public DemoModuleCfg(YMP owner) {
                // 从YMP框架中获取模块配置映射
                Map<String, String> _moduleCfgs = owner.getConfig().getModuleConfigs(IDemoModule.MODULE_NAME);
                //
                __moduleParamOne = _moduleCfgs.get("module_param_one");
                __moduleParamTwo = _moduleCfgs.get("module_param_two");
            }

            public String getModuleParamOne() {
                return __moduleParamOne;
            }

            public String getModuleParamTwo() {
                return __moduleParamTwo;
            }
        }

- 步骤三：实现模块及业务接口

    **注**：一定不要忘记在模块实现类上声明`@Module`注解，这样才能被YMP框架自动扫描、加载并初始化；

        @Module
        public class DemoModule implements IModule, IDemoModule {

            private YMP __owner;

            private IDemoModuleCfg __moduleCfg;

            private boolean __inited;

            public String getName() {
                return IDemoModule.MODULE_NAME;
            }

            public void init(YMP owner) throws Exception {
                if (!__inited) {
                    __owner = owner;
                    __moduleCfg = new DemoModuleCfg(owner);
                    //
                    __inited = true;
                }
            }

            public boolean isInited() {
                return __inited;
            }

            public YMP getOwner() {
                return __owner;
            }

            public IDemoModuleCfg getModuleCfg() {
                return __moduleCfg;
            }

            public void destroy() throws Exception {
                if (__inited) {
                    __inited = false;
                    //
                    __moduleCfg = null;
                    __owner = null;
                }
            }

            public String sayHi() {
                return "Hi, YMP!";
            }
        }

- 步骤四：在YMP的配置文件ymp-conf.properties中添加模块的配置内容

    格式： ymp.configs.<模块名称>.<参数名称>=[参数值]

        ymp.configs.demomodule.module_param_one=module_param_one_value
        ymp.configs.demomodule.module_param_two=module_param_two_value


##### 调用自定义模块

    public static void main(String[] args) throws Exception {
        YMP.get().init();
        try {
            // 获取自定义模块实例对象
            IDemoModule _demoModule = YMP.get().getModule(IDemoModule.class);
            // 调用模块业务接口方法
            System.out.println(_demoModule.sayHi());
            // 调用模块配置信息
            System.out.println(_demoModule.getModuleCfg().getModuleParamOne());
        } finally {
            YMP.get().destroy();
        }
    }

**注**：自定义模块不支持IoC、AOP等特性；

#### I18N

I18N服务是在YMP框架启动时初始化，其根据ymp.i18n_default_locale进行语言配置，默认采用系统运行环境的语言设置；


- 国际化资源管理器提供的主要方法：

    + 获取当前语言设置

            I18N.current();

    + 设置当前语言

            // 变更当前语言设置且不触发事件
            I18N.current(Locale.ENGLISH);

            或者

            // 将触发监听处理器onChanged事件
            I18N.change(Locale.ENGLISH);

    + 根据当前语言设置，加载指定名称资源文件内指定的属性值

            I18N.load("resources", "home_title");

            或者

            I18N.load("resources", "home_title", "首页");

    + 格式化消息字符串并绑定参数

            // 加载指定名称资源文件内指定的属性并使用格式化参数绑定
            I18N.formatMessage("resources", "site_title", "Welcome {0}, {1}"，"YMP"，“GoodLuck！”);

            // 使用格式化参数绑定
            I18N.formatMsg("Hello, {0}, {1}", "YMP"，“GoodLuck！”);

- 国际化资源管理器事件监听处理器，通过实现II18NEventHandler接口，在YMP配置文件中的`i18n_event_handler_class`参数进行设置，该监听器可以完成如下操作：

    + 自定义资源文件加载过程

    + 自定义获取当前语言设置

    + 语言设置变更的事件处理过程

#### Lang

##### BlurObject：模糊对象

    BlurObject.bind("1234").toLongValue();

##### PairObject：结对对象

    List<String> _key = new ArrayList<String>();
    Map<String, String> _value = new HashMap<String, String>();
    ...
    PairObject _pObj = new PairObject(_key, _value);

    //
    _pObj.getKey();
    //
    _pObj.getValue();

##### TreeObject：树型对象

    Object _id = UUIDUtils.UUID();
    TreeObject _target = new TreeObject()
            .put("id", _id)
            .put("category", new Byte[]{1, 2, 3, 4})
            .put("create_time", new Date().getTime(), true)
            .put("is_locked", true)
            .put("detail", new TreeObject()
                    .put("real_name", "汉字将被混淆", true)
                    .put("age", 32));

    // 这样赋值是List
    TreeObject _list = new TreeObject();
    _list.add("list item 1");
    _list.add("list item 2");

    // 这样赋值代表Map
    TreeObject _map = new TreeObject();
    _map.put("key1", "keyvalue1");
    _map.put("key2", "keyvalue2");

    TreeObject idsT = new TreeObject();
    idsT.put("ids", _list);
    idsT.put("maps", _map);

    // List操作
    System.out.println(idsT.get("ids").isList());
    System.out.println(idsT.get("ids").getList());

    // Map操作
    System.out.println(idsT.get("maps").isMap());
    System.out.println(idsT.get("maps").getMap());

    //
    _target.put("map", _map);
    _target.put("list", _list);

    //
    System.out.println(_target.get("detail").getMixString("real_name"));

    // TreeObject对象转换为JSON字符串输出
    String _jsonStr = _target.toJson().toJSONString();
    System.out.println(_jsonStr);

    // 通过JSON字符串转换为TreeObject对象-->再转为JSON字符串输出
    String _jsonStrTmp = (_target = TreeObject.fromJson(_target.toJson())).toJson().toJSONString();
    System.out.println(_jsonStrTmp);
    System.out.println(_jsonStr.equals(_jsonStrTmp));

#### Util

关于YMP框架常用的工具类，这里着重介绍以下几个：

- ClassUtils提供的BeanWrapper工具，它是一个类对象包裹器，赋予对象简单的属性操作能力；

        public static void main(String[] args) throws Exception {
            // 包裹一个Bean对象
            ClassUtils.BeanWrapper<DemoBean> _w = ClassUtils.wrapper(new DemoBean());
            // 输出该对象的成员属性名称
            for (String _fieldName : _w.getFieldNames()) {
                System.out.println(_fieldName);
            }
            // 为成员属性设置值
            _w.setValue("name", "YMP");
            // 获取成员属性值
            _w.getValue("name");
            // 拷贝Bean对象属性到目标对象(不局限相同对象)
            DemoBean _bean = _w.duplicate(new DemoBean());
            // 将对象属性转为Map存储
            Map<String, Object> _maps = _w.toMap();
            // 通过Map对象构建Bean对象并获取Bean实例
            DemoBean _target = ClassUtils.wrapper(DemoBean.class).fromMap(_maps).getTargetObject();
        }

- RuntimeUtils运行时工具类，获取运行时相关信息；

    + 获取当前环境变量：

            RuntimeUtils.getSystemEnvs();

            RuntimeUtils.getSystemEnv("JAVA_HOME");

    + 判断当前运行环境操作系统：

            RuntimeUtils.isUnixOrLinux();

            RuntimeUtils.isWindows();

    + 获取应用根路径：若WEB工程则基于.../WEB-INF/返回，若普通工程则返回类所在路径

            RuntimeUtils.getRootPath();

            RuntimeUtils.getRootPath(false);

    + 替换环境变量：支持${root}、${user.dir}和${user.home}环境变量占位符替换

            RuntimeUtils.replaceEnvVariable("${root}/home");
