package com.leetcode.primary.other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author BaoZhou
 * @date 2018/12/21
 */
public class TriAngle {

    public static void main(String[] args) {
        List<List<Integer>> lists = generate(10);
        for (int i = 0; i < lists.size(); i++) {
            System.out.println();
            List<Integer> t = lists.get(i);
            for (int i1 = 0; i1 < t.size(); i1++) {
                System.out.print(t.get(i1) + "" + " ");
            }
        }

    }

    public static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new ArrayList<>();
        if (numRows == 0)
        {
            return result;
        }

        Integer[] list = {1};
        List<Integer> list1 = Arrays.asList(list);
        result.add(list1);
        for (int i = 1; i < numRows; i++) {
            Integer[] temp = new Integer[i+1];
            temp[0] = 1;
            temp[i] = 1;
            List<Integer> lastRow = result.get(i - 1);
            for (int j = 1; j <= i - 1; j++) {
                temp[j] = lastRow.get(j - 1) + lastRow.get(j);
            }
            result.add(Arrays.asList(temp));
        }
        return result;
    }
}
