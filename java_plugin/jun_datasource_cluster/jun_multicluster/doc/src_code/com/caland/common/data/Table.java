package com.caland.common.data;

public class Table {
	// fields
	private java.lang.String name;
	private java.lang.String comment;
	private java.lang.String engine;
	private java.lang.Integer rows;
	private java.lang.Integer auto_increment;

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getComment() {
		return comment;
	}

	public void setComment(java.lang.String comment) {
		this.comment = comment;
	}

	public java.lang.String getEngine() {
		return engine;
	}

	public void setEngine(java.lang.String engine) {
		this.engine = engine;
	}

	public java.lang.Integer getRows() {
		return rows;
	}

	public void setRows(java.lang.Integer rows) {
		this.rows = rows;
	}

	public java.lang.Integer getAuto_increment() {
		return auto_increment;
	}

	public void setAuto_increment(java.lang.Integer auto_increment) {
		this.auto_increment = auto_increment;
	}

	@Override
	public String toString() {
		return "Table [name=" + name + ", comment=" + comment + ", engine="
				+ engine + ", rows=" + rows + ", auto_increment="
				+ auto_increment + "]";
	}

}
