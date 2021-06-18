package test;

import java.util.Collections;
import java.util.LinkedList;

public class Permutation {
	public static void allPermutation(String str) {
		if (str == null || str.length() == 0)
			return;
		// 保存所有的全排列
		LinkedList listStr = new LinkedList();
		allPermutation(str.toCharArray(), listStr, 0);
		print(listStr);
		// 打印全排列
	}

	private static void allPermutation(char[] c, LinkedList listStr, int start) {
		if (start == c.length - 1)
			listStr.add(String.valueOf(c));
		// System.out.println(String.valueOf(c));
		else {
			for (int i = start; i <= c.length - 1; i++) {
				// 只有当没有重叠的字符 才交换
				if (!isSwap(c, start, i)) {
					swap(c, i, start);
					// 相当于: 固定第 i 个字符
					allPermutation(c, listStr, start + 1);
					// 求出这种情形下的所有排列
					swap(c, start, i);// 复位
				}
			}
		}
	}

	private static void swap(char[] c, int i, int j) {
		char tmp;
		tmp = c[i];
		c[i] = c[j];
		c[j] = tmp;
	}

	private static void print(LinkedList listStr) {
		Collections.sort(listStr);
		// 使字符串按照'字典顺序'输出
		for (Object str : listStr) {
			System.out.println(str);
		}
		System.out.println("size:" + listStr.size());
	}

	// [start,end) 中是否有与 c[end] 相同的字符
	private static boolean isSwap(char[] c, int start, int end) {
		for (int i = start; i < end; i++) {
			if (c[i] == c[end])
				return true;
		}
		return false;
	}

	// hapjin test
	public static void main(String[] args) {
//		allPermutation("hapjin");
		allPermutation("01");
	}
}