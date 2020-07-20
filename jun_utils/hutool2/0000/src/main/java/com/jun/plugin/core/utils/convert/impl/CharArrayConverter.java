package com.jun.plugin.core.utils.convert.impl;

import com.jun.plugin.core.utils.convert.AbstractConverter;
import com.jun.plugin.core.utils.convert.Convert;
import com.jun.plugin.core.utils.util.CollectionUtil;

/**
 * char类型数组转换器
 * @author Looly
 *
 */
public class CharArrayConverter extends AbstractConverter<char[]>{
	
	@Override
	protected char[] convertInternal(Object value) {
		final Character[] result = Convert.convert(Character[].class, value);
		return CollectionUtil.unWrap(result);
	}

}
