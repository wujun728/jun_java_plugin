package com.jun.plugin.json.fastjson;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;

public class FastjsonDome {

	public static void main(String[] args) {	
		List<Fruits> fruits = new ArrayList<>();
		for(int i='a',j=0;i<='z';i++,j++) {
			//String kinds, Integer num, String size, Date date
			Fruits fruit = new Fruits((char)i+"类苹果",j,"苹果大小"+j,new Date());
			fruits.add(fruit);
		}
		//转JSON格式 json序列化
		String fruitsJson = JSON.toJSONString(fruits);
		System.out.println(fruitsJson);
		
		//JSON转对象 json反序列化
		List<Fruits> fs = JSON.parseArray(fruitsJson,Fruits.class);
		
		for(Fruits f:fs) {
			System.out.println(f);
		}
	}

}
