package com.jun.plugin.utils2.convert.impl;

import com.jun.plugin.utils2.convert.AbstractConverter;
import com.jun.plugin.utils2.convert.Convert;
import com.jun.plugin.utils2.util.CollectionUtil;

/**
 * boolean类型数组转换器
 * @author Looly
 *
 */
public class BooleanArrayConverter extends AbstractConverter<boolean[]>{
	
	@Override
	protected boolean[] convertInternal(Object value) {
		final Boolean[] result = Convert.convert(Boolean[].class, value);
		return CollectionUtil.unWrap(result);
	}

}
