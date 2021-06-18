package com.java1234.chap07.sec03;

import java.util.LinkedList;

import com.java1234.chap07.sec01.Student;

public class TestForeach {

	public static void main(String[] args) {
		LinkedList<Student> list=new LinkedList<Student>();
		list.add(new Student("张三",10));
		list.add(new Student("李四",20));
		list.add(new Student("王五",30));
		
		/**
		 * 用foreach遍历
		 */
		for(Student s:list){
			System.out.println("姓名："+s.getName()+"年龄："+s.getAge());
		}
	}
}
