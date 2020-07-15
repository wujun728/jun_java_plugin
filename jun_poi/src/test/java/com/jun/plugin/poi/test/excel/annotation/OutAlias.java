package com.jun.plugin.poi.test.excel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * 创建时间：2015-12-8下午9:41:18 项目名称：excel
 * 
 * @author shizhongtao
 * @version 1.0
 * @since JDK 1.7 文件名称：BingCell.java 类说明：
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OutAlias {
	
	/**
	 * 输出时候的sheet名称
	 * @return
	 */
	public String value() default "";
}
