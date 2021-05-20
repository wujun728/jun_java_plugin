package cn.iocoder.algorithm.leetcode.no0220;

import java.util.HashMap;
import java.util.Map;

/**
 * 桶排序
 */
public class Solution02 {

    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if (t < 0) {
            return false;
        }

        Map<Long, Long> buckets = new HashMap<>();
        long bucketSize = t + 1; // 加一的原因是。假设 t = 2 ，那么 [0, 1, 2] 是一个桶。
        for (int i = 0; i < nums.length; i++) {
            long num = nums[i];
            long bucketId = getBucketId(num, bucketSize);
            // 判断是否重复
            if (buckets.containsKey(bucketId)) { // 当前桶存在，间隔一定小于等于 t
                return true;
            }
            if (buckets.containsKey(bucketId + 1) && Math.abs(buckets.get(bucketId + 1) - num) <= t) { // abs 的原因，无法肯定到底是前者大还是后者大
                return true;
            }
            if (buckets.containsKey(bucketId - 1) && Math.abs(buckets.get(bucketId - 1) - num) <= t) { // abs 的原因，无法肯定到底是前者大还是后者大
                return true;
            }
            // 添加到当前桶。
            buckets.put(bucketId, num);
            // 移除超过范围的
            if (buckets.size() > k) {
                buckets.remove(getBucketId(nums[i - k], bucketSize), (long) nums[i - k]);
            }
        }

        return false;
    }

    private long getBucketId(long num, long bucketSize) {
        if (num >= 0) {
            return num / bucketSize;
        }
        // (num + 1) 的原因，是因为从 -1 开始，而不是 0 。
        // 最前面 - 1 的原因是，bucketId 是从 -1 开始。
        return -1 + (num + 1) / bucketSize;
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
        System.out.println(solution.containsNearbyAlmostDuplicate(new int[]{1, 0, 1, 1}, 1, 2)); // true
        System.out.println(solution.containsNearbyAlmostDuplicate(new int[]{1, 5, 9, 1, 5, 9}, 2, 3)); // false
        System.out.println(solution.containsNearbyAlmostDuplicate(new int[]{0, Integer.MAX_VALUE}, 1, Integer.MAX_VALUE)); // true
        System.out.println(solution.containsNearbyAlmostDuplicate(new int[]{2, 1}, 1, 1)); // true
        System.out.println(solution.containsNearbyAlmostDuplicate(new int[]{2147483647, -2147483647}, 1, 2147483647)); // false
        System.out.println(solution.containsNearbyAlmostDuplicate(new int[]{-3, 3}, 2, 4)); // false
    }

}
