# Spring-mongoDB
  随着Nosql分布式数据库的不断出现，各种非关系型数据库的重要性日益涌现。本项目基于Spring-data的API，实现一个Spring框架连接文档数据库mongoDB，并对数据进行管理的基本功能，包含的内容如下：
  
  1.包含对文档的基本增删改查（CRUD）的功能，基于Spring-data的MongoRepository扩展实现，update和insert操作本质上使用的同一个DAO接口，区别是update操作必须提供主键id，insert操作不提供id字段；
  
  2.包含数据排序、分页的功能；
  
  3.包含按条件进行数据筛选的功能；
  
  4.前端分页插件使用Bootgrid，前端框架使用Bootstrap；
  
  5.使用时，先开启mongoDB，新建数据库Spring-nosql;
  
  6.页面访问入口http://localhost:8080/Spring-mongoDB/mongodb/index
  
  
  本项目的实例文档模式如下：
```
{
    "_id" : ObjectId("58369d57eab6bfd4371a5d37"),
    "_class" : "po.Picture",
    "filename" : "activiti教程",
    "path" : "f盘",
    "size" : NumberLong(9765)
  }
```
  
  
  
  效果图

![输入图片说明](http://git.oschina.net/uploads/images/2016/1129/204109_2e0eb116_1110335.jpeg "在这里输入图片标题")
![输入图片说明](http://git.oschina.net/uploads/images/2016/1129/204119_e64b2915_1110335.jpeg "在这里输入图片标题")
![输入图片说明](http://git.oschina.net/uploads/images/2016/1129/204131_df5ed465_1110335.jpeg "在这里输入图片标题")