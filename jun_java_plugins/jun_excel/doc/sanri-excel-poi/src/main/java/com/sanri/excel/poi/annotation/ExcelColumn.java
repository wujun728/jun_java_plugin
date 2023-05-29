package com.sanri.excel.poi.annotation;

import com.sanri.excel.poi.converter.ExcelConverter;
import com.sanri.excel.poi.converter.NULLConverter;
import com.sanri.excel.poi.enums.CellType;

import java.lang.annotation.*;

/**
 * 
 * 作者:sanri <br/>
 * 时间:2017-8-12上午11:45:39<br/>
 * 功能:导入导出列配置,注解加到属性上说明对于导入和导出的配置是一样的,
 * 加到 set 上只针对导入,get 上只针对导出,get,set 上的配置会覆盖属性上的配置 <br/>
 */
@Target(value={ElementType.METHOD,ElementType.FIELD})
@Retention(value=RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelColumn {
	/**
	 * 作者:sanri <br/>
	 * 时间:2017-8-12上午11:47:56<br/>
	 * 功能:导出,导入的单元格标题 ,必填<br/>
	 * @return
	 */
	String value() ;
	/**
	 * 作者:sanri <br/>
	 * 时间:2017-8-12上午11:49:29<br/>
	 * 功能:导入,导出时的索引配置,从 0 开始,必须提供 <br/>
	 * @return
	 */
	int order() default -1;
	
	/**
	 * 
	 * 作者:sanri <br/>
	 * 时间:2017-8-12下午12:23:19<br/>
	 * 功能: 列的宽度配置,如果这里有配置,则使用这里的配置,否则使用自动宽度(如果配置为 true 的话,为 false 不设置)<br/>
	 * 注:使用 excel 的宽度设置,一个中文字对应 2 * 256 长度单位
	 * @return
	 */
	int width() default -1;
	
	/**
	 * 
	 * 作者:sanri <br/>
	 * 时间:2017-9-19下午4:46:29<br/>
	 * 功能:使用字符宽度,一个中文字填写 1  <br/>
	 * @return
	 */
	int charWidth() default -1;
	
	/**
	 * 
	 * 作者:sanri <br/>
	 * 时间:2017-9-19下午4:47:40<br/>
	 * 功能:使用像素宽度, 1 像素填写 1 <br/>
	 * @return
	 */
	int pxWidth() default -1;
	
	/**
	 * 
	 * 功能:由于自动宽度对中文支持不太好,所以这里加个中文的自动宽度支持,这个只在自动宽度设置为 true 时生效<br/>
	 * 创建时间:2017-8-13上午8:35:54<br/>
	 * 作者：sanri<br/>
	 * @return<br/>
	 */
	boolean chineseWidth() default false;
	
	/**
	 * 
	 * 作者:sanri <br/>
	 * 时间:2017-8-12下午12:26:47<br/>
	 * 功能: 当前列是否隐藏 默认 false<br/>
	 * @return
	 */
	boolean hidden() default false;
	
	/**
	 * 
	 * 作者:sanri <br/>
	 * 时间:2017-8-12下午1:46:24<br/>
	 * 功能: 时间格式化,默认 yyyy-MM-dd <br/>
	 * @return
	 */
	String pattern() default "yyyy-MM-dd";

	/**
	 * 如果是浮点型，保留几位小数 ，默认不处理
	 * 如果需要设置精度，修改此值
	 * @return
	 */
	int precision() default -1;

	/**
	 * 单元格类型,默认 String
	 * @return
	 */
	CellType cellType() default CellType.CELL_TYPE_STRING;

	/**
	 * 是否需要去除两边空格
	 * @return
	 */
	boolean trim() default true;

	/**
	 * 转换器设置,写 Excel 的时候，将当前列的值转换为目标值
	 * 注意：一个 Writer 只会有一个转换器实例，不能保存状态信息
	 * @return
	 */
	Class<? extends ExcelConverter> converter() default NULLConverter.class;
}
