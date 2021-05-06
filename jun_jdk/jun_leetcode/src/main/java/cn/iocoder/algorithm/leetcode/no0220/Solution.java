package cn.iocoder.algorithm.leetcode.no0220;

import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * https://leetcode-cn.com/problems/contains-duplicate-iii/
 *
 * 搜索二叉树，通过 TreeMap 来实现
 */
public class Solution {

    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        TreeMap<Long, Integer> map = new TreeMap<>();
        for (int i = 0; i < nums.length; i++) {
            long num = nums[i];
            long beginNum = num - t;
            // 判断是否有重复的
            NavigableMap<Long, Integer> tailMap = map.tailMap(beginNum, true);
            Iterator<Map.Entry<Long, Integer>> iterator = tailMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Long, Integer> entry = iterator.next();
                long diff = entry.getKey() - beginNum;
                if (diff > 2L * t) { // 大的过多
                    break;
                }
                if (i - entry.getValue() > k) {
                    iterator.remove();
                    continue;
                }
                return true;
            }
            // 添加到 map 中
            map.put(num, i);
        }
        return false;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.containsNearbyAlmostDuplicate(new int[]{1, 0, 1, 1}, 1, 2));
        System.out.println(solution.containsNearbyAlmostDuplicate(new int[]{1, 5, 9, 1, 5, 9}, 2, 3));
        System.out.println(solution.containsNearbyAlmostDuplicate(new int[]{0, Integer.MAX_VALUE}, 1, Integer.MAX_VALUE));
    }

}
