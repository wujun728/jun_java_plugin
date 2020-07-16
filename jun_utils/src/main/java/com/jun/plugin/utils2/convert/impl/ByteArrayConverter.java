package com.jun.plugin.utils2.convert.impl;

import com.jun.plugin.utils2.convert.AbstractConverter;
import com.jun.plugin.utils2.convert.Convert;
import com.jun.plugin.utils2.util.CollectionUtil;

/**
 * byte 类型数组转换器
 * @author Looly
 *
 */
public class ByteArrayConverter extends AbstractConverter<byte[]>{
	
	@Override
	protected byte[] convertInternal(Object value) {
		final Byte[] result = Convert.convert(Byte[].class, value);
		return CollectionUtil.unWrap(result);
	}

}
