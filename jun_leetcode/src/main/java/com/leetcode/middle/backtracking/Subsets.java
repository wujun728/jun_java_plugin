package com.leetcode.middle.backtracking;

import com.leetcode.leetcodeutils.PrintUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 子集
 *
 * @author BaoZhou
 * @date 2019/1/6
 */
public class Subsets {
    @Test
    void test() {
        int[] nums = new int[]{1, 2, 3};
        PrintUtils.printArrays(subsets(nums));
    }

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        if(nums.length ==0){
            return list;
        }
        backtrack(list, new ArrayList<>(),nums,0);
        return list;
    }
    private void backtrack(List<List<Integer>> list,List<Integer> temp,int[] nums,int begin){
        list.add(new ArrayList<>(temp));
        for(int i=begin;i<nums.length;i++){
            temp.add(nums[i]);
            backtrack(list,temp,nums,i+1);
            temp.remove(temp.size()-1);
        }
    }
}
