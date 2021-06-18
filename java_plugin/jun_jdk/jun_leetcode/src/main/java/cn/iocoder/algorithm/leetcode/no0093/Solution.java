package cn.iocoder.algorithm.leetcode.no0093;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/restore-ip-addresses/
 */
public class Solution {

    private List<String> result = new LinkedList<>();

    public List<String> restoreIpAddresses(String s) {
        if (s.length() == 0) {
            return Collections.emptyList();
        }

        this.backtracking(s, 0, "", 0);
        return result;
    }

    private void backtracking(String s, int beginIndex, String current, int count) {
        // 已经满足添加 3 个
        if (count == 4) {
            // 字符串长度全部使用
            if (beginIndex == s.length()) {
                result.add(current);
            }
            return;
        }

        int sum = 0;
        for (int i = beginIndex; i < 3 + beginIndex && i < s.length(); i++) {
            sum = sum * 10 + s.charAt(i) - '0';
            // 连续两个 0 ，跳过
            // 超过范围
            if (sum > 255) {
                return;
            }
            this.backtracking(s, i + 1, count == 0 ? String.valueOf(sum) : current + "." + sum,
                    count + 1);
            // 如果是以 0 开始，那么不能继续使用，因为会导致 0 被省略掉
            if (sum == 0) {
                return;
            }
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.restoreIpAddresses("25525511135"));
        System.out.println(solution.restoreIpAddresses("010010"));
    }

}
