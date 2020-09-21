### 插件（Plugin）

插件模块采用独立的ClassLoader类加载器来管理私有JAR包、类、资源文件等，设计目标是在接口开发模式下，将需求进行更细颗粒度拆分，从而达到一个理想化可重用代码的封装形态；

每个插件都是封闭的世界，插件与外界之间沟通的唯一方法是通过业务接口调用，管理这些插件的容器被称之为插件工厂(IPluginFactory)，负责插件的分析、加载和初始化，以及插件的生命周期管理，插件模块支持创建多个插件工厂实例，工厂对象之间完全独立，无任何依赖关系；

#### Maven包依赖

    <dependency>
        <groupId>net.ymate.platform</groupId>
        <artifactId>ymate-platform-plugin</artifactId>
        <version>2.0.2</version>
    </dependency>

> **注**：在项目的pom.xml中添加上述配置，该模块已经默认引入核心包依赖，无需重复配置。

#### 插件工厂

插件工厂分为两种，一种是以模块的形式封装，由YMP框架初始化时根据配置参数自动构建，称之为默认插件工厂(有且仅能存在一个默认工厂实例)，另一种是通过代码手动配置构建的自定义插件工厂，不同之处在于默认插件工厂与框架结合得更紧密，两种模式可以并存；

##### 默认插件工厂

默认插件工厂是在插件模块被YMP框架初始化时自动创建的，其初始化参数及说明如下：

	#-------------------------------------
	# Plugin插件模块初始化参数
	#-------------------------------------
	
	# 插件主目录路径，可选参数，默认值为${root}/plugins
	ymp.configs.plugin.plugin_home=
	
	# 自动扫描包路径集合，多个包名之间用'|'分隔，默认与框架自动扫描的包路径相同
	ymp.configs.plugin.autoscan_packages=
	
	# 插件是否自动启动，默认为true
	ymp.configs.plugin.automatic=
	
	# 是否加载当前CLASSPATH内的所有包含插件配置文件的JAR包，默认为true
	ymp.configs.plugin.included_classpath=

禁用默认插件工厂的方法：

	# 在YMP框架配置文件中找到"模块排除列表"项，添加插件模块的名称，如：
	ymp.excluded_modules=plugin

调用默认插件工厂的方法：

    Plugins.get().getPluginFactory();

默认插件工厂的事件监听方法：

> 默认插件工厂是通过YMP框架的事件服务订阅进行处理，PluginEvent插件事件对象包括以下事件类型：

|事务类型|说明|
|---|---|
|PLUGIN_INITED|插件初始化事件|
|PLUGIN_STARTED|插件启动事件|
|PLUGIN_SHUTDOWN|插件停止事件|
|PLUGIN_DESTROYED|插件销毁事件|

##### 自定义插件工厂

自定义插件工厂有两种方式：

- 通过`@PluginFactory`注解配置插件工厂，注解参数说明如下：

	|参数|说明|
	|---|---|
    |pluginHome|插件存放路径，必需提供；|
    |autoscanPackages|自动扫描路径，默认为插件工厂所在包路径；|
    |automatic|插件是否自动启动，默认为true；|
    |includedClassPath|是否加载当前CLASSPATH内的所有包含插件配置文件的JAR包，默认为false；|
    |listenerClass|插件生命周期事件监听器类对象, 可选配置；|

	示例代码：

            @PluginFactory(pluginHome = "${root}/plugins")
            public class DemoPluginFactory extends DefaultPluginFactory {
            }

            // 或者

            @PluginFactory(pluginHome = "${root}/plugins",
                    autoscanPackages = {"com.company", "cn.company"},
                    automatic = true,
                    includedClassPath = false,
                    listenerClass = DemoPluginEventListener.class)
            public class DemoPluginFactory extends DefaultPluginFactory {
            }

- 通过工厂配置对象实例化

	创建工厂配置对象：

            DefaultPluginConfig _conf = new DefaultPluginConfig();
            _conf.setPluginHome(new File(RuntimeUtils.replaceEnvVariable("${root}/plugins")));
            _conf.setAutomatic(true);
            _conf.setAutoscanPackages(Arrays.asList("com.company", "cn.company"));
            _conf.setIncludedClassPath(false);
            _conf.setPluginEventListener(new DefaultPluginEventListener());

	创建并初始化插件工厂实例对象：

            IPluginFactory _factory = new DefaultPluginFactory();
            _factory.init(_conf);

	自定义插件工厂的事件监听方法：

     > 自定义插件工厂的事件处理方式与默认插件工厂不同，须通过实现IPluginEventListener接口完成插件生命周期事件监听，IPluginEventListener接口事件方法及说明如下：

	|事件|说明|
	|---|---|
	|onInited|插件初始化事件；|
    |onStarted|插件启动事件；|
    |onShutdown|插件停止事件；|
    |onDestroy|插件销毁事件；|

	示例代码：

		public class DemoPluginEventListener implements IPluginEventListener {
		
		    public void onInited(IPluginContext context, IPlugin plugin) {
		        System.out.println("onInited: " + context.getPluginMeta().getName());
		    }
		
		    public void onStarted(IPluginContext context, IPlugin plugin) {
		        System.out.println("onStarted: " + context.getPluginMeta().getName());
		    }
		
		    public void onShutdown(IPluginContext context, IPlugin plugin) {
		        System.out.println("onShutdown: " + context.getPluginMeta().getName());
		    }
		
		    public void onDestroy(IPluginContext context, IPlugin plugin) {
		        System.out.println("onDestroy: " + context.getPluginMeta().getName());
		    }
		}

