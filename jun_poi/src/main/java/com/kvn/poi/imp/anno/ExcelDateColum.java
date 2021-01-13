package com.kvn.poi.imp.anno;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
* @author wzy
* @date 2017年7月12日 上午11:15:57
*/
@Target({ FIELD })
@Retention(RUNTIME)
public @interface ExcelDateColum {
	String value(); // 导入excel的列名
	String pattern() default "yyyy-MM-dd";
}
