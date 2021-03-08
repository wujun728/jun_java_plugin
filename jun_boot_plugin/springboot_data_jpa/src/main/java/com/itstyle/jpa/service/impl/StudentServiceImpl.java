package com.itstyle.jpa.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itstyle.jpa.dynamicquery.DynamicQuery;
import com.itstyle.jpa.model.Student;
import com.itstyle.jpa.service.IStudentService;

@Service
public class StudentServiceImpl implements IStudentService {

	@Autowired
	private DynamicQuery dynamicQuery;
	
	
	@Override
	public List<Object[]> listStudent() {
		String nativeSql = "SELECT s.id AS studentId,c.id AS classId,c.class_name AS className,c.teacher_name AS teacherName,s.name,s.age FROM app_student s,app_class c";
		List<Object[]> list = dynamicQuery.nativeQueryList(nativeSql, new Object[]{});
		return list;
	}
	
	@Override
	public List<Student> listStudentModel() {
		String nativeSql = "SELECT s.id AS studentId,c.id AS classId,c.class_name AS className,c.teacher_name AS teacherName,s.name,s.age FROM app_student s,app_class c";
		List<Student> list = dynamicQuery.nativeQueryListModel(Student.class, nativeSql, new Object[]{});
		return list;
	}

	@Override
	public List<Map<Object,Object>> listStudentMap() {
		String nativeSql = "SELECT s.id AS studentId,c.id AS classId,c.class_name AS className,c.teacher_name AS teacherName,s.name,s.age FROM app_student s,app_class c";
		List<Map<Object,Object>> list = dynamicQuery.nativeQueryListMap(nativeSql, new Object[]{});
		return list;
	}

}