#### 插件结构

插件有两种形式，一种是将插件以JAR包文件形式存储，这类插件可以直接与工程类路径下其它依赖包一起使用，另一种是将插件类文件及插件依赖包等资源放在插件目录结构下，这类插件可以放在工程路径以外，可以多模块共用插件，其目录结构如下：

    <PLUGIN_HOME>\
        |--.plugin\
        |   |--lib\
        |   |   |--xxxx.jar
        |   |   |--...
        |   |--classes\
        |   |   |--...
        |   |--...
        |--<plugin_xxx>\
        |   |--lib\
        |   |   |--xxxx.jar
        |   |   |--...
        |   |--classes\
        |   |   |--...
        |   |--...
        |--<plugin_xxxx>\
        |--...

> 插件目录结构说明：

> - 每一个插件工厂所指定的PLUGIN_HOME根路径下都可以通过一个名称为".plugin"的目录将一些JAR包或类等资源文件进行全局共享；

> - 每一个插件都是一个独立的目录，一般以插件ID命名(不限于)，并将插件相关的JAR包和类文件等资源分别放置在对应的lib、classes或其它目录下；

#### 插件

通过在一个实现了IPlugin接口的类上声明`@Plugin`注解来创建插件启动类，其将被插件工厂加载和管理，一个插件包可以包括多个插件启动类，每个插件启动类可以实现自己的业务接口对外提供服务；

- `@Plugin`注解参数说明：

    > id：插件唯一ID，若未填写则使用初始化类名称进行MD5加密后的值做为ID；
    >
    > name：插件名称，默认为"";
    >
    > alias：插件别名，默认为"";
    >
    > author：插件作者，默认为"";
    >
    > email：联系邮箱，默认为"";
    >
    > version：插件版本，默认为"1.0.0";
    >
    > automatic：是否加载后自动启动运行，默认true;
    >
    > description：插件描述，默认为"";

- IPlugin接口方法说明：

    > init：插件初始化；
    >
    > getPluginContext：返回插件环境上下文对象；
    >
    > isInited：返回插件是否已初始化；
    >
    > isStarted：返回插件是否已启动；
    >
    > startup：启动插件；
    >
    > shutdown：停止插件；
    >
    > destroy：销毁插件对象；

插件框架提供了一个封装了IPlugin接口的AbstractPlugin抽象类，建议直接继承，示例代码：

    @Plugin
    public class DemoPlugin extends AbstractPlugin {
        // 根据需要重写父类方法...
    }

结合业务接口的插件示例：

    // 定义一个业务接口
    public interface IBusiness {
        void sayHi();
    }

    @Plugin(id = "demo_plugin",
            name = "DemoPlugin",
            author = "有理想的鱼",
            email = "suninformaiton#163.com",
            version = "1.0")
    public class DemoPlugin extends AbstractPlugin implements IBusiness {

        @Override
        public void startup() throws Exception {
            super.startup();
            //
            System.out.println("started.");
        }

        @Override
        public void shutdown() throws Exception {
            super.shutdown();
            //
            System.out.println("shutdown.");
        }

        public void sayHi() {
            System.out.println("Hi, from Plugin.");
        }
    }

#### 插件的使用

上面我们已经创建了一个DemoPlugin插件并且实现了IBusiness业务接口，下面介绍如何使用插件和调用业务接口方法：

    public static void main(String[] args) throws Exception {
        YMP.get().init();
        try {
            DemoPlugin _plugin = (DemoPlugin) Plugins.get().getPluginFactory().getPlugin("demo_plugin");
            // 或者 
            // _plugin = Plugins.get().getPluginFactory().getPlugin(DemoPlugin.class);
            //
            _plugin.sayHi();
            //
            IBusiness _business = Plugins.get().getPluginFactory().getPlugin(IBusiness.class);
            _business.sayHi();
        } finally {
            YMP.get().destroy();
        }
    }

执行结果：

    Hi, from Plugin.
    Hi, from Plugin.
    shutdown.

**注**：同一个插件可以实现多个业务接口，若多个插件实现同一个业务接口，根据插件加载顺序，最后加载的插件实例对象将替换前者；