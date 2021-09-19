# jdbc-utils

Jdbc-utils 工具利用 ThreadLocal 使其获取的数据库连接具有事务性，并解决并发的问题，继承 commons-dbutils 的 QueryRunner 类，再复写它的方法，复写方法中用 Jdbc-utils  提供具有事务的连接对象，这样就实现了方法本身自动处理连接对象，使操作数据库的代码更加优雅！



### ThreadLocal

ThreadLocal 类只有三个方法：

```java
void set(T value); //保存值
T get(); //获取值
void remove(); //移除值
```

ThreadLocal 内部其实是个 Map 来保存数据。虽然在使用 ThreadLocal 时只给出了值，没有给出键，其实它内部使用了当前线程做为键。

![threadlocal](https://raw.githubusercontent.com/objcoding/objcoding.github.io/master/images/threadlocal.png)



### QueryRunner API

- update

```java
int update(String sql, Object... params); //可执行增、删、改语句
int update(Connection con, String sql, Object... parmas); //需要调用者提供Connection，这说明本方法不再管理Connection了。支持事务!
```

- query

```java
T query(String sql, ResultSetHandler rsh, Object... params); //可执行查询，它会先得到ResultSet，然后调用rsh的handle()把rs转换成需要的类型！
T query(Connection con, String sql, ResultSetHadler rsh, Object... params); //支持事务。
```



### ResultSetHandler 接口

- BeanHandler  (单行) --> 构造器需要一个 Class 类型的参数，用来把一行结果转换成指定类型的 javaBean 对象；


- BeanListHandler (多行) --> 构造器也是需要一个 Class 类型的参数，用来把一行结果集转换成一个javabean，那么多行就是转换成 List 对象，一堆 javabean；



- MapHandler (单行) --> 把一行结果集转换Map对象：


一行记录：

| uid  | username  | age  | gender |
| ---- | --------- | ---- | ------ |
| 1001 | objcoding | 18   | Male   |

对应一个Map：

```json
{uid:1001, username:zs, age:99, gender:male}
```

- MapListHandler (多行) --> 把一行记录转换成一个 Map，多行就是多个 Map，即 List<Map>！ScalarHandler (单行单列) --> 通常用与 select count(*) from user 语句！结果集是单行单列的！它返回一个 Object。