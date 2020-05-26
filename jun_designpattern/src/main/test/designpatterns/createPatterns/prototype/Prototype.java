package designpatterns.createPatterns.prototype;

import java.io.Serializable;

public class Prototype implements Cloneable ,Serializable{
	private String name;
	
	public Prototype(String string) {
		setName(string);
	}
	public Prototype() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private static final long serialVersionUID = -6953454805288994361L;
	
	public Object clone(){
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {

	}

}
