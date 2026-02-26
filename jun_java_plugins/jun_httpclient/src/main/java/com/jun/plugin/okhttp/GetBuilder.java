// This file is commented out — OkHttp wrapper moved to jun_okhttp module.
/*
package com.jun.plugin.okhttp;

import java.util.Map;

/**
 *
 * @author Wujun
 *
 * /
public class GetBuilder extends OkHttpRequestBuilder<GetBuilder> {
	@Override
	public RequestCall build() {
		if (params != null) {
			url = appendParams(url, params);
		}
		return new GetRequest(url, tag, params, headers, id).build();
	}

	protected String appendParams(String url, Map<String, String> params) {
		if (url == null || params == null || params.isEmpty()) {
			return url;
		}
		StringBuilder builder = new StringBuilder();
		params.forEach((k,v)->{
			if(builder.length()==0){
				builder.append("?");
			}else if (builder.length()>0) {
				builder.append("&");
			}
			builder.append(URIEncoder.encodeUTF8(k));
			builder.append("=").append(URIEncoder.encodeUTF8(v));
		});
		return url+builder.toString();
	}

	public GetBuilder params(Map<String, String> params) {
		this.params = params;
		return this;
	}

	public GetBuilder addParams(String key, String val) {
		params.put(key, val);
		return this;
	}

}
*/
