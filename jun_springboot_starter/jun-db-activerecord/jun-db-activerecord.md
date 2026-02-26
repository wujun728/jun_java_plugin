# jun-db-activerecord 操作手册

> 基于 Spring JDBC 的轻量级 ORM 框架，参考 JFinal ActiveRecord 设计模式。
> 版本：1.0.25 | Java：1.8+ | 协议：Apache 2.0

---

## 目录

- [1. 概述](#1-概述)
- [2. Maven 依赖](#2-maven-依赖)
- [3. 初始化](#3-初始化)
- [4. 四种操作模式](#4-四种操作模式)
  - [4.1 SQL 模式](#41-sql-模式)
  - [4.2 Record 模式](#42-record-模式)
  - [4.3 Bean 模式](#43-bean-模式)
  - [4.4 Model 模式](#44-model-模式)
- [5. 分页查询](#5-分页查询)
- [6. 事务支持](#6-事务支持)
- [7. 多数据源](#7-多数据源)
- [8. 数据库方言](#8-数据库方言)
- [9. 注解支持](#9-注解支持)
- [10. 工具类 RecordUtil](#10-工具类-recordutil)
- [11. 核心类 API 速查表](#11-核心类-api-速查表)
- [12. 线程安全与并发](#12-线程安全与并发)
- [13. 单元测试覆盖](#13-单元测试覆盖)
- [14. 常见问题](#14-常见问题)

---

## 1. 概述

`jun-db-activerecord` 是一个基于 Spring JDBC (`JdbcTemplate`) 构建的轻量级 ORM 框架，提供四种数据操作模式：

| 模式 | 入口类 | 适用场景 |
|------|--------|---------|
| **SQL 模式** | `Db` | 直接执行 SQL，返回 `Map`/基本类型 |
| **Record 模式** | `Db` | Key-Value 风格的 CRUD，无需定义实体类 |
| **Bean 模式** | `Db` | 操作 JPA/MyBatis-Plus 注解实体 |
| **Model 模式** | `Model<M>` | ActiveRecord 模式，实体自带 CRUD 方法 |

**核心架构**

```
Db (静态门面) → DbPro (数据库引擎) → Dialect (方言策略) → JdbcTemplate (Spring JDBC)
                    ↑
               Model<M> (ActiveRecord 基类)
```

**支持的数据库**：MySQL、Oracle、PostgreSQL、SQLite、H2

---

## 2. Maven 依赖

```xml
<dependency>
    <groupId>io.github.wujun728</groupId>
    <artifactId>jun-db-activerecord</artifactId>
    <version>1.0.25</version>
</dependency>
```

关键传递依赖：

| 依赖 | 版本 | 用途 |
|------|------|------|
| spring-jdbc | 5.3.39 | JDBC 操作核心 |
| druid | 1.2.24 | 数据源连接池 |
| hutool-all | 5.8.25 | 工具类/元数据获取 |
| fastjson2 | 2.0.37 | Bean/Map 序列化转换 |
| mybatis-plus-annotation | 3.5.9 | `@TableName`/`@TableField` 注解 |

---

## 3. 初始化

### 3.1 编程式初始化（推荐）

```java
// 方式一：传入 DataSource
DataSource dataSource = ...; // DruidDataSource, HikariDataSource 等
Db.init(dataSource);

// 方式二：传入连接参数（内部创建 DruidDataSource）
Db.init("main", "jdbc:mysql://localhost:3306/mydb", "root", "password");
```

### 3.2 Spring Boot 自动初始化

在 `application.yml` 中配置：

```yaml
db:
  main:
    enable: true
    url: jdbc:mysql://localhost:3306/mydb?useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: password
    driver: com.mysql.cj.jdbc.Driver  # 可省略，框架自动识别
```

`Db` 类标注了 `@Component`，Spring 容器启动后自动执行 `@PostConstruct` 初始化。

### 3.3 初始化行为

初始化过程自动完成：
1. 创建 `JdbcTemplate` 实例（fetchSize=32）
2. 创建 `TransactionTemplate` 实例
3. **自动检测数据库方言**（通过 `connection.getMetaData().getDatabaseProductName()`）
4. **自动注册所有表的主键信息**（通过 `hutool MetaUtil`）

---

## 4. 四种操作模式

### 4.1 SQL 模式

直接执行 SQL 语句，适合复杂查询和批量操作。

#### 增删改

```java
// 无参数执行
Db.execute("DELETE FROM user_info WHERE age < 18");

// 带参数执行（防 SQL 注入）
Db.execute("INSERT INTO user_info(user_name, age) VALUES(?, ?)",
    new Object[]{"张三", 25});

// 更新
Db.update("UPDATE user_info SET age = ? WHERE user_name = ?", 30, "张三");

// 插入并返回自增主键
long id = Db.insert("INSERT INTO user_info(user_name, age) VALUES(?, ?)",
    new Object[]{"李四", 28});
```

#### 查询

```java
// 查询列表 → List<Map<String, Object>>
List<Map<String, Object>> list = Db.queryList("SELECT * FROM user_info");
List<Map<String, Object>> list = Db.queryList("SELECT * FROM user_info WHERE age > ?", 20);

// 查询单条 → Map<String, Object>
Map<String, Object> map = Db.queryForMap("SELECT * FROM user_info WHERE id = ?", 1);

// 查询标量值
int count    = Db.queryForInt("SELECT count(*) FROM user_info");
long total   = Db.queryForLong("SELECT count(*) FROM user_info");
String name  = Db.queryForString("SELECT user_name FROM user_info WHERE id = ?", 1);
Date created = Db.queryForDate("SELECT create_time FROM user_info WHERE id = ?", 1);

// 查询第一个值（泛型）
Object first = Db.queryFirst("SELECT count(*) FROM user_info");

// 简便计数
int count = Db.count("SELECT count(*) FROM user_info WHERE age > ?", 20);
```

#### 批量操作

```java
List<Object[]> batchParams = new ArrayList<>();
batchParams.add(new Object[]{"用户A", 20, "a@test.com"});
batchParams.add(new Object[]{"用户B", 21, "b@test.com"});
batchParams.add(new Object[]{"用户C", 22, "c@test.com"});

Db.batchExecute("INSERT INTO user_info(user_name, age, email) VALUES(?, ?, ?)", batchParams);
```

---

### 4.2 Record 模式

通过 `Record` 对象进行 CRUD，无需定义实体类。`Record` 本质是一个 `Map<String, Object>` 的封装。

#### 创建与操作 Record

```java
// 链式创建
Record record = new Record()
    .set("user_name", "张三")
    .set("age", 25)
    .set("email", "zhangsan@test.com");

// 读取值（类型安全的 getter）
String name   = record.getStr("user_name");     // → "张三"
Integer age   = record.getInt("age");            // → 25
Long id       = record.getLong("id");            // → null
Double score  = record.getDouble("score");       // → null
Boolean flag  = record.getBoolean("active");
BigDecimal bd = record.getBigDecimal("amount");
byte[] data   = record.getBytes("avatar");

// 带默认值
Object val = record.get("missing", "默认值");    // → "默认值"

// 获取所有列名/值
String[] names   = record.getColumnNames();
Object[] values  = record.getColumnValues();
Map<String, Object> cols = record.getColumns();

// 删除列
record.remove("email");
record.remove("a", "b", "c");  // 批量删除
record.removeNullValueColumns(); // 删除值为 null 的列
record.clear();                  // 清空所有列
```

#### CRUD 操作

```java
// ===== 保存 =====
Record record = new Record().set("user_name", "张三").set("age", 25);
Db.save("user_info", record);                    // 自动检测主键
Db.save("user_info", "id", record);              // 手动指定主键

// ===== 查询 =====
Record user = Db.findById("user_info", 1);                           // 自动主键
Record user = Db.findById("user_info", "id", 1);                     // 指定主键
Record user = Db.findByIds("user_info", "id", 1);                    // 复合主键
List<Record> all = Db.findAll("user_info");                           // 查询全部
List<Record> list = Db.find("SELECT * FROM user_info WHERE age > ?", 20);
Record first = Db.findFirst("SELECT * FROM user_info ORDER BY id LIMIT 1");

// 按列值查询
List<Record> users = Db.findByColumnValueRecords("user_info", "user_name", "张三");

// ===== 更新 =====
Record user = Db.findById("user_info", "id", 1);
user.set("AGE", 30);  // 注意：数据库返回的列名可能是大写
Db.update("user_info", "id", user);

// ===== 删除 =====
Db.deleteById("user_info", 1);                   // 自动主键
Db.deleteById("user_info", "id", 1);             // 指定主键
Db.delete("user_info", "id", record);            // 通过 Record 删除
```

> **注意**：H2 等数据库返回**大写**列名（如 `USER_NAME`），更新时需确保使用正确的列名大小写，否则会产生重复列。框架在匹配主键值时支持大小写不敏感匹配。

#### Record 与 Model 互转

```java
// Record → Model
Record record = Db.findById("user_info", "id", 1);
UserModel user = record.toModel(UserModel.class);

// Model → Record
Record record = user.toRecord();
```

---

### 4.3 Bean 模式

直接操作 Java 实体对象，支持 JPA 和 MyBatis-Plus 注解。

#### 定义实体

```java
// 方式一：JPA 注解
@javax.persistence.Table(name = "user_info")
public class UserInfo {
    @javax.persistence.Id
    private Long id;
    private String userName;      // → 自动映射为 user_name
    @javax.persistence.Column(name = "email_address")
    private String email;         // → 映射为 email_address
    @javax.persistence.Transient
    private String tempField;     // → 忽略此字段
    // getter/setter ...
}

// 方式二：MyBatis-Plus 注解
@com.baomidou.mybatisplus.annotation.TableName("user_info")
public class UserInfo {
    @com.baomidou.mybatisplus.annotation.TableField("user_name")
    private String userName;
    @com.baomidou.mybatisplus.annotation.TableField(exist = false)
    private String ignoredField;  // → 忽略此字段
    // getter/setter ...
}

// 方式三：无注解（类名自动转下划线作为表名）
public class UserInfo { ... }  // → 表名 user_info
```

#### CRUD 操作

```java
// ===== 保存 =====
UserInfo user = new UserInfo();
user.setId(1L);
user.setUserName("张三");
user.setAge(25);
Db.saveBean(user);

// ===== 更新 =====
user.setAge(30);
Db.updateBean(user);

// ===== 删除 =====
Db.deleteBean(user);

// ===== 查询 =====
UserInfo user = Db.findBeanById(UserInfo.class, 1);
List<UserInfo> list = Db.findBeanList(UserInfo.class, "SELECT * FROM user_info");
List<UserInfo> list = Db.findBeanList(UserInfo.class,
    "SELECT * FROM user_info WHERE age > ?", 20);

// ===== 分页 =====
Page<UserInfo> page = Db.findBeanPages(UserInfo.class, 1, 10);
Page<UserInfo> page = Db.findBeanPages(UserInfo.class, 1, 10,
    "SELECT * FROM user_info WHERE age > 20 ORDER BY id");
```

---

### 4.4 Model 模式（ActiveRecord）

实体类继承 `Model<M>` 获得 CRUD 能力，无需编写 DAO/Mapper 层。

#### 定义 Model

```java
@Table(name = "user_info")
public class User extends Model<User> {
    // 静态 dao 对象，作为查询入口
    public static final User dao = new User().dao();
}

// 无 @Table 注解时，类名自动转下划线：OrderItem → order_item
public class OrderItem extends Model<OrderItem> {
    public static final OrderItem dao = new OrderItem().dao();
}

// 使用 @Id 指定非默认主键
@Table(name = "sys_config")
public class SysConfig extends Model<SysConfig> {
    @Id
    private String configKey;  // → 主键为 config_key
    public static final SysConfig dao = new SysConfig().dao();
}
```

#### 主键解析优先级

1. 字段上的 `@javax.persistence.Id` 注解
2. 数据库元数据中注册的主键（`TABLE_PK_MAP`）
3. 默认值 `"id"`

#### 表名解析优先级

1. `@javax.persistence.Table(name = "...")` 注解
2. `@com.baomidou.mybatisplus.annotation.TableName("...")` 注解
3. 类名驼峰转下划线（`UserInfo` → `user_info`）

#### CRUD 操作

```java
// ===== 保存 =====
new User().set("user_name", "张三").set("age", 25).set("email", "test@test.com").save();

// ===== 查询 =====
User user = User.dao.findById(1);
User user = User.dao.findFirst("SELECT * FROM user_info WHERE name = ?", "张三");
List<User> users = User.dao.find("SELECT * FROM user_info WHERE age > ?", 20);
List<User> users = User.dao.find("SELECT * FROM user_info");
List<User> all = User.dao.findAll();

// ===== 更新 =====
User user = User.dao.findById(1);
user.set("USER_NAME", "新名字").update();

// ===== 删除 =====
User user = User.dao.findById(1);
user.delete();                    // 通过对象删除
User.dao.deleteById(1);           // 通过主键删除
User.dao.deleteByIds(1, 2, 3);   // 批量删除（复合主键）
```

#### 属性操作

```java
User user = new User()
    .set("name", "张三")
    .set("age", 25);

// 类型安全读取
String name     = user.getStr("name");
Integer age     = user.getInt("age");
Long id         = user.getLong("id");
Double score    = user.getDouble("score");
Float rate      = user.getFloat("rate");
Boolean active  = user.getBoolean("active");
BigDecimal amt  = user.getBigDecimal("amount");
BigInteger bi   = user.getBigInteger("big_num");
Date date       = user.getDate("create_time");
Timestamp ts    = user.getTimestamp("update_time");
byte[] avatar   = user.getBytes("avatar");
Number num      = user.getNumber("count");

// 带默认值
Object val = user.get("missing", "默认值");

// 批量设置
Map<String, Object> map = new HashMap<>();
map.put("name", "李四");
map.put("age", 30);
user.put(map);

// 获取全部属性
Map<String, Object> attrs = user.getAttrs();
String[] names = user.getAttrNames();
Object[] values = user.getAttrValues();

// 移除属性
user.remove("temp_field");
user.remove("a", "b", "c");
user.clear();
```

#### equals / hashCode / toString

```java
User u1 = new User().set("id", 1L).set("name", "test");
User u2 = new User().set("id", 1L).set("name", "test");

u1.equals(u2);    // → true（比较 attrs Map 和 usefulClass）
u1.hashCode();     // → 基于 attrs 和 usefulClass 的哈希
u1.toString();     // → "User{id:1, name:test}"
```

---

## 5. 分页查询

所有分页方法返回 `Page<T>` 对象。

### Page 对象结构

| 字段 | 类型 | 含义 |
|------|------|------|
| `list` | `List<T>` | 当前页数据 |
| `pageNumber` | `int` | 当前页码（从 1 开始） |
| `pageSize` | `int` | 每页大小 |
| `totalPage` | `int` | 总页数 |
| `totalRow` | `int` | 总记录数 |

### Record 分页

```java
// 方式一：select + sqlExceptSelect 分离（自动生成 count SQL）
Page<Record> page = Db.paginate(1, 10, "SELECT *", "FROM user_info WHERE age > ?", 20);

// 方式二：完整 SQL 分页（手动指定 count SQL）
Page<Record> page = Db.paginateByFullSql(1, 10,
    "SELECT count(*) FROM user_info WHERE age > ?",
    "SELECT * FROM user_info WHERE age > ? ORDER BY id",
    20);

// 方式三：含 GROUP BY 的分页
Page<Record> page = Db.paginate(1, 10, true,
    "SELECT age, count(*)", "FROM user_info GROUP BY age");
```

### Bean 分页

```java
// 自动生成查询 SQL
Page<UserInfo> page = Db.findBeanPages(UserInfo.class, 1, 10);

// 指定查询 SQL
Page<UserInfo> page = Db.findBeanPages(UserInfo.class, 1, 10,
    "SELECT * FROM user_info WHERE age > 20 ORDER BY id");
```

### Map 分页

```java
Page<Map<String, Object>> page = Db.queryMapPages(
    "SELECT * FROM user_info ORDER BY id", 1, 10, null);
```

### Model 分页

```java
Page<User> page = User.dao.paginate(1, 10, "SELECT *", "FROM user_info ORDER BY id");
Page<User> page = User.dao.paginate(1, 10, "SELECT *",
    "FROM user_info WHERE age > ?", 20);
Page<User> page = User.dao.paginateByFullSql(1, 5,
    "SELECT count(*) FROM user_info",
    "SELECT * FROM user_info ORDER BY id");
```

### 边界行为

| 场景 | 行为 |
|------|------|
| `pageNumber < 1` 或 `pageSize < 1` | 抛出 `DbException` |
| 表为空（totalRow=0） | 返回空 Page（list 为空列表） |
| pageNumber 超过 totalPage | 返回空 Page（list 为空列表，保留 totalRow/totalPage） |

---

## 6. 事务支持

### 6.1 IAtom 事务（推荐）

```java
// 返回 true → 提交；返回 false → 回滚
boolean success = Db.tx(() -> {
    Db.execute("INSERT INTO user_info(user_name, age) VALUES('用户A', 20)");
    Db.execute("INSERT INTO user_info(user_name, age) VALUES('用户B', 21)");
    return true;  // 提交
});

// 回滚示例
boolean success = Db.tx(() -> {
    Db.execute("INSERT INTO user_info(user_name, age) VALUES('用户A', 20)");
    return false;  // 回滚，上面的插入不会生效
});

// 异常自动回滚
Db.tx(() -> {
    Db.execute("INSERT INTO user_info(user_name, age) VALUES('用户A', 20)");
    throw new SQLException("模拟异常");  // 自动回滚
});
```

### 6.2 BatchSql 事务

```java
BatchSql batchSql = new BatchSql();
batchSql.addBatch("INSERT INTO user_info(user_name, age) VALUES(?, ?)",
    new Object[]{"批量1", 20});
batchSql.addBatch("INSERT INTO user_info(user_name, age) VALUES(?, ?)",
    new Object[]{"批量2", 21});
batchSql.addBatch("DELETE FROM temp_table");  // 无参数

int result = Db.doInTransaction(batchSql);  // 返回 1 成功，异常时抛出 DbException 并回滚
```

### 6.3 Model 事务

```java
User.dao.tx(() -> {
    new User().set("user_name", "事务1").set("age", 20).save();
    new User().set("user_name", "事务2").set("age", 21).save();
    return true;
});
```

### 事务行为总结

| 场景 | 行为 |
|------|------|
| `IAtom.run()` 返回 `true` | 提交事务 |
| `IAtom.run()` 返回 `false` | 回滚事务，`tx()` 返回 `false` |
| `IAtom.run()` 抛出异常 | 回滚事务，异常向上传播 |
| `BatchSql` 中任一条 SQL 失败 | 整体回滚，抛出 `DbException` |
| `doInTransaction(null)` | 直接返回 `0`，不做任何操作 |

---

## 7. 多数据源

### 初始化多个数据源

```java
// 主数据源
Db.init(mainDataSource);

// 从库
Db.init("slave", slaveDataSource);

// 通过连接参数
Db.init("analytics", "jdbc:mysql://analytics-db:3306/data", "user", "pass");
```

### Db 方式切换数据源

```java
DbPro slaveDb = Db.use("slave");
List<Map<String, Object>> data = slaveDb.queryList("SELECT * FROM user_info");
slaveDb.execute("INSERT INTO log(msg) VALUES(?)", new Object[]{"操作日志"});
```

### Model 方式切换数据源

```java
// use() 返回新实例，线程安全
User user = User.dao.use("slave").findById(1);
List<User> users = User.dao.use("slave").find("SELECT * FROM user_info");

// 在从库上保存
new User().use("slave").set("user_name", "从库用户").set("age", 30).save();
```

### 获取底层对象

```java
DbPro dbPro = Db.use();            // 主数据源
DbPro dbPro = Db.use("slave");     // 指定数据源

DataSource ds = dbPro.getDataSource();
JdbcTemplate jt = dbPro.getJdbcTemplate();
Dialect dialect = dbPro.getDialectInstance();
```

### 异常情况

| 场景 | 行为 |
|------|------|
| 未初始化时调用 `Db.use()` | 抛出 `RuntimeException: The main dataSource is not initialized` |
| 空 configName | 抛出 `RuntimeException: configName不能为空` |
| 不存在的 configName | 抛出 `RuntimeException: DbPro not found with configName: xxx` |

---

## 8. 数据库方言

框架通过 `Dialect` 策略模式支持多种数据库，初始化时**自动检测**。

### 自动检测规则

| 数据库产品名（不区分大小写） | 使用的方言类 |
|------|------|
| 包含 `oracle` | `OracleDialect` |
| 包含 `mysql` 或 `mariadb` | `MysqlDialect` |
| 包含 `sqlite` | `Sqlite3Dialect` |
| 包含 `postgre` | `PostgreSqlDialect` |
| 包含 `h2` | `MysqlDialect`（兼容模式） |
| 其他 | `MysqlDialect`（默认） |

### 方言差异

| 特性 | MySQL | Oracle | PostgreSQL | SQLite |
|------|-------|--------|-----------|--------|
| 标识符引号 | `` ` `` | 无 | `"` | 无 |
| 分页语法 | `LIMIT offset, size` | `ROWNUM` 嵌套 | `LIMIT size OFFSET offset` | `LIMIT offset, size` |
| 日期处理 | 默认 | `fillStatementHandleDateType` | `fillStatementHandleDateType` | `fillStatementHandleDateType` |
| 序列支持 | 否 | `forDbSave` 中支持 `.nextval` | 否 | 否 |
| 默认主键名 | `id` | `ID` | `id` | `id` |

---

## 9. 注解支持

### 表名注解

| 注解 | 来源 | 优先级 |
|------|------|--------|
| `@javax.persistence.Table(name = "t_user")` | JPA | 最高 |
| `@TableName("t_user")` | MyBatis-Plus | 次之 |
| 无注解 | 类名驼峰转下划线 | 最低 |

### 列名注解

| 注解 | 来源 | 行为 |
|------|------|------|
| `@javax.persistence.Column(name = "email_addr")` | JPA | 使用指定列名 |
| `@TableField("email_addr")` | MyBatis-Plus | 使用指定列名 |
| `@TableField(exist = false)` | MyBatis-Plus | 忽略该字段 |
| `@javax.persistence.Transient` | JPA | 忽略该字段 |
| `transient` 修饰符 | Java | 忽略该字段 |
| 无注解 | 字段名驼峰转下划线 | 默认行为 |

### 主键注解

| 注解 | 来源 | 行为 |
|------|------|------|
| `@javax.persistence.Id` | JPA | 标记为主键字段 |

**Model 主键解析优先级**：`@Id` 注解 > 数据库元数据 > 默认 `"id"`

---

## 10. 工具类 RecordUtil

`io.github.wujun728.db.utils.RecordUtil` 提供全量的转换与工具方法。

### 字段名转换

```java
RecordUtil.toUnderlineCase("userName");    // → "user_name"
RecordUtil.toUnderlineCase("createTime");  // → "create_time"
RecordUtil.toUnderlineCase("HTMLParser");  // → "HTML_parser"

RecordUtil.toCamelCase("user_name");       // → "userName"
RecordUtil.toCamelCase("create_time");     // → "createTime"
```

### Record ↔ Map 转换

```java
// Map → Record
Record record = RecordUtil.mapToRecord(map);
List<Record> records = RecordUtil.mapToRecords(mapList);

// Record → Map
Map<String, Object> map = RecordUtil.recordToMap(record);
List<Map<String, Object>> maps = RecordUtil.recordToMaps(recordList);

// Record分页 → Map分页
Page<Map<String, Object>> mapPage = RecordUtil.pageRecordToPageMap(recordPage);
```

### Record ↔ Bean 转换

```java
// Record → Bean
UserInfo user = RecordUtil.recordToBean(record, UserInfo.class);
List<UserInfo> users = RecordUtil.recordToBeans(recordList, UserInfo.class);

// Bean → Record（字段名根据注解解析为数据库列名）
Record record = RecordUtil.beanToRecord(bean);
List<Record> records = RecordUtil.beanToRecords(beanList);
```

### Map ↔ Bean 转换

```java
// Map → Bean（通过 FastJSON2 序列化）
UserInfo user = RecordUtil.mapToBean(map, UserInfo.class);
List<UserInfo> users = RecordUtil.mapToBeans(mapList, UserInfo.class);

// Bean → Map（字段名转驼峰作为key）
Map<String, Object> map = RecordUtil.beanToMap(bean);
List<Map<String, Object>> maps = RecordUtil.beanToMaps(beanList);
```

### 表名/列名解析

```java
// 获取实体对应的表名
String tableName = RecordUtil.getTableName(UserInfo.class);    // → "user_info"
String tableName = RecordUtil.getTableName(new UserInfo());    // → "user_info"

// 获取字段对应的列名
Field field = UserInfo.class.getDeclaredField("email");
String colName = RecordUtil.getColumnName(field);              // → "email_address" 或 null（跳过）
```

### 反射元数据（带缓存）

```java
// 获取类的字段元数据（首次反射，之后缓存命中）
List<RecordUtil.FieldMeta> metas = RecordUtil.getFieldMetas(UserInfo.class);
for (RecordUtil.FieldMeta meta : metas) {
    Field field = meta.field;           // 字段引用
    String columnName = meta.columnName; // 数据库列名，null 表示应跳过
}

// 获取所有字段（遍历完整继承链）
List<Field> fields = RecordUtil.allFields(UserInfo.class);
```

### SQL 调试格式化

```java
String sql = RecordUtil.formatSql(
    "SELECT * FROM user WHERE name = ? AND age = ?",
    new Object[]{"张三", 25});
// → "SELECT * FROM user WHERE name = '张三' AND age = '25'"
```

### null 安全

所有转换方法对 `null` 和空集合输入都安全处理：

| 输入 | 返回值 |
|------|--------|
| `mapToRecord(null)` | 空 Record |
| `mapToRecords(null)` / `mapToRecords(空列表)` | 空 List |
| `recordToMap(null)` | 空 Map |
| `mapToBean(null, ...)` / `mapToBean(空Map, ...)` | `null` |
| `recordToBean(null, ...)` | `null` |
| `beanToRecords(null)` / `beanToMaps(null)` | 空 List |
| `pageRecordToPageMap(null)` | `null` |

---

## 11. 核心类 API 速查表

### Db（静态门面）

| 方法 | 返回类型 | 说明 |
|------|----------|------|
| `init(DataSource)` | `void` | 初始化主数据源 |
| `init(name, DataSource)` | `void` | 初始化命名数据源 |
| `use()` | `DbPro` | 获取主数据源引擎 |
| `use(name)` | `DbPro` | 获取命名数据源引擎 |
| `execute(sql)` | `int` | 执行 DDL/DML |
| `execute(sql, params)` | `int` | 带参数执行 |
| `update(sql, params...)` | `int` | 更新操作 |
| `insert(sql, params)` | `long` | 插入并返回主键 |
| `batchExecute(sql, paramsList)` | `int` | 批量执行 |
| `queryList(sql, params...)` | `List<Map>` | 查询列表 |
| `queryForMap(sql, params...)` | `Map` | 查询单条 |
| `queryForInt/Long/String/Date(sql, params...)` | 标量值 | 查询标量 |
| `queryFirst(sql, params...)` | `<T>` | 查询第一个值 |
| `count(sql, params...)` | `int` | 计数查询 |
| `find(sql, params...)` | `List<Record>` | Record 列表查询 |
| `findAll(tableName)` | `List<Record>` | 查询全部 Record |
| `findFirst(sql, params...)` | `Record` | 查询首条 Record |
| `findById(table, id)` | `Record` | 按主键查 Record |
| `save(table, record)` | `boolean` | 保存 Record |
| `update(table, pk, record)` | `boolean` | 更新 Record |
| `delete(table, pk, record)` | `boolean` | 删除 Record |
| `deleteById(table, id)` | `boolean` | 按主键删除 |
| `paginate(page, size, select, sql, params...)` | `Page<Record>` | Record 分页 |
| `paginateByFullSql(...)` | `Page<Record>` | 完整 SQL 分页 |
| `saveBean(bean)` | `boolean` | 保存 Bean |
| `updateBean(bean)` | `boolean` | 更新 Bean |
| `deleteBean(bean)` | `boolean` | 删除 Bean |
| `findBeanList(clazz, sql, params...)` | `List<T>` | Bean 列表查询 |
| `findBeanById(clazz, id)` | `<T>` | 按主键查 Bean |
| `findBeanPages(clazz, page, rows)` | `Page<T>` | Bean 分页 |
| `queryMapPages(sql, page, limit, params)` | `Page<Map>` | Map 分页 |
| `tx(IAtom)` | `boolean` | 事务执行 |
| `doInTransaction(BatchSql)` | `int` | 批量事务 |

### Record

| 方法 | 说明 |
|------|------|
| `set(column, value)` | 设置列值（链式） |
| `get(column)` / `get(column, default)` | 获取值 |
| `getStr/Int/Long/Double/Float/Boolean/...` | 类型安全 getter |
| `getColumns()` | 获取底层 Map |
| `getColumnNames()` / `getColumnValues()` | 获取列名/值数组 |
| `put(map)` / `put(key, value)` | 批量/单个设置 |
| `setColumns(map)` / `setColumns(record)` | 从 Map/Record 合并 |
| `remove(column)` / `remove(columns...)` | 移除列 |
| `removeNullValueColumns()` | 移除 null 值列 |
| `clear()` | 清空所有列 |
| `toModel(modelClass)` | 转为 Model 实例 |

### Model\<M\>

| 方法 | 说明 |
|------|------|
| `dao()` | 创建查询入口 |
| `use(configName)` | 切换数据源（返回新实例） |
| `set/get/getStr/getInt/...` | 属性操作（同 Record） |
| `put(map)` / `remove(attr)` / `clear()` | 批量操作 |
| `save()` | 保存 |
| `update()` | 更新 |
| `delete()` | 删除（当前对象） |
| `deleteById(id)` / `deleteByIds(ids...)` | 按主键删除 |
| `findById(id)` / `findByIds(ids...)` | 按主键查询 |
| `findAll()` | 查询全部 |
| `find(sql, params...)` | SQL 查询 |
| `findFirst(sql, params...)` | 查询首条 |
| `paginate(page, size, select, sql, params...)` | 分页查询 |
| `paginateByFullSql(...)` | 完整 SQL 分页 |
| `toRecord()` | 转为 Record |
| `tx(IAtom)` | 事务执行 |
| `equals/hashCode/toString` | 对象方法 |

### BatchSql

| 方法 | 说明 |
|------|------|
| `addBatch(sql)` | 添加无参 SQL |
| `addBatch(sql, Object[])` | 添加带参 SQL |
| `addBatch(sql, List<?>)` | 添加带参 SQL（List 形式） |
| `getSqlList()` | 获取所有 SQL 列表 |
| `clearBatch()` | 清空 |

---

## 12. 线程安全与并发

### 线程安全保证

| 组件 | 线程安全机制 |
|------|-------------|
| `DbPro.init()` | `synchronized` 双重检查锁定 |
| `DbPro.cache` | `ConcurrentHashMap`（static final） |
| `TABLE_PK_MAP` | `ConcurrentHashMap` |
| `Db.MAIN` | `volatile` 保证可见性 |
| `getPkNames()` 延迟初始化 | `synchronized` 保护 |
| `RecordUtil.FIELD_META_CACHE` | `ConcurrentHashMap` + `computeIfAbsent` |
| `Model` 缓存字段 | `volatile`（usefulClassCache/tableNameCache/primaryKeyCache） |
| `JdbcTemplate` | Spring 框架保证线程安全 |
| `TransactionTemplate` | Spring 框架保证线程安全 |

### 并发使用建议

```java
// ✅ 正确：多线程共享 Db 静态方法
ExecutorService executor = Executors.newFixedThreadPool(10);
for (int i = 0; i < 100; i++) {
    executor.submit(() -> Db.execute("INSERT INTO log(msg) VALUES(?)", new Object[]{"log"}));
}

// ✅ 正确：Model.dao 是无状态的，可安全共享
ExecutorService executor = Executors.newFixedThreadPool(10);
for (int i = 0; i < 100; i++) {
    executor.submit(() -> User.dao.findById(1));
}

// ✅ 正确：Model.use() 返回新实例，线程安全
executor.submit(() -> User.dao.use("slave").findAll());

// ⚠️ 注意：Record 和 Model 实例本身不是线程安全的，不要多线程共享同一个实例
Record record = new Record().set("name", "test");
// 不要在多个线程中同时读写这个 record
```

---

## 13. 单元测试覆盖

共 **204 个测试用例**，使用 H2 内存数据库运行，覆盖全部核心功能。

### 测试类概览

| 测试类 | 测试数 | 覆盖范围 |
|--------|--------|---------|
| `DbActiveRecordTest` | 74 | SQL 模式、Record 模式、Bean 模式、事务、分页、RecordUtil 工具、多数据源、方言检测 |
| `ModelTest` | 33 | Model CRUD、链式操作、@Table/@Id 注解解析、Record 互转、多数据源、事务、equals/hashCode/toString |
| `ConcurrencyAndEdgeCaseTest` | 97 | 并发初始化、并发 CRUD、多数据源并发、4 种方言 SQL 生成、Record 类型转换边界、null/空参数边界、分页边界、事务异常回滚 |

### 运行测试

```bash
cd jun-db-activerecord
mvn clean test
```

### 测试分类详细列表

#### SQL 模式测试

| 测试方法 | 验证内容 |
|----------|---------|
| `testExecute` | 无参数 SQL 执行 |
| `testExecuteWithParams` | 带参数 SQL 执行 |
| `testUpdate` | UPDATE 语句 |
| `testInsertReturnKey` | INSERT 返回自增主键 |
| `testBatchExecute` | 批量 INSERT |
| `testQueryList` / `testQueryListWithParams` | 列表查询 |
| `testQueryForMap` | 单条查询 |
| `testQueryForInt` / `testQueryForLong` / `testQueryForString` | 标量查询 |
| `testQueryFirst` | 第一个值查询 |
| `testCount` | 计数查询 |

#### Record 模式测试

| 测试方法 | 验证内容 |
|----------|---------|
| `testRecordSave` / `testRecordSaveWithPrimaryKey` | 保存 |
| `testRecordFindById` / `testRecordFindByIds` | 主键查询 |
| `testRecordFind` / `testRecordFindAll` / `testRecordFindFirst` | 列表/全部/首条查询 |
| `testRecordUpdate` | 更新 |
| `testRecordDelete` / `testRecordDeleteById` | 删除 |
| `testFindByColumnValueRecords` | 按列值查询 |
| `testRecordSetAndGet` / `testRecordFluentApi` | Record 属性操作 |
| `testRecordRemove` / `testRecordRemoveNullValueColumns` | 列删除 |
| `testRecordGetColumnNames` / `testRecordGetColumnValues` | 列名/值数组 |

#### Bean 模式测试

| 测试方法 | 验证内容 |
|----------|---------|
| `testSaveBean` | Bean 保存 |
| `testUpdateBean` | Bean 更新 |
| `testDeleteBean` | Bean 删除 |
| `testFindBeanList` / `testFindBeanListWithParams` | Bean 查询 |
| `testFindBeanPages` | Bean 分页 |

#### Model 模式测试

| 测试方法 | 验证内容 |
|----------|---------|
| `testChainSetAndGet` / `testGetWithDefault` / `testPutMap` | 属性操作 |
| `testRemoveAndClear` / `testGetAttrNamesAndValues` | 属性管理 |
| `testSave` / `testSaveAndFindById` | 保存 + 查询 |
| `testUpdate` / `testDelete` / `testDeleteById` | 更新/删除 |
| `testFind` / `testFindFirst` / `testFindAll` | 查询 |
| `testTableAnnotation` / `testClassNameAsTableName` | 表名解析 |
| `testIdAnnotation` / `testIdAnnotationUpdate` / `testIdAnnotationDelete` | 主键注解 |
| `testUseMultiDatasource` | 多数据源 |
| `testTxCommit` / `testTxRollback` | 事务 |
| `testToString` / `testEquals` / `testHashCode` | 对象方法 |
| `testToRecord` / `testRecordToModel` | 互转 |

#### 分页测试

| 测试方法 | 验证内容 |
|----------|---------|
| `testPaginate` / `testPaginateSecondPage` / `testPaginateLastPage` | 基础分页 |
| `testPaginateWithParams` / `testPaginateByFullSql` | 带参数/完整 SQL |
| `testPaginateEmptyTable` | 空表分页 |
| `testPaginateInvalidPageNumber` / `testPaginateInvalidPageSize` | 非法参数 |
| `testPaginatePageExceedsTotal` | 页码超限 |
| `testQueryMapPages` | Map 分页 |

#### 并发安全测试

| 测试方法 | 验证内容 |
|----------|---------|
| `testConcurrentDbProInit` | 20 线程同时初始化同名数据源 → 只产生 1 个实例 |
| `testConcurrentForceInit` | 10 线程强制重新初始化 |
| `testConcurrentInsert` | 50 线程并发 INSERT |
| `testConcurrentRecordSaveAndFind` | 30 线程并发 Record 保存 |
| `testConcurrentReadWrite` | 30 线程混合读写 |
| `testConcurrentMultiDatasourceAccess` | 20 线程在 2 个数据源间并发操作 |

#### 方言 SQL 生成测试

| 测试方法 | 验证内容 |
|----------|---------|
| `testMysqlDialectForDbUpdateExcludesPK` | MySQL UPDATE 排除主键列 |
| `testOracleDialectForDbUpdateExcludesPK` | Oracle UPDATE 排除主键列 |
| `testPostgreSqlDialectForDbUpdateExcludesPK` | PostgreSQL UPDATE 排除主键列 |
| `testSqlite3DialectForDbUpdateExcludesPK` | SQLite UPDATE 排除主键列 |
| `testDialectForDbUpdateCompositePK` | 复合主键 UPDATE |
| `testMysqlDialectForDbSave/FindById/DeleteById` | MySQL SQL 生成 |
| `test*ForPaginate` | 各方言分页 SQL |
| `testDialectIsPrimaryKey` | 主键匹配（大小写不敏感） |
| `testDialectIsOracle` | Oracle 方言标识 |
| `testDialectReplaceOrderBy` | ORDER BY 移除 |

#### 类型转换边界测试

| 测试方法 | 验证内容 |
|----------|---------|
| `testRecordGetIntFromLong` | Long → Integer 安全转换 |
| `testRecordGetIntFromString` | String → Integer 解析 |
| `testRecordGetLongFromInt` | Integer → Long 安全转换 |
| `testRecordGetDoubleFromInt` | Integer → Double 安全转换 |
| `testRecordGetFloatFromDouble` | Double → Float 安全转换 |
| `testRecordGet*FromString` | 各类型字符串解析 |
| `testRecordGet*Null` | null 值安全返回 |

---

## 14. 常见问题

### Q: H2 数据库列名返回大写怎么办？

H2 默认返回大写列名（如 `USER_NAME`），而代码中可能使用小写（如 `user_name`）。框架在以下场景做了兼容：

- `DbPro.getRecordValue()` 对主键匹配支持大小写不敏感
- `Dialect.isPrimaryKey()` 使用 `equalsIgnoreCase()` 比较

建议：查询后使用数据库实际返回的列名（通常是大写），避免重复列。

### Q: Bean 模式和 Model 模式如何选择？

| 场景 | 推荐模式 |
|------|---------|
| 已有 JPA/MyBatis-Plus 实体类 | Bean 模式（`Db.saveBean`） |
| 不想定义实体、快速开发 | Record 模式（`Db.save`） |
| 需要 ActiveRecord 风格链式调用 | Model 模式 |
| 复杂 SQL 查询 | SQL 模式（`Db.queryList`） |

### Q: 主键如何配置？

优先级从高到低：
1. **Model 模式**：字段上的 `@Id` 注解
2. **自动检测**：框架启动时通过数据库元数据自动注册所有表的主键
3. **手动指定**：`Db.save("table", "pk_column", record)`
4. **默认值**：`"id"`

### Q: 如何处理复合主键？

```java
// 主键用逗号分隔
Db.save("user_role", "user_id,role_id", record);
Db.findByIds("user_role", "user_id,role_id", userId, roleId);
Db.deleteById("user_role", "user_id,role_id", userId, roleId);

// Model 模式
@Table(name = "user_role")
public class UserRole extends Model<UserRole> {
    @Id private Long userId;
    @Id private Long roleId;
    public static final UserRole dao = new UserRole().dao();
}
UserRole.dao.findByIds(userId, roleId);
```

### Q: 异常处理机制？

所有数据库操作异常统一封装为 `DbException`（继承 `RuntimeException`），携带原始异常和 SQL 信息：

```java
try {
    Db.execute("INVALID SQL");
} catch (DbException e) {
    String msg = e.getMessage();  // 包含错误信息 + SQL
    Throwable cause = e.getCause(); // 原始异常
}
```

---

## 附录：项目结构

```
src/main/java/io/github/wujun728/db/
├── record/
│   ├── Db.java                    # 静态门面（入口）
│   ├── DbPro.java                 # 核心数据库引擎
│   ├── Record.java                # 数据记录类（Map 封装）
│   ├── Model.java                 # ActiveRecord 基类
│   ├── Page.java                  # 分页结果对象
│   ├── DbException.java           # 统一异常
│   ├── IAtom.java                 # 事务回调接口
│   ├── dialect/
│   │   ├── Dialect.java           # 方言抽象基类
│   │   ├── MysqlDialect.java      # MySQL 方言
│   │   ├── OracleDialect.java     # Oracle 方言
│   │   ├── PostgreSqlDialect.java # PostgreSQL 方言
│   │   └── Sqlite3Dialect.java    # SQLite 方言
│   └── mapper/
│       └── BatchSql.java          # 批量 SQL 容器
└── utils/
    └── RecordUtil.java            # ORM 工具类（转换/反射/缓存）

src/test/java/io/github/wujun728/db/
├── DbActiveRecordTest.java        # SQL/Record/Bean 模式测试（74）
├── ModelTest.java                 # Model 模式测试（33）
└── ConcurrencyAndEdgeCaseTest.java # 并发/方言/边界测试（97）
```
