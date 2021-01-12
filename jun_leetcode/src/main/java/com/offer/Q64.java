package com.offer;


import org.junit.jupiter.api.Test;

import java.util.*;


/**
 * 滑动窗口的最大值
 *
 * @author BaoZhou
 * @date 2020-6-27
 */
public class Q64 {

    @Test
    public void result() {
        int[] data = {2, 3, 4, 2, 6, 2, 5, 1};
        System.out.println(Arrays.toString((maxInWindows(data, 3)).toArray()));
    }

    public ArrayList<Integer> maxInWindows(int[] num, int size) {
        ArrayList<Integer> res = new ArrayList<>();
        if (size < 1 || num.length == 0) return res;
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < num.length; i++) {
            while (!list.isEmpty() && num[list.peekLast()] < num[i]) {
                list.pollLast();
            }
            list.add(i);
            //判断左边是否失效
            if (list.peekFirst() <= i - size) {
                list.pollFirst();
            }
            if (i >= size - 1)
                res.add(num[list.peekFirst()]);


        }
        return res;
    }
}
