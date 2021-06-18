package com.jun.plugin.gzip.compressclient.http;

import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;

/**
 * @Author:Yolanda
 * @Date: 2020/5/7 18:10
 */
public interface HttpClient {

    public String client(String url, HttpMethod method, MultiValueMap<String,String> params);
}
