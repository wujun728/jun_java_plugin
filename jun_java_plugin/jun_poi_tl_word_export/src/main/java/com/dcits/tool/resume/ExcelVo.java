package com.dcits.tool.resume;

import com.dcits.tool.resume.annotation.ExcelField;



/**
 * 表 b_staff_baseinfo
 * 
 * @author yanwlb
 * @date 2021-09-30
 */

public class ExcelVo
{
	@ExcelField(name = "姓名")
	private String name;

	@ExcelField(name = "性别")
	private String gender;

	@ExcelField(name = "出生日期")
	private String birthday;

	@ExcelField(name = "学历")
	private String studyLevel;

	@ExcelField(name = "毕业学校")
	private String school;

	@ExcelField(name = "技术职称")
	private String technicalTitle;

	@ExcelField(name = "任职公司")
	private String company;

	@ExcelField(name = "公司职务")
	private String post;

	@ExcelField(name = "任职时间")
	private String officeTime;

	@ExcelField(name = "本项目任职")
	private String workName;

	@ExcelField(name = "人员档次")
	private String level;

	@ExcelField(name = "工作简历及主要业绩")
	private String desc;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the birthday
	 */
	public String getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday to set
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return the studyLevel
	 */
	public String getStudyLevel() {
		return studyLevel;
	}

	/**
	 * @param studyLevel to set
	 */
	public void setStudyLevel(String studyLevel) {
		this.studyLevel = studyLevel;
	}

	/**
	 * @return the school
	 */
	public String getSchool() {
		return school;
	}

	/**
	 * @param school to set
	 */
	public void setSchool(String school) {
		this.school = school;
	}

	/**
	 * @return the technicalTitle
	 */
	public String getTechnicalTitle() {
		return technicalTitle;
	}

	/**
	 * @param technicalTitle to set
	 */
	public void setTechnicalTitle(String technicalTitle) {
		this.technicalTitle = technicalTitle;
	}

	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * @return the post
	 */
	public String getPost() {
		return post;
	}

	/**
	 * @param post to set
	 */
	public void setPost(String post) {
		this.post = post;
	}

	/**
	 * @return the officeTime
	 */
	public String getOfficeTime() {
		return officeTime;
	}

	/**
	 * @param officeTime to set
	 */
	public void setOfficeTime(String officeTime) {
		this.officeTime = officeTime;
	}

	/**
	 * @return the workName
	 */
	public String getWorkName() {
		return workName;
	}

	/**
	 * @param workName to set
	 */
	public void setWorkName(String workName) {
		this.workName = workName;
	}

	/**
	 * @return the level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * @param level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
