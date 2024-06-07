# 概述

- jun-db-record-sql-engine是一个集ActiveRecord及动态sql解析的数据层操作插件，抽取了mybatis源码SQL解析模块及jfinal的db+record模块，
- 1.1、相当于mybatis中的动态sql解析功能的抽取，主要是各种标签的XML解析，用法跟Mybatis的XML的SQL写法是一样的
- 1.2、类似mybatis的功能，解析带标签的动态sql，生成`?`占位符的sql和`?`对应的参数列表。
- 1.3、集成了ActiveRecord模型，完美解决弱类型的数据层组件，。
- 支持 `<if>` `<foreach>` `<where>` `<set>` `<trim>`

# 使用教程

- 在自己的maven项目中引入maven坐标
```xml
<dependency>
    <groupId>io.github.wujun728</groupId>
    <artifactId>jun-db-record-sql-engine</artifactId>
    <version>1.0.12</version>
</dependency>
```



- 核心api -- Db + Record模式
```

1､常见用法
Db类及其配套的Record类，提供了在Model类之外更为丰富的数据库操作功能。使用Db与Record类时，无需对数据库表进行映射，Record相当于一个通用的Model。以下为Db + Record模式的一些常见用法：

// 创建name属性为James,age属性为25的record对象并添加到数据库
Record user = new Record().set("name", "James").set("age", 25);
Db.save("user", user);
 
// 删除id值为25的user表中的记录
Db.deleteById("user", 25);
 
// 查询id值为25的Record将其name属性改为James并更新到数据库
user = Db.findById("user", 25).set("name", "James");
Db.update("user", user);
 
// 获取user的name属性
String userName = user.getStr("name");
// 获取user的age属性
Integer userAge = user.getInt("age");
 
// 查询所有年龄大于18岁的user
List<Record> users = Db.find("select * from user where age > 18");
 
// 分页查询年龄大于18的user,当前页号为1,每页10个user
Page<Record> userPage = Db.paginate(1, 10, "select *", "from user where age > ?", 18);
```





- 核心api -- SQL动态参数模式
```
DynamicSqlEngine engine = new DynamicSqlEngine();
SqlMeta sqlMeta = engine.parse(sql, map);
Object data = SqlEngine.executeSql(connection, apiSql.getSqlText(), sqlParam);
```
- 示例
```
@Test
public void testForeachIF() {
	DynamicSqlEngine engine = new DynamicSqlEngine();
	String sql = ("select * from user where name in <foreach collection='list' index='idx' open='(' separator=',' close=')'>#{item.name}== #{idx}<if test='id!=null'>  and id = #{id}</if></foreach>");
	Map<String, Object> map = new HashMap<>();

	ArrayList<User> arrayList = new ArrayList<>();
	arrayList.add(new User(10, "zhangsan"));
	arrayList.add(new User(11, "lisi"));
	map.put("list", arrayList.toArray());
	map.put("id", 100);

	SqlMeta sqlMeta = engine.parse(sql, map);
	SqlEngine.executeSql(connection, sql, map);
	System.out.println(sqlMeta.getSql());
	sqlMeta.getJdbcParamValues().forEach(System.out::println);
}

```

- 示例执行结果：
```
select * from user where name in  ( ? , ? ) 
zhangsan
lisi
```

