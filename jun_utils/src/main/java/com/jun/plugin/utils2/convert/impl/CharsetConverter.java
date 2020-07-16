package com.jun.plugin.utils2.convert.impl;

import java.nio.charset.Charset;

import com.jun.plugin.utils2.convert.AbstractConverter;
import com.jun.plugin.utils2.util.CharsetUtil;

/**
 * 编码对象转换器
 * @author Looly
 *
 */
public class CharsetConverter extends AbstractConverter<Charset>{

	@Override
	protected Charset convertInternal(Object value) {
		return CharsetUtil.charset(convertToStr(value));
	}

}
