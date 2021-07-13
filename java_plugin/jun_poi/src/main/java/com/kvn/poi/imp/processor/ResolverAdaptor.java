package com.kvn.poi.imp.processor;

import java.lang.reflect.Field;

import com.kvn.poi.imp.anno.ExcelColum;
import com.kvn.poi.imp.anno.ExcelDateColum;

/**
 * 解析器适配器
 * @author wzy
 * @date 2017年7月12日 下午3:30:52
 */
public class ResolverAdaptor {
	
	public static AbstractResolver<?> adapt(Field field) {
		ExcelColum ec = field.getAnnotation(ExcelColum.class);
		if (ec != null) {
			return new DefaultFieldResolver(field, ec);
		}

		ExcelDateColum edc = field.getAnnotation(ExcelDateColum.class);
		if (edc != null) {
			return new DateFieldResolver(field, edc);
		}

		return EmptyResolver.SINGLE.INSTANCE;
	}

}
