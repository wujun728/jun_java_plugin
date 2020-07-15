package com.jun.plugin.arithmetic.sort;

import java.util.Arrays;
import java.util.Collections;

import com.jun.plugin.arithmetic.util.MathUtil;

public class SelectSort {
	public static void main(String[] args) {
		int[] arr = { 12, 15, 9, 20, 6, 31, 24, 12 };
		System.out.println("原数组 ：" + Arrays.toString(arr));
		selectSort(arr);
		
		System.out.println(Arrays.toString(arr));

	}
	/**
	 * 正规选择排序：<br>
	 * 将序列分为两部分：有序区：无序区<br>
	 * 每轮排序从无序区找到一个最小记录,无序区长度-1<br>
	 * 
	 * 找到一个最小记录的过程：<br>
	 * 用minIndex存储最小记录的下标，初始就是无序区第一个记录<br>
	 * 对比:arr[minIndex](最小记录)>无序区记录j，minIndex=j;<br>
	 * 与假冒泡算法比较，仅仅使用了一个minIndex来存储最小记录的下标，从而减少了交互。不稳定的排序<br>
	 * 
	 * @param arr
	 */
	public static void selectSort(int[] arr){
		int size = arr.length;
		for (int i = 0; i < size - 2; i++) {//n-1轮选择
			int minIndex=i;
			for (int j = i + 1; j < size; j++) {
				if (arr[j] < arr[minIndex])// 在无序区中选择最小记录
					minIndex=j;
			}
			if(minIndex!=i)
				MathUtil.swap(arr, minIndex, i);
			System.out.println("minIndex:"+minIndex +"\t min:"+arr[minIndex]);
			System.out.println("第 " + (i+1) + "轮 ：" + Arrays.toString(arr));
		}
	}
}
