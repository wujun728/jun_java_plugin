package com.itheima.domain;

public class Student {
  private String idcard;
  private String examid;
  private String name;
  private String location;
  private double grade;
  
  
public Student(String idcard, String examid, String name, String location,
		double grade) {
	super();
	this.idcard = idcard;
	this.examid = examid;
	this.name = name;
	this.location = location;
	this.grade = grade;
}


public Student() {
	super();
}

public String toString() {
	return "Student [idcard=" + idcard + ", examid=" + examid + ", name="
			+ name + ", location=" + location + ", grade=" + grade + "]";
}


public String getIdcard() {
	return idcard;
}
public void setIdcard(String idcard) {
	this.idcard = idcard;
}
public String getExamid() {
	return examid;
}
public void setExamid(String examid) {
	this.examid = examid;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
}
public double getGrade() {
	return grade;
}
public void setGrade(double grade) {
	this.grade = grade;
}
  
  
}
