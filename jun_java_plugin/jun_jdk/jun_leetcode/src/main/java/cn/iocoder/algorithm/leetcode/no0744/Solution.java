package cn.iocoder.algorithm.leetcode.no0744;

/**
 * https://leetcode-cn.com/problems/find-smallest-letter-greater-than-target/
 */
public class Solution {

    public char nextGreatestLetter(char[] letters, char target) {
        assert letters.length > 0;

        // 如果最大的都比 target 大，就返回第一个
        if (letters[letters.length - 1] <= target) {
            return letters[0];
        }

        int left = 0, right = letters.length - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            char ch = letters[mid];
            if (ch <= target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return letters[left];
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.nextGreatestLetter(new char[]{'c', 'f', 'j'}, 'a'));
        System.out.println(solution.nextGreatestLetter(new char[]{'c', 'f', 'j'}, 'c'));
        System.out.println(solution.nextGreatestLetter(new char[]{'c', 'f', 'j'}, 'd'));
        System.out.println(solution.nextGreatestLetter(new char[]{'c', 'f', 'j'}, 'g'));
        System.out.println(solution.nextGreatestLetter(new char[]{'c', 'f', 'j'}, 'j'));
        System.out.println(solution.nextGreatestLetter(new char[]{'c', 'f', 'j'}, 'k'));
    }

}
