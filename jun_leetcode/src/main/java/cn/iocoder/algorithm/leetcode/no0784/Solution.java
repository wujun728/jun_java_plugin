package cn.iocoder.algorithm.leetcode.no0784;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/letter-case-permutation/
 *
 * 回溯算法
 *
 * 写的有点傻，修改下
 */
public class Solution {

    private List<String> results = new ArrayList<>();

    public List<String> letterCasePermutation(String S) {
        this.letterCasePermutation(0, S, "");
        return results;
    }

    private void letterCasePermutation(int i, String s, String current) {
        if (i == s.length()) {
            results.add(current);
            return;
        }
        char ch = s.charAt(i);
        this.letterCasePermutation(i + 1, s, current + ch);
        // TODO 芋艿，此处可以通过位操作，来大小写转换，后续在优化。
        if (ch >= 'a' && ch <= 'z') {
            this.letterCasePermutation(i + 1, s, current + Character.toUpperCase(ch));
        } else if (ch >= 'A' && ch <= 'Z') {
            this.letterCasePermutation(i + 1, s, current + Character.toLowerCase(ch));
        }
    }

}
