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



# mica-activerecord 模块

## 功能
- `@TableName` 注解 Model 自动 Mapping 映射。
- 基于 Druid 的可执行 Sql 打印。
- 基于 Druid 的 `DruidSqlDialect`，分页 sql 优化，支持多种数据库。
- Record 的 `jackson` 处理。
- `@Tx` 注解的 JFinal ActiveRecord 事务。
- 可自定义 `ActiveRecordPluginCustomizer` Bean，实现自定义扩展。
- `CodeGenerator` 代码生成 `markdown` 格式数据字典。
- `ModelUtil` 实现 Model、Record -> Bean 转换。

## 文档
jfinal ActiveRecord 文档：https://jfinal.com/doc/5-1

## 添加依赖
### maven
```xml
<dependency>
  <groupId>net.dreamlu</groupId>
  <artifactId>mica-activerecord</artifactId>
  <version>${version}</version>
</dependency>
```

## 配置
### ActiveRecord
| 配置项 | 默认值 | 说明 |
| ----- | ------ | ------ |
| mica.activerecord.dialect | mysql | 方言，默认：mysql，注意：设置为 Druid 时采用 Ansi + druid 分页优化，支持多种数据库 |
| mica.activerecord.auto-table-scan | true | 自定表扫描 |
| mica.activerecord.model-package |  | 模型的包路径 |
| mica.activerecord.base-template-path |  | sql 模板前缀 |
| mica.activerecord.sql-templates |  | sql 模板，支持多个 |
| mica.activerecord.transaction-level |  | 事务级别，默认：不可重复读 |

### Spring database（优先）
| 配置项 | 默认值 | 说明 |
| ----- | ------ | ------ |
| spring.datasource.url |  | 数据库地址 |
| spring.datasource.username |  | 数据库用户名 |
| spring.datasource.password |  | 数据库密码 |

### Druid
| 配置项 | 默认值 | 说明                     |
| ----- |----|------------------------|
| mica.druid.url |    | 数据库地址                  |
| mica.druid.username |    | 数据库用户名                 |
| mica.druid.password |    | 数据库密码                  |
| mica.druid.show-sql | true | 打印可执行 sql，默认为 true     |
| mica.druid.show-sql-patterns | [] | 打印 sql 的正则，list 列表，例如：`.*t_user.*` |
| mica.druid.connection-init-sql |    |                        |
| mica.druid.connection-properties |    |                        |
| mica.druid.default-transaction-isolation |    |                        |
| mica.druid.driver-class |    |                        |
| mica.druid.filters |    |                        |
| mica.druid.initial-size | 1  |                        |
| mica.druid.keep-alive |    |                        |
| mica.druid.log-abandoned | false |                        |
| mica.druid.max-active | 32 |                        |
| mica.druid.max-pool-prepared-statement-per-connection-size | -1 |                        |
| mica.druid.max-wait | -1 |                        |
| mica.druid.min-evictable-idle-time-millis | 1800000 |                        |
| mica.druid.min-idle | 10 |                        |
| mica.druid.public-key |    |                        |
| mica.druid.remove-abandoned | false |                        |
| mica.druid.remove-abandoned-timeout-millis | 300000 |                        |
| mica.druid.test-on-borrow | false |                        |
| mica.druid.test-on-return | false |                        |
| mica.druid.test-while-idle | true |                        |
| mica.druid.time-between-connect-error-millis | 30000 |                        |
| mica.druid.time-between-eviction-runs-millis | 60000 |                        |
| mica.druid.time-between-log-stats-millis |    |                        |
| mica.druid.validation-query | select 1 |                        |
| mica.druid.validation-query-timeout |    |                        |

## 代码生成
```java
/**
 * 在数据库表有任何变动时，运行一下 main 方法，极速响应变化进行代码重构
 */
public class CodeGeneratorTest {

	public static void main(String[] args) {
		CodeGenerator generator = CodeGenerator.create()
			.url("jdbc:mysql://127.0.0.1:3306/blog")
			.username("root")
			.password("12345678")
			.basePackageName("net.dreamlu.demo")
			.outputDir(PathKit.getWebRootPath())
			.openDir() // 完成后打开目录窗口
			.build();
		// 为生成器添加类型映射，将数据库反射得到的类型映射到指定类型
//		generator.addTypeMapping(Date.class, LocalDateTime.class);
		// 设置数据库方言
		generator.setDialect(new MysqlDialect());
		// 设置是否生成链式 setter 方法
		generator.setGenerateChainSetter(false);
		// 添加不需要生成的表名
		generator.addExcludedTable("adv");
		// 设置是否在 Model 中生成 dao 对象
		generator.setGenerateDaoInModel(true);
		// 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为 "User"而非 OscUser
		generator.setRemovedTableNamePrefixes("t_");
		// 生成
		generator.generate();
	}

}
```

## 示例：自定义 jFinal ActiveRecord Plugin 配置
```java
@AutoConfiguration
public class MicaArpCustomConfiguration {

	@Bean
	public ActiveRecordPluginCustomizer activeRecordPluginCustomizer() {
		return new ActiveRecordPluginCustomizer() {
			@Override
			public void customize(ActiveRecordPlugin arp) {
				System.out.println("----------------ActiveRecordPluginCustomizer-----------------");
				arp.setDevMode(true);
			}
		};
	}

}
```

## TODO 
- 对多数据源的支持