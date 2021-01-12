package com.offer;

import org.junit.jupiter.api.Test;

/**
 * 第一个只出现一次的字符
 * @author BaoZhou
 * @date 2020-6-11
 */

public class Q34 {
    @Test
    public void result() {
        System.out.println(FirstNotRepeatingChar("sadsdasdsadsadasdsadasd"));
    }

    public int FirstNotRepeatingChar(String str) {
        int[] counts = new int[58];
        for(int i = 0; i < str.length(); i++)
            counts[str.charAt(i)-'A'] +=1;
        for(int i = 0; i < str.length(); i++)
            if(counts[str.charAt(i)-'A'] == 1)
                return i;
        return -1;
    }
}


