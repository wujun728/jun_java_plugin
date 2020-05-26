package com.bing.excel.core.handler;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.bing.excel.converter.FieldValueConverter;
import com.bing.excel.exception.ConversionException;
import com.bing.excel.mapper.BaseGlobalConverterMapper;
import com.google.common.primitives.Primitives;

public class LocalConverterHandler implements ConverterHandler {
	private final Map<Class<?>, FieldValueConverter> defaultLocalConverter = Collections
			.synchronizedMap(new HashMap<Class<?>, FieldValueConverter>());


@Override
	public void registerConverter(Class<?> clazz,
			FieldValueConverter converter) {
		if (converter.canConvert(clazz)) {

			if (clazz.isPrimitive()) {
				defaultLocalConverter.put(Primitives.wrap(clazz), converter);
			} else {
				defaultLocalConverter.put(clazz, converter);
			}
		} else {
			throw new ConversionException("register converter for["
					+ clazz.getName() + "] failed!");
		}
	}

	
	@Override
	public FieldValueConverter getLocalConverter(Class<?> keyFieldType) {
		FieldValueConverter fieldValueConverter = defaultLocalConverter.get(keyFieldType);
		if(fieldValueConverter==null){
			final Class<?> keyType;
			if (keyFieldType.isEnum() || Enum.class.isAssignableFrom(keyFieldType)) {
				keyType=Enum.class;
			} else if(keyFieldType.isArray()){
				keyType=Array.class;
			}else if(Collection.class.isAssignableFrom(keyFieldType)){
				keyType=Collection.class;
			}else{
				keyType=keyFieldType;
			}
			fieldValueConverter=BaseGlobalConverterMapper.globalFieldConverterMapper.get(keyType);
			if (fieldValueConverter!=null) {
				defaultLocalConverter.put(keyFieldType, fieldValueConverter);
			}
		}
		return fieldValueConverter;
	}
}
