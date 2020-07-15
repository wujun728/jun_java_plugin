package com.jun.plugin.poi.test.common;

import com.jun.plugin.poi.test.excel.converter.FieldValueConverter;
import com.jun.plugin.poi.test.excel.core.BingExcel;

/**
 * @author shizhongtao
 *
 * @date 2015-12-17
 * Description:  
 */
public interface Builder<T> {
	
	T builder();

	Builder<T> registerFieldConverter(Class<?> clazz,
			FieldValueConverter converter);
}
