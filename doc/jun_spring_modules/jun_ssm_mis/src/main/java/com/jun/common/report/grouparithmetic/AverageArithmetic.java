package com.jun.common.report.grouparithmetic;

public class AverageArithmetic implements GroupArithmetic {
  public AverageArithmetic() {}

  public String getResult(double[] values) {
    double result = 0.0;

    for (int i = 0; i < values.length; i++) {
      result += values[i];
    }
    return Double.toString(result / values.length);
  }
}