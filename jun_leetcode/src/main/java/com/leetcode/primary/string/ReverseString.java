package com.leetcode.primary.string;

/**
 * 反转字符串
 *
 * @author BaoZhou
 * @date 2018/12/10
 */
public class ReverseString{

        public static void main(String[] args) {
            String s = "A man, a plan, a canal: Panama";
            //int[] nums = {1, 1, 2};
            // nums 是以“引用”方式传递的。也就是说，不对实参做任何拷贝
            System.out.println(reverseString(s));
        }

        public static String reverseString(String s) {
            char[] chars = s.toCharArray();
            for (int i = 0; i < chars.length / 2; i++) {
                char temp = chars[i];
                chars[i] = chars[chars.length - 1 - i];
                chars[chars.length - 1 - i] = temp;
            }
            return String.valueOf(chars);
        }
}
