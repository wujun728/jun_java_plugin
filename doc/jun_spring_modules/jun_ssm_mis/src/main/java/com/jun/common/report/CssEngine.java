package com.jun.common.report;

/**
 * 报表Css处理类
 * @author Wujun
 *
 */
public class CssEngine {
	public CssEngine() {}

	/**
	 * 表格样式应用
	 * @param t
	 * @return
	 * @throws ReportException
	 */
	public static Table applyCss(Table t) throws ReportException {
		if (t == null || t.getType().equals(Report.NONE_TYPE)){
			return t;
		}
		Table result = t.deepClone();
		String type = result.getType();
		TableRow tr = null;
		for (int i = 0; i < result.getRowCount(); i++) {
			tr = result.getRow(i);
			if (tr.getType().equals(Report.NONE_TYPE)) {
				tr.setType(type);
			}
			result.setRow(i, applyCss(tr));
		}
		return result;
	}
	
	/**
	 * 表格行单元格样式应用
	 * @param tr
	 * @return
	 * @throws ReportException
	 */
	private static TableRow applyCss(TableRow tr) throws ReportException {
		if (tr.getType().equals(Report.NONE_TYPE)){
			return tr;
		}
		TableRow result = tr.deepClone();
		String type = result.getType();
		TableCell tc = null;
		for (int i = 0; i < result.getCellCount(); i++) {
			tc = result.getCell(i);
			if (tc.getCssClass().equals(Report.NONE_TYPE)) {
				tc.setCssClass(type);
			}
		}
		return result;
	}
}