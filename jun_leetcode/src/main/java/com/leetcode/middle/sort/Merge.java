package com.leetcode.middle.sort;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 合并区间
 *
 * @author BaoZhou
 * @date 2019/4/9
 */
public class Merge {
    @Test
    void test() {
        List<Interval> list = new ArrayList<>();
        list.add(new Interval(1, 4));
        list.add(new Interval(4, 6));
        list.add(new Interval(10, 16));
        list.add(new Interval(10, 20));
        merge(list);
        System.out.println(merge(list).toString());
    }

    public List<Interval> merge(List<Interval> intervals) {
        List<Interval> result = new ArrayList<>();
        intervals.sort(Comparator.comparingInt(i -> i.start));
        for (int i = 0; i < intervals.size(); i++) {
            if (i == intervals.size() - 1) {
                result.add(intervals.get(i));
                break;
            }
            int end = intervals.get(i).end;
            int start = intervals.get(i).start;
            for (int j = i + 1; j < intervals.size(); j++) {
                if (intervals.get(j).start <= end) {
                    i++;
                    end = Math.max(end, intervals.get(j).end);
                    if(i == intervals.size() - 1){
                        result.add(new Interval(start, end));
                        break;
                    }
                } else {
                    result.add(new Interval(start, end));
                    break;
                }
            }
        }
        return result;
    }
}
