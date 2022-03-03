package com.jun.common.report.printer;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public abstract class ExcelCss {
  public ExcelCss() {
    super();
  }
  /**
   * 设置各属性。调用的此类其他方法之前应该调用它。
   */
  public abstract void init(HSSFWorkbook workbook);


  /**
   * 缺省列宽度
   */
  private short defaultColumnWidth=10;

  /**
   * 分组汇总部分样式
   */
  private HSSFCellStyle groupTotal;
  /**
   * 总汇总部分样式
   */
  private HSSFCellStyle total;
  /**
   * 报表主体表头部分样式
   */
  private HSSFCellStyle head;
  /**
   * 数据部分样式
   */
  private HSSFCellStyle data;
  /**
   * 报表标题部分样式
   */
  private HSSFCellStyle title;
  /**
   * 交叉表左上角表头部分样式
   */
  private HSSFCellStyle crossHeadHead;

  /**
   * 获得总汇总部分样式
   * @return
   */
  public HSSFCellStyle getTotal() {
    return total;
  }

  /**
   * 获得报表标题部分样式
   * @return
   */
  public HSSFCellStyle getTitle() {
    return title;
  }

  /**
   * 获得报表主体表头部分样式
   * @return
   */
  public HSSFCellStyle getHead() {
    return head;
  }

  /**
   * 获得分组汇总部分样式
   * @return
   */

  public HSSFCellStyle getGroupTotal() {
    return groupTotal;
  }

  /**
   * 获得数据部分样式
   * @return
   */
  public HSSFCellStyle getData() {
    return data;
  }

  /**
   * 设置总汇总部分样式
   * @param total
   */
  public void setTotal(HSSFCellStyle total) {
    this.total = total;
  }

  /**
   * 设置报表标题部分样式
   * @param title
   */
  public void setTitle(HSSFCellStyle title) {
    this.title = title;
  }

  /**
   * 设置报表主体表头部分样式
   * @param head
   */
  public void setHead(HSSFCellStyle head) {
    this.head = head;
  }

  /**
   * 设置分组汇总部分样式
   * @param groupTotal
   */
  public void setGroupTotal(HSSFCellStyle groupTotal) {
    this.groupTotal = groupTotal;
  }

  /**
   * 设置数据部分样式
   * @param data
   */
  public void setData(HSSFCellStyle data) {
    this.data = data;
  }

  /**
   * 获得交叉表左上角表头部分样式
   * @return
   */
  public HSSFCellStyle getCrossHeadHead() {
    return crossHeadHead;
  }

  /**
   * 设置交叉表左上角表头部分样式
   * @param crossHeadHead
   */
  public void setCrossHeadHead(HSSFCellStyle crossHeadHead) {
    this.crossHeadHead = crossHeadHead;
  }
  /**
   * 获得缺省列宽度
   * @return
   */
  public short getDefaultColumnWidth() {
    return defaultColumnWidth;
  }
  /**
   * 设置缺省列宽度
   * @param defaultColumnWidth
   */
  public void setDefaultColumnWidth(short defaultColumnWidth) {
    this.defaultColumnWidth = defaultColumnWidth;
  }

}
