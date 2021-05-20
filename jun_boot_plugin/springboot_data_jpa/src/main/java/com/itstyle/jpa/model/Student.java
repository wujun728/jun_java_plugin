package com.itstyle.jpa.model;
//学生(组合表)
public class Student {
	private Integer studentId;
	private Integer classId;
	private String className;
	private String teacherName;
	private String name;
	private Integer age;
	
	public Student() {
		super();
	}

	public Student(Integer studentId, Integer classId, String className,
			String teacherName, String name, Integer age) {
		super();
		this.studentId = studentId;
		this.classId = classId;
		this.className = className;
		this.teacherName = teacherName;
		this.name = name;
		this.age = age;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
}
