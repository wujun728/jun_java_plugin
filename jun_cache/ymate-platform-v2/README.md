### YMP v2——轻量级JAVA应用开发框架

![LOGO](http://git.oschina.net/suninformation/ymate-platform-v2/raw/master/misc/logo_small.png "LOGO")

YMP是一个非常简单、易用的一套轻量级JAVA应用开发框架，设计原则主要侧重于简化工作任务、规范开发流程、提高开发效率，让开发工作像搭积木一样轻松是我们一直不懈努力的目标！


#### 主要技术特点

- 采用组件化、模块化打包方式，可按需装配，灵活可扩展；

- 采用微内核实现Autoscan、AOP、IoC、Event等，涵盖SSH框架中绝大部分核心功能；

- 统一配置体系结构，感受不一样的文件资源配置及管理模式；

- 整合多种日志系统（log4j、jcl、slf4j）、日志文件可分离存储；

- 轻量级持久化层封装，针对RDBMS（MySQL、SQLServer、Oracle等）和NoSQL（MongoDB、Redis等）提供支持；

- 完善的插件机制，助力于更细颗粒度的业务拆分；

- 独特的独立服务开发体验；

- 功能强大的验证框架，完全基于Java注解，易于使用和扩展；

- 灵活的缓存服务，支持EhCache、Redis和多级缓存（MultiLevel）技术；

- 配置简单的MVC架构，强大且易于维护和扩展，支持RESTful风格，支持JSP、HTML、Binary、Freemarker、Velocity等多种视图技术；


#### 模块及功能

> YMP框架主要是由框架核心(Core)和若干模块(Modules)组成，整体结构非常简约、清晰，如图所示：

![Structure](http://git.oschina.net/suninformation/ymate-platform-v2/raw/master/misc/structure_diagram.png "Structure")

##### 框架核心(Core)
> 主要负责框架的初始化和模块的加载及其生命周期管理，功能包括：
>
> - 类对象管理器：提供包类的自动扫描以及Bean生命周期管理、依赖注入和方法拦截等特性；
> - 事件服务：通过事件注册和广播的方式触发和监听事件动作，并支持同步和异步两种模式执行事件队列；
> - 模块：是YMP框架所有功能特性封装的基础形式，负责模块的生命周期管理；
> - 国际化资源管理器：提供统一的资源文件加载、销毁和内容读取，支持自定义资源加载和语言变化的事件监听；
> - 另外，提供了一组自定义的数据结构和框架中需要的各种工具类；
>
> [查看文档...](http://git.oschina.net/suninformation/ymate-platform-v2/blob/master/ymate-platform-core/README.md)


##### 配置体系(Configuration)
> 通过简单的目录结构实现在项目开发以及维护过程中，对配置等各种文件资源的统一管理，为模块化开发和部署提供灵活的、简单有效的解决方案：
>
> - 规范模块化开发流程、统一资源文件管理；
> - 具备有效的资源重用和灵活的系统集成构建、部署和数据备份与迁移等优势；
> - 简单的配置文件检索、加载及管理模式；
> - 模块间资源共享，模块可以共用所属项目的配置、类和jar包等资源文件；
> - 默认支持XML和Properties配置文件解析，可以通过IConfigurationProvider接口自定义文件格式，支持缓存，避免重复加载；
> - 配置对象支持注解方式声明，无需编码即可自动加载并填充配置内容到类对象；
> - 集成模块的构建与分发、服务的启动与停止*，以及清晰的资源文件分类结构可快速定位；
>
> [查看文档...](http://git.oschina.net/suninformation/ymate-platform-v2/blob/master/ymate-platform-configuration/README.md)

##### 日志(Log)
> 基于开源日志框架Log4J 2实现，提供对日志记录器对象的统一管理，可以在任意位置调用任意日志记录器输出日志，实现系统与业务日志的分离，并针对apache-commons-logging日志框架和slf4j日志系统提供支持；
>
> [查看文档...](http://git.oschina.net/suninformation/ymate-platform-v2/blob/master/ymate-platform-log/README.md)

##### 持久化(Persistence)

###### JDBC

> 针对关系型数据库(RDBMS)数据存取的一套简单解决方案，主要关注数据存取的效率、易用性和透明，其具备以下功能特征：
>
> - 基于JDBC框架API进行轻量封装，结构简单、便于开发、调试和维护；
> - 优化批量数据更新、标准化结果集、预编译SQL语句处理；
> - 支持单实体ORM操作，无需编写SQL语句；
> - 提供脚手架工具，快速生成数据实体类，支持链式调用；
> - 支持通过存储器注解自定义SQL语句或从配置文件中加载SQL并自动执行；
> - 支持结果集与值对象的自动装配，支持自定义装配规则；
> - 支持多数据源，默认支持C3P0、DBCP、JNDI连接池配置，支持数据源扩展；
> - 支持多种数据库(如:Oracle、MySQL、SQLServer等)；
> - 支持面向对象的数据库查询封装，有助于减少或降低程序编译期错误；
> - 支持数据库事务嵌套；
> - 支持数据库视图和存储过程；
>
> [查看文档...](http://git.oschina.net/suninformation/ymate-platform-v2/blob/master/ymate-platform-persistence-jdbc/README.md)

###### MongoDB
> 
> 针对MongoDB的数据存取操作的特点，以JDBC模块的设计思想进行简单封装，采用会话机制，支持多数据源配置和实体操作、基于对象查询、MapReduce、GridFS、聚合及函数表达式集成等，仍需进一步完善改进，文档整理中，敬请期待...

###### Redis
>
> 基于Jedis驱动封装，采用会话机制，支持多数据源及连接池配置，仍需进一步完善改进，文档整理中，敬请期待...

##### 插件(Plugin)
> 采用独立的ClassLoader类加载器来管理私有JAR包、类、资源文件等，设计目标是在接口开发模式下，将需求进行更细颗粒度拆分，从而达到一个理想化可重用代码的封装形态；
>
> 每个插件都是封闭的世界，插件与外界之间沟通的唯一方法是通过业务接口调用，管理这些插件的容器被称之为插件工厂，负责插件的分析、加载和初始化，以及插件的生命周期管理，插件模块支持创建多个插件工厂实例，工厂对象之间完全独立，无任何依赖关系；

>
>
> [查看文档...](http://git.oschina.net/suninformation/ymate-platform-v2/blob/master/ymate-platform-plugin/README.md)

##### 服务(Serv)
> 一套基于NIO实现的通讯服务框架，提供TCP、UDP协议的客户端与服务端封装，灵活的消息监听与消息内容编/解码，简约的配置使二次开发更加便捷；
> 
> 同时默认提供断线重连、链路维护(心跳)等服务支持，您只需了解业务即可轻松完成开发工作。
>
>
>[查看文档...](http://git.oschina.net/suninformation/ymate-platform-v2/blob/master/ymate-platform-serv/README.md)

##### 验证(Validation)
> 服务端参数有效性验证工具，采用注解声明方式配置验证规则，更简单、更直观、更友好，支持方法参数和类成员属性验证，支持验证结果国际化I18N资源绑定，支持自定义验证器，支持多种验证模式；
>
> [查看文档...](http://git.oschina.net/suninformation/ymate-platform-v2/blob/master/ymate-platform-validation/README.md)

##### 缓存(Cache)
> 以EhCache作为默认JVM进程内缓存服务，通过整合外部Redis服务实现多级缓存（MultiLevel）的轻量级缓存框架，并与YMP框架深度集成(支持针对类方法的缓存，可以根据方法参数值进行缓存)，灵活的配置、易于使用和扩展；
>
> [查看文档...](http://git.oschina.net/suninformation/ymate-platform-v2/blob/master/ymate-platform-cache/README.md)

##### WebMVC框架
> WebMVC模块在YMP框架中是除了JDBC模块以外的另一个非常重要的模块，集成了YMP框架的诸多特性，在功能结构的设计和使用方法上依然保持一贯的简单风格，同时也继承了主流MVC框架的基因，对于了解和熟悉SSH等框架技术的开发人员来说，上手极其容易，毫无学习成本。
> 
> 其主要功能特性如下：
> 
> - 标准MVC实现，结构清晰，完全基于注解方式配置简单；
> - 支持约定模式，无需编写控制器代码，直接匹配并执行视图；
> - 支持多种视图技术(JSP、Freemarker、Velocity、Text、HTML、JSON、Binary、Forward、Redirect、HttpStatus等)；
> - 支持RESTful模式及URL风格；
> - 支持请求参数与控制器方法参数的自动绑定；
> - 支持参数有效性验证；
> - 支持控制器方法的拦截；
> - 支持注解配置控制器请求路由映射；
> - 支持自动扫描控制器类并注册；
> - 支持事件和异常的自定义处理；
> - 支持I18N资源国际化；
> - 支持控制器方法和视图缓存；
> - 支持控制器参数转义；
> - 支持插件扩展；
>
> [查看文档...](http://git.oschina.net/suninformation/ymate-platform-v2/blob/master/ymate-platform-webmvc/README.md)


#### 相关阅读

- [Quickstart——快速上手](http://git.oschina.net/suninformation/ymate-platform-v2/wikis/Quickstart)

- [Quickstart_New——扩展工具篇](http://git.oschina.net/suninformation/ymate-platform-v2/wikis/Quickstart_New)



#### One More Thing

YMP不仅提供便捷的Web及其它Java项目的快速开发体验，也将不断提供更多丰富的项目实践经验。

感兴趣的小伙伴儿们可以加入 官方QQ群480374360，一起交流学习，帮助YMP成长！

了解更多有关YMP框架的内容，请访问官网：http://www.ymate.net/
