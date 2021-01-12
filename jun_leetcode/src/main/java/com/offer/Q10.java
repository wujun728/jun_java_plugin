package com.offer;


import org.junit.jupiter.api.Test;

/**
 * 矩形覆盖
 * @author BaoZhou
 * @date 2020-5-28
 */

public class Q10 {


    @Test
    public void result() {
        System.out.println(RectCover(1));
        System.out.println(RectCover(2));
        System.out.println(RectCover(3));
        System.out.println(RectCover(4));
    }


    public int RectCover(int target) {
        int a1 = 1;
        int a2 = 2;
        int next = 0;
        if (target == 1) {
            return 1;
        }
        if (target == 2) {
            return 2;
        }
        for (int i = 0; i < target - 2; i++) {
            next = a1 + a2;
            a1 = a2;
            a2 = next;
        }
        return next;
    }
}
