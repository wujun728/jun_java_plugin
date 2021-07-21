package aop.impl.advice;

public class Waiter {

	public void leadCustomer(String name){
		System.out.println("--I am leading you--"+name);
	}
	public void serveCutomer(String name){
		System.out.println("--I am serving you--"+name);
	}
	
}
