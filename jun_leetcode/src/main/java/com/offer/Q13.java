package com.offer;


import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 调整数组顺序使奇数位于偶数前面
 * 冒泡的思路
 * @author: BaoZhou
 * @date : 2020/5/28 10:17 上午
 */
public class Q13 {


    @Test
    public void result() {
        int[] data = {1, 2, 3, 4, 5, 6,7};//1,3,5,2,4,6
        int[] dat2 = {1, 2, 1, 1, 1, 1};//1,3,5,2,4,6
        reOrderArray(data);
        System.out.println(Arrays.toString(data));//111
    }


    public void reOrderArray(int[] array) {
        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i] % 2 == 0) {
                for (int j = i; j < array.length; j++) {
                    if (array[j] % 2 == 1) {
                        int t = array[i];
                        array[i] = array[j];
                        array[j] = t;
                        i = j;
                    }
                }
            }
        }
    }
}

