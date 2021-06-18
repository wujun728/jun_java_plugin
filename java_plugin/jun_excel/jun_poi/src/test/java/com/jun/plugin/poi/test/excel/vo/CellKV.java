package com.jun.plugin.poi.test.excel.vo;

import javax.management.RuntimeOperationsException;

import com.google.common.base.MoreObjects;

/**
 * @author Wujun
 *
 * @date 2016-3-1
 * Description:  
 */
public class CellKV <T>{

	/**
	 * @serial Attribute name.
	 */
	private int index;

	/**
	 * @serial Attribute value
	 */
	private T value = null;

	/**
	 * Constructs an CellKV object which associates the given attribute name
	 * with the given value.
	 * 
	 * @param name
	 *            A String containing the name of the attribute to be created.
	 *            Cannot be null.
	 * @param value
	 *            The Object which is assigned to the attribute. This object
	 *            must be of the same type as the attribute.
	 * 
	 */
	public CellKV(int index, T value) {

		if (index < 0) {
			throw new RuntimeOperationsException(new IllegalArgumentException(
					"CellKV index cannot be ls 0 "));
		}

		this.index = index;
		this.value = value;
	}

	/**
	 * Returns a String containing the name of the attribute.
	 * 
	 * @return the name of the attribute.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Returns an String that is the value of this attribute.
	 * 
	 * @return the value of the attribute.
	 */
	public T getValue() {
		return value;
	}

	/**
	 * Returns a String object representing this Attribute's value. The format
	 * of this string is not specified, but users can expect that two Attributes
	 * return the same string if and only if they are equal.
	 */
	public String toString() {
		return MoreObjects.toStringHelper(this).omitNullValues()
				.add("index", index).add("value", value).toString();
	}

}