# tyboot

[getee](https://gitee.com/magintursh/tyboot)

[github](https://github.com/magintursh/tyboot)

### 介绍

    tyboot是一个基于springboot的服务端脚手架，面向单体服务快速开发，需要微服务方案的可以看另一个项目tycloud(还在完善中)

### 特点

    1.简化基础业务开发过程
        a.针对单表查询，不用写sql。列表，分页，都不用写。
        b.dao层的mapper只是一个空的接口，大部分业务不需要在mapper中写任何代码(除非复杂报表查询)，也不需要mapper的xml文件。
        c.baseService中有大量的泛型方法以供使用，单表单对象增删改查在service层也不需要写代码，列表和分页查询也只需要一行代码。
        d.提倡单表操作。不提倡使用复杂sql解决复杂的业务问题。
    2.降低学习成本。新人快速上手，基础知识过关的新人，可以快速进入业务开发状态。
    3.常用组件集成。redis，mq，事件，mongodb等等
    4.通用业务模型的实现。开箱即用的业务模型，可以大大的缩减项目开发周期。计划实现的通用业务模型有
      订单系统，虚拟账户系统，支付网关，动态表单，报表系统，通用预约系统，优惠策略定制，
      基础数据(验证码，字典，内部消息，地理位置信息，通用文件信息存储，操作记录与计数，)
    5.集成常用第三方系统。短信（阿里大鱼），存储（七牛，阿里OSS），支付（微信公众平台，支付宝）

   ###  技术栈

     1. SpringBoot 2.1.6.RELEASE
     2. MyBatis-Plus 3.x
     3. mybatis-spring-boot-starter 1.2.0
     5. Kaptcha 2.3.2
     6. jackson-databind 2.9.7
     7. springfox-swagger2 2.2.2
     8. HikariCP 2.7.9

   ###  项目结构

  ~~~
   tyboot 
    ├─tyboot-api
    │  ├─tyboot-api-boot                示例项目，实现了数据字典，权限管理，其他项目通用的基础功能
    ├─tyboot-component------组件
    │  ├─tyboot-component-activemq------集成activemq
    │  ├─tyboot-component-amqp----------对spring的amqp简单集成
    │  ├─tyboot-component-cache---------缓存，对redis的进一步实现和封装，地理位置，管道，zset实现分页查询，分布式锁
    │  ├─tyboot-component-emq-----------集成mqtt服务 EMQ
    │  ├─tyboot-component-event---------利用spring的事件机制实现基于rest接口的事件处理机制
    │  ├─tyboot-component-mongo---------集成mongo
    │  ├─tyboot-component-opendata------集成常用第三方开放接口，七牛，阿里大鱼，腾讯im，极光推送
    │  └─tyboot-component-validation----参数校验
    ├─tyboot-core-----------核心包，一般情况下实例项目都会引用到
    │  ├─tyboot-core-auth---------------用户认证，session共享
    │  ├─tyboot-core-foundation---------常用工具类库，线程内上下文封装；Bean、File,列表转树结构，加密解密等等。。。。。
    │  ├─tyboot-core-rdbms--------------集成mybatis，mybatisplus，简化orm和封装servic通用操作
    │  └─tyboot-core-restful------------对restful风格的接口封装，集成接口文档，统一异常处理，请求拦截处理，返回数据封装，上下文封装
    └─tyboot-prototype------通用业务模型，针对特定业务场景进行封装实现
        ├─tyboot-prototype-account------虚拟账户系统
        ├─tyboot-prototype-order--------通用订单系统
        └─tyboot-prototype-trade--------简单支付渠道实现，支付宝，微信
   	    
  ~~~

   目录约定：

   	示例：
   	tyboot-api-privilege----------------以下目录为项目约定目录结构
      	    org.typroject.api.privilege.
      	                            controller-------接口目录 
      	                            face.------------业务层
      	                                model--------vo
      	                                orm.---------数据操作
      	                                    dao------mapper接口目录
      	                                    entity---po目录
      	                                service------业务实现类
   ###  约定

   **orm**

    1.entity需要继承BaseEntity
        对应的数据表不能缺少通用字段
     
         SEQUENCE_NBR    bigint	20    物理主键
         REC_USER_ID    varchar	32    最后更新者的id
         REC_DATE    datetime        最后更新时间
         
    2.使用了mybatisplus，所以实例项目中不需要引入mapper.xml，baseMapper的方法足够使用。
      除非要进行复杂查询，可自行引入xml文件 
    3.通常情况下dao层的mapper子接口只是一个空接口，除非要自己写sql，或引入了xml，才会在其中写代码。

   **service**
    
    1.继承baseService
  ```JAVA
    public class LocationInfoService extends BaseService<LocationInfoModel,LocationInfo,LocationInfoMapper> 
    {}
````
    2.service中的方法不要重载,因为会只根据方法名通过反射获取方法实例。
    3.示例项目中的service层没有写接口，直接使用的实现类进行操作。需要接口的自行定夺。负责业务设计时候 设计原则还是要讲究的。对于简单业务，可视情况而定。
    4.单表单对象的操作可以不需要在service中写方法，也不需要引入mapper，泛型方法足够用，
      分页查询和列表查询也只需要一行代码，示例如下：
-
      分页：
```java
        public Page<DictionarieModel> queryDictPage(Page page, String agencyCode ,
                                                       String buType,
                                                       String dictAlias,
                                                       @Condition(Operator.like) String dictName,
                                                       String dictCode) throws Exception
            {
                return this.queryForPage(page,"排序字段",false,agencyCode,buType,dictAlias,dictName,dictCode);
            }
```
          作为查询条件的参数名称需要和对应model中的属性名称一致。
          传入baseService.queryForPage中的params参数列表需要和前置方法(queryDictPage)的参数顺序一致，
          底层会自动解析前置方法参数名称并对值判空，然后转换为数据库字段名，用于构建条件组装器。
          注解@Condition用于定义条件操作符，已实现的条件操作符详见Operator，所有查询条件的逻辑关系都是与关系
          目前还没打算实现或关系。         
-
      列表：（参数约定与分页方法相同）
```java
      public List<DictionarieModel> queryDictList(String agencyCode ,
                                                     String buType,
                                                     String dictAlias,
                                                      @Condition(Operator.like)String dictName,
                                                     String dictCode) throws Exception
          {
              return this.queryForList("排序字段",false,agencyCode,buType,dictAlias,dictName,dictCode);
          }
```


   **controller**

    1.统一返回值，所有接口统一使用ResponseModel封装返回值。
    2.自定义注解@TycloudOperation用来定义接口的访问级别ApiLevel，鉴权控制needAuth
    3.可以设置是否返回真实http状态，或者全部返回200.

   **关于缓存**
    
    1.单表单对象缓存，单表列表缓存都已经集成到baseService的方法中,可以随着对象的更新刷新或删除缓存，可以查看方法备注以选择是使用。
    2.其他缓存场景建议直接使用rediTemplate进行操作
    3.tyboot-component-cache模块提供了基于redis Zset分页查询；地理位置计算和查询；redis管道的使用

   **代码生成器的使用**

    1.使用mybatisplus提供的代码生成器，详见示例项目。

   

   ###  最佳实践

    1.将tyboot-core和tyboot-component中的组件包打包发布到maven私服nexus中统一管理，
      然后各个实例项目引用后进行业务项目的开发，这样实例项目的业务代码会更加清晰，打包速度更快，可以随时升级所引用的tyboot版本。

### 计划

`完善文档`

`完善示例项目`

   ###  技术交流群
qq群：965400761
![tyboot技术交流群](https://images.gitee.com/uploads/images/2020/0424/100603_be4adf7d_431907.png "tyboot技术交流群群二维码.png")