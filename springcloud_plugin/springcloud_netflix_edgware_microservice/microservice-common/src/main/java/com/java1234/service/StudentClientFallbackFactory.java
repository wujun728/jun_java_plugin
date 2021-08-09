package com.java1234.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.java1234.entity.Student;

import feign.hystrix.FallbackFactory;

@Component
public class StudentClientFallbackFactory implements FallbackFactory<StudentClientService>{

	@Override
	public StudentClientService create(Throwable cause) {
		// TODO Auto-generated method stub
		return new StudentClientService() {
			
			@Override
			public boolean save(Student student) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public List<Student> list() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Map<String, Object> getInfo() {
				Map<String,Object> map=new HashMap<String,Object>();
                map.put("code", 500);
                map.put("info", "系统出错，稍后重试");
                return map;
			}
			
			@Override
			public Student get(Integer id) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean delete(Integer id) {
				// TODO Auto-generated method stub
				return false;
			}
		};
	}

}
