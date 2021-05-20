package com.jun.plugin.json.fastjson;

import java.util.List;

public class Teacher {

	String teacherName;

	Integer teacherAge;
	Course course;
	List<Student> students;

	public Teacher() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Teacher(String teacherName, Integer teacherAge, Course course, List<Student> students) {
		super();
		this.teacherName = teacherName;
		this.teacherAge = teacherAge;
		this.course = course;
		this.students = students;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public Integer getTeacherAge() {
		return teacherAge;
	}

	public void setTeacherAge(Integer teacherAge) {
		this.teacherAge = teacherAge;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	
	
	

}
