package com.bing.common;

import com.bing.excel.converter.FieldValueConverter;
import com.bing.excel.core.BingExcel;

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
