package ioc.anote;

import org.springframework.stereotype.Component;

@Component("personphone")
public class Phone {

	private String brand;
	private String color;
	
	public Phone(){ }
	public Phone(String brand, String color) {
		super();
		this.brand = brand;
		this.color = color;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}	
}
