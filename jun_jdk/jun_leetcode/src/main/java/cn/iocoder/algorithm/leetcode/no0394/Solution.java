package cn.iocoder.algorithm.leetcode.no0394;

/**
 * https://leetcode-cn.com/problems/decode-string/
 *
 * 递归
 */
public class Solution {

    private class ReturnT {

        /**
         * 字符串
         */
        private String result;
        /**
         * 新的下标
         */
        private int index;

        public ReturnT(String result, int index) {
            this.result = result;
            this.index = index;
        }
    }

    public String decodeString(String s) {
        return decodeString(s, 0).result;
    }

    private ReturnT decodeString(String s, int index) {
        String result = "";
        int number = 0; // 数字字符串
        for (; index < s.length(); index++) {
            char ch = s.charAt(index);
            // 数字的情况
            if (ch >= '0' && ch <= '9') {
                number = number * 10 + (ch - '0');
                continue;
            }
            // 左括号的情况
            if (ch == '[') {
                ReturnT returnT = this.decodeString(s, index + 1);
                result = result + this.gen(returnT.result, number);
                index = returnT.index;
                number = 0;
                continue;
            }
            // 右括号的情况
            if (ch == ']') {
                return new ReturnT(result, index);
            }
            // 字母的情况
            result += ch;
        }
        return new ReturnT(result, index);
    }

    private String gen(String str, int number) {
        if (number == 1) {
            return str;
        }
        if ((number & 1) == 1) {
            return str + gen(str + str, number / 2);
        } else {
            return gen(str + str, number / 2);
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.decodeString("2[ab]"));
        System.out.println(solution.decodeString("2[2[ab]]"));
        System.out.println(solution.decodeString("2[2[ab]]bc"));
        System.out.println(solution.decodeString("3[a2[c]]"));
        System.out.println(solution.decodeString("2[abc]3[cd]ef"));
    }

}
