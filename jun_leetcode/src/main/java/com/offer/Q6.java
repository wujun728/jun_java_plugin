package com.offer;

import org.junit.jupiter.api.Test;

import java.util.Stack;

/**
 * 旋转数组的最小数字
 * @author: BaoZhou
 * @date : 2020/5/28 9:53 上午
 */
public class Q6 {
    @Test
    public void result() {
        int[] data1 = {0, 0, 0, 0, 0, 0, 0, 0};
        int[] data2 = {1, 0, 1, 1, 1};
        int[] data3 = {3, 4, 5, 1, 2, 3};
        System.out.println(minNumberInRotateArray(data1));
        System.out.println(minNumberInRotateArray(data2));
        System.out.println(minNumberInRotateArray(data3));
    }

    public int minNumberInRotateArray(int[] array) {
        if (array.length == 0) {
            return 0;
        }
        int left = 0;
        int right = array.length - 1;
        while (left < right) {
            if (array[left] < array[right]) {
                return array[left];
            }
            int mid = (left + right) / 2;
            if (array[left] < array[mid]) {
                left = mid + 1;
            } else if (array[mid] < array[right]) {
                right = mid;
            } else {
                left++;
            }
        }
        return array[left];
    }
}
