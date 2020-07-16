package com.jun.plugin.utils2.convert.impl;

import com.jun.plugin.utils2.convert.AbstractConverter;
import com.jun.plugin.utils2.convert.Convert;
import com.jun.plugin.utils2.util.CollectionUtil;

/**
 * int 类型数组转换器
 * @author Looly
 *
 */
public class IntArrayConverter extends AbstractConverter<int[]>{
	
	@Override
	protected int[] convertInternal(Object value) {
		final Integer[] result = Convert.convert(Integer[].class, value);
		return CollectionUtil.unWrap(result);
	}

}
