package com.leetcode.primary.mathproblem;

import org.junit.jupiter.api.Test;

/**
 * 阶乘后的零
 * 计算含有五的因子
 *
 * @author BaoZhou
 * @date 2019/1/11
 */
public class TrailingZeroes {

    @Test
    void test() {
        System.out.println(trailingZeroes(3175));
    }

    public int trailingZeroes(int n) {
        int count = 0;
        for (int i = 1; n >= Math.pow(5, i); i++) {
            //含有多个因数 5 的数字将叠加多次
            count = count + (int) (n / Math.pow(5, i));
        }
        return count;
    }
}
