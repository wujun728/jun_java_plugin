package com.offer;

import org.junit.jupiter.api.Test;

/**
 * 跳台阶
 * @author: BaoZhou
 * @date : 2020/5/28 9:55 上午
 */
public class Q8 {
    @Test
    public void result() {
        System.out.println(JumpFloor(1));
        System.out.println(JumpFloor(2));
        System.out.println(JumpFloor(3));
        System.out.println(JumpFloor(4));

    }

    public int JumpFloor(int target) {
        int a1 = 1;
        int a2 = 2;
        int result = 0;
        if (target == 0) {
            return 0;
        }
        if (target == 1) {
            return a1;
        }
        if (target == 2) {
            return a2;
        }
        for (int i = 0; i < target - 2; i++) {
            result = a1 + a2;
            a1 = a2;
            a2 = result;
        }
        return result;

    }
}
