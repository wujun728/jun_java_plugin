package com.rann.sort;

import static org.junit.Assert.*;

import org.junit.Test;

public class SelectSortTest {

	@Test
	public void testSelectSort() {
		int[] a = {28, 16, 32, 12, 60, 2, 5, 72};
		new SelectSort().selectSort(a);
        assertArrayEquals(a, new int[]{2,5,12,16,28,32,60,72});
	}
}
