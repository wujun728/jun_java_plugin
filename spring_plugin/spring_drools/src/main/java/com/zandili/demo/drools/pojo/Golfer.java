package com.zandili.demo.drools.pojo;

import java.io.Serializable;

public class Golfer implements Serializable {

	private static final long serialVersionUID = -7288800128523719334L;
	private String name;
	private String color;
	private int position;

	public Golfer() {

	}

	public Golfer(String name, String color, int position) {
		super();
		this.name = name;
		this.color = color;
		this.position = position;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return this.color;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the name
	 */
	public int getPosition() {
		return this.position;
	}

}