package cn.iocoder.algorithm.leetcode.no0282;

import java.util.ArrayList;
import java.util.List;

/**
 * 在 {@link Solution} 的基础上，尝试优化
 *
 * 将 {@link #result} 属性，从 Set 改成 List ，214 ms ，超过 64%
 */
public class Solution02 {

    private List<String> result;

    public List<String> addOperators(String num, int target) {
        this.result = new ArrayList<>();
        // 回溯
        this.backtracking(num, 0, target, null, null);
        return result;
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
                this.backtracking(num, i + 1, target, String.valueOf(currentNumber), currentNumber);
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
        return current + op + lastNumber;
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
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
