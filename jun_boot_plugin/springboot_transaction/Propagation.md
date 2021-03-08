# Transaction Propagation

相关文档：[Spring官方文档](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#tx-propagation)，[Javadoc](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/transaction/annotation/Propagation.html)

相关代码：[src/main/java/propagation](src/main/java/propagation)，[src/test/java/propagation](src/test/java/propagation)

在Spring官方文档中只对三种Transaction Propagation模式做了说明：

1. [Required](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#tx-propagation-required)
2. [RequiresNew](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#tx-propagation-requires_new)
3. [Nested](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#tx-propagation-nested)

并没有对另外四种做说明：

1. SUPPORTS
2. MANDATORY
3. NOT_SUPPORTED
4. NEVER

此外也没有对不同的Transaction Propagation嵌套组合的行为作一个详细说明，因此本文将会补全这一点。

## 测试方法的说明

有两张表FOO、BAR，这两张表都很简单，只有一个``NAME VARCHAR2``字段。针对这两张表分别有[FooTxExecutor](src/main/java/propagation/FooTxExecutor.java)和[BarTxExecutor](src/main/java/propagation/BarTxExecutor.java)。

[FooTxExecutor](src/main/java/propagation/FooTxExecutor.java)有各种不同的实现，每个实现做的事情都相同，只是声明的Propagation模式不同。[BarTxExecutor](src/main/java/propagation/BarTxExecutor.java)也是如此。

先看看[BarTxAbstractExecutor](src/main/java/propagation/BarTxAbstractExecutor.java)做了什么：

1. 插入数据
2. 如果要求其抛出异常，则抛出一个FakeException

先看看[FooTxAbstractExecutor](src/main/java/propagation/FooTxAbstractExecutor.java)做了什么：

1. 插入数据
2. 执行[BarTxExecutor](src/main/java/propagation/BarTxExecutor.java)
3. 捕获[BarTxExecutor](src/main/java/propagation/BarTxExecutor.java)的异常
4. 如果要求其抛出异常，则抛出一个FakeException

然后将不同Propagation模式的[FooTxExecutor](src/main/java/propagation/FooTxExecutor.java)和[BarTxExecutor](src/main/java/propagation/BarTxExecutor.java)进行组合，获得测试结果。

## 测试结果

下表可以通过运行[PropagationCombinationTest](src/test/java/propagation/PropagationCombinationTest.java)获得。


Foo mode | Foo throw ex | Bar mode | Bar throw ex | Foo success? | Bar success? | Exception |
---------|--------------|----------|--------------|--------------|--------------|-----------|
REQUIRED | N | REQUIRED | N | Y | Y |   |
REQUIRED | Y | REQUIRED | N | N | N |   |
REQUIRED | N | REQUIRED | Y | N | N | org.springframework.transaction.UnexpectedRollbackException Transaction rolled back because it has been marked as rollback-only |
REQUIRED | Y | REQUIRED | Y | N | N |   |
REQUIRED | N | SUPPORTS | N | Y | Y |   |
REQUIRED | Y | SUPPORTS | N | N | N |   |
REQUIRED | N | SUPPORTS | Y | N | N | org.springframework.transaction.UnexpectedRollbackException Transaction rolled back because it has been marked as rollback-only |
REQUIRED | Y | SUPPORTS | Y | N | N |   |
REQUIRED | N | MANDATORY | N | Y | Y |   |
REQUIRED | Y | MANDATORY | N | N | N |   |
REQUIRED | N | MANDATORY | Y | N | N | org.springframework.transaction.UnexpectedRollbackException Transaction rolled back because it has been marked as rollback-only |
REQUIRED | Y | MANDATORY | Y | N | N |   |
REQUIRED | N | REQUIRES_NEW | N | Y | Y |   |
REQUIRED | Y | REQUIRES_NEW | N | N | Y |   |
REQUIRED | N | REQUIRES_NEW | Y | Y | N |   |
REQUIRED | Y | REQUIRES_NEW | Y | N | N |   |
REQUIRED | N | NOT_SUPPORTED | N | Y | Y |   |
REQUIRED | Y | NOT_SUPPORTED | N | N | Y |   |
REQUIRED | N | NOT_SUPPORTED | Y | Y | Y |   |
REQUIRED | Y | NOT_SUPPORTED | Y | N | Y |   |
REQUIRED | N | NEVER | N | Y | N |   |
REQUIRED | Y | NEVER | N | N | N |   |
REQUIRED | N | NEVER | Y | Y | N |   |
REQUIRED | Y | NEVER | Y | N | N |   |
REQUIRED | N | NESTED | N | Y | Y |   |
REQUIRED | Y | NESTED | N | N | N |   |
REQUIRED | N | NESTED | Y | Y | N |   |
REQUIRED | Y | NESTED | Y | N | N |   |
SUPPORTS | N | REQUIRED | N | Y | Y |   |
SUPPORTS | Y | REQUIRED | N | Y | Y |   |
SUPPORTS | N | REQUIRED | Y | Y | N |   |
SUPPORTS | Y | REQUIRED | Y | Y | N |   |
SUPPORTS | N | SUPPORTS | N | Y | Y |   |
SUPPORTS | Y | SUPPORTS | N | Y | Y |   |
SUPPORTS | N | SUPPORTS | Y | Y | Y |   |
SUPPORTS | Y | SUPPORTS | Y | Y | Y |   |
SUPPORTS | N | MANDATORY | N | Y | N |   |
SUPPORTS | Y | MANDATORY | N | Y | N |   |
SUPPORTS | N | MANDATORY | Y | Y | N |   |
SUPPORTS | Y | MANDATORY | Y | Y | N |   |
SUPPORTS | N | REQUIRES_NEW | N | Y | Y |   |
SUPPORTS | Y | REQUIRES_NEW | N | Y | Y |   |
SUPPORTS | N | REQUIRES_NEW | Y | Y | N |   |
SUPPORTS | Y | REQUIRES_NEW | Y | Y | N |   |
SUPPORTS | N | NOT_SUPPORTED | N | Y | Y |   |
SUPPORTS | Y | NOT_SUPPORTED | N | Y | Y |   |
SUPPORTS | N | NOT_SUPPORTED | Y | Y | Y |   |
SUPPORTS | Y | NOT_SUPPORTED | Y | Y | Y |   |
SUPPORTS | N | NEVER | N | Y | Y |   |
SUPPORTS | Y | NEVER | N | Y | Y |   |
SUPPORTS | N | NEVER | Y | Y | Y |   |
SUPPORTS | Y | NEVER | Y | Y | Y |   |
SUPPORTS | N | NESTED | N | Y | Y |   |
SUPPORTS | Y | NESTED | N | Y | Y |   |
SUPPORTS | N | NESTED | Y | Y | N |   |
SUPPORTS | Y | NESTED | Y | Y | N |   |
MANDATORY | N | REQUIRED | N | N | N | org.springframework.transaction.IllegalTransactionStateException No existing transaction found for transaction marked with propagation 'mandatory' |
MANDATORY | Y | REQUIRED | N | N | N | org.springframework.transaction.IllegalTransactionStateException No existing transaction found for transaction marked with propagation 'mandatory' |
MANDATORY | N | REQUIRED | Y | N | N | org.springframework.transaction.IllegalTransactionStateException No existing transaction found for transaction marked with propagation 'mandatory' |
MANDATORY | Y | REQUIRED | Y | N | N | org.springframework.transaction.IllegalTransactionStateException No existing transaction found for transaction marked with propagation 'mandatory' |
MANDATORY | N | SUPPORTS | N | N | N | org.springframework.transaction.IllegalTransactionStateException No existing transaction found for transaction marked with propagation 'mandatory' |
MANDATORY | Y | SUPPORTS | N | N | N | org.springframework.transaction.IllegalTransactionStateException No existing transaction found for transaction marked with propagation 'mandatory' |
MANDATORY | N | SUPPORTS | Y | N | N | org.springframework.transaction.IllegalTransactionStateException No existing transaction found for transaction marked with propagation 'mandatory' |
MANDATORY | Y | SUPPORTS | Y | N | N | org.springframework.transaction.IllegalTransactionStateException No existing transaction found for transaction marked with propagation 'mandatory' |
MANDATORY | N | MANDATORY | N | N | N | org.springframework.transaction.IllegalTransactionStateException No existing transaction found for transaction marked with propagation 'mandatory' |
MANDATORY | Y | MANDATORY | N | N | N | org.springframework.transaction.IllegalTransactionStateException No existing transaction found for transaction marked with propagation 'mandatory' |
MANDATORY | N | MANDATORY | Y | N | N | org.springframework.transaction.IllegalTransactionStateException No existing transaction found for transaction marked with propagation 'mandatory' |
MANDATORY | Y | MANDATORY | Y | N | N | org.springframework.transaction.IllegalTransactionStateException No existing transaction found for transaction marked with propagation 'mandatory' |
MANDATORY | N | REQUIRES_NEW | N | N | N | org.springframework.transaction.IllegalTransactionStateException No existing transaction found for transaction marked with propagation 'mandatory' |
MANDATORY | Y | REQUIRES_NEW | N | N | N | org.springframework.transaction.IllegalTransactionStateException No existing transaction found for transaction marked with propagation 'mandatory' |
MANDATORY | N | REQUIRES_NEW | Y | N | N | org.springframework.transaction.IllegalTransactionStateException No existing transaction found for transaction marked with propagation 'mandatory' |
MANDATORY | Y | REQUIRES_NEW | Y | N | N | org.springframework.transaction.IllegalTransactionStateException No existing transaction found for transaction marked with propagation 'mandatory' |
MANDATORY | N | NOT_SUPPORTED | N | N | N | org.springframework.transaction.IllegalTransactionStateException No existing transaction found for transaction marked with propagation 'mandatory' |
MANDATORY | Y | NOT_SUPPORTED | N | N | N | org.springframework.transaction.IllegalTransactionStateException No existing transaction found for transaction marked with propagation 'mandatory' |
MANDATORY | N | NOT_SUPPORTED | Y | N | N | org.springframework.transaction.IllegalTransactionStateException No existing transaction found for transaction marked with propagation 'mandatory' |
MANDATORY | Y | NOT_SUPPORTED | Y | N | N | org.springframework.transaction.IllegalTransactionStateException No existing transaction found for transaction marked with propagation 'mandatory' |
MANDATORY | N | NEVER | N | N | N | org.springframework.transaction.IllegalTransactionStateException No existing transaction found for transaction marked with propagation 'mandatory' |
MANDATORY | Y | NEVER | N | N | N | org.springframework.transaction.IllegalTransactionStateException No existing transaction found for transaction marked with propagation 'mandatory' |
MANDATORY | N | NEVER | Y | N | N | org.springframework.transaction.IllegalTransactionStateException No existing transaction found for transaction marked with propagation 'mandatory' |
MANDATORY | Y | NEVER | Y | N | N | org.springframework.transaction.IllegalTransactionStateException No existing transaction found for transaction marked with propagation 'mandatory' |
MANDATORY | N | NESTED | N | N | N | org.springframework.transaction.IllegalTransactionStateException No existing transaction found for transaction marked with propagation 'mandatory' |
MANDATORY | Y | NESTED | N | N | N | org.springframework.transaction.IllegalTransactionStateException No existing transaction found for transaction marked with propagation 'mandatory' |
MANDATORY | N | NESTED | Y | N | N | org.springframework.transaction.IllegalTransactionStateException No existing transaction found for transaction marked with propagation 'mandatory' |
MANDATORY | Y | NESTED | Y | N | N | org.springframework.transaction.IllegalTransactionStateException No existing transaction found for transaction marked with propagation 'mandatory' |
REQUIRES_NEW | N | REQUIRED | N | Y | Y |   |
REQUIRES_NEW | Y | REQUIRED | N | N | N |   |
REQUIRES_NEW | N | REQUIRED | Y | N | N | org.springframework.transaction.UnexpectedRollbackException Transaction rolled back because it has been marked as rollback-only |
REQUIRES_NEW | Y | REQUIRED | Y | N | N |   |
REQUIRES_NEW | N | SUPPORTS | N | Y | Y |   |
REQUIRES_NEW | Y | SUPPORTS | N | N | N |   |
REQUIRES_NEW | N | SUPPORTS | Y | N | N | org.springframework.transaction.UnexpectedRollbackException Transaction rolled back because it has been marked as rollback-only |
REQUIRES_NEW | Y | SUPPORTS | Y | N | N |   |
REQUIRES_NEW | N | MANDATORY | N | Y | Y |   |
REQUIRES_NEW | Y | MANDATORY | N | N | N |   |
REQUIRES_NEW | N | MANDATORY | Y | N | N | org.springframework.transaction.UnexpectedRollbackException Transaction rolled back because it has been marked as rollback-only |
REQUIRES_NEW | Y | MANDATORY | Y | N | N |   |
REQUIRES_NEW | N | REQUIRES_NEW | N | Y | Y |   |
REQUIRES_NEW | Y | REQUIRES_NEW | N | N | Y |   |
REQUIRES_NEW | N | REQUIRES_NEW | Y | Y | N |   |
REQUIRES_NEW | Y | REQUIRES_NEW | Y | N | N |   |
REQUIRES_NEW | N | NOT_SUPPORTED | N | Y | Y |   |
REQUIRES_NEW | Y | NOT_SUPPORTED | N | N | Y |   |
REQUIRES_NEW | N | NOT_SUPPORTED | Y | Y | Y |   |
REQUIRES_NEW | Y | NOT_SUPPORTED | Y | N | Y |   |
REQUIRES_NEW | N | NEVER | N | Y | N |   |
REQUIRES_NEW | Y | NEVER | N | N | N |   |
REQUIRES_NEW | N | NEVER | Y | Y | N |   |
REQUIRES_NEW | Y | NEVER | Y | N | N |   |
REQUIRES_NEW | N | NESTED | N | Y | Y |   |
REQUIRES_NEW | Y | NESTED | N | N | N |   |
REQUIRES_NEW | N | NESTED | Y | Y | N |   |
REQUIRES_NEW | Y | NESTED | Y | N | N |   |
NOT_SUPPORTED | N | REQUIRED | N | Y | Y |   |
NOT_SUPPORTED | Y | REQUIRED | N | Y | Y |   |
NOT_SUPPORTED | N | REQUIRED | Y | Y | N |   |
NOT_SUPPORTED | Y | REQUIRED | Y | Y | N |   |
NOT_SUPPORTED | N | SUPPORTS | N | Y | Y |   |
NOT_SUPPORTED | Y | SUPPORTS | N | Y | Y |   |
NOT_SUPPORTED | N | SUPPORTS | Y | Y | Y |   |
NOT_SUPPORTED | Y | SUPPORTS | Y | Y | Y |   |
NOT_SUPPORTED | N | MANDATORY | N | Y | N |   |
NOT_SUPPORTED | Y | MANDATORY | N | Y | N |   |
NOT_SUPPORTED | N | MANDATORY | Y | Y | N |   |
NOT_SUPPORTED | Y | MANDATORY | Y | Y | N |   |
NOT_SUPPORTED | N | REQUIRES_NEW | N | Y | Y |   |
NOT_SUPPORTED | Y | REQUIRES_NEW | N | Y | Y |   |
NOT_SUPPORTED | N | REQUIRES_NEW | Y | Y | N |   |
NOT_SUPPORTED | Y | REQUIRES_NEW | Y | Y | N |   |
NOT_SUPPORTED | N | NOT_SUPPORTED | N | Y | Y |   |
NOT_SUPPORTED | Y | NOT_SUPPORTED | N | Y | Y |   |
NOT_SUPPORTED | N | NOT_SUPPORTED | Y | Y | Y |   |
NOT_SUPPORTED | Y | NOT_SUPPORTED | Y | Y | Y |   |
NOT_SUPPORTED | N | NEVER | N | Y | Y |   |
NOT_SUPPORTED | Y | NEVER | N | Y | Y |   |
NOT_SUPPORTED | N | NEVER | Y | Y | Y |   |
NOT_SUPPORTED | Y | NEVER | Y | Y | Y |   |
NOT_SUPPORTED | N | NESTED | N | Y | Y |   |
NOT_SUPPORTED | Y | NESTED | N | Y | Y |   |
NOT_SUPPORTED | N | NESTED | Y | Y | N |   |
NOT_SUPPORTED | Y | NESTED | Y | Y | N |   |
NEVER | N | REQUIRED | N | Y | Y |   |
NEVER | Y | REQUIRED | N | Y | Y |   |
NEVER | N | REQUIRED | Y | Y | N |   |
NEVER | Y | REQUIRED | Y | Y | N |   |
NEVER | N | SUPPORTS | N | Y | Y |   |
NEVER | Y | SUPPORTS | N | Y | Y |   |
NEVER | N | SUPPORTS | Y | Y | Y |   |
NEVER | Y | SUPPORTS | Y | Y | Y |   |
NEVER | N | MANDATORY | N | Y | N |   |
NEVER | Y | MANDATORY | N | Y | N |   |
NEVER | N | MANDATORY | Y | Y | N |   |
NEVER | Y | MANDATORY | Y | Y | N |   |
NEVER | N | REQUIRES_NEW | N | Y | Y |   |
NEVER | Y | REQUIRES_NEW | N | Y | Y |   |
NEVER | N | REQUIRES_NEW | Y | Y | N |   |
NEVER | Y | REQUIRES_NEW | Y | Y | N |   |
NEVER | N | NOT_SUPPORTED | N | Y | Y |   |
NEVER | Y | NOT_SUPPORTED | N | Y | Y |   |
NEVER | N | NOT_SUPPORTED | Y | Y | Y |   |
NEVER | Y | NOT_SUPPORTED | Y | Y | Y |   |
NEVER | N | NEVER | N | Y | Y |   |
NEVER | Y | NEVER | N | Y | Y |   |
NEVER | N | NEVER | Y | Y | Y |   |
NEVER | Y | NEVER | Y | Y | Y |   |
NEVER | N | NESTED | N | Y | Y |   |
NEVER | Y | NESTED | N | Y | Y |   |
NEVER | N | NESTED | Y | Y | N |   |
NEVER | Y | NESTED | Y | Y | N |   |
NESTED | N | REQUIRED | N | Y | Y |   |
NESTED | Y | REQUIRED | N | N | N |   |
NESTED | N | REQUIRED | Y | N | N | org.springframework.transaction.UnexpectedRollbackException Transaction rolled back because it has been marked as rollback-only |
NESTED | Y | REQUIRED | Y | N | N |   |
NESTED | N | SUPPORTS | N | Y | Y |   |
NESTED | Y | SUPPORTS | N | N | N |   |
NESTED | N | SUPPORTS | Y | N | N | org.springframework.transaction.UnexpectedRollbackException Transaction rolled back because it has been marked as rollback-only |
NESTED | Y | SUPPORTS | Y | N | N |   |
NESTED | N | MANDATORY | N | Y | Y |   |
NESTED | Y | MANDATORY | N | N | N |   |
NESTED | N | MANDATORY | Y | N | N | org.springframework.transaction.UnexpectedRollbackException Transaction rolled back because it has been marked as rollback-only |
NESTED | Y | MANDATORY | Y | N | N |   |
NESTED | N | REQUIRES_NEW | N | Y | Y |   |
NESTED | Y | REQUIRES_NEW | N | N | Y |   |
NESTED | N | REQUIRES_NEW | Y | Y | N |   |
NESTED | Y | REQUIRES_NEW | Y | N | N |   |
NESTED | N | NOT_SUPPORTED | N | Y | Y |   |
NESTED | Y | NOT_SUPPORTED | N | N | Y |   |
NESTED | N | NOT_SUPPORTED | Y | Y | Y |   |
NESTED | Y | NOT_SUPPORTED | Y | N | Y |   |
NESTED | N | NEVER | N | Y | N |   |
NESTED | Y | NEVER | N | N | N |   |
NESTED | N | NEVER | Y | Y | N |   |
NESTED | Y | NEVER | Y | N | N |   |
NESTED | N | NESTED | N | Y | Y |   |
NESTED | Y | NESTED | N | N | N |   |
NESTED | N | NESTED | Y | Y | N |   |
NESTED | Y | NESTED | Y | N | N |   |

