package com.jun.plugin.core.utils.convert.impl;

import com.jun.plugin.core.utils.convert.AbstractConverter;
import com.jun.plugin.core.utils.convert.Convert;
import com.jun.plugin.core.utils.util.CollectionUtil;

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
