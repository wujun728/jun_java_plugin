package com.offer;

import org.junit.jupiter.api.Test;

/**
 * 把数组排成最小的数
 *
 * @author BaoZhou
 * @date 2020-6-11
 */

public class Q32 {
    @Test
    public void result() {
        int data[] = {3, 32, 321};
        int data2[] = {3, 32, 3299999};
        System.out.println(PrintMinNumber(data));
        System.out.println(PrintMinNumber(data2));
    }

    public String PrintMinNumber(int[] numbers) {
        if (numbers == null || numbers.length == 0) return "";
        for (int i = 0; i < numbers.length; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                int sum1 = Integer.parseInt(numbers[i] + "" + numbers[j]);
                int sum2 = Integer.parseInt(numbers[j] + "" + numbers[i]);
                if (sum1 > sum2) {
                    int temp = numbers[j];
                    numbers[j] = numbers[i];
                    numbers[i] = temp;
                }
            }
        }
        StringBuilder str = new StringBuilder(new String(""));
        for (int number : numbers) str.append(number);
        return str.toString();

    }
}


