package com.jun.plugin.algorithm.sort;

import org.junit.Test;

import com.jun.plugin.algorithm.sort.InsertionSort;

public class InsertionSortTest {
	@Test
	public void testInsertionSort() {
		int[] a = {28, 16, 32, 12, 60, 2, 5, 72};
		new InsertionSort().insertionSort(a);
		System.out.println("======插入排序测试======");
		for (int i = 0; i < a.length; ++i) {
			System.out.print(a[i] + " ");
		}
	}
}
