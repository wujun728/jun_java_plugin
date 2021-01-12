package com.offer;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;


/**
 * 和为S的连续正数序列
 *
 * @author BaoZhou
 * @date 2020-6-19
 */

public class Q42 {
    @Test
    public void result() {
        int[] data = {1, 2, 4, 7, 11, 15};
        System.out.println(FindNumbersWithSum(data, 15));
    }

    public ArrayList<Integer> FindNumbersWithSum(int[] array, int sum) {
        ArrayList<Integer> result = new ArrayList<>();
        if (array.length == 0) return result;
        int tmp = Integer.MAX_VALUE;
        int num1 = 0, num2 = 0;
        int i = 0, j = array.length;
        while (i < j) {
            if (array[i] + array[j - 1] == sum) {
                if (array[i] * array[j - 1] < tmp) {
                    tmp = array[i] * array[j - 1];
                    num1 = array[i];
                    num2 = array[j - 1];
                }
                ++i;
                --j;
            } else if (array[i] + array[j - 1] < sum) {
                ++i;
            } else {
                --j;
            }
        }
        if(num1 == num2) return result;
        result.add(num1);
        result.add(num2);
        return result;
    }
}



