package com.bing.excel.mapper;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import com.bing.excel.converter.FieldValueConverter;
import com.bing.excel.converter.base.BooleanFieldConverter;
import com.bing.excel.converter.base.ByteFieldConverter;
import com.bing.excel.converter.base.CharacterFieldConverter;
import com.bing.excel.converter.base.DateFieldConverter;
import com.bing.excel.converter.base.DoubleFieldConverter;
import com.bing.excel.converter.base.FloatFieldConverter;
import com.bing.excel.converter.base.IntegerFieldConverter;
import com.bing.excel.converter.base.LongFieldConverter;
import com.bing.excel.converter.base.ShortFieldConverter;
import com.bing.excel.converter.base.StringFieldConverter;
import com.bing.excel.converter.collections.ArrayConverter;
import com.bing.excel.converter.collections.CollectionConverter;
import com.bing.excel.converter.enums.EnumConVerter;
import com.google.common.collect.ImmutableMap;

/**
 * 默认的全局转换类，先静态吧，容我想想
 * @author shizhongtao
 *
 * @date 2016-3-19
 * Description:  
 */
public class BaseGlobalConverterMapper {
	static ImmutableMap.Builder<Class<?>, FieldValueConverter>   builder;
	static{
		builder=ImmutableMap.builder();
		builder.put(String.class,new StringFieldConverter());
		builder.put(Date.class,new DateFieldConverter());
		builder.put(Enum.class,new EnumConVerter());
		builder.put(Array.class,new ArrayConverter());
		//builder.put(Collections.class,new ArrayConverter());
		
		
		builder.put(Integer.class,new IntegerFieldConverter());
		builder.put(Long.class,new LongFieldConverter());
		builder.put(Boolean.class,new BooleanFieldConverter());
		builder.put(Byte.class,new ByteFieldConverter());
		builder.put(Character.class,new CharacterFieldConverter());
		builder.put(Double.class,new DoubleFieldConverter());
		builder.put(Float.class,new FloatFieldConverter());
		builder.put(Short.class,new ShortFieldConverter());
		builder.put(Collection.class,new CollectionConverter());
	}
	public final static ImmutableMap<Class<?>, FieldValueConverter> globalFieldConverterMapper=builder.build();
	
}
