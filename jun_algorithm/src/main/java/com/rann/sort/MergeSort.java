package com.rann.sort;

/**
 * 归并排序(申请一个临时数组) - 稳定
 * 复杂度：O(nlogn) - O(nlgn) - O(nlgn) - O(n)[平均 - 最好 - 最坏 - 空间复杂度]
 */
public class MergeSort {
    // 排序
    public void mergeSort(int[] a, int low, int high) {
        if (null == a || a.length < 2) {
            return;
        }
        int mid = (low + high) / 2;
        if (low < high) {
            // 左边排序
            mergeSort(a, low, mid);
            // 右边排序
            mergeSort(a, mid + 1, high);
            // 有序序列合并
            merge(a, low, mid, high);
        }
    }

    // 合并
    private void merge(int a[], int low, int mid, int high) {
        // 临时数组
        int[] temp = new int[high - low + 1];
        // 左指针
        int i = low;
        // 右指针
        int j = mid + 1;
        // 临时数组索引
        int k = 0;

        while (i <= mid && j <= high) {
            if (a[i] < a[j]) {
                temp[k++] = a[i++];
            } else {
                temp[k++] = a[j++];
            }
        }

        // 把左边剩余的数移入数组
        while (i <= mid) {
            temp[k++] = a[i++];
        }

        // 把右边剩余的数移入数组
        while (j <= high) {
            temp[k++] = a[j++];
        }

        // 注意这里是low + t
        for (int t = 0; t < temp.length; t++) {
            a[low + t] = temp[t];
        }
    }
}
