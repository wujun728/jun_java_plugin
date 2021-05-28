`smart boot`是以**spring boot**为原型而构建的一款技术架构模型，致力于为企业的微服务方案实施提供基础服务。`smart boot`的本质就是一个spring boot工程，因此对于熟悉spring boot的开发者而言，掌握smart boot的几乎没什么门槛。即便仅是了解spring，也能在短时间内上手。

##smart-boot特点
1. 模块化编程、面向服务编程、测试框架;
2. 遵循“习惯优于配置”的原则，使用Smart Boot只需要很少的配置，大部分的时候我们直接使用默认的配置即可；
3. 项目快速搭建，可以无需配置的自动整合第三方的框架；
4. 可以完全不使用XML配置文件，只需要自动配置和Java Config；
5. 内嵌Servlet容器，降低了对环境的要求，可以使用命令直接执行项目，应用可用jar包执行：java -jar；
6. 提供了starter POM, 能够非常方便的进行包管理, 很大程度上减少了jar hell或者dependency hell；
7. 运行中应用状态的监控；
8. 对主流开发框架的无配置集成；


##smart-boot依赖
1. Mysql 默认对接mysql数据库，可根据实际项目需要重新进行配置
2. Redis 默认使用redis提供缓存服务，可根据实际项目需要重新进行配置
3. [smart-sosa](https://git.oschina.net/smartboot/smartboot-sosa) 提供底层RPC服务
4. [maven-mybatisdalgen-plugin](https://git.oschina.net/smartboot/maven-mybatisdalgen-plugin) 数据层采用了mybatis框架，通过该maven-mybatisdalgen-plugin插件可以方便的生成DAL层的代码以及配置文件。


##工程结构

- smart-assembly   
集中管理smart-boot工程依赖的所有配置文件(.properties、.yml、.xml等)
- smart-dal  
数据访问层,实现对数据库的CRUD操作
- smart-service-integration  
与第三方系统对接的module，以供smart-boot调用第三方服务
- smart-component  
组件module，遵循单一职责原则，向下对接smart-dal、smart-service-integration，向上为业务层smart-service-impl提供各组件式服务
- smart-service-facade  
定义smart-boot的服务接口，一个独立的module,不依赖其他模块。第三方系统可通过该module提供的接口调用服务
- smart-service-impl  
该module通过引用smart-componet提供的各组件用于实现smart-service-facade中定义的接口。对于私有服务可直接在本module中定义接口，无需放置在smart-service-facade中.  

>为方便使用，也可直接调用smart-service-integration中提供的服务

- smart-shared  
该module完全独立于业务，主要用于提供一些工具类，可被任一module引用
- smart-restful  
Web层，仅负责前后端的数据交互，不建议在该module中进行复杂的业务处理，应统一交由smart-service-impl处理

![系统结构图](images/1.png)

