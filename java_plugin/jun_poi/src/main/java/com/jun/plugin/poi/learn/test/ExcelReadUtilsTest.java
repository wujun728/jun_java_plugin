package com.jun.plugin.poi.learn.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.jun.plugin.poi.learn.ExcelReadUtils;

public class ExcelReadUtilsTest {

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		ArrayList<ArrayList<Object>> title = ExcelReadUtils.readRows("d:/test2007_2.xlsx", 0, 1);
		for (ArrayList<Object> row : title) {
			for (Object cell : row) {
				System.out.println(cell);
			}
		}

		System.out.println("======================");
		ArrayList<ArrayList<Object>> rows = ExcelReadUtils.readRows("d:/test2007_2.xlsx", 1, 10);
		int i = 0;
		for (ArrayList<Object> row : rows) {
			System.out.println(i++);
			for (Object cell : row) {
				System.out.println(cell);
			}
		}
	}
}
