package com.offer;

import com.leetcode.leetcodeutils.TreeNode;
import com.leetcode.leetcodeutils.TreeWrapper;
import org.junit.jupiter.api.Test;


/**
 * 数组中只出现一次的数字
 *
 * @author BaoZhou
 * @date 2020-6-18
 */

public class Q40 {
    @Test
    public void result() {
        int[] k = {1, 2, 2, 3, 3, 4, 4, 6, 6, 7};
        int[] num1 = new int[1];
        int[] num2 = new int[1];
        FindNumsAppearOnce(k, num1, num2);
        System.out.println(num1[0]);
        System.out.println(num2[0]);
    }

    public void FindNumsAppearOnce(int[] array, int num1[], int num2[]) {

        int xor1 = 0;
        for (int i = 0; i < array.length; i++)
            xor1 = xor1 ^ array[i];
        //在xor1中找到第一个不同的位对数据进行分类，分类为两个队列对数据进行异或求和找到我们想要的结果
        int index = 1;
        while ((index & xor1) == 0)
            index = index << 1;//因为可能有多个位为1所以需要求一下位置
        int result1 = 0;
        int result2 = 0;
        for (int i = 0; i < array.length; i++) {
            if ((index & array[i]) == 0)
                result1 = result1 ^ array[i];
            else
                result2 = result2 ^ array[i];
        }
        num1[0] = result1;
        num2[0] = result2;
    }
}



