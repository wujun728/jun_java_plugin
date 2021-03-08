package org.typroject.tyboot.core.restful.doc;

import org.typroject.tyboot.core.foundation.enumeration.UserType;
import org.typroject.tyboot.core.restful.limit.Frequency;
import org.typroject.tyboot.core.restful.limit.Strategy;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;


/**
 * 
 * <pre>
 *  Tyrest
 *  File: TycloudOperation.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 *  $Id: TycloudOperation.java  Tyrest\magintrursh $
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月1日		magintrursh		Initial.
 *
 * </pre>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TycloudOperation {
	/**
	 * 是否需要验证
	 * TODO.
	 *
	 * @return
	 */
	boolean needAuth() default true;

	/**
	 * API可见级别
	 *  ANONYMOUS  匿名用户
	 *  PUBLIC     公网用户
	 *  CUSTOMER   
	 *  AGENCY
	 *  SUPER_ADMIN
	 */
	UserType ApiLevel();


	/**
	 * 请求频次限制所用到的时间周期单位
	 */
	TimeUnit frequencyTimeUnit() default  TimeUnit.SECONDS;


	/**
	 * 请求频次限制所用到的时间周期长度。
	 */
	long frequencyPeriod() default  60;


	/**
	 * 请求频次限制所用到的限制请求数量。
	 */
	long frequencyQuantity() default  30;

	/**
	 * 限制策略
	 * @return
	 */
	Strategy strategy() default Strategy.USERID;

	/**
	 * 默认不启用限制策略
	 * @return
	 */
	boolean enableLimitStrategy() default false;


	/**
	 * 是否用于生成feign客户端的方法
	 * @return
	 */
	boolean enableFeignMethod() default true;

}

/*
*$Log: av-env.bat,v $
*/