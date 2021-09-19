package com.java.plugin.xml.rw.test;

import junit.framework.Assert;

import org.junit.Test;

import com.java.plugin.xml.rw.dao.StudentDao;
import com.java.plugin.xml.rw.domain.Student;

public class Test1 {
	
	StudentDao dao = new StudentDao();

//	@Test
	public void add(){
		Student stu = new Student("100","1200","�żҸ�","�żҽ�1",100);
		boolean flag = dao.addStudent(stu);
		Assert.assertEquals(true, flag);
	}
	
	@Test
	public void findStudent(){
		String str="1200";
		Student stu = dao.findStudentByExamid(str);
		System.out.println(stu);
	}
	@Test
	public void updateStudent(){
		String str="1200";
		Student stu = dao.findStudentByExamid(str);
		System.out.println(stu);
	}
	
//	@Test
	public void deleteStudent(){
		String name ="�żҸ�";
		dao.deleteStudentByName(name);
	}

}
