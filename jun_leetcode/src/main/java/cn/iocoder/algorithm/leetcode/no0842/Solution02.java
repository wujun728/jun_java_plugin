package cn.iocoder.algorithm.leetcode.no0842;

import java.util.ArrayList;
import java.util.List;

/**
 * 在 {@link Solution} 的基础上，做优化
 *
 * 重点在 😈 处的剪枝，直接超过 100% 提交
 */
public class Solution02 {

    private List<Integer> result = new ArrayList<>();

    public List<Integer> splitIntoFibonacci(String S) {
        this.backtracking(S, 0);
        return result;
    }

    private boolean backtracking(String S, int index) {
        if (index == S.length() && result.size() > 2) {
            return true;
        }

        // 获得最后两位
        Integer first = null;
        Integer second = null;
        if (result.size() == 1) {
            first = result.get(result.size() - 1);
        } else if (result.size() > 1) {
            first = result.get(result.size() - 2);
            second = result.get(result.size() - 1);
        }

        // 解析结果
        int third = 0;
        for (int i = index; i < S.length(); i++) {
            if (third == 0 && i > index) { // 解决连续 0 的问题。
                return false;
            }
            // 生成第三者
            third = third * 10 + (S.charAt(i) - '0');
            if (third < 0) { // 说明超过 Int 的范围。😈 重点，此处的剪枝
                return false;
            }

            // 已经生成好了 first 和 second ，那么就是不断累加，验证
            if (first != null && second != null) {
                int sum = first + second;
                if (sum < third) {
                    return false;
                }
                if (sum > third) {
                    continue;
                }
                return this.backtracking(S, i, third);
            }

            // 如果 first 没生成
            if (first == null) {
                if (this.backtracking(S, i, third)) {
                    return true;
                }
                continue;
            }

            // 剩余，就是生成 second
            if (this.backtracking(S, i, third)) {
                return true;
            }
        }

        return false;
    }

    private boolean backtracking(String S, int index, int value) {
        result.add(value);
        if (this.backtracking(S, index + 1)) {
            return true;
        }
        result.remove(result.size() - 1);
        return false;
    }

    public static void main(String[] args) {
//        int i = Integer.MAX_VALUE * 10 + 9;
//        System.out.println(i);
        Solution02 solution = new Solution02();
        System.out.println(solution.splitIntoFibonacci("123456579"));
//        System.out.println(solution.splitIntoFibonacci("11235813"));
//        System.out.println(solution.splitIntoFibonacci("112358130"));
//        System.out.println(solution.splitIntoFibonacci("0123"));
//        System.out.println(solution.splitIntoFibonacci("1101111"));
    }

}
