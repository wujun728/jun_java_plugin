### 配置体系（Configuration）

配置体系模块，是通过简单的目录结构实现在项目开发以及维护过程中，对配置等各种文件资源的统一管理，为模块化开发和部署提供灵活的、简单有效的解决方案；

#### Maven包依赖

    <dependency>
        <groupId>net.ymate.platform</groupId>
        <artifactId>ymate-platform-configuration</artifactId>
        <version>2.0.2</version>
    </dependency>

> **注**：在项目的pom.xml中添加上述配置，该模块已经默认引入核心包依赖，无需重复配置。

#### 特点

- 从开发角度规范了模块化开发流程、统一资源文件的生命周期管理；
- 从可维护角度将全部资源集成在整个体系中，具备有效的资源重用和灵活的系统集成构建、部署和数据备份与迁移等优势；
- 简单的配置文件检索、加载及管理模式；
- 模块间资源共享，模块(modules)可以共用所属项目(projects)的配置、类和jar包等资源文件；
- 默认支持XML和Properties配置文件解析，可以通过IConfigurationProvider接口自定义文件格式，支持缓存，避免重复加载；
- 配置对象支持`@Configuration`注解方式声明，无需编码即可自动加载并填充配置内容到类对象；
- 集成模块的构建（编译）与分发、服务的启动与停止，以及清晰的资源文件分类结构可快速定位；

#### 配置体系目录结构

按优先级由低到高的顺序依次是：全局(configHome) -> 项目(projects) -> 模块(modules)：


    CONFIG_HOME\
        |--bin\
        |--cfgs\
        |--classes\
        |--dist\
        |--lib\
        |--logs\
        |--plugins\
        |--projects\
        |   |--<project_xxx>
        |   |   |--cfgs\
        |   |   |--classes\
        |   |   |--lib\
        |   |   |--logs\
        |   |   |--modules\
        |   |   |   |--<module_xxx>
        |   |   |   |   |--cfgs\
        |   |   |   |   |--classes\
        |   |   |   |   |--lib\
        |   |   |   |   |--logs\
        |   |   |   |   |--plugins\
        |   |   |   |   |--<......>
        |   |   |   |--<......>
        |   |   |--plugins\
        |   |--<......>
        |--temp\
        |--......

#### 模块配置

配置体系模块初始化参数, 将下列配置项按需添加到ymp-conf.properties文件中, 否则模块将使用默认配置进行初始化:


        #-------------------------------------
        # 配置体系模块初始化参数
        #-------------------------------------
        
        # 配置体系根路径，必须绝对路径，前缀支持${root}、${user.home}和${user.dir}变量，默认为${root}
        ymp.configs.configuration.config_home=
        
        # 项目名称，做为根路径下级子目录，对现实项目起分类作用，默认为空
        ymp.configs.configuration.project_name=
        
        # 模块名称，此模块一般指现实项目中分拆的若干子项目的名称，默认为空
        ymp.configs.configuration.module_name=
        
        # 指定配置体系下的默认配置文件分析器，默认为net.ymate.platform.configuration.impl.DefaultConfigurationProvider
        ymp.configs.configuration.provider_class=


#### 示例一：解析XML配置

- 基于XML文件的基础配置格式如下, 为了配合测试代码, 请将该文件命名为configuration.xml并放置在`config_home`路径下的cfgs目录里:


        <?xml version="1.0" encoding="UTF-8"?>
        <!-- XML根节点为properties -->
        <properties>
        
            <!-- 分类节点为category, 默认分类名称为default -->
            <category name="default">
            
                <!-- 属性标签为property, name代表属性名称, value代表属性值(也可以用property标签包裹) -->
                <property name="company_name" value="Apple Inc."/>
                
                <!-- 用属性标签表示一个数组或集合数据类型的方法 -->
                <property name="products">
                    <!-- 集合元素必须用value标签包裹, 且value标签不要包括任何扩展属性 -->
                    <value>iphone</value>
                    <value>ipad</value>
                    <value>imac</value>
                    <value>itouch</value>
                </property>
                
                <!-- 用属性标签表示一个MAP数据类型的方法, abc代表扩展属性key, xyz代表扩展属性值, 扩展属性与item将被合并处理  -->
                <property name="product_spec" abc="xzy">
                    <!-- MAP元素用item标签包裹, 且item标签必须包含name扩展属性(其它扩展属性将被忽略), 元素值由item标签包裹 -->
                    <item name="color">red</item>
                    <item name="weight">120g</item>
                    <item name="size">small</item>
                    <item name="age">2015</item>
                </property>
            </category>
        </properties>


