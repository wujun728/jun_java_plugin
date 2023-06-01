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
public @interface Commit {

	/**
	 * 定义tcc事务提交操作的名字，多个操作，名字不可重复
	 * 
	 * @return tcc事务提交操作的名字
	 */
	String value();

	/**
	 * 定义tcc事务触发回滚时，该触发的取消操作名字。默认值和确认操作的name相同
	 * @return tcc事务触发回滚时触发的操作的名字
	 */
	String rollBack() default "";
	
	/**
	 * 事务提交失败时重试次数，默认值为0，表示不重试
	 * @return
	 */
	int retry() default 0;

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
