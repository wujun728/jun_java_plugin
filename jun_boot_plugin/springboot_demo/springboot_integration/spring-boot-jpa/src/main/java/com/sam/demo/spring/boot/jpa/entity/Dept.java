package com.sam.demo.spring.boot.jpa.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;


public class Dept {
	@NotNull(message="aaa")
	private Long id;
	
	@NotNull(message="aaa")
	@Length(min = 1, max = 6, message="aaa")
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
