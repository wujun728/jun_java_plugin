package com.us.demo.ticket;

public class Customer {
	private String name;
	private String subscription;
	public Customer(){
		
	}
	public Customer(final String name,final String subscription){
		this.name=name;
		this.subscription=subscription;
	}
	public String getName(){return name;}
	public String getSubscription(){return subscription;}
	public String toString(){return "[sub:"+subscription+" ] ["+subscription+"]";}

}
