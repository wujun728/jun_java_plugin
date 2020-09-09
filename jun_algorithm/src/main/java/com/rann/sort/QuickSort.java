package com.rann.sort;

import java.util.Stack;

/**
 * 快速排序 - 不稳定
 * 复杂度：O(nlgn) - O(nlgn) - O(n^2) - O(1)[平均 - 最好 - 最坏 - 空间复杂度]
 * 栈空间 O(lgn) - O(n)
 */
public class QuickSort {
    // 递归，固定基准
    public void quickSort(int[] a, int low, int high) {
        if (null == a || a.length < 2) {
            return;
        }
        if (low < high) {
            int mid = partition(a, low, high);
            quickSort(a, low, mid - 1);
            quickSort(a, mid + 1, high);
        }
    }

    // 非递归，固定基准
    public void quickSortNonRecur(int[] a, int low, int high) {
        if (null == a || a.length < 2) {
            return;
        }
        Stack<Integer> stack = new Stack<>();
        stack.push(low);
        stack.push(high);
        while (!stack.isEmpty()) {
            high = stack.pop();
            low = stack.pop();
            if (low < high) {
                int mid = partition(a, low, high);
                stack.push(low);
                stack.push(mid - 1);
                stack.push(mid + 1);
                stack.push(high);
            }
        }
    }

    private int partition(int[] a, int low, int high) {
        int pivot = a[low];

        while (low < high) {
            // 注意等于，否则当全部待排序数字都相同的时候会死循环
            while (low < high && a[high] >= pivot) {
                high--;
            }
            a[low] = a[high];
            // 注意等于，否则当全部待排序数字都相同的时候会死循环
            while (low < high && a[low] <= pivot) {
                low++;
            }
            a[high] = a[low];
        }
        a[low] = pivot;

        return low;
    }
}
