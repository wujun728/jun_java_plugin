package cn.iocoder.algorithm.leetcode.no0220;

import java.util.TreeSet;

/**
 * 在 {@link Solution011} 的基础上进行优化
 *
 * 还是使用 long ，减少计算。
 *
 * 目前 leetcode 跑下来，目前是二叉搜搜树最优的方案。
 */
public class Solution012 {

    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        TreeSet<Long> set = new TreeSet<>();
        for (int i = 0; i < nums.length; i++) {
            long num = nums[i];
            long beginNum = num - t;
            // 判断是否重复
            Long ceilingKey = set.ceiling(beginNum);
            if (ceilingKey != null && ceilingKey - beginNum <= 2L * t) {
                return true;
            }
            // 添加到 set 中
            set.add(num);
            // 移除超过的
            if (set.size() > k) {
                set.remove((long) nums[i - k]);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Solution012 solution = new Solution012();
//        System.out.println(solution.containsNearbyAlmostDuplicate(new int[]{1, 0, 1, 1}, 1, 2)); // true
//        System.out.println(solution.containsNearbyAlmostDuplicate(new int[]{1, 5, 9, 1, 5, 9}, 2, 3)); // false
//        System.out.println(solution.containsNearbyAlmostDuplicate(new int[]{0, Integer.MAX_VALUE}, 1, Integer.MAX_VALUE)); // true
//        System.out.println(solution.containsNearbyAlmostDuplicate(new int[]{2, 1}, 1, 1)); // true
        System.out.println(solution.containsNearbyAlmostDuplicate(new int[]{2147483647, -2147483647}, 1, 2147483647)); // false
    }

}
