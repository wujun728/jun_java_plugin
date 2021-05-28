package com.lmy.demo.test;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lmy.demo.pojo.PostPojo;
import com.lmy.demo.utils.JSONUtils;

public class TestJsonUtils {
	public static void main(String[] args) {
		String str="[{\"userId\":10,\"id\":99,\"title\":\"213\",\"body\":\"12\"}]";
		List<PostPojo> stringToList = JSONUtils.stringToList(str, new TypeReference<List<PostPojo>>() {});
    	System.out.println(stringToList);
	}
}
