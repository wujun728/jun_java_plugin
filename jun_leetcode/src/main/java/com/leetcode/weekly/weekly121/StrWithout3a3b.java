package com.leetcode.weekly.weekly121;

import org.junit.jupiter.api.Test;

/**
 * 不含 AAA 或 BBB 的字符串
 *
 * @author BaoZhou
 * @date 2019/1/27
 */
public class StrWithout3a3b {

    @Test
    public void test() {

        System.out.println(strWithout3a3b(1, 3));
    }

    public String strWithout3a3b(int A, int B) {
        StringBuilder result = new StringBuilder();
        int x = Math.max(A, B);
        int y = Math.min(A, B);
        char c1, c2;
        if (A > B) {
            c1 = 'a';
            c2 = 'b';
        } else {
            c1 = 'b';
            c2 = 'a';
        }

        while (x != 0 || y != 0) {
            if (x > y) {
                result.append(c1);
                x--;
                if(x>0){
                    result.append(c1);
                    x--;
                }
                if(y>0){
                    result.append(c2);
                    y--;
                }
            } else  {
                result.append(c1);
                x--;
                if(y>0){
                    result.append(c2);
                    y--;
                }
            }
        }
        return result.toString();
    }

}
