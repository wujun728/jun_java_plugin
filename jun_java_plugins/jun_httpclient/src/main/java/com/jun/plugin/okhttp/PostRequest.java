// This file is commented out — OkHttp wrapper moved to jun_okhttp module.
/*
package com.jun.plugin.okhttp;

import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 *
 * @author Wujun
 *
 * /
public class PostRequest extends OkHttpRequest {
	//
	public static Logger logger = LoggerFactory.getLogger(PostRequest.class);
	//
	public PostRequest(String url, Object tag, Map<String, String> params,
			Map<String, String> headers,List<FileInfo> fileInfos,String postBody,int id) {
		super(url, tag, params, headers, fileInfos,postBody,id);
	}

	@Override
	protected RequestBody buildRequestBody() {
		if(postBody!=null&&postBody.length()>0){
			MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");
			return RequestBody.create(MEDIA_TYPE_PLAIN,postBody);
		}
		else if (fileInfos == null || fileInfos.isEmpty()) {
			FormBody.Builder builder = new FormBody.Builder();
			addParams(builder);
			FormBody formBody = builder.build();
			return formBody;
		} else{
			MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
			addParams(builder);
			fileInfos.forEach(fileInfo -> {
				RequestBody fileBody = RequestBody.create(MediaType.parse(getMimeType(fileInfo.fileName)),
						fileInfo.fileContent);
				builder.addFormDataPart(fileInfo.partName, fileInfo.fileName, fileBody);
			});
			return builder.build();
		}
	}

	@Override
	protected Request buildRequest(RequestBody requestBody) {
		return builder.post(requestBody).build();
	}

	private void addParams(FormBody.Builder builder) {
		if (params!= null) {
			params.forEach((k,v)->builder.add(k,v));
		}
	}
	//
	private void addParams(MultipartBody.Builder builder) {
		if (params != null && !params.isEmpty()) {
			params.forEach((k,v)->{
				builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + k + "\""),
						RequestBody.create(null,v));
			});
		}
	}

	//
	public static class FileInfo {
		public String partName;
		public String fileName;
		public byte[] fileContent;
	}
	//
	public static String getMimeType(String path) {
		FileNameMap fileNameMap = URLConnection.getFileNameMap();
		String contentTypeFor = null;
		try {
			contentTypeFor = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(),e);
		}
		if (contentTypeFor == null) {
			contentTypeFor = "application/octet-stream";
		}
		return contentTypeFor;
	}
}
*/
