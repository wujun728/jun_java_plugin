package com.jun.plugin.core.utils.convert.impl;

import com.jun.plugin.core.utils.convert.AbstractConverter;
import com.jun.plugin.core.utils.convert.Convert;
import com.jun.plugin.core.utils.util.CollectionUtil;

/**
 * short 类型数组转换器
 * @author Looly
 *
 */
public class ShortArrayConverter extends AbstractConverter<short[]>{
	
	@Override
	protected short[] convertInternal(Object value) {
		final Short[] result = Convert.convert(Short[].class, value);
		return CollectionUtil.unWrap(result);
	}

}
