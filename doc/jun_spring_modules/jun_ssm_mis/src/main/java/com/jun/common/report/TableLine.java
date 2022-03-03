package com.jun.common.report;

/**
 * 表格行列基类
 * @author Wujun
 *
 */
public abstract class TableLine {
	  /**
	   * 用来控制TableCell的cssClass属性
	   */
	  protected String type = Report.NONE_TYPE;

	  /**
	   * 获取指定的单元格
	   * @param index 单元格索引
	   * @return TableCell
	   */
	  public abstract TableCell getCell(int index) throws ReportException;

	  /**
	   * 获取单元格数
	   * @return int
	   */
	  public abstract int getCellCount();
	  /**
	   * 向列中添加单元格
	   * @param tc
	   */
	  public abstract void addCell(TableCell tc);

	  /**
	   * 返回行的所有单元格
	   * @return TableCell[]
	   */
	  public abstract TableCell[] getCells();

	  /**
	   * 设置类型。
	   * @return
	   */
	  public void setType(String type) throws ReportException {
	    this.type = type;
	  }

	  /**
	   * 获得类型。
	   * @param type
	   */
	  public String getType() {
	    return type;
	  }

	  /**
	   * 设置跨度值
	   * @param tc 要设置跨度的单元格。
	   * @param span 跨度值
	   */
	  public abstract void setSpan(TableCell tc, int span);

	  /**
	   * 获取跨度值。
	   * @param tc 单元格
	   * @return 跨度值
	   */
	  public abstract int getSpan(TableCell tc);

	  /**
	   * 将传入TableLine对象克隆成当前对象的一个副本
	   * @param tl 传入对象
	   * @return 当前对象副本
	   */
	  protected TableLine clone(TableLine tl) {
	    tl.type = this.type;
	    return tl;
	  }
}
