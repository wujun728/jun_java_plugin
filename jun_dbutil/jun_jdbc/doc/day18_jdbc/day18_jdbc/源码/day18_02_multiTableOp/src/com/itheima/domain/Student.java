package com.itheima.domain;

import java.util.ArrayList;
import java.util.List;

public class Student {
	private int id;
	private String name;
	private String grade;
	
	private List<Teacher> teachers = new ArrayList<Teacher>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public List<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", grade=" + grade
				+ "]";
	}
	
}
