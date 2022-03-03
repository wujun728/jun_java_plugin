package com.jun.admin;

import java.util.ArrayList;
import java.util.List;

/**
 * operamasksui 的Grid插件的数据模型 </p>
 * 很简单，只包装了数据的总条数total和所有数据集合rows，这样包装是为了前台omGrid组件能够识别返回的JSON个数数据。</p>
 * 它需要的格式是：</p> {"total":15,"rows":[{childJSON}]}</p>
 * 
 * 
 * @param <T>
 */
public class GridDataModel<T> {
	// 显示的总数
	private int total;

	// 行数据
	private List<T> rows = new ArrayList<T>();

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
