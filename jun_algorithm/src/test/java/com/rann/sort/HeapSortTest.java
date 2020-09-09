package com.rann.sort;

import org.junit.Test;

public class HeapSortTest {
	@Test
	public void testHeapSort() {
		int[] a = {28, 16, 32, 12, 60, 2, 5, 72};
		new HeapSort().heapSort(a);
		System.out.println("======堆排序排序测试======");
		for (int i = 0; i < a.length; ++i) {
			System.out.print(a[i] + " ");
		}
	}
}
