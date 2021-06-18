package cn.iocoder.algorithm.leetcode.no0282;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * https://leetcode-cn.com/problems/expression-add-operators/
 *
 * 回溯算法，暴力，吐血过了。。。。430 ms ，超过 8%
 */
public class Solution {

    private Set<String> result;

    public List<String> addOperators(String num, int target) {
        this.result = new HashSet<>();
        // 回溯
        this.backtracking(num, 0, target, null, null);
        return new ArrayList<>(this.result);
    }

    private void backtracking(String num, int index, int target, String current, Integer lastNumber) {
        if (index == num.length()) {
            if (lastNumber != null) {
                target = target - lastNumber;
            }
            if (target == 0) {
                result.add(current);
            }
            return;
        }

        int currentNumber = 0;
        for (int i = index; i < num.length(); i++) {
            if (currentNumber == 0 && i > index) {
                return;
            }
            currentNumber = currentNumber * 10 + (num.charAt(i) - '0');
            if (currentNumber < 0) {
                return;
            }
            if (lastNumber == null) {
                // + 号
                this.backtracking(num, i + 1, target, this.append(current, '+', currentNumber), currentNumber);
//                // - 号
//                if (current == null && currentNumber == 0) {
//                    this.backtracking(num, i + 1, target, this.append(current, '-', currentNumber), -currentNumber);
//                }
            } else {
                // + 号
                this.backtracking(num, i + 1, target - lastNumber, this.append(current, '+', currentNumber), currentNumber);
                // - 号
                this.backtracking(num, i + 1, target - lastNumber, this.append(current, '-', currentNumber), -currentNumber);
                // * 号
                this.backtracking(num, i + 1, target, this.append(current, '*', currentNumber), lastNumber * currentNumber);
            }
        }
    }

    private String append(String current, char op, int lastNumber) {
        if (current == null) {
            return String.valueOf(lastNumber);
        }
        return current + op + lastNumber;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.addOperators("123", 6));
        System.out.println(solution.addOperators("232", 8));
        System.out.println(solution.addOperators("105", 5));
        System.out.println(solution.addOperators("00", 0));
        System.out.println(solution.addOperators("3456237490", 9191));
//        long now = System.currentTimeMillis();
        System.out.println(solution.addOperators("2147483648", -2147483648));
//        int i = -2147483648;
//        System.out.println(System.currentTimeMillis() - now);
    }

}
