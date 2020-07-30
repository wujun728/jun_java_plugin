package cn.iocoder.algorithm.leetcode.no0540;

public class Solution02 {

    public int singleNonDuplicate(int[] nums) {
        // 只有一个元素
        if (nums.length == 1) {
            return nums[0];
        }

        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + ((right - left) >> 1);
            if ((mid & 1) == 1) { // 保证 mid 是偶数
                mid--;
            }
            // 判断
            if (mid != nums.length - 1 && nums[mid] == nums[mid + 1]) {
                left = mid + 2; // 跳两格，因为凑对了
            } else {
                right = mid - 2; // 跳两格，因为凑对了
            }
        }

        return nums[left];
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
//        System.out.println(solution.singleNonDuplicate(new int[]{1, 1, 2, 3, 3, 4, 4, 8, 8}));
//        System.out.println(solution.singleNonDuplicate(new int[]{3, 3, 7, 7, 10, 11, 11}));
        System.out.println(solution.singleNonDuplicate(new int[]{1, 1, 2}));
    }

}
