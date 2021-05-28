package com.ibeetl.admin.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 用来标注功能id
 * <pre>
 * &#064;Function("user.add")
 * public String addUser(){
 * }
 * </pre>
 * 
 * 只有拥有此项功能的角色才能操作，否则，权限不足
 * @author Wujun
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Function {
	public String value();
	public String name() default "";
}
