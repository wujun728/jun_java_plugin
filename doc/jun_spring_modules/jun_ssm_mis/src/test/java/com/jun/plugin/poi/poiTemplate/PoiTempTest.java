package com.jun.plugin.poi.poiTemplate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.jun.plugin.poi.PoiTemplate;
import com.jun.plugin.poi.TagUtil;

public class PoiTempTest {

	@Test
	public void test() {
		TagUtil.registerTag(TestTag.class);
		try {
			PoiTemplate temp = new PoiTemplate(getClass().getResource("/temp.xls").getFile(), getClass().getResource("/").getFile() + System.currentTimeMillis() + ".xls");
			temp.addValue("hello", "hello excel!");
			List<Student> students = Student.getStudents(8,5);
			temp.addValue("students", students);
			temp.addValue("a", "下");
			temp.addValue("b", "上");
			temp.writeExcel();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetTag(){
		System.out.println(TagUtil.getTag("#test"));
	}

	
	@Test
	public void testGetValue(){
		Map<String, Object> a = new HashMap<String,Object>();
		a.put("haha", "哈哈");
		Student s = new Student();
		s.setStudentName("小明");
		s.setStudentSex("men");
		Map<String, Student> map = new HashMap<String, Student>();
		map.put("xiaoming", s);
		a.put("map", map);
		System.out.println(PoiTemplate.getValue(a, "haha"));
		System.out.println(PoiTemplate.getValue(s, "studentName"));
		System.out.println(PoiTemplate.getValue(a, "map.xiaoming.studentSex"));
	}
	
	
	@Test
	public void testGetEl(){
		Map<String, Object> a = new HashMap<String,Object>();
		a.put("haha", "哈哈");
		Student s = new Student();
		s.setStudentName("小明");
		s.setStudentSex("men");
		Map<String, Student> map = new HashMap<String, Student>();
		map.put("xiaoming", s);
		a.put("map", map);
		PoiTemplate temp = new PoiTemplate();
		temp.addValue("haha", a);
		System.out.println(temp.getEl("${haha.map.xiaoming.studentName}"));
	}
	@Test
	public void testParseString(){
		Map<String, Object> a = new HashMap<String,Object>();
		a.put("haha", "哈哈");
		Student s = new Student();
		s.setStudentName("小明");
		s.setStudentSex("men");
		Map<String, Student> map = new HashMap<String, Student>();
		map.put("xiaoming", s);
		a.put("map", map);
		PoiTemplate temp = new PoiTemplate();
		temp.addValue("haha", a);
		System.out.println(temp.parseString("abc${haha.map.xiaoming.studentName}${haha.map.xiaoming.studentSex}--123${haha.haha}"));
	}
	
	
	
}
