package com.offer;
import org.junit.jupiter.api.Test;

/**
 * 连续子数组的最大和
 *
 * @author BaoZhou
 * @date 2020-6-10
 */

public class Q30 {
    @Test
    public void result() {
        int[] data = {6, -3, -2, 7, -15, 1, 2, 2};

        System.out.println(FindGreatestSumOfSubArray(data));
    }

    public int FindGreatestSumOfSubArray(int[] array) {
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            array[i] += Math.max(array[i - 1], 0);
            max = Math.max(max, array[i]);
        }
        return max;
    }
}


