package cn.iocoder.algorithm.leetcode.no0392;

/**
 * https://leetcode-cn.com/problems/is-subsequence/
 *
 * 贪心算法
 *
 * 逐个匹配，如果不匹配，则跳过字符
 */
public class Solution {

    public boolean isSubsequence(String s, String t) {
        int sIndex = 0;
        int tIndex = 0;
        while (sIndex < s.length() && tIndex < t.length()) {
            char sCh = s.charAt(sIndex);
            char tCh = t.charAt(tIndex);
            if (sCh == tCh) {
                sIndex++;
                tIndex++;
                continue;
            }
            tIndex++;
        }
        return sIndex == s.length();
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.isSubsequence("abc", "ahbgdc"));
//        System.out.println(solution.isSubsequence("axc", "ahbgdc"));
//        System.out.println(solution.isSubsequence("aaa", "aa"));
        System.out.println(solution.isSubsequence("aaa", "aaa"));
    }

}
