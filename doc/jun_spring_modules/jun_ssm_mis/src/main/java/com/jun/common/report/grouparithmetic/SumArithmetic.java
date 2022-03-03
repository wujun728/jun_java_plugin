package com.jun.common.report.grouparithmetic;

public class SumArithmetic implements GroupArithmetic {
	private boolean isInt = false;

	public SumArithmetic() {
	}

	public SumArithmetic(boolean isInt) {
		this.isInt = isInt;
	}

	public String getResult(double[] values) {
		double result = 0.0;
		if(values!=null)
			for (int i = 0; i < values.length; i++) 
			result += values[i];
		
		if (isInt) {
			return Long.toString(new Double(result).longValue());
		} else
			return Double.toString(result);
	}
}