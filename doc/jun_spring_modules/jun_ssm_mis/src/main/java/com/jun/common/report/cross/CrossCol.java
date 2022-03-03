package com.jun.common.report.cross;

import com.jun.common.report.grouparithmetic.GroupArithmetic;

public class CrossCol {
  public CrossCol() {
  }

  /**
   *
   * @param index 交叉部分在原始表格数据中的列序号
   * @param headerText 说明文字
   * @param arith 交叉部分的汇总算法
   */
  public CrossCol(int index,String headerText, GroupArithmetic arith) {
    this.index = index;
    this.headerText=headerText;
    this.arith = arith;
  }

  /**
   * 交叉部分在原始表格数据中的列序号。
   */
  private int index;

  /**
   * 汇总算法。
   */
  private GroupArithmetic arith;

  /**
   * 说明文字。
   */
  private String headerText;
  /**
   * 获得说明文字
   * @return
   */
  public String getHeaderText() {
    return headerText;
  }

  /**
   * 设置说明文字
   * @param headerText
   */
  public void setHeaderText(String headerText) {
    this.headerText = headerText;
  }

  /**
   * 获得交叉部分在原始表格数据中的列序号。
   * @return
   */
  public int getIndex() {
    return index;
  }

  /**
   * 获得交叉部分的汇总算法。
   * @return
   */
  public GroupArithmetic getArith() {
    return arith;
  }

  /**
   * 设置交叉部分在原始表格数据中的列序号。
   * @param index
   */
  public void setIndex(int index) {
    this.index = index;
  }

  /**
   * 设置交叉部分的汇总算法。
   * @param arith
   */
  public void setArith(GroupArithmetic arith) {
    this.arith = arith;
  }

}