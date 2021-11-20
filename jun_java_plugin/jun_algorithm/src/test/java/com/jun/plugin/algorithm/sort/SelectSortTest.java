package com.jun.plugin.algorithm.sort;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jun.plugin.algorithm.sort.SelectSort;

public class SelectSortTest {

	@Test
	public void testSelectSort() {
		int[] a = {28, 16, 32, 12, 60, 2, 5, 72};
		new SelectSort().selectSort(a);
        assertArrayEquals(a, new int[]{2,5,12,16,28,32,60,72});
	}
}
