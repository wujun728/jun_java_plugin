package com.jun.plugin.core.utils.convert.impl;

import com.jun.plugin.core.utils.convert.AbstractConverter;
import com.jun.plugin.core.utils.convert.Convert;
import com.jun.plugin.core.utils.util.CollectionUtil;

/**
 * float 类型数组转换器
 * @author Looly
 *
 */
public class FloatArrayConverter extends AbstractConverter<float[]>{
	
	@Override
	protected float[] convertInternal(Object value) {
		final Float[] result = Convert.convert(Float[].class, value);
		return CollectionUtil.unWrap(result);
	}

}
