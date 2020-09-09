package io.itit.itf.okhttp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;

/**
 * 
 * @author icecooly
 *
 */
public final class URIEncoder {

	public static String encodeUTF8(String input) {
		return encode(input, StandardCharsets.UTF_8);
	}

	public static String encode(String input, String encoding) {
		return encode(input, Charset.forName(encoding));
	}

	public static String encode(String input, Charset encoding) {
		if (input == null)
			return null;
		if (input.trim().isEmpty())
			return "";
		try {
			return URLEncoder.encode(input, encoding.name());
		} catch (UnsupportedEncodingException e) {
			throw new UnsupportedCharsetException(encoding.name());
		}
	}
}
