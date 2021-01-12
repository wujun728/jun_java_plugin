package com.leetcode.primary.array;

import java.util.HashSet;
import java.util.Set;

/**
 * 存在重复
 *
 * @author: BaoZhou
 * @date : 2018/12/10 9:24
 */
public class JudgeDuplicate {

    public static void main(String[] args) {
        int[] nums = {1,2};
        //int[] nums = {1, 1, 2};
        System.out.println(containsDuplicate(nums));
    }
    private static Set save = new HashSet();
    public static boolean containsDuplicate(int[] nums) {
        save.clear();
        if(nums.length == 1){
            return false;
        }
        for(int a : nums){
            //Set.add(Object)添加失败会返回false
            if(!save.add(a)) {
                return true;
            }
        }
        return false;
    }
}

