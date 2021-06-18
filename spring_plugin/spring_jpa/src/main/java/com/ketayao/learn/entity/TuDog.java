package com.ketayao.learn.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/** 
 * @author Wujun
 * @since   2013年12月5日 上午10:39:51 
 */
@Entity
@Table(name="test_dog")
public class TuDog extends Dog {
	@Column
	private String color;

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
}
