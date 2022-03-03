package com.jun.plugin.poi.poiTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Student {
	
	private static Map<String, String> titleMap;
	
    private Integer studentId;

    private String studentName;

    private String studentSex;

    private Date studentBirthday;

    private Integer classId;
    
    private List<Score> scores;
    
    public Student() {
		super();
	}
    
    public Student(Integer studentId, String studentName, String studentSex, Date studentBirthday,
			Integer classId) {
		super();
		this.studentId = studentId;
		this.studentName = studentName;
		this.studentSex = studentSex;
		this.studentBirthday = studentBirthday;
		this.classId = classId;
	}

	public static Map<String, String> getTitleMap(){
    	if(titleMap == null){
    		titleMap = new LinkedHashMap<String, String>();
    		titleMap.put("姓名", "studentName");
    		titleMap.put("性别", "studentSex");
    		titleMap.put("生日", "studentBirthday");
    		titleMap.put("班级", "classId");
    	}
    	return titleMap;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName == null ? null : studentName.trim();
    }

    public String getStudentSex() {
        return studentSex;
    }

    public void setStudentSex(String studentSex) {
        this.studentSex = studentSex == null ? null : studentSex.trim();
    }

    public Date getStudentBirthday() {
        return studentBirthday;
    }

    public void setStudentBirthday(Date studentBirthday) {
        this.studentBirthday = studentBirthday;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

	public List<Score> getScores() {
		return scores;
	}

	public void setScores(List<Score> scores) {
		this.scores = scores;
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", studentName="
				+ studentName + ", studentSex=" + studentSex
				+ ", studentBirthday=" + studentBirthday + ", classId="
				+ classId + ", scores=" + scores + "]";
	}
	public static List<Student> getStudents(int count,int scoreCount){
		List<Student> students = new ArrayList<Student>(1);
		for(int i = 0; i < count; i++){
			Student s = new Student(i+1, "小明" + i,i%3 == 0 ? "男" : "女", new Date(),i + 3);
			if(scoreCount > 0){
				List<Score> scores = new ArrayList<Score>();
				for(int j = 0; j < scoreCount; j++){
					Score sc = new Score(j+2,i+1,"课程"+(j+1),80);
					scores.add(sc);
				}
				s.setScores(scores);
			}
			students.add(s);
		}
		return students;
	}
	
}