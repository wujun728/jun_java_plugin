package com.rann.sort;

import org.junit.Test;

public class RadixSortTest {
	@Test
	public void testRadixSort() {
		int[] a = {28, 16, 321, 121, 60, 2, 5, 72};
		new RadixSort().radixSort(a, 0, a.length-1, 3);
		System.out.println("======基数排序测试======");
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + " ");
		}
	}
}
