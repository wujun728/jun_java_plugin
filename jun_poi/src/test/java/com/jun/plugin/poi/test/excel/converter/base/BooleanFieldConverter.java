package com.jun.plugin.poi.test.excel.converter.base;

import java.lang.reflect.Type;

import com.google.common.base.Strings;
import com.jun.plugin.poi.test.excel.converter.AbstractFieldConvertor;
import com.jun.plugin.poi.test.excel.core.handler.ConverterHandler;
import com.jun.plugin.poi.test.excel.exception.ConversionException;
import com.jun.plugin.poi.test.excel.vo.OutValue;

/**
 * 
 * @author shizhongtao
 * 
 * @date 2016-3-21 Description: thanks for Joe Walnes and David Blevins
 */
public final class BooleanFieldConverter extends AbstractFieldConvertor {
	private final boolean caseSensitive;
	private final String trueCaseStr;
	private final String falseCaseStr;

	/**
	 * @param trueCaseStr 为真时候的输入
	 * @param falseCaseStr 为家时候的输入
	 * @param caseSensitive 是不是忽略大小写
	 */
	public BooleanFieldConverter(String trueCaseStr, String falseCaseStr,
			boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
		this.trueCaseStr = trueCaseStr;
		this.falseCaseStr = falseCaseStr;
	}

	/**
	 * 默认的boolean类型转换器，支持"TRUE", "FALSE"字符的转换
	 */
	public BooleanFieldConverter() {
		this("TRUE", "FALSE", false);
	}

	@Override
	public boolean canConvert(Class<?> clz) {
		return clz.equals(boolean.class) || clz.equals(Boolean.class);
	}

	@Override
	public OutValue toObject(Object source,ConverterHandler converterHandler) {
		if(source==null){
			return null;
		}
		String re;
		if((boolean)source){
			re=trueCaseStr;
		}else{
			re=falseCaseStr;
		}
		return OutValue.stringValue(re);
	}

	/*
	 * in other case ,return false?FIXME
	 */
	@Override
	public Object fromString(String cell,ConverterHandler converterHandler,Type targetType) {
		if (Strings.isNullOrEmpty(cell)) {
			return null;
		}
		Boolean re;
		if (caseSensitive) {
			re = trueCaseStr.equals(cell) ? Boolean.TRUE : Boolean.FALSE;
		} else {
			re = trueCaseStr.equalsIgnoreCase(cell) ? Boolean.TRUE
					: Boolean.FALSE;
		}
		if (!re) {
			if (caseSensitive) {
				if (!falseCaseStr.equals(cell)) {
					throw new ConversionException("Cann't parse value '"+cell+"' to java.lang.Boolean");
				}
			} else {
				if (!falseCaseStr.equalsIgnoreCase(cell)) {

				}
			}
		}
		return re;
	}

}
