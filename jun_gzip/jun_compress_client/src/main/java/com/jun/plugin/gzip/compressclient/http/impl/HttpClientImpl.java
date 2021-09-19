package com.jun.plugin.gzip.compressclient.http.impl;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.jun.plugin.gzip.compressclient.http.HttpClient;

/**
 * @Author:Yolanda
 * @Date: 2020/5/7 18:11
 */
@Service
public class HttpClientImpl implements HttpClient {

    public String client(String url, HttpMethod method, MultiValueMap<String,String> params){

        RestTemplate template = new RestTemplate();
//        ResponseEntity<String> response = template.getForEntity(url, String.class, params);
        return template.postForObject(url, params, String.class);
//        return response.getBody();
    }




}
