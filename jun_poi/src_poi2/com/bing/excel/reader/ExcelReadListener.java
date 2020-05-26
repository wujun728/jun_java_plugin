package com.bing.excel.reader;


import com.bing.excel.vo.ListRow;

/**
 * @author shizhongtao
 *
 * @date 2016-3-1
 * Description:  
 */
public interface ExcelReadListener {
	/**
	 * 该方法自动被调用，每读一行调用一次，在方法中写自己的业务逻辑即可
	 * 
	 * @param sheetIndex
	 *            工作簿序号
	 * @param curRow
	 *            处理到第几行
	 * @param rowList
	 *            当前数据行的数据集合
	 */
	void optRow(int curRow, ListRow rowList);
	void startSheet(int sheetIndex, String name);
	/**
	 * @param sheetIndex 工作表下标 从0开始
	 * @param name 工作表名称
	 */
	void endSheet(int sheetIndex, String name);
	void endWorkBook();
}
  