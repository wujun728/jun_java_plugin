package com.buxiaoxia.business.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by xw on 2017/3/16.
 * 2017-03-16 16:23
 */
@Data
@Entity
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String model;

	private String brand;

	private double price;

	private Date productDate;

	public Car() {
	}

	public Car(String brand, String model, double price, Date productDate) {
		this.brand = brand;
		this.model = model;
		this.price = price;
		this.productDate = productDate;
	}
}
