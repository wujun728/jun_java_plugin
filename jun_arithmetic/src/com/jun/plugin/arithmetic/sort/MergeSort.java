package com.jun.plugin.arithmetic.sort;

import com.jun.plugin.arithmetic.arrayrandom.MyPrinter2;
import com.jun.plugin.arithmetic.util.MathUtil;

public class MergeSort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr0={1,3,5,6,7};
		int[] arr1={2,4};
		int[] arr = { 1 };
		int[] arr2 = { 5, 6, -1, 4, 2, 80, -20, -1 };
		MyPrinter2.printArr(mergeArray(arr1, arr0));
//		msort(arr2);
//		MyPrinter2.printArr(arr2);
		// MathUtil.printRange(arr2, 2, 3);
		// MyPrinter2.printArr(merge(arr2, new int[10], 0, 1, 2));
	}

	/**
	 * 将两个数组合并,每次往目标数组中放一个元素,谁元素小就放谁<br>
	 * 直到一个数组全部放入目标数组，对另一个进行收尾工作。<br>
	 * 
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	public static int[] mergeArray(int[] arr1, int[] arr2) {
		int size1 = arr1.length;
		int size2 = arr2.length;
		int[] temp = new int[size1 + size2];//目标数组
		int i = 0, j = 0, k = 0;
		while (i < size1 && j < size2) {// 直到一个数组全部放入目标数组
			if (arr1[i] < arr2[j])
				temp[k++] = arr1[i++];
			else
				temp[k++] = arr2[j++];
		}
		MyPrinter2.printArr(temp);
		// 收尾工作
		while (i < size1)
			temp[k++] = arr1[i++];
		while (j < size2)
			temp[k++] = arr2[j++];
		MyPrinter2.printArr(temp);
		return temp;
	}

	/**
	 * 将两个相邻的有序区间source[first~mid],source[mid+1~last]合并到temp数组中<br>
	 * 
	 * @return
	 */
	public static void merge(int[] source, int[] temp, int first, int mid, int last) {
		int i = first, j = mid + 1;
		int k = 0;
		while (i <= mid && j <= last) {// 直到一个数组全部放入目标数组
			if (source[i] < source[j])
				temp[k++] = source[i++];
			else
				temp[k++] = source[j++];
		}
		// 收尾工作
		while (i <= mid)
			temp[k++] = source[i++];
		while (j <= last)
			temp[k++] = source[j++];
		// 将归并结果放入原数组中。
		for (i = 0; i < k; i++)
			source[first + i] = temp[i];
	}
	//递归过程是：在递进的过程中拆分数组，在回归的过程合并数组
	public static void mergeSort(int[] source, int[] temp, int first, int last) {
		if (first < last) {
			int mid = (first + last) / 2;
			mergeSort(source, temp, first, mid);	//归并排序前半个子序列
			mergeSort(source, temp, mid + 1, last); //归并排序后半个子序列
			merge(source, temp, first, mid, last);	//在回归过程中合并
		} else if (first == last) {					//待排序列只有一个，递归结束
			temp[first] = source[first];
		}
	}

	public static void msort(int[] arr) {
		int size = arr.length;
		int[] temp = new int[size];
		mergeSort(arr, temp, 0, size - 1);
		MyPrinter2.printArr(temp);//temp与arr一样，都已排序好。
	}
}
