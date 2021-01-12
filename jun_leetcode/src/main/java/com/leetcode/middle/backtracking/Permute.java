package com.leetcode.middle.backtracking;

import com.leetcode.leetcodeutils.PrintUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 全排列
 *
 * @author BaoZhou
 * @date 2019/1/4
 */
public class Permute {
    @Test
    void test() {
        int[] nums = new int[]{1, 2, 3};
        PrintUtils.printArrays(permute(nums));
    }


    public List<List<Integer>> permute(int[] nums) {
        boolean[] use = new boolean[nums.length];
        List<List<Integer>> resultList = new ArrayList<>();
        if (nums.length == 0) {
            return resultList;
        }
        dfs(new ArrayList<>(), nums, use, resultList);
        return resultList;
    }

    public void dfs(List<Integer> result, int[] nums, boolean[] use, List<List<Integer>> resultList) {
        if (result.size() == use.length) {
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < result.size(); i++) {
                list.add(result.get(i));
            }
            resultList.add(list);
            return;
        }

        for (int m = 0; m < use.length; m++) {

            if (use[m]) {
                continue;
            }
            use[m] = true;
            result.add(nums[m]);
            dfs(result, nums, use, resultList);
            use[m] = false;
            result.remove(result.size() - 1);
        }
    }
}


