package com.demo.spring.rpc.service;

import java.util.List;

import com.demo.spring.rpc.service.Student;

/**
 * 业务接口
 */
public interface WelcomeService {
	public void sayHello(String name);
	public int getLength(String name);
	public List<Student> getStudents(int n);
}
