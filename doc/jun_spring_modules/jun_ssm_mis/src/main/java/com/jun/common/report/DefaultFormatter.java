package com.jun.common.report;

import java.text.*;


public class DefaultFormatter implements Formatter {
  private DecimalFormat f = new DecimalFormat();
  public DefaultFormatter(int maxDigit,int minDigit) {
    //应进行4舍5入

    //确定小数位数
    f.setMaximumFractionDigits(maxDigit);
    f.setMinimumFractionDigits(minDigit);
  }


  public String format(String str) throws ParseException {
    double temp = f.parse(str).doubleValue();

    return f.format(temp);

  }
}