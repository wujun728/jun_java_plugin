package com.leetcode.primary.mathproblem;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 欢乐数
 *
 * @author BaoZhou
 * @date 2019/1/11
 */
public class HappyNum {

    @Test
    void test() {
        System.out.println(isHappy(4));
    }

    public boolean isHappy(int n) {
        List<Integer> list = new ArrayList<>();
        int currNum = n;
        while (!list.contains(currNum)) {
            list.add(currNum);
            n = list.get(list.size() - 1);
            currNum = 0;
            while (n != 0) {
                int t = n % 10;
                currNum += t * t;
                n /= 10;
            }
            if (currNum == 1) {
                return true;
            }
        }
        return false;
    }
}
