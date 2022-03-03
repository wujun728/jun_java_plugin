package com.jun.common.report.grouparithmetic;

import java.util.Arrays;

import com.jun.common.report.ReportException;

public class MinArithmetic implements GroupArithmetic {
	public MinArithmetic() {}

	public String getResult(double[] values) throws ReportException {
		if (values == null) {
			throw new ReportException("values can not be null.");
		}
		double[] temp = (double[]) values.clone();
		Arrays.sort(temp);
		return Double.toString(temp[0]);
	}
}