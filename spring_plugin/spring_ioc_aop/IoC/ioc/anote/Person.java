package ioc.anote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("person")
public class Person {

	private String name;
	@Autowired
	private Car car;
	@Autowired
	@Qualifier("personphone")
	private Phone phone;	
	//FMark: refer to @Component("personphone")
	//When mark Phone class 
	
	public Person(){ }
	public Person(String name, Car car, Phone phone) {
		super();
		this.name = name;
		this.car = car;
		this.phone = phone;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	public Phone getPhone() {
		return phone;
	}
	public void setPhone(Phone phone) {
		this.phone = phone;
	}
	
}
