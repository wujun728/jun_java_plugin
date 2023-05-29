package com.sanri.excel.poi.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.sanri.excel.poi.enums.ExcelVersion;

/**
 * 
 * 作者:sanri <br/>
 * 时间:2017-8-12上午11:44:36<br/>
 * 功能:excel 导出标记,支持 excel 导出 <br/>
 */
@Target(value=ElementType.TYPE)
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelExport {
	/**
	 * 
	 * 作者:sanri <br/>
	 * 时间:2017-8-12上午11:57:21<br/>
	 * 功能:导出版本,默认导出 2007 版本 <br/>
	 * @return
	 */
	ExcelVersion version() default ExcelVersion.EXCEL2007;
	
	/**
	 * 
	 * 作者:sanri <br/>
	 * 时间:2017-8-12下午6:43:35<br/>
	 * 功能:最顶部的标题行高度,需要设置 title 才能使其生效 <br/>
	 * @return
	 */
	short titleRowHeight() default 40;
	
	/**
	 * 
	 * 作者:sanri <br/>
	 * 时间:2017-8-12下午12:16:06<br/>
	 * 功能: 头标题行高度,以像素为单位<br/>
	 * @return
	 */
	short headRowHeight() default 30;
	
	/**
	 * 
	 * 作者:sanri <br/>
	 * 时间:2017-8-12下午12:16:18<br/>
	 * 功能:内容行高度,以像素为单位 <br/>
	 * @return
	 */
	short bodyRowHeight() default 25;
	
	/**
	 * 
	 * 作者:sanri <br/>
	 * 时间:2017-8-12下午12:18:30<br/>
	 * 功能:是否自动宽度,默认为 true <br/>
	 * @return
	 */
	boolean autoWidth() default true;
	
	/**
	 * 作者:sanri <br/>
	 * 时间:2017-8-12下午1:32:57<br/>
	 * 功能:一个sheet 页的最大记录数,默认是不限制的,如果是 2003 版本,限制为 60000 行 <br/>
	 * @return
	 */
	int sheetMaxRow() default -1;

	/**
	 * 使用 new SXSSFWorkbook(rowAccessWindowSize) 快速导出 Excel
	 * @return
	 */
	boolean fastModel() default true;

	/**
	 * rowAccessWindowSize 数量，根据内存大小来确定
	 * @return
	 */
	int rowAccessWindowSize() default 1000;
}
