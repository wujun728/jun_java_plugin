package com.jun.plugin.gzip.compressclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jun.plugin.gzip.compressclient.aop.TestAop;
import com.jun.plugin.gzip.compressclient.compress.CompressUtils;
import com.jun.plugin.gzip.compressclient.encrypt.AESUtils;
import com.jun.plugin.gzip.compressclient.http.HttpClient;

import java.io.IOException;

/**
 * @Author:Yolanda
 * @Date: 2020/5/7 18:05
 */
@Controller
public class TestController {

    @Autowired
    private TestAop testAop;

    @RequestMapping("/test")
    @ResponseBody
    public String testSpringBootAop(String str){
        return testAop.testSpringBootAop(str);
    }

    @Autowired
    HttpClient httpClient;

    @RequestMapping(value = "/compress")
    @ResponseBody
    public String hello(){
        String url = "http://localhost:8081/hello";
        HttpMethod method = HttpMethod.GET;
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String test="test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 test1234567890 ";
        String afterCompressStr = "";
        String afterEncryptStr = "";
        try {
            // 压缩
            afterCompressStr = CompressUtils.compress(test);
            // 加密
            afterEncryptStr = AESUtils.encrypt(afterCompressStr);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

//        params.add("data", afterCompressStr);
        params.add("data", afterEncryptStr);

        String response = httpClient.client(url, method, params);
        System.out.println(response);
        return response;
    }
}
