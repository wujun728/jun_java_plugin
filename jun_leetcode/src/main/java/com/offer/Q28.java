package com.offer;


import org.junit.jupiter.api.Test;

import java.util.*;


/**
 * 数组中出现次数超过一半的数字
 *
 * @author BaoZhou
 * @date 2020-6-9
 */

public class Q28 {
    @Test
    public void result() {
        int[] data = {2, 2, 3, 5, 4};

        System.out.println(MoreThanHalfNum_Solution(data));
    }

    public int MoreThanHalfNum_Solution(int[] array) {
        int cond = -1;
        int cnt = 0;
        for (int i = 0; i < array.length; ++i) {
            if (cnt == 0) {
                cond = array[i];
                ++cnt;
            } else {
                if (cond == array[i]) ++cnt;
                else --cnt;
            }

        }
        cnt = 0;
        for (int k : array) {
            if (cond == k) ++cnt;
        }
        if (cnt > array.length / 2) return cond;
        return 0;
    }

}


