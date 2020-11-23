package com.jun.plugin.poi.test.excel.vo;

import java.util.Collections;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * listrow 对象
 * 
 * @author Wujun
 * 
 * @date 2016-2-17 Description:
 */
public class ListLine {
	private List<CellKV<String>> listStr = null;
	private List<CellKV<Double>> listDouble = null;
	private List<CellKV<Boolean>> listBoolean = null;
	private List<CellKV<Date>> listDate = null;
	
	@SuppressWarnings("unchecked")
	public List<CellKV<String>> getListStr() {
		return listStr==null?Collections.EMPTY_LIST:listStr;
	}

	@SuppressWarnings("unchecked")
	public List<CellKV<Double>> getListDouble() {
		return listDouble==null?Collections.EMPTY_LIST:listDouble;
	}

	@SuppressWarnings("unchecked")
	public List<CellKV<Boolean>> getListBoolean() {
		return listBoolean==null?Collections.EMPTY_LIST:listBoolean;
	}

	@SuppressWarnings("unchecked")
	public List<CellKV<Date>> getListDate() {
		return listDate==null?Collections.EMPTY_LIST:listDate;
	}

	public ListLine addValue(int index, int value) {
		return addValue(index, (double) value);
		
	}
	public ListLine addValue(int index, long value) {
		return addValue(index, (double) value);
		
	}

	public ListLine addValue(int index, double value) {
		if (listDouble == null) {
			listDouble = new ArrayList<>();
		}
		listDouble.add(new CellKV<Double>(index, value));
		return this;
	}

	public ListLine addValue(int index, String value) {
		if (listStr == null) {
			listStr = new ArrayList<>();
		}
		listStr.add(new CellKV<String>(index, value));
		return this;
	}

	public ListLine addValue(int index, boolean value) {
		if (listBoolean == null) {
			listBoolean = new ArrayList<>();
		}
		listBoolean.add(new CellKV<Boolean>(index, value));
		return this;
	}

	public ListLine addValue(int index, Date value) {
		if (listDate == null) {
			listDate = new ArrayList<>();
		}
		listDate.add(new CellKV<Date>(index, value));
		return this;
	}
}
