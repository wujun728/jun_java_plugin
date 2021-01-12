package com.leetcode.middle.array;

import java.util.Scanner;

/**
 * 最长回文子串
 * 解题思路： https://www.jianshu.com/p/e74ce81ecc7d
 * @author BaoZhou
 * @date 2018/12/24
 */
public class LongestPalindrome {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.print("请输入字符串：");
            String line = s.nextLine();
            if (line.equals("exit")) break;
            else {
                System.out.println("最大回文子串: "+manacher(line));
            }
        }

    }
    public static String manacher(String s) {
        // 生成辅助字符串，插入#，使得串的长度必定为奇数

        StringBuilder t = new StringBuilder("$#");
        for (int i = 0; i < s.length(); ++i) {
            t.append(s.charAt(i));
            t.append("#");
        }
        t.append("@");


        int[] p = new int[t.length()];
        int mx = 0, id = 0, resLen = 0, resCenter = 0;
        for (int i = 1; i < t.length()-1; ++i) {
            p[i] = mx > i ? Math.min(p[2 * id - i], mx - i) : 1;
            while (((i - p[i])>=0) && ((i + p[i])<t.length()-1) && (t.charAt(i + p[i]) == t.charAt(i - p[i]))) {
                ++p[i];
            }
            if (mx < i + p[i]) {
                mx = i + p[i];
                id = i;
            }
            if (resLen < p[i]) {
                resLen = p[i];
                resCenter = i;
            }
        }
        return s.substring((resCenter - resLen) / 2, (resCenter - resLen) / 2 + resLen-1);
    }

}
