package com.leetcode.weekly.weekly122;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 查询后的偶数和
 *
 * @author BaoZhou
 * @date 2019/1/27
 */
public class SumEvenAfterQueries {

    @Test
    public void test() {
        int[] A = {1, 2, 3, 4};
        int[][] queries = {{1, 0}, {-3, 1}, {-4, 0}, {2, 3}};
        System.out.println(Arrays.toString(sumEvenAfterQueries(A, queries)));
    }

    public int[] sumEvenAfterQueries(int[] A, int[][] queries) {
        int[] result = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            A[queries[i][1]] += queries[i][0];
            for (int i1 = 0; i1 < A.length; i1++) {
                result[i] += A[i1] % 2 == 0 ? A[i1] : 0;
            }
        }
        return result;
    }
}
