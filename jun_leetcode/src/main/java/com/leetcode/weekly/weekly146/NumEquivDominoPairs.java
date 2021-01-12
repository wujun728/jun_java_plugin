package com.leetcode.weekly.weekly146;

import org.junit.jupiter.api.Test;

/**
 * 等价多米诺骨牌对的数量
 *
 * @author BaoZhou
 * @date 2019/7/22 9:40
 */
public class NumEquivDominoPairs {
    @Test
    public void test() {
        //int[][] nums = new int[][]{{3, 2}, {4, 1}, {2, 1}, {4, 9}};
        int[][] nums = new int[][]{{3, 2}, {2, 3}, {2, 1}, {4, 9}};

        System.out.println(numEquivDominoPairs(nums));
    }

    public int numEquivDominoPairs(int[][] dominoes) {
        int result = 0;
        int[] list = new int[100];
        int k = 0;
        for (int i = 0; i < dominoes.length; i++) {
            if (dominoes[i][0] == -1) {
                continue;
            }
            else{
                for (int j = i + 1; j < dominoes.length; j++) {
                    if (checkEqual(dominoes[i], dominoes[j])) {
                        list[k]++;
                        dominoes[j][0] = -1;
                    }
                }
                k++;
            }
        }
        for (int i = 0; i < list.length; i++) {
            if(list[i]==0){
                continue;
            }
            else {
                result+=getNum(list[i]+1);
            }
        }
        return result;
    }

    public boolean checkEqual(int[] a, int[] b) {
        if ((a[1] == b[1] && (a[0] == b[0])) || (a[1] == b[0] && (a[0] == b[1]))) {
            return true;
        } else {
            return false;
        }
    }

    public int getNum(int n) {
        return n * (n - 1) / 2;
    }
}
