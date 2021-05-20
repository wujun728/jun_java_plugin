package cn.iocoder.algorithm.leetcode.no0010;

/**
 * https://leetcode-cn.com/problems/regular-expression-matching/
 *
 * 错误思路，顺序匹配是不行的。
 */
public class Solution {

    public boolean isMatch(String s, String p) {
        int i = 0;
        int j = 0;
        while (i < s.length() && j < p.length()) {
            char chP = p.charAt(j);
            // 处理 . 的情况
            if (chP == '.') {
                i++;
                j++;
                continue;
            }
            // 处理 * 的情况
            if (chP == '*') {
                assert j > 0;
                char chPPrev = p.charAt(j - 1);
                // 直接匹配无限多
                if (chPPrev == '.') {
                    return j == p.length() - 1;
                }
                int sum = 0;
                while (i < s.length() && s.charAt(i) == chPPrev) {
                    i++;
                    sum++;
                }
                j++;
                while (j < p.length() && p.charAt(j) == chPPrev && sum > 0) {
                    sum--;
                    j++;
                }
                continue;
            }
            if (s.charAt(i) != chP) {
                if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
                    j = j + 2;
                    continue;
                }
                return false;
            }
            i++;
            j++;
        }

        return i >= s.length() && j >= p.length();
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.isMatch("aa", "a"));
//        System.out.println(solution.isMatch("aa", "a*")); // true
//        System.out.println(solution.isMatch("ab", ".*"));
//        System.out.println(solution.isMatch("aab", "c*a*b"));
//        System.out.println(solution.isMatch("mississippi", "mis*is*p*."));
//        System.out.println(solution.isMatch("ab", ".*c"));
//        System.out.println(solution.isMatch("aaa", "a*a"));
        System.out.println(solution.isMatch("aaa", "ab*a*c*a"));
    }

}
