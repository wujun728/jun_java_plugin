package com.sanri.excel.poi.annotation;

import com.sanri.excel.poi.enums.ExcelVersion;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 作者:sanri <br/>
 * 时间:2017-8-12上午11:45:14<br/>
 * 功能:excel 导入标记,支持 excel 导入 <br/>
 */
@Target(value=ElementType.TYPE)
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelImport {
	/**
	 * 
	 * 作者:sanri <br/>
	 * 时间:2017-8-12上午11:47:03<br/>
	 * 功能:导入版本支持,默认支持 2007 和 2003  <br/>
	 * @return
	 */
	ExcelVersion[] support() default {ExcelVersion.EXCEL2003,ExcelVersion.EXCEL2007};
	
	/**
	 * 
	 * 功能:指定哪一行才是真正的数据开始的地方 从 0 开始<br/>
	 * 创建时间:2017-8-12下午10:19:15<br/>
	 * 作者：sanri<br/>
	 * @return<br/>
	 */
	int startRow() default 1;
}
