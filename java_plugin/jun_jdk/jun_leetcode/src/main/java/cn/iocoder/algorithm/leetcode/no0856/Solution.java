package cn.iocoder.algorithm.leetcode.no0856;

/**
 * https://leetcode-cn.com/problems/score-of-parentheses/
 */
public class Solution {

    /**
     * 下标
     */
    private int index;

    public int scoreOfParentheses(String S) {
        int number = 0;
        for (; index < S.length();) {
            if (S.charAt(index) == '(') {
                index++;
                number = number + this.scoreOfParentheses(S);
            } else {
                index++;
                return number > 0 ? 2 * number : 1;
            }
        }
        return number;
    }

    public static void main(String[] args) {
//        Solution solution = new Solution();
        System.out.println(new Solution().scoreOfParentheses("()"));
        System.out.println(new Solution().scoreOfParentheses("(())"));
        System.out.println(new Solution().scoreOfParentheses("(()(()))"));
    }

}
