package cn.iocoder.algorithm.leetcode.no0220;

import java.util.TreeSet;

/**
 * 在 {@link Solution} 的基础上进行优化
 *
 * 1、TreeMap 修改成 TreeSet
 * 2、只要 TreeSet 超过数量，就移除最早添加的元素
 * 3、不使用 Long ，而是 Int ，但是会越界，所以变两次 set 的处理
 */
public class Solution011 {

    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            // 判断是否重复
            Integer ceilingKey = set.ceiling(num);
            if (ceilingKey != null && ceilingKey <= t + num) { // 避免越界 int
                return true;
            }
            Integer floorKey = set.floor(num);
            if (floorKey != null && num <= t + floorKey) { // 避免越界 int
                return true;
            }
            // 添加到 set 中
            set.add(num);
            // 移除超过的
            if (set.size() > k) {
                set.remove(nums[i - k]);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Solution011 solution = new Solution011();
//        System.out.println(solution.containsNearbyAlmostDuplicate(new int[]{1, 0, 1, 1}, 1, 2)); // true
//        System.out.println(solution.containsNearbyAlmostDuplicate(new int[]{1, 5, 9, 1, 5, 9}, 2, 3)); // false
//        System.out.println(solution.containsNearbyAlmostDuplicate(new int[]{0, Integer.MAX_VALUE}, 1, Integer.MAX_VALUE)); // true
//        System.out.println(solution.containsNearbyAlmostDuplicate(new int[]{2, 1}, 1, 1)); // true
        System.out.println(solution.containsNearbyAlmostDuplicate(new int[]{2147483647, -2147483647}, 1, 2147483647)); // false
    }

}
