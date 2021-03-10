# Transaction Isolation

相关代码：[src/main/java/isolation](src/main/java/isolation), [src/test/java/isolation](src/test/java/isolation)

参考文档：

1. [Ask Tom: On Transaction Isolation Levels][Ask Tom: On Transaction Isolation Levels]
2. [wiki isolation level][wiki isolation level]
3. [JDBC transaction][JDBC transaction]

## 数据库并发访问读取时的现象

先来了解一下当数据库并发读取时会出现的现象，因为所谓的事务隔离级别是针对这些现象而提出的：

1. Dirty read([wiki][Dirty read])：A事务在读取**某一行**数据的时候，能够读到B事务还未提交的、对**同一行**数据的修改。
2. Nonrepeatable read:([wiki][Nonrepeatable read])：在同一个事务里，在T1时间读取到的**某一行**的数据，在T2时间再次读取**同一行**数据时，发生了变化。后者变化可能是被更新了、消失了。
3. Phantom read([wiki][Phantom read])：在同一个事务里，用条件A，在T1时间查询到的数据是10行，但是在T2时间查询到的数据多于10行。需要注意的是，和 Nonrepeatable read 不同，Phantom read 在T1时读到的数据在T2时不会发生变化。注意，为何只说比10行多，那么比10行少就不是 Phantom read 了吗？因为 Nonrepeatable read 包含了数据消失的情况。

## 事务隔离级别

Isolation Level | Dirty Read | Nonrepeatable Read | Phantom Read |
----------------|------------|--------------------|--------------|
READ UNCOMMITTED| Permitted  | Permitte           | Permitted    |
READ COMMITTED  | --         | Permitted          | Permitted    |
REPEATABLE READ | --         | --                 | Permitted    |
SERIALIZABLE    | --         | --                 | --           |

## 例子

事务隔离级别因数据库的不同实现而有所区别（见[Ask Tom: On Transaction Isolation Levels][Ask Tom: On Transaction Isolation Levels])，因此这里只提供Oracle相关的测试代码。顺便一提，因为Oracle只支持READ COMMITTED和SERIALIZABLE两种隔离级别因此只针对这两个做了测试。具体情况就不多说了，自己看吧。

如果要启动测试代码，你需要有一个oracle数据库，然后修改 src/test/resources/application-isolation.properties 文件，修改里面的配置文件，然后再运行。


[Ask Tom: On Transaction Isolation Levels]: http://www.oracle.com/technetwork/issue-archive/2010/10-jan/o65asktom-082389.html
[wiki isolation level]: https://en.wikipedia.org/wiki/Isolation_(database_systems)
[JDBC transaction]: https://docs.oracle.com/javase/tutorial/jdbc/basics/transactions.html
[Dirty read]: https://en.wikipedia.org/wiki/Isolation_(database_systems)#Dirty_reads
[Nonrepeatable read]: https://en.wikipedia.org/wiki/Isolation_(database_systems)#Non-repeatable_reads
[Phantom read]: https://en.wikipedia.org/wiki/Isolation_(database_systems)#Phantom_reads