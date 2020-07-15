package com.jun.plugin.arithmetic.util;

import java.util.Arrays;

public class MathUtil {
	public static <T> void swap(T[] arr, int i1, int i2) {
		T temp = arr[i1];
		arr[i1] = arr[i2];
		arr[i2] = temp;
	}

	public static void swap(int[] arr, int i1, int i2) {
		int temp = arr[i1];
		arr[i1] = arr[i2];
		arr[i2] = temp;
	}

	public static void printRange(int[] arr, int first, int last) {
		int[] temp = new int[last - first + 1];
		for (int i = 0; first + i <= last; i++) {
			temp[i] = arr[first + i];
		}
		System.out.println(Arrays.toString(temp));
	}

}
