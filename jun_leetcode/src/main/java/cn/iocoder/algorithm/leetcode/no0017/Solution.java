package cn.iocoder.algorithm.leetcode.no0017;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number/
 *
 * 回溯算法
 */
public class Solution {

    private static List<List<String>> MAPPINGS = new ArrayList<>();

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
        return this.gen(digits, 0);
    }

    private List<String> gen(String digits, int index) {
        // 获得当前的
        List<String> mappings = MAPPINGS.get(digits.charAt(index) - '0');
        if (index == digits.length() - 1) {
            return mappings;
        }

        // 获得后续的
        List<String> others = this.gen(digits, index + 1);
        List<String> result = new ArrayList<>();
        for (String mapping : mappings) {
            for (String other : others) {
                result.add(mapping + other);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.letterCombinations("23"));
    }

}
