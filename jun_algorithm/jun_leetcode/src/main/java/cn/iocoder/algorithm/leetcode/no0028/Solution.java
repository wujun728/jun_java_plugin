package cn.iocoder.algorithm.leetcode.no0028;

/**
 * https://leetcode-cn.com/problems/implement-strstr/
 *
 * 字符串
 *
 * TODO 更多解题方法 https://leetcode-cn.com/problems/implement-strstr/solution/c5chong-jie-fa-ku-han-shu-bfkmpbmsunday-by-2227/
 */
public class Solution {

    public int strStr(String haystack, String needle) {
        // 空串
        if (haystack.length() == 0) {
            return needle.length() == 0 ? 0 : -1;
        }

        // 非空串匹配
        for (int i = 0; i < haystack.length() - needle.length() + 1; i++) {
            boolean match = true;
            // 开始匹配
            for (int j = 0; j < needle.length(); j++) {
                if (haystack.charAt(i) != needle.charAt(j)) {
                    match = false;
                    break;
                }
            }
            if (match) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.strStr("hello", "ll"));
//        System.out.println(solution.strStr("hello", "lloo"));
//        System.out.println(solution.strStr("", ""));
        System.out.println(solution.strStr("a", "a"));
    }

}
