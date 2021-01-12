package com.leetcode.primary.sort;

/**
 * 第一个错误的版本
 * 标准二分查找，需要小心n较大时溢出
 * @author BaoZhou
 * @date 2018/12/17
 */
public class FirstBadVersion extends VersionControl {
    public static void main(String[] args) {
        System.out.println(firstBadVersion(8));
    }


    public static int firstBadVersion(int n) {
        if (isBadVersion(1)) {
            return 1;
        }
        int left = 1;
        int right = n;
        while (left < right) {
            int mid = left/2 + right/2;
            if (isBadVersion(mid)) {
                    right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }


}
