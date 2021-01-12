package com.offer;


import org.junit.jupiter.api.Test;

/**
 * 变态跳台阶
 * @author BaoZhou
 * @date 2020-5-28
 */

public class Q9 {


    @Test
    public void result() {
        System.out.println(JumpFloorII(1));
        System.out.println(JumpFloorII(2));
        System.out.println(JumpFloorII(4));
    }


    public int JumpFloorII(int target) {
        int result =1;
        for (int i = 0; i < target-1; i++) {
            result*=2;
        }
        return result;
    }
}
