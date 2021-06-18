package ioc.anote;

import org.springframework.stereotype.Component;

@Component("car")
public class Car {

	private String color;
		
	public Car(){ this.color="Blue";}
	public Car(String color) {
		super();
		this.color = color;
	}

	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
}
