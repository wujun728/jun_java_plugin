package com.jun.plugin.core.utils.convert.impl;

import com.jun.plugin.core.utils.convert.AbstractConverter;
import com.jun.plugin.core.utils.convert.Convert;
import com.jun.plugin.core.utils.util.CollectionUtil;

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
