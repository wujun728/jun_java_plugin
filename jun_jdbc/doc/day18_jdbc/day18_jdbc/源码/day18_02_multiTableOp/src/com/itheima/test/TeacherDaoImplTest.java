package com.itheima.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.itheima.dao.impl.TeacherDaoImpl;
import com.itheima.domain.Student;
import com.itheima.domain.Teacher;

public class TeacherDaoImplTest {
	private TeacherDaoImpl dao = new TeacherDaoImpl();
	@Test
	public void testSaveTeacher() {
		
		Teacher t1 = new Teacher();
		t1.setId(1);
		t1.setName("任瞳");
		t1.setSalary(10000);
		
		Teacher t2 = new Teacher();
		t2.setId(2);
		t2.setName("王昭珽");
		t2.setSalary(11000);
		
		
		Student s1 = new Student();
		s1.setId(1);
		s1.setName("张新朋");
		s1.setGrade("A");
		
		Student s2 = new Student();
		s2.setId(2);
		s2.setName("张湾");
		s2.setGrade("A");
		
		//建立关系
		t1.getStudents().add(s1);
		t1.getStudents().add(s2);
		
		t2.getStudents().add(s1);
		t2.getStudents().add(s2);
		
		dao.saveTeacher(t1);
		dao.saveTeacher(t2);
		
		
		
		
	}

	@Test
	public void testFindTeacherById() {
		Teacher t = dao.findTeacherById(2);
		System.out.println(t);
		for(Student s:t.getStudents())
			System.out.println(s);
	}

}
