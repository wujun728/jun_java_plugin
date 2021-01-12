package com.leetcode.weekly.weekly118;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 煎饼排序
 * @author BaoZhou
 * @date 2019/1/12
 */

public class PancakeSort {
    @Test
    public void test() {
        int[] nums = new int[]{3,2,4,1};

        System.out.println(pancakeSort(nums));
    }

    public List<Integer> pancakeSort(int[] A) {
        //ans记录翻转
        List<Integer> ans = new ArrayList();
        int N = A.length;

        Integer[] B = new Integer[N];
        for (int i = 0; i < N; ++i) {
            B[i] = i + 1;
        }

        //按照原数组中从大到小取数字所在的位置
        Arrays.sort(B, (i, j) -> A[j - 1] - A[i - 1]);
        for (int i : B) {
            for (int f : ans) {
                //翻转f位置的元素
                if (i <= f) {
                    //计算翻转后的位置，翻转后原来在i上的元素，会到f+1-1的位置
                    i = f + 1 - i;
                }
            }
            ans.add(i);
            ans.add(N--);
        }

        return ans;
    }
}
