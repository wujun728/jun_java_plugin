package com.jun.plugin.poi.test.excel.core.handler;

import com.jun.plugin.poi.test.excel.converter.FieldValueConverter;


/**
 * @author Wujun
 *
 * @date 2015-12-14  PM 4:44:52  
 * Description:  
 */
public interface ConverterHandler {

	
	/**
	 * Get default FieldConverter which is difined by <code>BaseGlobalConverterMapper</code> or user. 
	 * @param keyFieldType
	 * @return defaultConvetter or null
	 */
	FieldValueConverter getLocalConverter(Class<?> keyFieldType);

	/**
	 * Registe converter for this {@code Class} clazz.
	 * @param clazz
	 * @param converter
	 */
	void registerConverter(Class<?> clazz, FieldValueConverter converter);
	
	
}
