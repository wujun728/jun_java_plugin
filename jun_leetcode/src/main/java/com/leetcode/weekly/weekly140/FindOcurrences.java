package com.leetcode.weekly.weekly140;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Bigram 分词
 *
 * @author: BaoZhou
 * @date : 2019/6/9 10:32
 */
public class FindOcurrences {
    @Test
    public void test() {
        String text = "alice is a good girl she is a good student", first = "a", second = "good";
        System.out.println(Arrays.toString(findOcurrences(text, first, second)));
    }


    public String[] findOcurrences(String text, String first, String second) {
        ArrayList<String> result = new ArrayList<>();
        String[] split = text.split(" ");
        for (int i = 0; i < split.length - 2; i++) {
            if (split[i].equals(first) && split[i + 1].equals(second)) {
                result.add(split[i + 2]);
            }
        }
        String[] ans = new String[result.size()];
        for (int i = 0; i < result.size(); i++) {
            ans[i] = result.get(i);
        }
        return ans;

    }
}
