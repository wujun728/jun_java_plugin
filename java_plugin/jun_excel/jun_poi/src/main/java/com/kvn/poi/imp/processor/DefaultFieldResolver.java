package com.kvn.poi.imp.processor;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.kvn.poi.common.PoiUtils;
import com.kvn.poi.imp.anno.ExcelColum;

/**
* @author wzy
* @date 2017年7月12日 下午2:40:20
*/
public class DefaultFieldResolver extends AbstractResolver<ExcelColum> {
	private ExcelColum excelColum;
	
	public DefaultFieldResolver(Field field, ExcelColum excelColum) {
		super();
		this.field = field;
		this.excelColum = excelColum;
	}

	public Object process() {
		String columnName = excelColum.value();
		int indexOfColumn = head.indexOf(columnName);
		Object columnRawValue = row.get(indexOfColumn);
		return PoiUtils.parse2Type(columnRawValue, field.getType());
	}

	public static void main(String[] args) {
		DefaultFieldResolver resovler = new DefaultFieldResolver(null, null);
		ParameterizedType parameterizedType = (ParameterizedType) resovler.getClass().getGenericSuperclass();
		Type pa = parameterizedType.getActualTypeArguments()[0];
	}
	
}
