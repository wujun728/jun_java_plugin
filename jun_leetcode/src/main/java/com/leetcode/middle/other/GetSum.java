package com.leetcode.middle.other;

import org.junit.jupiter.api.Test;

/**
 * 两整数之和
 * @author BaoZhou
 * @date 2019/1/6
 */
public class GetSum {
    @Test
    public void test(){
        System.out.println(getSum(1, -4));
    }

    public int getSum(int a, int b) {
        //没有进位时，完成运算，a为最终和。
        if (b == 0) {
            return a;
        }
        int sum, carry;
        //没有进位的加法运算
        sum = a ^ b;
        //进位，左移运算。
        carry = (a & b) << 1;
        //递归，相加。
        return getSum(sum, carry);
    }

}
