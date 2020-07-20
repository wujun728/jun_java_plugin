package com.jun.plugin.core.utils.demo;

import com.jun.plugin.core.utils.json.JSONObject;
import com.jun.plugin.core.utils.lang.Console;

public class JsonDemo {
	public static void main(String[] args) {
		JSONObject jsonObject = new JSONObject();
		int a = 1;
		jsonObject.put("a", a);
		jsonObject.put("b", 2);
		jsonObject.put("c", 2L);
		jsonObject.put("d", true);
		
		Console.log(jsonObject);
		Console.log(jsonObject.getInt("a"));
	}
}
