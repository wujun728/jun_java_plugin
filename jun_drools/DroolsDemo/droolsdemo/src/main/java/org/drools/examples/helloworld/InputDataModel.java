package org.drools.examples.helloworld;

public class InputDataModel {
	
	private String agroType;
	private double moisture;
	
	public InputDataModel(String agroType, double moisture) {
		this.agroType = agroType;
		this.moisture = moisture;
	}
	
	public String getAgroType() {
		return agroType;
	}
	public void setAgroType(String agroType) {
		this.agroType = agroType;
	}
	public double getMoisture() {
		return moisture;
	}
	public void setMoisture(double moisture) {
		this.moisture = moisture;
	}
}
