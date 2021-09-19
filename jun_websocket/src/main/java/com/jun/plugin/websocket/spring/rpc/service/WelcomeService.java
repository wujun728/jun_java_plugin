package com.jun.plugin.websocket.spring.rpc.service;

import java.util.List;

import com.jun.plugin.websocket.spring.rpc.service.Student;

/**
 * ҵ��ӿ�
 */
public interface WelcomeService {
	public void sayHello(String name);
	public int getLength(String name);
	public List<Student> getStudents(int n);
}