- 新建配置类DemoConfig, 通过`@Configuration`注解指定配置文件相对路径


        @Configuration("cfgs/configuration.xml")
        public class DemoConfig extends DefaultConfiguration {
        }


- 测试代码, 完成模块初始化并加载配置文件内容:


        public static void main(String[] args) throws Exception {
            YMP.get().init();
            try {
                DemoConfig _cfg = new DemoConfig();
                if (Cfgs.get().fillCfg(_cfg)) {
                    System.out.println(_cfg.getString("company_name"));
                    System.out.println(_cfg.getMap("product_spec"));
                    System.out.println(_cfg.getList("products"));
                }
            } finally {
                YMP.get().destroy();
            }
        }

- 执行结果:


        Apple Inc.
        {abc=xzy, color=red, size=small, weight=120g, age=2015}
        [itouch, imac, ipad, iphone]

#### 示例二：解析Properties配置

- 基于Properties文件的基础配置格式如下, 同样请将该文件命名为configuration.properties并放置在`config_home`路径下的cfgs目录里:


        #--------------------------------------------------------------------------
        # 配置文件内容格式: properties.<categoryName>.<propertyName>=[propertyValue]
        #
        # 注意: attributes将作为关键字使用, 用于表示分类, 属性, 集合和MAP的子属性集合
        #--------------------------------------------------------------------------
        
        # 举例1: 默认分类下表示公司名称, 默认分类名称为default
        properties.default.company_name=Apple Inc.
        
        #--------------------------------------------------------------------------
        # 数组和集合数据类型的表示方法: 多个值之间用'|'分隔, 如: Value1|Value2|...|ValueN
        #--------------------------------------------------------------------------
        properties.default.products=iphone|ipad|imac|itouch
        
        #--------------------------------------------------------------------------
        # MAP<K, V>数据类型的表示方法:
        # 如:产品规格(product_spec)的K分别是color|weight|size|age, 对应的V分别是热red|120g|small|2015
        #--------------------------------------------------------------------------
        properties.default.product_spec.color=red
        properties.default.product_spec.weight=120g
        properties.default.product_spec.size=small
        properties.default.product_spec.age=2015
        
        # 每个MAP都有属于其自身的属性列表(深度仅为一级), 用attributes表示, abc代表属性key, xyz代表属性值
        # 注: MAP数据类型的attributes和MAP本身的表示方法达到的效果是一样的
        properties.default.product_spec.attributes.abc=xyz


- 修改配置类DemoConfig如下, 通过`@ConfigurationProvider`注解指定配置文件内容解析器:


        @Configuration("cfgs/configuration.properties")
        @ConfigurationProvider(PropertyConfigurationProvider.class)
        public class DemoConfig extends DefaultConfiguration {
        }


- 重新执行示例代码, 执行结果与示例一结果相同:


        Apple Inc.
        {abc=xzy, color=red, size=small, weight=120g, age=2015}
        [itouch, imac, ipad, iphone]

#### 示例三：无需创建配置对象, 直接加载配置文件

    public static void main(String[] args) throws Exception {
        YMP.get().init();
        try {
            IConfiguration _cfg = Cfgs.get().loadCfg("cfgs/configuration.properties");
            if (_cfg != null) {
                System.out.println(_cfg.getString("company_name"));
                System.out.println(_cfg.getMap("product_spec"));
                System.out.println(_cfg.getList("products"));
            }
        } finally {
            YMP.get().destroy();
        }
    }


#### 配置体系模块更多操作

##### 获取路径信息

下列方法的返回结果会根据配置体系模块配置的不同而不同:

    // 返回配置体系根路径
    Cfgs.get().getConfigHome();
    
    // 返回项目根路径
    Cfgs.get().getProjectHome();
    
    // 返回项目模块根路径
    Cfgs.get().getModuleHome();
    
    // 返回user.dir所在路径
    Cfgs.get().getUserDir();
    
    // 返回user.home所在路径
    Cfgs.get().getUserHome();

##### 搜索目标文件

    // 在配置体系中搜索cfgs/configuration.xml文件并返回其File对象
    Cfgs.get().searchFile("cfgs/configuration.xml");
    
    // 在配置体系中搜索cfgs/configuration.properties文件并返回其绝对路径
    Cfgs.get().searchPath("cfgs/configuration.properties");
