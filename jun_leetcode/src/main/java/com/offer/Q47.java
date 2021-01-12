package com.offer;

import org.junit.jupiter.api.Test;



/**
 * 求1+2+3+...+n
 * @author BaoZhou
 * @date 2020-6-24
 */
public class Q47 {

    @Test
    public void result() {
        System.out.println(Sum_Solution(10));
    }


    public int Sum_Solution(int n) {
        boolean b = (n > 0) && ((n += Sum_Solution(n - 1)) > 0);
        return n;
    }

}
