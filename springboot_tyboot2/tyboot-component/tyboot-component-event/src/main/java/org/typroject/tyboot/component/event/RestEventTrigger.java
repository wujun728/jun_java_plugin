package org.typroject.tyboot.component.event;

import java.lang.annotation.*;

/**
 * 
 * <pre>
 * 
 *  Tyrest
 *  File: TyrestEventTrigger.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2015
 * 
 *  Description:系统业务事件触发器注解类
 *  TODO 用于标示哪些方法是业务事件触发器
 * 
 *  Notes:
 *  $Id: TyrestEventTrigger.java 31101200-9 2014-10-14 16:43:51Z Tyrest\magintursh $
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2015年8月7日		magintursh		Initial.
 *
 * </pre>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RestEventTrigger
{
	/**
	 * 标示该触发器触发什么系统业务事件,一个事件对应一个事件处理器
	 */
	String[] value() default {};

	/**
	 * 方法描述或者被标注的方法中文名
	 * @return
	 */
	String  label() default "";
}

/*
*$Log: av-env.bat,v $
*/