package com.jun.common.report.printer;

public class Point {
  private double x=0.0;
  private double y=0.0;


  /**
   *
   * @param x x坐标
   * @param y y坐标
   */
  public Point(double x,double y) {
    this.x=x;
    this.y=y;
  }
  /**
   * 获得x坐标
   * @return
   */
  public double getX() {
    return x;
  }
  /**
   * 获得y坐标
   * @return
   */
  public double getY() {
    return y;
  }
  /**
   * 设置y坐标
   * @param y
   */
  public void setY(double y) {
    this.y = y;
  }
  /**
   * 设置x坐标
   * @param x
   */
  public void setX(double x) {
    this.x = x;
  }

}
