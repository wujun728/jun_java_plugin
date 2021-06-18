package org.typroject.tyboot.core.foundation.utils;

import java.math.BigDecimal;

public class CalculateUtil {
	
	public static final String add = "+";
	public static final String minus = "-";
	public static final String mul = "*";
	public static final String div = "/";
	
	
	/**
	 * 使用进行BigDecimal计算
	 * 除法默认保留两位四舍五入的算法
	 */
	public static Double getArith(Double a,Double b,String operatorType){
		BigDecimal a1 = new BigDecimal(Double.toString(a));
		BigDecimal b1 = new BigDecimal(Double.toString(b));
		if(operatorType.equals(add)){
			return a1.add(b1).doubleValue();
		}else if(operatorType.equals(minus)){
			return a1.subtract(b1).doubleValue();
		}else if(operatorType.equals(mul)){
			return a1.multiply(b1).doubleValue();
		}else if(operatorType.equals(div)){
			return a1.divide(b1,2,BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return null;
	}
	
	/**
	 * 使用进行BigDecimal计算
	 * 除法默认保留两位四舍五入的算法
	 */
	public static Double getArith(String a,String b,String operatorType){
		BigDecimal a1 = new BigDecimal(a);
		BigDecimal b1 = new BigDecimal(b);
		if(operatorType.equals(add)){
			return a1.add(b1).doubleValue();
		}else if(operatorType.equals(minus)){
			return a1.subtract(b1).doubleValue();
		}else if(operatorType.equals(mul)){
			return a1.multiply(b1).doubleValue();
		}else if(operatorType.equals(div)){
			return a1.divide(b1,2,BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return null;
	}
	
	
	/**
	 * 除法计算
	 */
	public static Double getDIVArith(String a,String b,int roundingMode,int scale){
		BigDecimal a1 = new BigDecimal(a);
		BigDecimal b1 = new BigDecimal(b);
		return a1.divide(b1,scale,roundingMode).doubleValue();
	}
}
