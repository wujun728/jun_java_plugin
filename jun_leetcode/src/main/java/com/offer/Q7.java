package com.offer;

import org.junit.jupiter.api.Test;


/**
 * 斐波那契数列
 * @author: BaoZhou
 * @date : 2020/5/28 9:55 上午
 */
public class Q7 {
    @Test
    public void result() {
        for (int i = 0; i < 39; i++) {
            System.out.println(Fibonacci(i));
        }

    }

    public int Fibonacci(int n) {
        int a = 0;
        int b = 1;
        int temp;
        while (n > 0) {
            temp = a + b;
            a = b;
            b = temp;
            n--;
        }
        return a;
    }
}
