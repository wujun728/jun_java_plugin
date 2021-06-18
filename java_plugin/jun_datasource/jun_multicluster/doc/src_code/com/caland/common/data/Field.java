package com.caland.common.data;

public class Field {
	// fields
	private java.lang.String name;
	private java.lang.String fieldType;
	private java.lang.String fieldDefault;
	private java.lang.String fieldProperty;
	private java.lang.String comment;
	private java.lang.String nullable;
	private java.lang.String extra;

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getFieldType() {
		return fieldType;
	}

	public void setFieldType(java.lang.String fieldType) {
		this.fieldType = fieldType;
	}

	public java.lang.String getFieldDefault() {
		return fieldDefault;
	}

	public void setFieldDefault(java.lang.String fieldDefault) {
		this.fieldDefault = fieldDefault;
	}

	public java.lang.String getFieldProperty() {
		return fieldProperty;
	}

	public void setFieldProperty(java.lang.String fieldProperty) {
		this.fieldProperty = fieldProperty;
	}

	public java.lang.String getComment() {
		return comment;
	}

	public void setComment(java.lang.String comment) {
		this.comment = comment;
	}

	public java.lang.String getNullable() {
		return nullable;
	}

	public void setNullable(java.lang.String nullable) {
		this.nullable = nullable;
	}

	public java.lang.String getExtra() {
		return extra;
	}

	public void setExtra(java.lang.String extra) {
		this.extra = extra;
	}

	@Override
	public String toString() {
		return "Field [name=" + name + ", fieldType=" + fieldType
				+ ", fieldDefault=" + fieldDefault + ", fieldProperty="
				+ fieldProperty + ", comment=" + comment + ", nullable="
				+ nullable + ", extra=" + extra + "]";
	}
}
