package com.jun.plugin.algorithm.array;
import org.junit.Test;

/**
 * leetCode 189：Rotate Array
 给定一个数组，长度为n，要求把后k个元素移动至前面，前n-k个元素移动至后面。
 */
public class _189RotateArray {
    public void swap(int[] array, int a, int b) {
        int t = array[a];
        array[a] = array[b];
        array[b] = t;
    }

    /**
     * 逆置数组的start到end部分
     */
    public void reverse(int[] array, int start, int end) {
        if (array == null || array.length <= 1) {
            return;
        }
        for (int i = start, j = end; i < j; i++, j--) {
            swap(array, i, j);
        }
    }

    /**
     * 旋转数组
     */
    public void rotate(int[] nums, int k) {
        if (k == 0) {
            return;
        }
        int n = nums.length;
        if (k > n) {
            k = k % n;
        }
        reverse(nums, 0, n - 1 - k);
        reverse(nums, n - k, n - 1);
        reverse(nums, 0, n - 1);
    }

	public void printArray(int[] array) {
		for (int i : array) {
			System.out.print(i + " ");
		}
		System.out.println();
	}

    @Test
    public void test() {
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        printArray(nums);
        rotate(nums, 4);
        printArray(nums);
    }
}
