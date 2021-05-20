package cn.iocoder.algorithm.leetcode.no0318;

/**
 * https://leetcode-cn.com/problems/maximum-product-of-word-lengths/
 *
 * 位操作
 */
public class Solution {

    public int maxProduct(String[] words) {
        // 计算
        int[] bits = new int[words.length];
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            for (int j = 0; j < word.length(); j++) {
                int index = word.charAt(j) - 'a';
                bits[i] = bits[i] | (1 << index);
            }
        }

        // 计算乘积
        int max = 0;
        for (int i = 0; i < words.length; i++) {
            for (int j = i + 1; j < words.length; j++) {
                if ((bits[i] & bits[j]) != 0) {
                    continue;
                }
                max = Math.max(words[i].length() * words[j].length(), max);
            }
        }
        return max;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.maxProduct(new String[]{"abcw","baz","foo","bar","xtfn","abcdef"}));
        System.out.println(solution.maxProduct(new String[]{"a","ab","abc","d","cd","bcd","abcd"}));
        System.out.println(solution.maxProduct(new String[]{"a","aa","aaa","aaaa"}));
    }

}
