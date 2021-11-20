package com.jun.plugin.json.fastjson;

public class Course {
	
	String courseName;
	Integer code;
	
	public Course() {
		super();
	}
	public Course(String courseName, Integer code) {
		super();
		this.courseName = courseName;
		this.code = code;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	
	
}
