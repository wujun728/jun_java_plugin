package com.jun.plugin.arithmetic.sort;

import java.util.Arrays;

public class InsertionSort {
	public static void main(String[] args) {

		int[] arr = { 12, 15, 9, 20, 6, 31, 24 };
		System.out.println("原数组 ：" + Arrays.toString(arr));
		straightInsertionSort(arr);
		System.out.println(Arrays.toString(arr));
	}

	/**
	 * 直接插入排序的分步理解
	 * 
	 * @param arr
	 */

	public static void straightInsertionSort_step(int[] arr) {
		int size = arr.length;
		for (int i = 1; i < size; i++) { // arr[i]为关键码
			int destIndex = 0; // 插入的目标下标
			int key = arr[i]; // 保存关键码
			// 遍历寻找destIndex
			for (int j = i - 1; j >= 0; j--) {
				if (arr[j] <= key) { // 如果关键码>=序区元素j，则插入j之后,否则，插入0下标
					destIndex = j + 1;
					break;
				}
			}
			// 后移记录
			for (int j = i - 1; j >= destIndex; j--) {// 从前向后会覆盖
				arr[j + 1] = arr[j];
			}
			// 插入
			arr[destIndex] = key;
			System.out.print("第 " + i + "轮 ：" + Arrays.toString(arr));
			System.out.println("\tdestIndex:" + destIndex);
		}
	}

	/**
	 * 直接插入排序
	 * 
	 * @param arr
	 */
	public static void straightInsertionSort(int[] arr) {
		int size = arr.length;
		for (int i = 1; i < size; i++) { 		// 外层控制轮数
			int key = arr[i]; 			// arr[i]为关键码
			int j = i - 1;
			for (; j >= 0 && key < arr[j]; j--){	//如果关键码>=序区元素j，则插入j之
				arr[j + 1] = arr[j];		// 在寻找关键码插入位置的同时将元素后移
			}
			arr[j + 1] = key; 			// 插入目标位置
			System.out.print("第 " + i + "轮 ：" + Arrays.toString(arr));
			System.out.println("\tdestIndex:" + (j + 1));
		}
	}
	/**
	 * 	“把记录按步长 gap分组，对每组记录采用直接插入排序方法进行排序。保证整个序列基本有序.”<br>
	 * @param arr
	 */
	public static void shellSort(int[] arr) {
		int size = arr.length;
		//把下标增量为gap的记录编为一个子序列
		//增量逐步减小，直到整个序列的子序列为本身，即增量gap=1
		for (int gap = size / 2; gap >= 1; gap /= 2) {
			//对子序列进行直接插入排序
			for (int i = gap; i < size; i++) {
				int key = arr[i];
				int j = i - gap;
				for (; j >= 0 && key < arr[j]; j -= gap) {
					arr[j + gap] = arr[j];
				}
				arr[j + gap] = key;
			}
			System.out.println("gap: " + gap + " ：" + Arrays.toString(arr));
		}
	}
}
