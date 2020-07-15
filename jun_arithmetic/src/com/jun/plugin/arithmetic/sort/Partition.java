package com.jun.plugin.arithmetic.sort;

import com.jun.plugin.arithmetic.arrayrandom.MyPrinter2;
import com.jun.plugin.arithmetic.util.MathUtil;

/**
 * 划分算法<br>
 * 在很多算法都用划分算法的影子：快速排序，将数组正数和负数分开，奇数和偶数分开等<br>
 * 算法的核心思想:两根指针分别从头和尾向中间靠，直到相遇，则完成划分。
 * 
 * @author klguang
 * 
 */
public class Partition {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = { 1, 2, -1, -8, 2, -6, -4, -5, -1 };
		partition_plus_minus(arr);
	}

	public static void partition(int[] arr, int first, int end) {
		int i = first;
		int j = end;
		while (i < j) {

		}
	}

	/**
	 * 定义头尾两根指针，分别向中间靠拢。<br>
	 * 当头指针所指的元素为正plus，尾部指针
	 */
	public static void partition_plus_minus(int[] arr) {
		int size = arr.length;
		int i = 0, j = size - 1;// 定义头尾指针
		while (i < j) {
			while (arr[i] < 0 && i <= j)
				i++;
			while (arr[j] > 0 && i <= j)
				j--;
			if (i != j)// 防止指针相遇交互，当全正或全负的時候
				MathUtil.swap(arr, i, j);
			MyPrinter2.printArr(arr);
		}
	}

	public static void partition_odd_even(int[] arr) {
		
	}

}
