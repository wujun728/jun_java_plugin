package com.leetcode.primary.array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 两个数组的交集 II
 * @author: BaoZhou
 * @date : 2018/12/10 0:58
 */

public class Intersection {

    public static void main(String[] args) {
        int[] nums1 = {4, 9, 5};
        int[] nums2 = {9, 4, 9, 8, 4};
        //int[] nums = {1, 1, 2};

        int[] intersect = intersect(nums1, nums2);

        for (int i = 0; i < intersect.length; i++) {
            System.out.print(intersect[i]);
        }
    }


    private static int[] intersect(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<>(16);
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < nums1.length; i++) {
            map.put(nums1[i], map.getOrDefault(nums1[i], 0) + 1);
        }

        for (int i = 0; i < nums2.length; i++) {
            if (map.containsKey(nums2[i])) {
                int count = map.get(nums2[i]);
                if (count > 0)
                {
                    map.put(nums2[i], count-1);
                    result.add(nums2[i]);
                }

            }
        }

        int[] resultInt = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            resultInt[i] = result.get(i);
        }
        return resultInt;
    }

}

