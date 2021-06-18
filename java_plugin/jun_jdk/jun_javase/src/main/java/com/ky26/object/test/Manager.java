package com.ky26.object.test;

public class Manager extends Empolyee{
	private double bonus;
	public Manager(String name, String Id, double salary,double bonus) {
		super(name, Id, salary);
		this.bonus=bonus;
	}

	public double getBonus() {
		return bonus;
	}

	public void setBonus(double bonus) {
		this.bonus = bonus;
	}

	void work() {
		System.out.println("mananger...");
	}

}
