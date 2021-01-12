package com.offer;

import org.junit.jupiter.api.Test;


/**
 * 不用加减乘除做加法
 * @author BaoZhou
 * @date 2020-6-24
 */
public class Q48 {

    @Test
    public void result() {
        System.out.println(Add(10, 12));
    }


    public int Add(int num1, int num2) {
        while (num2 != 0) {
            //忽略进位的相加 因为相同为0，不同为1
            int temp = num1 ^ num2;
            //再计算进位的数字
            num2 = (num1 & num2) << 1;
            num1 = temp;
        }
        return num1;
    }

}
