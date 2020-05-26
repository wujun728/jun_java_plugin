package com.bing.excel.converter.base;

import java.lang.reflect.Type;

import com.bing.excel.converter.AbstractFieldConvertor;
import com.bing.excel.core.handler.ConverterHandler;
import com.bing.excel.vo.OutValue;
import com.google.common.base.Strings;

/**
 * @author shizhongtao
 *
 * @date 2016-3-21
 * Description:  
 */
public final class ByteFieldConverter extends AbstractFieldConvertor {

	@Override
	public boolean canConvert(Class<?> clz) {
		 return clz.equals(byte.class) || clz.equals(Byte.class);
	}

	

	@Override
	public Object fromString(String cell,ConverterHandler converterHandler,Type targetType) {
		if (Strings.isNullOrEmpty(cell)) {
			return null;
		}
		int value = Integer.decode(cell).intValue();
    	if(value < Byte.MIN_VALUE || value > 0xFF) {
    		throw new NumberFormatException("For input string: \"" + cell + '"');
    	}
        return new Byte((byte)value);
	}



	@Override
	public OutValue toObject(Object source,ConverterHandler converterHandler) {
		if (source==null) {
			return null;
		}
		return OutValue.intValue(((Byte)source).intValue());
	}


}
