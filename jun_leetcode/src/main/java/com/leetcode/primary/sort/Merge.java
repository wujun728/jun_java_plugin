package com.leetcode.primary.sort;

/**
 * 合并两个有序数组
 *
 * @author BaoZhou
 * @date 2018/12/17
 */
public class Merge {

    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int m = 3;
        int[] nums2 = {2, 5, 6};
        int n = 3;
        merge(nums1, m, nums2, n);

        for (int i = 0; i < nums1.length; i++) {
            System.out.println(nums1[i]);
        }
    }


    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        //找到最后一个位置
        int p = m-- + n-- - 1;
        while (m >= 0 && n >= 0) {
            //取两个数组中比较大的那个，然后移动指针
            nums1[p--] = nums1[m] > nums2[n] ? nums1[m--] : nums2[n--];
        }
        //数组1没有元素了，就把数组2元素都塞进去
        while (n >= 0) {
            nums1[p--] = nums2[n--];
        }
    }
}
