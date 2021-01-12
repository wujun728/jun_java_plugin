package com.leetcode.weekly.weekly118;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 强整数
 * @author BaoZhou
 * @date 2019/1/6
 */
public class PowerfulIntegers {
    @Test
    public void test() {
        System.out.println(powerfulIntegers(1, 2, 1000));
    }

    public List<Integer> powerfulIntegers(int x, int y, int bound) {
        List<Integer> list = new ArrayList<>();
        backtracking(list, x, 1, y, 1, bound);
        return list;
    }

    public void backtracking(List<Integer> list, int x, int xResult, int y, int yResult, int bound) {
        if (xResult > bound || yResult > bound) {
            return;
        }
        int result = xResult + yResult;
        if (result <= bound) {
            if(!list.contains(result)) {
                list.add(result);
            }
        } else {
            return;
        }
        if(x!=0 && x!=1){
            backtracking(list, x, xResult * x, y, yResult, bound);
        }
        if(y!=0&& y!=1){
            backtracking(list, x, xResult, y, yResult * y, bound);
        }
    }


}
