package com.jun.plugin.utils2.convert.impl;

import com.jun.plugin.utils2.convert.AbstractConverter;
import com.jun.plugin.utils2.convert.Convert;
import com.jun.plugin.utils2.util.CollectionUtil;

/**
 * double 类型数组转换器
 * @author Looly
 *
 */
public class DoubleArrayConverter extends AbstractConverter<double[]>{
	
	@Override
	protected double[] convertInternal(Object value) {
		final Double[] result = Convert.convert(Double[].class, value);
		return CollectionUtil.unWrap(result);
	}

}
