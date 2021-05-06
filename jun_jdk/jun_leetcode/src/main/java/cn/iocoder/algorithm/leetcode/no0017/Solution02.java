package cn.iocoder.algorithm.leetcode.no0017;

import java.util.*;

/**
 * 和 {@link Solution} 的差别，是完全递归，不需要两重循环
 *
 * 回溯算法
 */
public class Solution02 {

    private static List<List<String>> MAPPINGS = new ArrayList<>();

    private List<String> result = new LinkedList<>();

    static {
        MAPPINGS.add(null); // 0
        MAPPINGS.add(null); // 1
        MAPPINGS.add(Arrays.asList("a", "b", "c"));
        MAPPINGS.add(Arrays.asList("d", "e", "f"));
        MAPPINGS.add(Arrays.asList("g", "h", "i"));
        MAPPINGS.add(Arrays.asList("j", "k", "l"));
        MAPPINGS.add(Arrays.asList("m", "n", "o"));
        MAPPINGS.add(Arrays.asList("p", "q", "r", "s"));
        MAPPINGS.add(Arrays.asList("t", "u", "v"));
        MAPPINGS.add(Arrays.asList("w", "x", "y", "z"));
    }

    public List<String> letterCombinations(String digits) {
        if (digits.length() == 0) {
            return Collections.emptyList();
        }
        this.backtracking(digits, 0, "");
        return result;
    }

    private void backtracking(String digits, int index, String str) {
        if (index == digits.length()) {
            result.add(str);
            return;
        }
        // 获得当前的
        List<String> mappings = MAPPINGS.get(digits.charAt(index) - '0');
        for (String mapping : mappings) {
            this.backtracking(digits, index + 1, str + mapping);
        }
    }

}
