package cn.iocoder.algorithm.leetcode.no0125;

/**
 * https://leetcode-cn.com/problems/valid-palindrome/
 */
public class Solution {

    public boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            // 跳过无效
            if (!isValid(s.charAt(left))) {
                left++;
                continue;
            }
            if (!isValid(s.charAt(right))) {
                right--;
                continue;
            }
            // 比较
            if (!isSome(s.charAt(left), s.charAt(right))) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    private boolean isValid(char ch) {
        return (ch >= 'a' && ch <= 'z')
                || (ch >= 'A' && ch <= 'Z')
                || (ch >= '0' && ch <= '9');
    }

    private boolean isSome(char ch1, char ch2) {
        if (ch1 >= 'A' && ch1 <= 'Z') {
            ch1 = (char) (ch1 + 32); // 转换成大写
        }
        if (ch2 >= 'A' && ch2 <= 'Z') {
            ch2 = (char) (ch2 + 32); // 转换成大写
        }
        return ch1 == ch2;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.isPalindrome("A man, a plan, a canal: Panama"));
    }

}
