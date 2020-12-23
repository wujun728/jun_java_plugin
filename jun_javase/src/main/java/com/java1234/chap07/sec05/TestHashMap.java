package com.java1234.chap07.sec05;

import java.util.HashMap;
import java.util.Iterator;

import com.java1234.chap07.sec01.Student;

public class TestHashMap {

	public static void main(String[] args) {
		HashMap<String,Student> hashMap=new HashMap<String,Student>();
		hashMap.put("1号", new Student("张三",10));
		hashMap.put("2号", new Student("李四",20));
		hashMap.put("3号", new Student("王五",30));
		
		// 通过key，获取value
		Student s=hashMap.get("1号");
		System.out.println(s.getName()+":"+s.getAge());
		
		Iterator<String> it=hashMap.keySet().iterator(); // 获取key的集合，再获取迭代器
		while(it.hasNext()){
			String key=it.next();  // 获取key
			Student student=hashMap.get(key);  // 通过key获取value
			System.out.println("key="+key+" value=["+student.getName()+","+student.getAge()+"]");
		}
	}
}
