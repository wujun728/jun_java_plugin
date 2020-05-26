package com.bing.excel.annotation;

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
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CellConfig {
	/**
	 * <p>Title: 下标值</p>
	 * <p>Description: 从0开始，转入时候必须有值，转出时用index值确定在excel中列数，如果没有则不能确定位置。</p>
	 * @return 转换为orm模型中java的类，如果不能转换返回null，基本类型中为默认值。
	 */
	public int index() ;
	
	/**
	 * excel 读取数据时候，是不是为必须参数。默认是false
	 * @return
	 */
	public boolean readRequired() default false;
	/**
	 * excel导出时候，字段是否忽略导出，default <code>false</code>。
	 * @return
	 */
	//public boolean omitOutput() default false;
	
	/**
	 * 输出时候的title名称
	 * @return
	 */
	public String aliasName() default "";
}
