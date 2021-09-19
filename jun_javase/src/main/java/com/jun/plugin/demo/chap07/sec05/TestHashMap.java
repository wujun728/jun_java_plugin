package com.jun.plugin.demo.chap07.sec05;

import java.util.HashMap;
import java.util.Iterator;

import com.jun.plugin.demo.chap07.sec01.Student;

public class TestHashMap {

	public static void main(String[] args) {
		HashMap<String,Student> hashMap=new HashMap<String,Student>();
		hashMap.put("1��", new Student("����",10));
		hashMap.put("2��", new Student("����",20));
		hashMap.put("3��", new Student("����",30));
		
		// ͨ��key����ȡvalue
		Student s=hashMap.get("1��");
		System.out.println(s.getName()+":"+s.getAge());
		
		Iterator<String> it=hashMap.keySet().iterator(); // ��ȡkey�ļ��ϣ��ٻ�ȡ������
		while(it.hasNext()){
			String key=it.next();  // ��ȡkey
			Student student=hashMap.get(key);  // ͨ��key��ȡvalue
			System.out.println("key="+key+" value=["+student.getName()+","+student.getAge()+"]");
		}
	}
}
