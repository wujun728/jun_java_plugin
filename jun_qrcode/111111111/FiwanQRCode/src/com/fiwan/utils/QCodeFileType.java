
package com.fiwan.utils;

/**
 * 支持的类型枚举
 * 
 */
public enum QCodeFileType {

	// -------------
	JPG("JPG", false), //
	BMP("BMP", false), //
	GIF("GIF", false), //
	PNG("PNG", false), //
	// TIF("TIF", false), //
	// TXT("TXT", false), //
	// HTML("HTML", false), //
	// XAML("XAML", false), //
	// ------------------------
	EPS("EPS", true), //
	PDF("PDF", true), //
	SVG("SVG", true), //
	;

	/**
	 * 是否是矢量图
	 */
	private final boolean vector;
	private final String chName;

	private QCodeFileType(String chName, boolean vector) {
		this.chName = chName;
		this.vector = vector;
	}

	/**
	 * @return Returns the chName.
	 */
	public String getChName() {
		return chName;
	}

	/**
	 * @return Returns the vector.
	 */
	public boolean isVector() {
		return vector;
	}
}
