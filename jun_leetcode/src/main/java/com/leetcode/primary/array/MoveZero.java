package com.leetcode.primary.array;

/**
 * 移动零
 *
 * @author BaoZhou
 * @date 2018/12/10
 */
public class MoveZero {
    public static void main(String[] args) {
        int[] nums = {0,1,0,3,12};
        //int[] nums = {1, 1, 2};
        moveZeroes(nums);
        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i]);
        }
    }

    public static void moveZeroes(int[] nums) {
        if(nums.length == 1){
            return;
        }
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0){
               nums[j]= nums[i];
               if(i!=j)
                   nums[i] = 0;
               j++;
            }
        }
    }
}
