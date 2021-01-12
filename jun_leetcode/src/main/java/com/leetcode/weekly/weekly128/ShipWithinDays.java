package com.leetcode.weekly.weekly128;

import org.junit.jupiter.api.Test;

/**
 * 在 D 天内送达包裹的能力
 * (二分法)
 * @author BaoZhou
 * @date 2019/2/24
 */
public class ShipWithinDays {

    @Test
    public void test() {
        int weight[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println(shipWithinDays(weight, 5));
    }

    public int shipWithinDays(int[] weights, int D) {
        int sum = 0;
        int result = 0;
        int max = Integer.MIN_VALUE;
        for (int weight : weights) {
            if (weight > max) {
                max = weight;
            }
            sum += weight;
        }
        int l = max;
        int r = sum;

        while (l <= r) {
            int m = (l + r) / 2;
            if (check(weights, D, m)) {
                result = m;
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return result;
    }

    public boolean check(int[] weights, int D, int room) {
        int size = weights[0];
        D--;
        for (int i = 1; i < weights.length; i++) {
            if (size + weights[i] <= room) {
                size += weights[i];
            } else {
                D--;
                if (D < 0) {
                    return false;
                }
                size = weights[i];
            }
        }
        return true;
    }

}
