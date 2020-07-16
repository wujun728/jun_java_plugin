package com.jun.plugin.utils2.convert.impl;

import com.jun.plugin.utils2.convert.AbstractConverter;
import com.jun.plugin.utils2.convert.Convert;
import com.jun.plugin.utils2.util.CollectionUtil;

/**
 * long 类型数组转换器
 * @author Looly
 *
 */
public class LongArrayConverter extends AbstractConverter<long[]>{
	
	@Override
	protected long[] convertInternal(Object value) {
		final Long[] result = Convert.convert(Long[].class, value);
		return CollectionUtil.unWrap(result);
	}

}
