package com.us.demo.ticket;

public class Ticket {
	private Customer customer;
	private String status;

	public Ticket(){
		
	}
	public Ticket(final Customer customer){
		super();
		this.customer=customer;
		this.status="New";
	}
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String toString(){
		return "User: "+this.customer.getSubscription()+"";
	}
}
