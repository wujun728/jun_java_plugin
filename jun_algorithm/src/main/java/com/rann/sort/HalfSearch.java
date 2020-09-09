package com.rann.sort;

/**
 * Created by Lemonjing on 2015/4/18.
 * Github: Lemonjing
 * 折半查找
 */
public class HalfSearch {
    public int halfSearch(int[] a, int low, int high, int x) {
        while (low <= high) {
            int mid = (low + high) / 2;
            if (x < a[mid]) {
                high = mid - 1;
            } else if (x > a[mid]) {
                low = mid + 1;
            } else
                return mid;
        }
        return -1;
    }
}
