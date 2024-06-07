
package com.jun.plugin.common.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 字符集工具类
 *
 * @author L.cm
 */
public interface Charsets {

	/**
	 * 字符集ISO-8859-1
	 */
	Charset ISO_8859_1 = StandardCharsets.ISO_8859_1;
	String ISO_8859_1_NAME = ISO_8859_1.name();

	/**
	 * 字符集GBK
	 */
	Charset GBK = Charset.forName(StringPool.GBK);
	String GBK_NAME = GBK.name();

	/**
	 * 字符集utf-8
 	 */
	Charset UTF_8 = StandardCharsets.UTF_8;
	String UTF_8_NAME = UTF_8.name();
}
