package com.jun.common.report.grouparithmetic;


public class CountArithmetic implements GroupArithmetic {
  public CountArithmetic() {}

  public String getResult(double[] values) {
      return Integer.toString(values.length);
  }
}