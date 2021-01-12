package com.leetcode.primary.math;

import java.util.HashMap;
import java.util.Map;

/**
 * 罗马数字转整数
 *
 * @author BaoZhou
 * @date 2018/12/18
 */
public class RomanToInt {
    public static void main(String[] args) {
        System.out.println(romanToInt("MCMXCIV"));
    }


    public static int romanToInt(String s) {
        Map<String, Integer> map = new HashMap<>(10);
        map.put("I", 1);
        map.put("V", 5);
        map.put("X", 10);
        map.put("L", 50);
        map.put("C", 100);
        map.put("D", 500);
        map.put("M", 1000);

        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            result += map.get(s.charAt(i)+"");
        }
        if (s.indexOf("IV") != -1 || s.indexOf("IX") != -1) {
            result = result - 2;
        }
        if (s.indexOf("XL") != -1 || s.indexOf("XC") != -1) {
            result = result - 20;
        }
        if (s.indexOf("CD") != -1 || s.indexOf("CM") != -1) {
            result = result - 200;
        }
        return result;
    }
}

