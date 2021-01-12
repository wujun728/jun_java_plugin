package com.leetcode.primary.string;

import java.util.ArrayList;
import java.util.List;

/**
 * 报数
 *
 * @author BaoZhou
 * @date 2018/12/10
 */
public class CountAndSay {

    public static void main(String[] args) {
        for (int i = 1; i < 30; i++) {
            System.out.println(countAndSay(i));
        }

    }
    static List<String> result ;
    static {
        result = new ArrayList<>();
        result.add("1");
        for (int i = 0; i < 30; i++) {
            String s = result.get(i);
            StringBuilder next = new StringBuilder();
            int count = 1;
            for (int j = 0; j < s.length(); j++) {
                if (j != s.length() - 1) {
                    if (s.charAt(j) != s.charAt(j + 1)) {

                        next.append(Integer.toString(count));
                        next.append(s.charAt(j));
                        count = 1;
                    } else {
                        count++;
                    }
                }else{
                    next.append(Integer.toString(count));
                    next.append(s.charAt(j));
                }

            }
            result.add(next.toString());
            next.delete(0, next.length());
        }
    }

    public static String countAndSay(int n) {
        return result.get(n-1);
    }

}
