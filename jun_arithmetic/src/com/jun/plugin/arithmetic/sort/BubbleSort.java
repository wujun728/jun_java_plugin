package com.jun.plugin.arithmetic.sort;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import com.jun.plugin.arithmetic.util.MathUtil;

public class BubbleSort {
	public static void main(String[] args) {
		int[] arr = { 12, 15, 9, 20, 6, 31, 24, 12 };
		System.out.println("原数组 ：" + Arrays.toString(arr));
		//selectSort(arr);
		//bubble_fake(arr);
		bubble_normal(arr);
		//bubble_optimize(arr);
		System.out.println(Arrays.toString(arr));
	}

	@Test
	public void testFake() {
		Num[] arr = { 
				new Num(12, 1), 
				new Num(15, 1), 
				new Num(9, 1), 
				new Num(20, 1),
				new Num(12, 3), 
				new Num(6, 1), 
				new Num(31, 1), 
				new Num(24, 1), 
				new Num(12, 2), 
		};
		
		int size = arr.length;
		for (int i = 0; i < size - 1; i++) {
			for (int j = i + 1; j < size; j++) {
				if (arr[j].val < arr[i].val)// 确保arr[i]为序列中 除开有序区 最小的
					MathUtil.swap(arr, i, j);
			}
		}
		System.out.println("\t" + Arrays.toString(arr));
	}

	/**
	 * 非冒泡排序，也非选择排序，姑且叫他假冒泡<br>
	 * 将序列分为两部分：有序区：无序区<br>
	 * 每轮排序从无序区找到一个最小记录,无序区长度-1<br>
	 * 
	 * 找到一个最小记录的过程：<br>
	 * 用无序区的第一个元素存储最小记录。<br>
	 * 对比交换：最小记录>无序区记录就交换<br>
	 * 纪录在排序过程中的移动不是冒泡，而是跳跃的交换。不稳定的排序<br>
	 * 
	 * 缺点：本来位于前面的较小数被交换到后面
	 * 
	 * @param arr
	 */
	public static void bubble_fake(int[] arr) {
		int size = arr.length;
		for (int i = 0; i < size - 2; i++) {//n-1轮
			for (int j = i + 1; j < size; j++) {
				if (arr[j] < arr[i])	//确保arr[i]为无序区最小的
					MathUtil.swap(arr, i, j);
			}
			System.out.println("第 " + (i+1) + "轮 ：" + Arrays.toString(arr));
		}
	}
	
	/**
	 * 将序列分为两部分：有序区：无序区 <br>
	 * 每趟从无序区 冒泡一个最小的，有序区＋1.
	 * 稳定的排序
	 * 
	 * @param arr
	 */
	public static void bubble_normal(int[] arr) {
		int size = arr.length;
		for (int i = 0; i < size - 2; i++) {//n-1轮冒泡
			for (int j = size - 1; j > i; j--) {
				if (arr[j] < arr[j - 1])// 从后往前，小的记录往前冒泡
					MathUtil.swap(arr, j, j - 1);
			}
			System.out.println("第 " + (i + 1) + "轮 ：" + Arrays.toString(arr));
		}
	}
	/**
	 * 冒泡算法的改进，增加isSwaped标志<br>
	 * 在一轮循环中记录没有交互，也就表明序列已经有序的。<br>
	 * 当记录交换（冒泡）则isSwaped=true；说明无序。<br>
	 * @param arr
	 */
	public static void bubble_optimize(int[] arr) {
		int size = arr.length;
		boolean isSwaped = true;
		for (int i = 0; i < size - 2 && isSwaped; i++) {//n-1轮
			isSwaped = false; 					// 重置状态
			for (int j = size - 1; j > i; j--) {
				if (arr[j] < arr[j - 1]) { 		// 从后往前，小的记录往前冒泡
					MathUtil.swap(arr, j, j - 1);
					isSwaped = true; 			// 改变则赋值true
				}
				System.out.println("\t" + Arrays.toString(arr));
			}
			System.out.println("isChanged:" + isSwaped);
			System.out.println("第 " + (i + 1) + "轮 ：" + Arrays.toString(arr));
		}
	}
	
	public static void bubble(int[] arr) {
		int size = arr.length;
		for (int i = 0; i < size; i++) {

		}
	}
}

class Num {
	int val;
	int flag;

	public Num(int val, int flag) {
		this.val = val;
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "[val=" + val + ", flag=" + flag + "]";
	}
}
