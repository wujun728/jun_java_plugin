package com.leetcode.primary.math;

import java.util.Arrays;
import java.util.List;

/**
 * Fizz Buzz
 *
 * @author BaoZhou
 * @date 2018/12/18
 */
public class FizzBuzz {
    public static void main(String[] args) {
        System.out.println(fizzBuzz(50).toString());
    }

    private static String[] result;

    static {
        result = new String[Integer.MAX_VALUE/1000];
        for (int i = 1; i < result.length; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                result[i-1] = "FizzBuzz";
            } else if (i % 3 == 0) {
                result[i-1] = "Fizz";
            } else if (i % 5 == 0) {
                result[i-1] = "Buzz";
            } else {
                result[i-1] = Integer.toString(i);
            }
        }
    }

    public static List<String> fizzBuzz(int n) {
        return Arrays.asList(Arrays.copyOf(result, n));
    }
}
