# 手动回滚事务

相关文档：[Spring官方文档](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#transaction-declarative-rolling-back 章节结尾部分)

相关代码：[src/main/java/manual](src/main/java/manual)，[src/test/java/manual](src/test/java/manual)

在前面的文章[异常发生时的回滚机制](Exception_Rollback.md)里讲到，当发生checked exception时将处理权交给了调用方。在这里介绍一种逼不得已，不推荐使用的手动回滚事务的方法：

```java
TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
```

此方法之所以逼不得已，不推荐使用，是因为Spring更推荐一种不具侵入性的方法：声明式的事务处理。声明式的事务处理能够将领域代码（业务代码）和具体框架脱离，而不是紧紧得绑定在一起。



