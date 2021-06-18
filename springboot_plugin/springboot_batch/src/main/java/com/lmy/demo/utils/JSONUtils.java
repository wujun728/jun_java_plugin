package com.lmy.demo.utils;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtils {

	public static <T> List<T> stringToList(String str,TypeReference<List<T>> jsonTypeReference){
		ObjectMapper mapper = new ObjectMapper();
		List<T> list=new ArrayList<T>();
		try {
			list= mapper.readValue(str,jsonTypeReference);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        return list;
	}
}
