package com.reger.tcc.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;

@Inherited
@Target(METHOD)
@Retention(RUNTIME)
public @interface RollBack {

	/**
	 * 事务回滚操作的标识名，在tcc事务提交时需要指定 ,如果定义了多个同名的操作，同名的回滚操作都会被触发
	 * 
	 * @return 事务回滚时的标识名
	 */
	String value();

	/**
	 * 本地事务管理器的名字
	 */
	String transactionManager() default "";

	/**
	 * 事务隔离级别
	 * 
	 * @return
	 */
	Isolation isolation() default Isolation.DEFAULT;

	/**
	 * 事务只读
	 * 
	 * @return
	 */
	boolean readOnly() default false;

	/**
	 * 事务超时时间,注意局部事务超时时间过短，会造成那整体事务的失效
	 * 
	 * @return
	 */
	int timeout() default TransactionDefinition.TIMEOUT_DEFAULT;

	/**
	 * 不触发回滚的异常
	 * 
	 * @return
	 */
	Class<? extends Throwable>[] noRollbackFor() default {};
}
