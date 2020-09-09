package com.rann.sort;

/**
 * 插入排序 - 稳定
 * 复杂度：O(n^2) - O(n) - O(n^2) - O(1)[平均 - 最好 - 最坏 - 空间复杂度]
 */
public class InsertionSort {
    public void insertionSort(int[] a) {
        if (null == a || a.length < 2) {
            return;
        }
        for (int i = 1; i < a.length; i++) {
            // 暂存当前值
            int temp = a[i];
            int j = i - 1;
            while (j >= 0 && temp < a[j]) {
                // 后移
                a[j + 1] = a[j];
                j--;
            }
            // 当前值归位
            a[j + 1] = temp;
        }
    }
}
