package cn.iocoder.algorithm.leetcode.no0696;

/**
 * https://leetcode-cn.com/problems/count-binary-substrings/
 */
public class Solution {

    public int countBinarySubstrings(String s) {
        int cnt = 0;

        int lastCounts = 0;
        int index = 0;
        while (index < s.length()) {
            // 计算从当前位置开始，有多少数量
            int counts = this.count(s, index);
            // 计算增加的组合。通过计算每一个数字的连续数量，对比之前另外一个数字的连续数量，取小的，就是可以增加的数。
            // 例如说，00110011 ，就是 2-2-2-2 ，那么可以增加的组合就是 2 + 2 + 2 = 6 .
            cnt += Math.min(counts, lastCounts);
            // 重置 last 数量
            lastCounts = counts;
            // 修改 index
            index += counts;
        }

        return cnt;
    }

    private int count(String s, int start) {
        int startCh = s.charAt(start);
        int startChCounts = 0;

        // 计算 startCh 数量
        while (start < s.length()
                && s.charAt(start) == startCh) {
            startChCounts++;
            start++;
        }

        return startChCounts;
    }

}
