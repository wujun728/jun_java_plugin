package com.rann.sort;

import org.junit.Test;

public class MergeSortTest {
	@Test
	public void testMergeSort() {
		int[] a = {28, 16, 32, 12, 60, 2, 5, 72};
		new MergeSort().mergeSort(a, 0, a.length-1);
		System.out.println("======归并排序测试======");
		for (int i = 0; i < a.length; ++i) {
			System.out.print(a[i] + " ");
		}
	}
}
