package io.springside.springtime.serializer;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 序列器的接口
 */
public interface Serializer {
	String JSON_TYPE = "application/json; charset=utf-8";

	Object decode(InputStream inputBuf, Class parameterType);

	void encode(OutputStream outputBuf, Object value);
}
