package com.demo.spring.rpc.service;

import java.util.ArrayList;
import java.util.List;

import com.demo.spring.rpc.service.Student;
import com.demo.spring.rpc.service.WelcomeService;

/**
 * pojo 
 */
public class WelcomeServiceImpl implements WelcomeService {
	public void sayHello(String name) {
		System.out.println(name);
	}
	
	public int getLength(String name){
		return name.length() ;
	}
	
	public List<Student> getStudents(int n){
		List<Student> list = new ArrayList<Student>();
		Student s = null ;
		for(int i =0 ; i < n ; i ++){
			s = new Student();
			s.setId(1 + i);
			s.setName("tom" + i);
			s.setAge(20 + i);
			list.add(s);
		}
		return list ;
	}
}
