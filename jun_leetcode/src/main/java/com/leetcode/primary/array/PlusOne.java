package com.leetcode.primary.array;

/**
 * PlusOne
 *
 * @author: BaoZhou
 * @date : 2018/12/10 14:24
 */

public class PlusOne {

    public static void main(String[] args) {
        int[] nums = {9, 9, 9, 9};
        //int[] nums = {1, 1, 2};
        int[] result = plusOne(nums);
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i]);
        }
    }

    public static int[] plusOne(int[] digits) {
        int n = digits.length;
        for(int i=n-1;i>=0;i--){
            //非9加1
            if(digits[i]<9){
                digits[i]++;
                return digits;
            }
            //逢9置0
            digits[i]=0;
        }
        //全部为9，则需要数组扩充1位
        int[] result = new int[n+1];
        result[0] = 1;
        return result;
    }

}

