# 异常发生时的事务回滚机制

相关文档：[Spring官方文档](http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#transaction-declarative-rolling-back)

相关代码：[src/main/java/exception](src/main/java/exception)，[src/test/java/exception](src/test/java/exception)

在Spring官方文档中说到，当Transaction内发生unchecked exception的时候，会自动rollback，但是当Transaction内发生checked exception时，是不会自动rollback的。

注意，这种处理机制和直觉是不同的，初学者甚至有经验的开发人员直觉上会认为只要Transaction内发生异常，都会自动rollback。

其实仔细想想Spring这样处理是有意为之的，因为unchecked exception是一种出乎意料的异常（RuntimeException），这类异常在java语言层面不强制catch，那么出现这种异常的时候spring自然应该自动rollback。

但是checked exception不同，这类异常在java语言层面是强制catch的，也就是说强制调用方对这类异常做处理，所以此时Spring就将处理权转移给了调用方，而不是自动rollback。
