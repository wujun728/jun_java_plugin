package com.leetcode.weekly.weekly128;

import org.junit.jupiter.api.Test;

/**
 * 十进制整数的补码
 *
 * @author BaoZhou
 * @date 2019/2/24
 */
public class BitwiseComplement {

    @Test
    public void test() {

        System.out.println(bitwiseComplement(2));

        System.out.println(bitwiseComplement(7));
        System.out.println(bitwiseComplement(10));
        System.out.println(bitwiseComplement(88));
    }

    public int bitwiseComplement(int N) {
        if (N == 0) {
            return 1;
        }

        if (N == 1) {
            return 0;
        }

        int sum = 1;
        while (sum <= N) {
            sum = sum * 2;
        }
        return sum - N - 1;
    }
}
