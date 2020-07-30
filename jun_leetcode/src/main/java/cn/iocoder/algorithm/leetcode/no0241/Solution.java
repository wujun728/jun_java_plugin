package cn.iocoder.algorithm.leetcode.no0241;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/different-ways-to-add-parentheses/
 */
public class Solution {

    public List<Integer> diffWaysToCompute(String input) {
        if (input.equals("")) {
            return Collections.emptyList();
        }
        // 解析字符串
        List<Character> ops = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();
        int index = 0;
        while (index < input.length()) {
            char ch = input.charAt(index);
            index++;
            // 运算符
            if (!this.isNumber(ch)) {
                ops.add(ch);
                continue;
            }
            int value = ch - '0';
            while (index < input.length()
                && isNumber(input.charAt(index))) {
                value = value * 10 + input.charAt(index) - '0';
                index++;
            }
            numbers.add(value);
        }

        // 计算情况
        return this.partition(ops, numbers, 0, numbers.size() - 1);
    }

    private List<Integer> partition(List<Character> ops, List<Integer> numbers, int start, int end) {
        List<Integer> results = new ArrayList<>();
        if (start == end) {
            results.add(numbers.get(start));
            return results;
        }

        // 顺序选择一个位置操作
        for (int i = start; i < end; i++) { // 操作符的遍历
            // 左半区间的组合
            for (Integer left : this.partition(ops, numbers, start, i)) {
                // 右半组件的组合
                for (Integer right : this.partition(ops, numbers, i + 1, end)) {
                    // 组合结果
                    results.add(this.calc(ops.get(i), left, right));
                }
            }
        }
        return results;
    }

    private boolean isNumber(char ch) {
        return ch >= '0' && ch <= '9';
    }

    private int calc(char ch, Integer left, Integer right) {
        if (ch == '+') {
            return left + right;
        }
        if (ch == '-') {
            return left - right;
        }
        if (ch == '*') {
            return left * right;
        }
        throw new IllegalStateException("未知字符串" + ch);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.diffWaysToCompute("2-1-1"));
        System.out.println(solution.diffWaysToCompute("2*3-4*5"));
    }

}
