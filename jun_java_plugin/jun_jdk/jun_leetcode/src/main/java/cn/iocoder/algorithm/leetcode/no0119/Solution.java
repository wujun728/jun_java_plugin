package cn.iocoder.algorithm.leetcode.no0119;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/pascals-triangle-ii/
 *
 * 数组
 */
public class Solution {

    public List<Integer> getRow(int rowIndex) {
        List<Integer> results = new ArrayList<>();
        for (int i = 0; i <= rowIndex; i++) {
            results.add(1);
        }
        for (int i = 1; i < rowIndex; i++) {
            for (int j = i; j >= 0; j--) {
                int left = j - 1 >= 0 ? results.get(j - 1) : 0;
                int right = results.get(j);
                results.set(j, left + right);
            }
        }
        return results;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.getRow(1));
        System.out.println(solution.getRow(2));
        System.out.println(solution.getRow(3));
        System.out.println(solution.getRow(4));
        System.out.println(solution.getRow(5));
    }

}
