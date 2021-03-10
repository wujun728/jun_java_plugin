package com.zzb.spring.jdbc;

public class Employee {
	private Integer id;
	private String lastName;
	private String email;
	
	private Department dpetId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Department getDpetId() {
		return dpetId;
	}

	public void setDpetId(Department dpetId) {
		this.dpetId = dpetId;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", lastName=" + lastName + ", email=" + email + ", dpetId=" + dpetId + "]";
	}


	
}
