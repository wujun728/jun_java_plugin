package com.jun.plugin.arithmetic.sort;

import java.util.Arrays;

import com.jun.plugin.arithmetic.util.MathUtil;

public class QuickSort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = { 12, 15, 9, 20, 6, 31, 24, 12 };
		System.out.println("原数组 ：" + Arrays.toString(arr));
		qsort(arr);
		System.out.println(Arrays.toString(arr));
	}
	/**
	 * 划分区间:arr[first]~arr[end]<br>
	 * 右侧扫描:直到 尾指针 指向的记录 小于 轴值，交互，头指针+1。<br>
	 * 左侧扫描:直到 头指针 指向的记录 大于 轴值，交互，尾指针-1。<br>
	 * 头尾两根指针相遇，完成划分。<br>
	 * 
	 * @param arr
	 * @param first 划分区间的头指针
	 * @param end 划分区间的头指针
	 * @return
	 */
	public static int partition(int[] arr, int first, int end) {
		while (first < end) {//头尾指针相遇，退出循环,即为最终的轴值记录的位置
			while (first < end && arr[first] < arr[end])// 右侧扫描
				end--;
			if (first < end) {
				MathUtil.swap(arr, first, end);// 较小记录交互到前面
				first++;
			}
			//具有操作的对称性
			while (first < end && arr[first] < arr[end])// 左侧扫描
				first++;
			if (first < end) {
				MathUtil.swap(arr, first, end);// 较大记录交互到后面
				end--;
			}
		}
		return first;
	}
	/**
	 * 将序列一次划分后，分别对两个子序列进行划分（递归处理），直到划分区间<1 <br>
	 * @param arr
	 * @param first
	 * @param end
	 */
	public static void quickSort(int[] arr,int first, int end){
		if(first<end){//区间长度<1，递归结束
			int pivot=partition(arr, first, end);
			quickSort(arr, first, pivot - 1);//递归对左侧子序列进行快排
			quickSort(arr, pivot + 1, end);  //递归对右侧子序列进行快排
		}
	}
	
	public static void qsort(int[] arr){
		quickSort(arr, 0, arr.length-1);
	}
	

}
