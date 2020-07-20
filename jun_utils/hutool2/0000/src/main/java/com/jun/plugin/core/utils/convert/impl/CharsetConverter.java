package com.jun.plugin.core.utils.convert.impl;

import java.nio.charset.Charset;

import com.jun.plugin.core.utils.convert.AbstractConverter;
import com.jun.plugin.core.utils.util.CharsetUtil;

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
