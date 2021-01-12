package com.offer;

import org.junit.jupiter.api.Test;

/**
 * 整数中1出现的次数（从1到n整数中1出现的次数）
 * 思路是分别计算个位、十位、百位........上出现 1 的个数。
 * 以  n =216为例：
 * 个位上： 1 ，11，21，31，.....211。个位上共出现（216/10）+ 1个 1 。因为除法取整，210~216间个位上的1取不到，所以我们加8进位。你可能说为什么不加9，n=211怎么办，这里把最后取到的个位数为1的单独考虑，先往下看。
 * 十位上：10~19，110~119，210~216.   十位上可看成 求（216/10）=21 个位上的1的个数然后乘10。这里再次把最后取到的十位数为1的单独拿出来，即210~216要单独考虑 ，个数为（216%10）+1 .这里加8就避免了判断的过程。
 * 后面以此类推。
 * 时间复杂度 O(logN)
 * @author BaoZhou
 * @date 2020-6-11
 */

public class Q31 {
    @Test
    public void result() {
        int data = 11;
        System.out.println(NumberOf1Between1AndN_Solution(data));
    }

    public int NumberOf1Between1AndN_Solution(int n) {
        int cnt = 0;
        for (int m = 1; m <= n; m *= 10) {
            int a = n / m, b = n % m;
            cnt += (a + 8) / 10 * m + (a % 10 == 1 ? b + 1 : 0);
        }
        return cnt;
    }
}


