package cn.iocoder.algorithm.leetcode.no0010;

/**
 * 在 {@link Solution02} 的基础上修改，当前字符 + 后面是否有 * 。
 *
 * 回溯的思想。
 */
public class Solution03 {

    public boolean isMatch(String s, String p) {
        return this.isMatch(s, p, 0, 0);
    }

    public boolean isMatch(String s, String p, int i, int j) {
        if (i >= s.length() && j >= p.length()) {
            return true;
        } else if (j >= p.length()) {
            return false;
        }

        char chP = p.charAt(j);
        if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
            while (i < s.length()
                && (chP == '.' || chP == s.charAt(i))) {
                if (this.isMatch(s, p, i, j + 2)) {
                    return true;
                }
                i++;
            }
            return this.isMatch(s, p, i, j + 2);
        } else {
            return i < s.length()
                    && (s.charAt(i) == chP || chP == '.')
                    && this.isMatch(s, p, i + 1, j + 1);
        }
    }

    public static void main(String[] args) {
        Solution03 solution = new Solution03();
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
//        System.out.println(!solution.isMatch("a", ".*..a"));
        System.out.println(solution.isMatch("mississippi", "mis*is*ip*."));
//        System.out.println(solution.isMatch("miss", "mis*"));
    }

}
