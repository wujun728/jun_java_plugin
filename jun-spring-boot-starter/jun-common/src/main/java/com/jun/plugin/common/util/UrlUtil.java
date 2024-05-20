
package com.jun.plugin.common.util;

import org.apache.commons.io.Charsets;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriUtils;

/**
 * url处理工具类
 *
 * @author L.cm
 */
public class UrlUtil extends org.springframework.web.util.UriUtils {

	/**
	 * encode
	 *
	 * @param source source
	 * @return sourced String
	 */
	public static String encode(String source) {
		return UriUtils.encode(source, Charsets.UTF_8);
	}

	/**
	 * decode
	 *
	 * @param source source
	 * @return decoded String
	 */
	public static String decode(String source) {
		return StringUtils.uriDecode(source, Charsets.UTF_8);
	}
}
