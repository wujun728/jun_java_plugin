package com.kvn.poi.imp.processor;

import java.lang.reflect.Field;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.kvn.poi.imp.anno.ExcelDateColum;

/**
* @author wzy
* @date 2017年7月12日 下午2:40:20
*/
public class DateFieldResolver extends AbstractResolver<ExcelDateColum> {
	private ExcelDateColum excelDateColum;

	public DateFieldResolver(Field field, ExcelDateColum edc) {
		super();
		this.field = field;
		this.excelDateColum = edc;
	}

	@Override
	public Object process() {
		String columnName = excelDateColum.value();
		int indexOfColumn = head.indexOf(columnName);
		Object columnRawValue = row.get(indexOfColumn);
		DateTimeFormatter format = DateTimeFormat.forPattern(excelDateColum.pattern());
//		// 时间解析
		return DateTime.parse(columnRawValue.toString(), format).toDate();
	}

	
}
