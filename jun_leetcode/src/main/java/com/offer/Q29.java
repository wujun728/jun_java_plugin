package com.offer;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * 最小的K个数
 *
 * @author BaoZhou
 * @date 2020-6-10
 */

public class Q29 {
    @Test
    public void result() {
        int[] data = {4, 5, 1, 6, 2, 7, 3, 8};

        System.out.println(Arrays.toString(GetLeastNumbers_Solution(data, 5).toArray()));
    }

    public ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        ArrayList<Integer> result = new ArrayList<>();
        if (k > input.length) {
            return result;
        }
        for (int i = 0; i < k; i++) {
            for (int j = i + 1; j < input.length; j++) {
                if (input[i] > input[j]) {
                    swapChar(i, j, input);
                }
            }
        }


        for (int i = 0; i < k; i++) {
            result.add(input[i]);
        }
        return result;
    }

    private void swapChar(int i, int j, int[] str) {
        int t = str[i];
        str[i] = str[j];
        str[j] = t;
    }
}


