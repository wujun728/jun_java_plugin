package cn.iocoder.algorithm.leetcode.no0981;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/time-based-key-value-store/
 *
 * 哈希 + 二分查找 + 减枝
 */
public class TimeMap {

    private static class Pair {

        public String key;
        public int value;

        public Pair(String key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private Map<String, List<Pair>> timeMap = new HashMap<>();

    /** Initialize your data structure here. */
    public TimeMap() {
    }

    // 注意，默认 timestamp 就是有序的
    public void set(String key, String value, int timestamp) {
//        timeMap.computeIfAbsent(key, s -> new ArrayList<>())
//                .add(new Pair(value, timestamp));
        List<Pair> pairs = timeMap.computeIfAbsent(key, s -> new ArrayList<>());
        if (!pairs.isEmpty()) {
            // 需要这里减枝，不然会超时。
            Pair pair = pairs.get(pairs.size() - 1);
            if (pair.key.equals(value)) {
                return;
            }
        }
        pairs.add(new Pair(value, timestamp));
    }

    public String get(String key, int timestamp) {
        List<Pair> pairs = timeMap.get(key);
        if (pairs == null) {
            return "";
        }

        // 二分查找
        int left = 0, right = pairs.size() - 1;
        while (left <= right) {
            int middle = left + ((right - left) >> 1);
            Pair pair = pairs.get(middle);
            if (pair.value == timestamp) {
                return pair.key;
            }
            if (pair.value > timestamp) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }

        // 返回结果
        if (right < 0) {
            return "";
        }
        Pair pair = pairs.get(right);
        return pair != null && pair.value <= timestamp ? pair.key
                : "";
    }

//    public String get(String key, int timestamp) {
//        if (!timeMap.containsKey(key)) return "";
//
//        List<Pair<Integer, String>> A = timeMap.get(key);
//        int i = Collections.binarySearch(A, new Pair<>(timestamp, "{"),
//                Comparator.comparingInt(Pair::getKey));
//
//        if (i >= 0)
//            return A.get(i).getValue();
//        else if (i == -1)
//            return "";
//        else
//            return A.get(-i-2).getValue();
//    }

    public static void main(String[] args) {
        TimeMap timeMap = new TimeMap();
        timeMap.set("love", "high", 10);
        timeMap.set("love", "low", 20);
        timeMap.get("love", 5);
        timeMap.get("love", 10);
        timeMap.get("love", 15);
        timeMap.get("love", 20);
        timeMap.get("love", 25);
    }

}
