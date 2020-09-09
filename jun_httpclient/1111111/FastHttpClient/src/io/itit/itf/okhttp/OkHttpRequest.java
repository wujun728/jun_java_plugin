package io.itit.itf.okhttp;

import java.util.List;
import java.util.Map;

import io.itit.itf.okhttp.PostRequest.FileInfo;
import io.itit.itf.okhttp.callback.Callback;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 
 * @author icecooly
 *
 */
public abstract class OkHttpRequest {
	protected int id;
	protected String url;
	protected Map<String, String> params;
	protected Map<String, String> headers;
	protected String postBody;
	protected List<FileInfo> fileInfos;
	protected Request.Builder builder = new Request.Builder();
	//
	protected OkHttpRequest(String url, Object tag, Map<String, String> params,
			Map<String, String> headers,List<FileInfo> fileInfos,String postBody,int id) {
		this.url = url;
		this.params = params;
		this.headers = headers;
		this.fileInfos=fileInfos;
		this.postBody=postBody;
		this.id = id;
		if (url==null) {
			throw new IllegalArgumentException("url can not be null.");
		}
		builder.url(url).tag(tag);
		appendHeaders();
	}

	protected abstract RequestBody buildRequestBody();
	
	protected abstract Request buildRequest(RequestBody requestBody);

	public RequestCall build() {
		return new RequestCall(this);
	}

	public Request createRequest(Callback callback) {
		RequestBody requestBody=buildRequestBody();
		Request request = buildRequest(requestBody);
		return request;
	}

	protected void appendHeaders() {
		Headers.Builder headerBuilder = new Headers.Builder();
		if (headers == null || headers.isEmpty())
			return;

		for (String key : headers.keySet()) {
			headerBuilder.add(key, headers.get(key));
		}
		builder.headers(headerBuilder.build());
	}

	public int getId() {
		return id;
	}

}
