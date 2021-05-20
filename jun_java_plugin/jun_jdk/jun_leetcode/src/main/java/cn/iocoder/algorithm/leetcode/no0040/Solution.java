package cn.iocoder.algorithm.leetcode.no0040;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/combination-sum-ii/
 *
 * 回溯算法
 */
public class Solution {

    private List<List<Integer>> result = new ArrayList<>();

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        if (candidates.length == 0) {
            return Collections.emptyList();
        }
        // 排序
        Arrays.sort(candidates);
        // 回溯
        this.backtracking(candidates, target,  0, new ArrayList<>());
        return result;
    }

    private void backtracking(int[] candidates, int target, int index, List<Integer> current) {
        // 如果满足，结束
        if (target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        // 回溯
        Integer last = null;
        for (int i = index; i < candidates.length; i++) {
            int newTarget = target - candidates[i];
            if (newTarget < 0) {
                break;
            }
            if (last != null && last == candidates[i]) {
                continue;
            }
            last = candidates[i];
            current.add(candidates[i]);
            this.backtracking(candidates, newTarget, i + 1, current);
            current.remove(current.size() - 1);
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.combinationSum2(new int[]{2,5,2,1,2}, 5));
//        System.out.println(solution.combinationSum2(new int[]{10,1,2,7,6,1,5}, 8));
    }

}
