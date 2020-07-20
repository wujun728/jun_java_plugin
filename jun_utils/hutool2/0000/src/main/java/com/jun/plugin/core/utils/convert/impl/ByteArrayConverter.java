package com.jun.plugin.core.utils.convert.impl;

import com.jun.plugin.core.utils.convert.AbstractConverter;
import com.jun.plugin.core.utils.convert.Convert;
import com.jun.plugin.core.utils.util.CollectionUtil;

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
