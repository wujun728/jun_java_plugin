package com.mycompany.myproject.base.rest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.print.attribute.standard.Media;
import java.util.HashMap;

public class RestTemplateTest {

    public static void main(String[] args) throws Exception{

        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(3000);
        httpRequestFactory.setConnectTimeout(3000);
        httpRequestFactory.setReadTimeout(3000);

        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);


        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        requestHeaders.add("Authorization","Basic RHNCMThXZWpRNTBfU1Z6WXZxc1RhSWExS1pRYTpoYWFqbFRFQWdmY3p5Nk1uaHNRQUZWS2JCSEVh");

//        HashMap<String,String > params = new HashMap<String ,String >();
//        params.put("grant_type","password");
//        params.put("username","jansie-hyatt-dev");
//        params.put("password","Hyatt123*");

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("grant_type", "password");
        map.add("username", "jansie-hyatt-dev");
        map.add("password", "Hyatt123*");

        HttpEntity httpEntity = new HttpEntity(map,requestHeaders);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://api.hero-cloud.com:8243/token", httpEntity, String.class);

        String ss = responseEntity.getBody();
        System.out.println(ss);
    }


}
