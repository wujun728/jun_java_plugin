package com.offer;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * 为S的连续正数序列
 * @author BaoZhou
 * @date 2020-6-19
 */

public class Q41 {
    @Test
    public void result() {
        System.out.println(FindContinuousSequence(100));
    }

    public ArrayList<ArrayList<Integer>> FindContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer>> ret = new ArrayList<>();
        int l = 1, r = 1;
        int tmp = 0;
        while (l <= sum / 2) {
            if (tmp < sum) {
                tmp += r;
                ++r;
            } else if (tmp > sum) {
                tmp -= l;
                ++l;
            } else {
                ArrayList<Integer> ans = new ArrayList<>();
                for (int k = l; k < r; ++k)
                    ans.add(k);
                ret.add(ans);
                tmp -= l;
                ++l;
            }
        }
        return ret;
    }
}



