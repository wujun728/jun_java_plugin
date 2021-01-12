package com.leetcode.middle.sort;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 前K个高频元素
 *
 * @author BaoZhou
 * @date 2019/1/22
 */
public class TopKFrequent {
    @Test
    void test() {
        int[] nums = {6, 8, 1, 1, 1, 2, 2, 3};
        int k = 2;
        System.out.println(topKFrequent(nums, k));
    }

    public List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int value = map.containsKey(nums[i]) ? map.get(nums[i]) + 1 : 1;
            map.put(nums[i], value);
        }
        // 升序比较器
        Comparator<Map.Entry<Integer, Integer>> valueComparator = (o1, o2) -> o2.getValue()-o1.getValue();
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list,valueComparator);
        System.out.println(map);
        return list.stream().limit(k).map(Map.Entry::getKey).collect(Collectors.toList());
    }


}
