package com.bing.excel.converter.base;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.DateUtil;

import com.bing.excel.converter.AbstractFieldConvertor;
import com.bing.excel.core.handler.ConverterHandler;
import com.bing.excel.exception.ConversionException;
import com.bing.excel.vo.OutValue;
import com.bing.utils.StringParseUtil;

/**
 * @author shizhongtao
 * 
 * @date 2016-3-21 Description:
 */
public final class DateFieldConverter extends AbstractFieldConvertor {

	private static final ThreadLocal<Map<Object, Object>> localFormat = new ThreadLocal<>();
	private final String inFormatStr;
	private final String outFormatStr;
	private final String inFormatKey = "inKey";
	private final String outFormatKey = "outKey";

	public DateFieldConverter(boolean smartConversion) {
		this("yyyy-MM-dd HH:mm:ss", smartConversion);
	}

	public DateFieldConverter() {
		this(false);
	}

	public DateFieldConverter(String formats, boolean smartConversion) {
		this(formats, formats, smartConversion);
	}

	public DateFieldConverter(String inFormats, String outFormats,
			boolean smartConversion) {
		this.inFormatStr = inFormats;
		this.outFormatStr = outFormats;
	}

	@Override
	public boolean canConvert(Class<?> clz) {
		return clz.equals(Date.class);
	}

	@Override
	public OutValue toObject(Object source,ConverterHandler converterHandler) {
		if(source==null){
			return null;
		}
		return OutValue.dateValue(source);
	}

	@Override
	public   Object  fromString(String cell,ConverterHandler converterHandler,Type targetType) {
		
		
		String temp=cell;
		SimpleDateFormat inFormat=getFormat(outFormatKey);
		
		if(inFormat==null){
			throw new NullPointerException("inFormat[SimpleDateFormat] is null");
		}
		Date date;
		try {
			date = inFormat.parse(temp);
			return date;
		} catch (ParseException e) {
			try {
				inFormat.applyPattern("yy-MM-dd HH:mm");
				date = inFormat.parse(temp);
				return date;
			} catch (ParseException e1) {
				try {
					inFormat.applyPattern("yy-MM-dd");
					date = inFormat.parse(temp);
					return date;
				} catch (ParseException e2) {
					throw new ConversionException("Cannot parse date" + cell, e2);
				}
			}

		}

	}

	public SimpleDateFormat getFormat(String key) {
		if (key.equals(inFormatKey)) {
			Map<Object, Object> map = localFormat.get();
			if (map == null) {
				map = Collections.synchronizedMap(new HashMap<>());
				localFormat.set(map);
			}
			Object object = map.get(key);
			if (object == null) {
				SimpleDateFormat format = new SimpleDateFormat(inFormatStr);
				map.put(key, format);
				object = format;
				
			}
			return (SimpleDateFormat) object;
		} else if (key.equals(outFormatKey)) {
			Map<Object, Object> map = localFormat.get();
			if (map == null) {
				map = Collections.synchronizedMap(new HashMap<>());
				localFormat.set(map);
			}
			Object object = map.get(key);
			if (object == null) {
				SimpleDateFormat format = new SimpleDateFormat(outFormatStr);
				map.put(key, format);
				object = format;
			}
			return (SimpleDateFormat) object;
		}
		return null;
	}
}
