> 即使你是天才，如果你不努力，你也会被其它人超越。

![201509100645102367.jpg](https://blog.52itstyle.com/usr/uploads/2017/07/124857438.jpg)
<!--more-->
## 扯淡


有人说 从 jdbc->jdbctemplate->hibernation/mybatis 再到 jpa，真当开发人员的学习时间不要钱？我觉得到 h/m 这一级的封装已经有点过了，再往深处走就有病了。

还有人说JPA 很反人类（一个面试官），还举了一个很简单举了例子说：一个数据库如果有 50 个字段，那你写各种条件查询不是要写很多？就是应该用类似 SQL 的方式来查询啊？

其实在我看来，存在即合理，人们总是向着好的方向去发展，学习什么不需要成本，底层语言牛逼倒是去学啊，不还是看不懂，弄不明白。很多知识对于程序员来说，都是一通百通，查询文档就是了，最主要的是能方便以后的开发即可。

对于反人类这一说，只能说 to young to simple，JPA的初衷肯定也不会是让你写一个几十个字段的查询，顶多一到两个而已，非要这么极端？再说JPA也是提供了EntityManager来实现SQL或者HQL语句查询的不是，JPA本质上还是集成了Hibernate的很多优点的。

#### 2018年3月28日更新

最近在做一款小程序测评项目，后台用到了JPA，借此机会，同时也更新下使用到的一些比较好的API。

#### 需求：
学生表（app_student）、班级表（app_class）、当然表结构比较简单，比如这时候我们需要查询学生列表，但是需要同时查询班级表的一些数据，并以JSON或者实体的方式返回给调用者。

本次需求，主要实现JPA的以下几个特性：
- 封装EntityManager基类
- 多表查询返回一个List
- 多表查询返回一个Map
- 多表查询返回一个实体

Entitymanager的核心概念图：

![](https://gitee.com/uploads/images/2018/0328/144319_e5658f37_87650.jpeg "1190778-20171004143209911-1516587547.jpg")

## Swagger2测试

![输入图片说明](https://gitee.com/uploads/images/2018/0328/144439_3a51d954_87650.png "1.png")

返回List< Object[] >：

![输入图片说明](https://gitee.com/uploads/images/2018/0328/144446_7837e24f_87650.png "2.png")

返回List< Map< Object, Object > >：

![输入图片说明](https://gitee.com/uploads/images/2018/0328/144451_30e7d82a_87650.png "3.png")

返回List< Student >：

![输入图片说明](https://gitee.com/uploads/images/2018/0328/144458_555052d0_87650.png "4.png")


作者： 小柒2012

欢迎关注： https://blog.52itstyle.com

详细说明：

https://blog.52itstyle.com/archives/1297/

https://blog.52itstyle.com/archives/2582/