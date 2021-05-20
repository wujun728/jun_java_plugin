package com.ibeetl.admin.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 用来标准一个查询类
 * @author Wujun
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Query {
	
	public static final  int TYPE_GENERAL = 1;
	public static final  int TYPE_DATE_BETWEEN = 2;
	//未实现
	public static final  int TYPE_DATETIME_BETWEEN = 3;
	public static final  int TYPE_VALUE_BETWEEN = 4;
	public static final  int TYPE_DICT = 5;
	//用户自己定义
	public static final  int TYPE_CONTROL = 6;
	
	
	/**
	 * 中文名字
	 * @return
	 */
	public String name();
	/**
	 * 查询类型,常规，范围，字典，前端自定义
	 * @return
	 */
	public int type() default TYPE_GENERAL;
	
	/**
	 * 是否显示在查询界面上
	 * @return
	 */
	public boolean display() default false;
	
	/**
	 * 模糊查询，仅仅针对TYPE_GENERAL
	 * @return
	 */
	public boolean fuzzy() default false;
	
	/**
	 * 字典的主键，比如,"user_state"
	 * @return
	 */
	public String dict() default "";
	
	/*描述*/
	public String comment() default "";
	
	/**
	 * 控件名字，如组织机构面板
	 * @return
	 */
	public String control() default "";
	
	/**
	 * 顺序，值越小，排在前面
	 * @return
	 */
	public int order() default 0;
	
	/*控件组*/
	public String group() default "";
}
