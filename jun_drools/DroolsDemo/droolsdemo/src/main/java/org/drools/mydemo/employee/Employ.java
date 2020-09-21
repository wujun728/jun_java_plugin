package org.drools.mydemo.employee;

public class Employ {
	//public static final double BONUS = 1000;

	private String eduInfo;

	private String resume;

	private String annualExam;

	private String awardPunish;

	private double basicSalary;

	private double dutySalary;

	private double bonus;

	private double percent;

	private double totalSalary;

	public String getEduInfo() {
		return eduInfo;
	}

	public void setEduInfo(String eduInfo) {
		this.eduInfo = eduInfo;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getAnnualExam() {
		return annualExam;
	}

	public void setAnnualExam(String annualExam) {
		this.annualExam = annualExam;
	}

	public String getAwardPunish() {
		return awardPunish;
	}

	public void setAwardPunish(String awardPunish) {
		this.awardPunish = awardPunish;
	}

	public double getBasicSalary() {
		return basicSalary;
	}

	public void setBasicSalary(double basicSalary) {
		this.basicSalary = basicSalary;
	}

	public double getDutySalary() {
		return dutySalary;
	}

	public void setDutySalary(double dutySalary) {
		this.dutySalary = dutySalary;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public double getTotalSalary() {
		return totalSalary;
	}

	public void setTotalSalary(double totalSalary) {
		this.totalSalary = totalSalary;
	}
	
	public double getBonus() {
		return bonus;
	}

	public void setBonus(double bonus) {
		this.bonus = bonus;
	}

	public String toString() {

		return "[" + eduInfo + " " + resume + " " + annualExam +

		" " + awardPunish + " " + basicSalary + " " + dutySalary +

		" " + bonus + " " + totalSalary + "]";

	}

}
