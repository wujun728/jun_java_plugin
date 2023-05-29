package com.jun.plugin.util4j.cache.callBack.impl;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 回调函数 用于AsyncRequest
 * @author Administrator
 */
@Target({ElementType.ANNOTATION_TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CallBackFunction{
	
	/**
	 * 函数ID
	 * @return
	 */
	public int id();
}