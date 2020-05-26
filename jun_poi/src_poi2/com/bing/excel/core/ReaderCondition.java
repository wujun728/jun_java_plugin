package com.bing.excel.core;


/**
 * 读取条件类
 * @author shizhongtao
 * 
 * @date 2016-2-17 Description:
 */
public class ReaderCondition<T>  {
	private int startRow = 1;
	private int endRow = Integer.MAX_VALUE;
	private int sheetIndex = 0;
	private Class<T> clazz;

	/**
	 * @param sheetIndex index of sheet,started 0;
	 * @param clazz
	 */
	public ReaderCondition(int sheetIndex, Class<T> clazz) {
		this.sheetIndex = sheetIndex;
		this.clazz = clazz;
	}

	

	/**
	 * @param sheetIndex
	 * @param startRow from which line to read.started 0;default 1;
	 * @param clazz
	 */
	public ReaderCondition(int sheetIndex, int startRow, Class<T> clazz) {
		this.startRow = startRow;
		this.sheetIndex = sheetIndex;
		this.clazz = clazz;
	}

	public ReaderCondition<T> setStartRow(int startRow) {
		this.startRow = startRow;
		return this;
	}

	/**
	 * @param endRow the end index num of row  ，0 started。default <code>Integer.MAX_VALUE</code> 
	 * @return
	 */
	public ReaderCondition<T> setEndRow(int endRow) {
		this.endRow = endRow;
		return this;
	}

	public ReaderCondition<T> setSheetIndex(int sheetIndex) {
		this.sheetIndex = sheetIndex;
		return this;
	}

	public int getStartRow() {
		return startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public int getSheetIndex() {
		return sheetIndex;
	}

	public Class<T> getTargetClazz() {
		return clazz;
	}

}
