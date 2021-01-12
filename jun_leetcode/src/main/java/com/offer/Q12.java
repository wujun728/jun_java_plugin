package com.offer;


import org.junit.jupiter.api.Test;

/**
 * 数值的整数次方
 *
 * @author: BaoZhou
 * @date : 2020/5/28 10:17 上午
 */
public class Q12 {


    @Test
    public void result() {
        System.out.println(Power(2,-3));//111
        System.out.println(Power(2,0));//1000
    }


    public double Power(double base, int exponent) {
        if (exponent < 0) {
            base = 1 / base;
            exponent = - exponent;
        }
        double ret = 1.0 ;
        for (int i = 0; i < exponent; ++i) ret *= base;
        return ret;
    }
}

