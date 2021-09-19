package com.jun.plugin.demo.chap07.sec03;

import java.util.LinkedList;

import com.jun.plugin.demo.chap07.sec01.Student;

public class TestForeach {

	public static void main(String[] args) {
		LinkedList<Student> list=new LinkedList<Student>();
		list.add(new Student("����",10));
		list.add(new Student("����",20));
		list.add(new Student("����",30));
		
		/**
		 * ��foreach����
		 */
		for(Student s:list){
			System.out.println("������"+s.getName()+"���䣺"+s.getAge());
		}
	}
}
