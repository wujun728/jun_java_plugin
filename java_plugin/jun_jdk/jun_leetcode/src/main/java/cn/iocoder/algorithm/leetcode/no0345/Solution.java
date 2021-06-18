package cn.iocoder.algorithm.leetcode.no0345;

import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode-cn.com/problems/reverse-vowels-of-a-string/
 *
 * 双指针，左右寻找元音字符，符合则进行交换。
 */
public class Solution {

    private static Set<Character> VOWEL_LETTERS = new HashSet<>();

    static {
        for (Character ch : new Character[]{'a', 'e', 'i', 'o', 'u'}) {
            VOWEL_LETTERS.add(ch);
            VOWEL_LETTERS.add((char)(ch - 32));
        }
    }

    public String reverseVowels(String s) {
        StringBuilder sb = new StringBuilder(s);

        int left = 0, right = s.length() - 1;
        while (left < right) {
            while (!VOWEL_LETTERS.contains(s.charAt(left))
                && left < right) { // 如果一直不匹配，就会出现 left = right 的情况。这样，下面的交换，也不会有影响。
                left++;
            }
            while (!VOWEL_LETTERS.contains(s.charAt(right))
                && left < right) {
                right--;
            }
            // swap
            sb.setCharAt(left, s.charAt(right));
            sb.setCharAt(right, s.charAt(left));
            // 增加
            left++;
            right--;
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.reverseVowels("leetcode"));
        System.out.println(solution.reverseVowels("LEETCODE"));
        System.out.println(solution.reverseVowels("LED"));
        System.out.println(solution.reverseVowels("hello"));
    }

}
