package cn.iocoder.algorithm.leetcode.no0151;

/**
 * https://leetcode-cn.com/problems/reverse-words-in-a-string/
 *
 * 字符串 6ms 击败 70% 多
 * 从末尾开始，寻找每一个空格之间的字符串
 *
 * 在 https://leetcode-cn.com/problems/reverse-words-in-a-string/solution/2-ms-ji-bai-100-java-yong-hu-by-_zhangheng/ 中，代码会更加简洁一点，思路是一样的。
 */
public class Solution {

    public String reverseWords(String s) {
        StringBuilder result = new StringBuilder(s.length());
        int index = s.length() - 1;
        while (index >= 0) {
            // 去掉空格
            int end = index;
            while (end >= 0 && s.charAt(end) == ' ') {
                end--;
            }
            if (end < 0) { // 例如说，刚好开头是空格。
                break;
            }
            // 寻找开始
            int start = end;
            while (start >= 1 && s.charAt(start - 1) != ' ') {
                start--;
            }
            // 截取添加到 result 中
            if (result.length() > 0) {
                result.append(" ");
            }
            result.append(s, start, end + 1);
            // 设置 index
            index = start - 1;
        }
        return result.toString();
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.reverseWords(" hello world!  "));
        System.out.println(solution.reverseWords("  hello world!  "));
//        System.out.println(solution.reverseWords("hello world!  "));
//        System.out.println(solution.reverseWords("a good   example"));
    }

}
