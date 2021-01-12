package com.offer;

import org.junit.jupiter.api.Test;


/**
 * 翻转单词顺序列
 *
 * @author BaoZhou
 * @date 2020-6-24
 */

public class Q44 {
    @Test
    public void result() {
        String data = "student. a am I";
        System.out.println(ReverseSentence(data));
    }

    public String ReverseSentence(String str) {
        if (str.isEmpty()) {
            return "";
        }
        String[] s = str.split(" ");
        if (s.length <= 1) {
            return str;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = s.length - 1; i >= 0; i--) {
            stringBuilder.append(s[i]);
            if (i != 0) {
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }
}



