package cn.iocoder.algorithm.leetcode.no0010;

/**
 * 动态规划
 *
 * TODO 未解决，应该是二元。
 */
public class Solution04 {

    public boolean isMatch(String s, String p) {
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        dp[0][0] = true;
//        for (int i = 0; i <= s.length(); i++) {
//            dp[i][0] = true;
//        }

        for (int j = 0; j < p.length(); ) {
            char chP = p.charAt(j);
            if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
                for (int i = 0; i < s.length(); i++) {
                    dp[i + 1][j + 2] = dp[i][j]
                            && (chP == '.' || chP == s.charAt(i));
                    dp[i + 1][j + 1] = dp[i + 1][j + 1] || dp[i + 1][j + 2];
                }
                j = j + 2;
            } else {
                for (int i = 0; i < s.length(); i++) {
                    dp[i + 1][j + 1] = dp[i][j]
                            && (s.charAt(i) == chP || chP == '.');
                }
                j++;
            }
        }

        return dp[s.length()][p.length()];
    }

    public static void main(String[] args) {
        Solution04 solution = new Solution04();
//        System.out.println(!solution.isMatch("aa", "a")); // false
        System.out.println(solution.isMatch("aa", "a*")); // true
//        System.out.println(solution.isMatch("ab", ".*")); // true
//        System.out.println(solution.isMatch("aab", "c*a*b")); // true
//        System.out.println(!solution.isMatch("mississippi", "mis*is*p*.")); // false
//        System.out.println(!solution.isMatch("ab", ".*c")); // false
//        System.out.println(solution.isMatch("aaa", "a*a")); // true
//        System.out.println(solution.isMatch("aaa", "ab*a*c*a")); // true
//        System.out.println(solution.isMatch("a", "ab*c*"));
//        System.out.println(solution.isMatch("bbbba", ".*a*a"));
//        System.out.println(!solution.isMatch("a", ".*..a"));
//        System.out.println(solution.isMatch("mississippi", "mis*is*ip*."));
//        System.out.println(solution.isMatch("miss", "mis*"));
    }

}
