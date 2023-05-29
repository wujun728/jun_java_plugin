package com.jun.plugin.demo.chap07.sec03;

import java.util.Iterator;
import java.util.LinkedList;

import com.jun.plugin.demo.chap07.sec01.Student;

public class TestIterator {

	public static void main(String[] args) {
		LinkedList<Student> list=new LinkedList<Student>();
		list.add(new Student("����",10));
		list.add(new Student("����",20));
		list.add(new Student("����",30));
		
		/**
		 * ��Iterator��������
		 */
		Iterator<Student> it=list.iterator();  // ����һ��������
		while(it.hasNext()){
			Student s=it.next();   // ���ص�������һ��Ԫ�ء�
			System.out.println("������"+s.getName()+"���䣺"+s.getAge());
		}
	}
}
