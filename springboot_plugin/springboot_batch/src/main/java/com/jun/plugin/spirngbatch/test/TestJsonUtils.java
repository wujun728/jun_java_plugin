package com.jun.plugin.spirngbatch.test;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jun.plugin.spirngbatch.pojo.PostPojo;
import com.jun.plugin.spirngbatch.utils.JSONUtils;

public class TestJsonUtils {
	public static void main(String[] args) {
		String str="[{\"userId\":10,\"id\":99,\"title\":\"213\",\"body\":\"12\"}]";
		List<PostPojo> stringToList = JSONUtils.stringToList(str, new TypeReference<List<PostPojo>>() {});
    	System.out.println(stringToList);
	}
}
