package app;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import app.dao.StudentDao;
import app.entity.Student;

public class StudentDaoTest extends TestBase {
	
	@Autowired
	private StudentDao dao;
	
	@Test
	public void testAdd() {
		Student s = new Student();
		s.setStuName("Jim");
		
		dao.save(s);
		
		// 自增ID
		System.out.println(s.getId());
		
		System.out.println(dao.get(s));
	}
	
}
