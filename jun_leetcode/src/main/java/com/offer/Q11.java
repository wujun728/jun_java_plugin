package com.offer;


import org.junit.jupiter.api.Test;

/**
 * 二进制中1的个数
 * @author: BaoZhou
 * @date : 2020/5/28 10:17 上午
 */
public class Q11 {


    @Test
    public void result() {
        System.out.println(NumberOf1(7));//111
        System.out.println(NumberOf1(8));//1000
        System.out.println(NumberOf1(13));//1101
        System.out.println(NumberOf1(16));//10000
        System.out.println(NumberOf1(-1));//10000
    }


    public int NumberOf1(int n) {
        int count = 0;
        while(n!=0){
            count++;
            n=n&(n-1);
        }
        return count;
    }
}
