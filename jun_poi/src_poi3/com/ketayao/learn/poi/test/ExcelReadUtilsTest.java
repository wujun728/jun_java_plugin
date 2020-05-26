package com.ketayao.learn.poi.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.ketayao.learn.poi.ExcelReadUtils;

/**
 * @author <a href="mailto:ketayao@gmail.com">ketayao</a>
 * @since 2013年9月29日 下午3:56:48
 */
public class ExcelReadUtilsTest {

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		ArrayList<ArrayList<Object>> title = ExcelReadUtils.readRows("d:/test2007.xlsx", 0, 1);
		for (ArrayList<Object> row : title) {
			for (Object cell : row) {
				System.out.println(cell);
			}
		}

		System.out.println("======================");
		ArrayList<ArrayList<Object>> rows = ExcelReadUtils.readRows("d:/test2007.xlsx", 1, 10);
		int i = 0;
		for (ArrayList<Object> row : rows) {
			System.out.println(i++);
			for (Object cell : row) {
				System.out.println(cell);
			}
		}
	}
}
