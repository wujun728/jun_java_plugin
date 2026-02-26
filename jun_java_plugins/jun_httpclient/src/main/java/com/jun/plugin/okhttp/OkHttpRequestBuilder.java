// This file is commented out — OkHttp wrapper moved to jun_okhttp module.
/*
package com.jun.plugin.okhttp;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Wujun
 * /

@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class OkHttpRequestBuilder<T extends OkHttpRequestBuilder> {
	protected String url;
	protected Object tag;
	protected Map<String, String> headers;
	protected Map<String, String> params;
	protected int id;
	//
	public OkHttpRequestBuilder(){
		headers=new LinkedHashMap<>();
		params=new LinkedHashMap<>();
	}
	//
	public T id(int id) {
		this.id = id;
		return (T) this;
	}

	public T url(String url) {
		this.url = url;
		return (T) this;
	}

	public T tag(Object tag) {
		this.tag = tag;
		return (T) this;
	}

	public T headers(Map<String, String> headers) {
		this.headers = headers;
		return (T) this;
	}

	public T addHeader(String key, String val) {
		headers.put(key, val);
		return (T) this;
	}

	public abstract RequestCall build();
}
*/
