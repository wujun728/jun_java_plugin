package cn.iocoder.algorithm.leetcode.no0008;

/**
 * https://leetcode-cn.com/problems/string-to-integer-atoi/
 *
 * 字符串
 *
 * 也推荐看看 https://leetcode-cn.com/problems/string-to-integer-atoi/solution/javaban-ben-jian-dan-yi-dong-by-sinozww/ 不使用 long 作为结果运算
 */
public class Solution {

    public int myAtoi(String str) {
        boolean found = false;
        long num = 0;
        int factor = 1; // 系数，如果是负数，则是 -1
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (!found) {
                if (ch == '+') {
                    found = true;
                    continue;
                } else if (ch == '-') {
                    found = true;
                    factor = -1;
                    continue;
                } else if (ch >= '0' && ch <= '9') { // 数字，放在后面处理
                    found = true;
                    // 不 continue
                } else if (ch == ' ') { // 丢弃空格
                    continue;
                } else { // 先找到字幕了，所以返回 0
                    return 0;
                }
            }
            // 处理数字
            if (ch >= '0' && ch <= '9') {
                if (num == 0) {
                    num = (ch - '0') * factor;
                } else {
                    num = num * 10 + (ch - '0') * factor;
//                    System.out.println(num);
                    // 判断是否越界
                    if (factor > 0 && num >= Integer.MAX_VALUE) {
                        return Integer.MAX_VALUE;
                    }
                    if (factor < 0 && num <= Integer.MIN_VALUE) {
                        return Integer.MIN_VALUE;
                    }
                }
            // 发现有数字后，后面又出现非数字，直接结束
            } else {
                break;
            }
        }
        return (int) num;
    }

    public static void main(String[] args) {
//        System.out.println(Integer.MAX_VALUE * 10);
//        System.out.println(Integer.MIN_VALUE * 10);
        Solution solution = new Solution();
//        System.out.println(912834723 * 10);
        System.out.println(solution.myAtoi("42") == 42);
        System.out.println(solution.myAtoi("   -42") == -42);
        System.out.println(solution.myAtoi("4193 with words") == 4193);
        System.out.println(solution.myAtoi("words and 987") == 0);
        System.out.println(solution.myAtoi("-91283472332") == Integer.MIN_VALUE);
        System.out.println(solution.myAtoi("3.1415926") == 3);
    }

}
