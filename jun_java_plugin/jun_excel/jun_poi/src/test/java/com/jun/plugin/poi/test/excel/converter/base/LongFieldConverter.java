package com.jun.plugin.poi.test.excel.converter.base;

import java.lang.reflect.Type;

import com.google.common.base.Strings;
import com.jun.plugin.poi.test.excel.converter.AbstractFieldConvertor;
import com.jun.plugin.poi.test.excel.core.handler.ConverterHandler;
import com.jun.plugin.poi.test.excel.vo.OutValue;

public final class LongFieldConverter extends AbstractFieldConvertor {

	@Override
	public boolean canConvert(Class<?> clz) {
		return clz.equals(long.class) || clz.equals(Long.class);
	}

	@Override
	public Object fromString(String cell, ConverterHandler converterHandler,
			Type targetType) {
		if (Strings.isNullOrEmpty(cell)) {
			return null;
		}
		int len = cell.length();
		 if (len < 17) {
	            return Long.decode(cell);
	        }
	        char c0 = cell.charAt(0);
	        if (c0 != '0' && c0 != '#') {
	            return Long.decode(cell);
	        }
	        char c1 = cell.charAt(1);
	        final long high;
	        final long low;
	        if (c0 == '#' && len == 17) {
	            high = Long.parseLong(cell.substring(1, 9), 16) << 32;
	            low = Long.parseLong(cell.substring(9, 17), 16);
	        } else if ((c1 == 'x' || c1 == 'X') && len == 18) {
	            high = Long.parseLong(cell.substring(2, 10), 16) << 32;
	            low = Long.parseLong(cell.substring(10, 18), 16);
	        } else if (len == 23 && c1 == '1') {
	            high = Long.parseLong(cell.substring(1, 12), 8) << 33;
	            low = Long.parseLong(cell.substring(12, 23), 8);
	        } else {
	            return Long.decode(cell);
	        }
	        final long num = high | low;
	        return new Long(num);
	}

	@Override
	public OutValue toObject(Object source, ConverterHandler converterHandler) {
		if (source == null) {
			return null;
		}
		return OutValue.longValue(source);
	}

}
