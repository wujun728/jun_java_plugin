package com.lmy.demo.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lmy.demo.pojo.PostPojo;

@Service
public class RestService {
	
	private static final String URL="http://jsonplaceholder.typicode.com/posts";
	
	@Autowired
	RestTemplate restTemplate;
	
	public String getPosts(){
		Map<String,Object> params=new HashMap<String,Object>();
		HttpHeaders headers=new HttpHeaders();
		headers.add("token", "123123");
		ResponseEntity<String> result= restTemplate.exchange(URL, HttpMethod.GET,new HttpEntity<String>(headers),String.class);
		System.out.println(result);
		return result.getBody();
	} 
	
}
