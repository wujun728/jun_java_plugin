package cn.iocoder.algorithm.leetcode.no0739;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

/**
 * https://leetcode-cn.com/problems/daily-temperatures/
 *
 * 倒序 + 平衡树
 *
 * 效率比较低
 */
public class Solution {

    public int[] dailyTemperatures(int[] T) {
        int[] results = new int[T.length];
        TreeMap<Integer, Integer> map = new TreeMap<>(); // key：温度，value：位置
        for (int i = T.length - 1; i >= 0; i--) {
            // 获得比当前温度高的后几天
            Map.Entry<Integer, Integer> higherEntry = map.ceilingEntry(T[i] + 1); // + 1 原因是，需要超过它
            if (higherEntry != null) {
                results[i] = higherEntry.getValue() - i;
            } else {
                results[i] = 0;
            }

            // 添加当前温度到 map 中
            map.headMap(T[i]).entrySet().clear(); // 移除比当前小的。
            map.put(T[i], i);
        }
        return results;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        // 1 1 4 2 1 1 0 0
        // 1 1 4 2 1 2 0 0
//        System.out.println(Arrays.toString(solution.dailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73})));
//        [8,1,5,4,1,2,1,1,0,0]
//        [8,1,5,4,3,2,1,1,0,0]
        System.out.println(Arrays.toString(solution.dailyTemperatures(new int[]{89, 62, 70, 58, 47, 47, 46, 76, 100, 70})));
    }

}
