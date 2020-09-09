package com.rann.sort;

/**
 * 冒泡排序 - 稳定
 * 复杂度：O(n^2) - O(n) - O(n^2) - O(1)[平均 - 最好 - 最坏 - 空间复杂度]
 */
public class BubbleSort {
    public void bubbleSort(int[] a) {
        if (null == a || a.length < 2) {
            return;
        }
        boolean flag;
        for (int i = 0; i < a.length - 1; i++) {
            flag = false;
            for (int j = 0; j < a.length - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                    flag = true;
                }
            }
            if (false == flag) {
                return;
            }
        }
    }
}
