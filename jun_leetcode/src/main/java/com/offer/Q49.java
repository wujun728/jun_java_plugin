package com.offer;

import org.junit.jupiter.api.Test;


/**
 * 不用加减乘除做加法
 *
 * @author BaoZhou
 * @date 2020-6-24
 */
public class Q49 {

    @Test
    public void result() {
        int[] data = {1, 2, 3, 3, 4, 5, 5};
        int[] duplication = new int[1];
        duplicate(data, 7, duplication);
        System.out.println(duplication[0]);
    }

    public boolean duplicate(int numbers[], int length, int[] duplication) {
        if (numbers == null || length <= 1) {
            return false;
        }

        for (int i = 0; i < numbers.length; i++) {
            while (numbers[i] != i) {
                if (numbers[i] == numbers[numbers[i]]) {
                    duplication[0] = numbers[i];
                    return true;
                }
                // swap
                int tmp = numbers[i];
                numbers[i] = numbers[tmp];
                numbers[tmp] = tmp;
            }
        }
        return false;
    }


}
