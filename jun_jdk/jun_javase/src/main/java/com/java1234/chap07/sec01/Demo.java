package com.java1234.chap07.sec01;

import java.util.LinkedList;

public class Demo {

	public static void main(String[] args) {
		Student student[]=new Student[3];
		Student s1=new Student("张三",1);
		Student s2=new Student("李四",3);
		Student s3=new Student("王五",4);
		
		LinkedList<Student> list=new LinkedList<Student>();
		list.add(s1);
		list.add(s2);
		list.add(s3);
		
		/**
		 * 遍历集合
		 */
		for(int i=0;i<list.size();i++){
			Student s=list.get(i);
			System.out.println(s.getName()+":"+s.getAge());
		}
	}
}
