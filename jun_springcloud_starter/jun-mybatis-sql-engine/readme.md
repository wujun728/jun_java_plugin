# 概述

- jun-mybatis-sql-engine是一个动态sql解析，抽取了mybatis源码SQL解析模块，相当于mybatis中的动态sql解析功能的抽取，主要是各种标签的XML解析，用法跟Mybatis的XML的SQL写法是一样的
- 类似mybatis的功能，解析带标签的动态sql，生成`?`占位符的sql和`?`对应的参数列表。
- 支持 `<if>` `<foreach>` `<where>` `<set>` `<trim>`

# 使用教程

- 在自己的maven项目中引入maven坐标
```xml
<dependency>
    <groupId>io.github.wujun728</groupId>
    <artifactId>jun-mybatis-sql-engine</artifactId>
    <version>1.0.0-RELEASE</version>
</dependency>
```

- 核心api
```
DynamicSqlEngine engine = new DynamicSqlEngine();
SqlMeta sqlMeta = engine.parse(sql, map);
```
- 示例
```
@Test
public void testForeachIF() {
	DynamicSqlEngine engine = new DynamicSqlEngine();
	String sql = ("select * from user where name in <foreach collection='list' index='idx' open='(' separator=',' close=')'>#{item.name}== #{idx}<if test='id!=null'>  and id = #{id}</if></foreach>");
	Map<String, Object> map = new HashMap<>();

	ArrayList<User> arrayList = new ArrayList<>();
	arrayList.add(new User(10, "tom"));
	arrayList.add(new User(11, "jerry"));
	map.put("list", arrayList.toArray());
	map.put("id", 100);

	SqlMeta sqlMeta = engine.parse(sql, map);
	System.out.println(sqlMeta.getSql());
	sqlMeta.getJdbcParamValues().forEach(System.out::println);
}

```

- 示例执行结果：
```

select * from user where name in  ( ? , ? ) 
tom
jerry
```

