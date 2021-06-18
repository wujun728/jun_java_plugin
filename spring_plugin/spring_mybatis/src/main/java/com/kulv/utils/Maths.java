package com.kulv.utils;

import java.math.BigDecimal;
/**
 * Double类型的相加，相减，相乘
 * 黄依蔓
 * @author Wujun
 *
 */
public class Maths {
	//double类型相加
	public static Double add(Double x, Double y){ 
		BigDecimal add1 = new BigDecimal(Double.toString(x));
		BigDecimal add2 = new BigDecimal(Double.toString(y));
		return  add1.add(add2).doubleValue() ;
	}
	//double类型相减
	public static Double sub(Double v1,Double v2){
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.subtract(b2).doubleValue();
    }
	//double类型相乘
	public static Double multiply(Double v1,Double v2){
		BigDecimal aa = new BigDecimal(v1.toString());
	    BigDecimal bb = new BigDecimal(v2.toString());
	    return aa.multiply(bb).doubleValue();
    }
}
