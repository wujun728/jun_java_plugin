package cn.iocoder.algorithm.leetcode.no0557;

/**
 * https://leetcode-cn.com/problems/reverse-words-in-a-string-iii/
 *
 * 字符串
 */
public class Solution {

    public String reverseWords(String s) {
        char[] array = s.toCharArray();
        // 遍历
        int start = 0;
        for (int i = 0; i <= array.length; i++) {
            if (i == array.length || array[i] == ' ') {
                swap(array, start, i - 1);
                start = i + 1;
            }
        }
        return new String(array);
    }

    private void swap(char[] array, int start, int end) {
        while (start < end) {
            // 交换
            char tmp = array[start];
            array[start] = array[end];
            array[end] = tmp;
            // 修改
            start++;
            end--;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.reverseWords("Let's take LeetCode contest"));
    }

}
