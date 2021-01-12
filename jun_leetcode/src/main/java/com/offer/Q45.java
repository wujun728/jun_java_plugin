package com.offer;

import org.junit.jupiter.api.Test;

import java.util.Arrays;


/**
 * 扑克牌顺子
 *
 * @author BaoZhou
 * @date 2020-6-24
 */

public class Q45 {
    @Test
    public void result() {
        int data[] = {1, 3, 2, 5, 4};
        int data2[] = {1, 0, 1, 0, 0};
        System.out.println(isContinuous(data2));
    }

    public boolean isContinuous(int[] numbers) {
        if(numbers.length == 0){
            return false;
        }
        Arrays.sort(numbers);
        int kingCount = 0;
        int temp = -1;
        int count = 0;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == 0) {
                kingCount++;
                continue;
            }
            if (temp == -1) {
                temp = numbers[i];
            } else {
                if(temp == numbers[i]){
                    return false;
                }
                count += numbers[i] - temp - 1;
                temp = numbers[i];
            }
        }
        return count <= kingCount;
    }
}



