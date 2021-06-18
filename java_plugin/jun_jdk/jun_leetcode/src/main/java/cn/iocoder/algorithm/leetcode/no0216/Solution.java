package cn.iocoder.algorithm.leetcode.no0216;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/combination-sum-iii/
 */
public class Solution {

    private List<List<Integer>> result = new ArrayList<>();

    public List<List<Integer>> combinationSum3(int k, int n) {
        this.backtracking(k, n, 1, new ArrayList<>());
        return this.result;
    }

    private void backtracking(int count, int target, int start, List<Integer> current) {
        if (target == 0 && count == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        if (count == 0) {
            return;
        }
        int max = Math.min(9, target / count);
        count--;
        for (int i = start; i <= max; i++) {
            int newTarget = target - i;
            if (newTarget < 0) {
                return;
            }
            current.add(i);
            this.backtracking(count, newTarget, i + 1, current);
            current.remove(current.size() - 1);
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.combinationSum3(3, 7));
        System.out.println(solution.combinationSum3(3, 9));
    }

}
