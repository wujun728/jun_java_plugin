package cn.iocoder.algorithm.leetcode.no0010;

/**
 * 思路是正确，不过应该是在每个非 * 的情况下，去判断后面是否为 * ，然后去处理。
 *
 * 继续修改
 */
public class Solution02 {

    public boolean isMatch(String s, String p) {
        return this.isMatch(s, p, 0, 0);
    }

    public boolean isMatch(String s, String p, int i, int j) {
        if (i >= s.length() && j >= p.length()) {
            return true;
        } else if (j >= p.length()) {
            return false;
        }

        // 处理 . 的情况
        char chP = p.charAt(j);
        if (chP == '.') {
            return this.isMatch(s, p, i + 1, j + 1)
                    || (j + 1 < p.length() && p.charAt(j + 1) == '*' && this.isMatch(s, p, i, j + 1));
        }

        // 处理 * 的情况
        if (chP == '*') {
            assert j > 0;
            char chPPrev = p.charAt(j - 1);
            // 直接匹配无限多
            if (chPPrev == '.') {
                while (i < s.length()) {
                    if (this.isMatch(s, p, i, j + 1)) {
                        return true;
                    }
                    i++;
                }
//                return j == p.length() - 1;
                return this.isMatch(s, p, i, j + 1);
            } else {
                while (i < s.length() && s.charAt(i) == chPPrev) {
                    if (this.isMatch(s, p, i, j + 1)) {
                        return true;
                    }
                    i++;
                }
                return this.isMatch(s, p, i, j + 1);
            }
        }

        // 处理普通情况
        if (i < s.length() && s.charAt(i) == chP) {
            return this.isMatch(s, p, i + 1, j + 1)
                    || (j + 1 < p.length() && p.charAt(j + 1) == '*' && this.isMatch(s, p, i, j + 1));
        }

        // 处理不相等，但是后面有个 * 的情况
        if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
            return this.isMatch(s, p, i, j + 2);
        }

        // 真的不相等了
        return false;
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
//        System.out.println(!solution.isMatch("aa", "a")); // false
//        System.out.println(solution.isMatch("aa", "a*")); // true
//        System.out.println(solution.isMatch("ab", ".*")); // true
//        System.out.println(solution.isMatch("aab", "c*a*b")); // true
//        System.out.println(!solution.isMatch("mississippi", "mis*is*p*.")); // false
//        System.out.println(!solution.isMatch("ab", ".*c")); // false
//        System.out.println(solution.isMatch("aaa", "a*a")); // true
//        System.out.println(solution.isMatch("aaa", "ab*a*c*a")); // true
//        System.out.println(solution.isMatch("a", "ab*c*"));
//        System.out.println(solution.isMatch("bbbba", ".*a*a"));
        System.out.println(solution.isMatch("a", ".*..a"));
    }

}
