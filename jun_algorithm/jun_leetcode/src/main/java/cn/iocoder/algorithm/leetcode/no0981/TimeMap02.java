package cn.iocoder.algorithm.leetcode.no0981;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * https://leetcode-cn.com/problems/time-based-key-value-store/
 *
 * 使用 TreeMap 实现
 *
 * 实际结果，超时。尴尬~
 */
public class TimeMap02 {

    // Integer 指的是 timestamp
    private Map<String, TreeMap<Integer, String>> timeMap = new HashMap<>();

    /** Initialize your data structure here. */
    public TimeMap02() {
    }

    public void set(String key, String value, int timestamp) {
        Map<Integer, String> times = timeMap.computeIfAbsent(key, s -> new TreeMap<>());
        times.put(timestamp, value);
    }

    public String get(String key, int timestamp) {
        TreeMap<Integer, String> times = timeMap.get(key);
        if (times == null) {
            return "";
        }

        // 返回 timestamp 最接近的 key 。关键
        Integer floorKey = times.floorKey(timestamp);
        return floorKey != null ? times.get(floorKey) : "";
    }

}
