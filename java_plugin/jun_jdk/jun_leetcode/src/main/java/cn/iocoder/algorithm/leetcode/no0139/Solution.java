package cn.iocoder.algorithm.leetcode.no0139;

import java.util.List;

/**
 * https://leetcode-cn.com/problems/word-break/
 *
 * 动态规划
 *
 * dp[i] = dp[i] | dp[i - string.length]
 *
 * 不断遍历 s 字符串，判断 wordDict 往前找，是否有匹配成功的。
 */
public class Solution {

    public boolean wordBreak(String s, List<String> wordDict) {
        // 初始化 dp 数组
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true; // 无字符串，满足可以匹配。

        // dp 开始
        for (int i = 1; i <= s.length(); i++) { // 第 i 个字符，存储在 dp[i] ，会有 1 的偏移。
            for (String word : wordDict) {
                int length = word.length();
                if (length > i) {
                    continue;
                }
                // 如果之前位置不符合，则不进行匹配字符串
                if (!dp[i - length]) {
                    continue;
                }
                // 匹配字符串
                String target = s.substring(i - length, i);
                if (target.equals(word)) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }

}
