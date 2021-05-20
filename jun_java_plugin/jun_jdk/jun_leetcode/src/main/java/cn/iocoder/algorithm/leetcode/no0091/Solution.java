package cn.iocoder.algorithm.leetcode.no0091;

/**
 * https://leetcode-cn.com/problems/decode-ways/
 *
 * 动态规划
 *
 * 表达式是，dp[i] = dp[i - 1] + dp[i - 2] 。
 *
 * dp[i - 2] 需要满足，前 i - 1 字母 + 当前 i 字母，拼凑出来是 >= 10 && <= 26
 * dp[i - 1] 需要满足，第 i 字母，必须是 > 0 ，否则只能和前 i - 1 凑对
 */
public class Solution {

    public int numDecodings(String s) {
        if (s.length() == 0) {
            return 0;
        }
        int dp0 = 1;
        int dp1 = 1;
        int last = s.charAt(0) - '0'; // 第 0 字符
        if (last == 0) {
            dp1 = 0;
        } else if (s.length() > 1 && s.charAt(1) == '0') { // 第 1 个字符字符，如果是 0 ，说明必须和第 0 个字符凑对。
            dp1 = 0;
        }
        for (int i = 1; i < s.length(); i++) {
            int value = s.charAt(i) - '0';
            int dp2 = 0;
            // 如果等于 0 ，说明一定要跟前面连接了
            if (value > 0) {
                dp2 = dp1;
            }
            // 判断是否 dp0 可以拼接
            int lastAndValue = last * 10 + value;
            if (lastAndValue >= 10 && lastAndValue <= 26) {
                dp2 = dp2 + dp0;
            }
            // 赋值
            dp0 = dp1;
            dp1 = dp2;
            last = value;
        }
        return dp1;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.numDecodings("230"));
        System.out.println(solution.numDecodings("0"));
        System.out.println(solution.numDecodings("12"));
        System.out.println(solution.numDecodings("226"));
        System.out.println(solution.numDecodings("10"));
        System.out.println(solution.numDecodings("101"));
        System.out.println((solution.numDecodings("1111"))); // 1-1-11，1-11-1，11-1-1，11-11，1-1-1-1
    }

}
