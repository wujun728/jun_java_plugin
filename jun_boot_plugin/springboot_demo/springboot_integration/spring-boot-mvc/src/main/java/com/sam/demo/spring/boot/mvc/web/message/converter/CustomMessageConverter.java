package com.sam.demo.spring.boot.mvc.web.message.converter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class CustomMessageConverter extends MappingJackson2HttpMessageConverter {

	private Gson gson;

	public void setGson(Gson gson) {
		this.gson = gson;
	}

	/**
	 * 将请求转换为对象,用于@RequestBody
	 */
	@Override
	public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		/**
		 * 测试代码 直接将请求体中的json字符串转换为controller方法中定义的参数
		 * 项目中根据实际情况将json解析后创建controller方法所需对象
		 */
		String currentMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
		
		String json = IOUtils.toString(inputMessage.getBody(), "UTF-8");
		Map map = gson.fromJson(json, type);
		map.put("id", 100);
		map.put("time", new Date());
		return map;
		
//		TypeToken<?> token = TypeToken.get(type);
//		return readTypeToken(token, inputMessage);
		
	}

	/**
	 * @ResponseBody返回的对象转换为json时调用该方法 后续可扩展
	 */
	@Override
	protected void writeInternal(Object o, Type type, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		super.writeInternal(o, type, outputMessage);

	}

	/**
	 * 内部读?
	 */
	@Override
	protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		/**
		 * 测试代码 直接将请求体中的json字符串转换为controller方法中定义的参数
		 * 项目中根据实际情况将json解析后创建controller方法所需对象
		 */
		TypeToken<?> token = TypeToken.get(clazz);
		return readTypeToken(token, inputMessage);
	}

	private Object readTypeToken(TypeToken<?> token, HttpInputMessage inputMessage) throws IOException {
		Reader json = new InputStreamReader(inputMessage.getBody(), getCharset(inputMessage.getHeaders()));
		try {
			return this.gson.fromJson(json, token.getType());
		} catch (JsonParseException ex) {
			throw new HttpMessageNotReadableException("Could not read JSON: " + ex.getMessage(), ex);
		}
	}

	private Charset getCharset(HttpHeaders headers) {
		if (headers == null || headers.getContentType() == null || headers.getContentType().getCharset() == null) {
			return DEFAULT_CHARSET;
		}
		return headers.getContentType().getCharset();
	}
	
	
}
