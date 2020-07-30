package cn.iocoder.algorithm.leetcode.no0118;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/pascals-triangle/
 *
 * 数组
 */
public class Solution {

    public List<List<Integer>> generate(int numRows) {
        if (numRows == 0) {
            return Collections.emptyList();
        }

        List<List<Integer>> results = new ArrayList<>();
        results.add(Collections.singletonList(1));
        for (int i = 1; i < numRows; i++) {
            List<Integer> parents = results.get(i - 1);
            List<Integer> currents = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                int left = j - 1 >= 0 ? parents.get(j - 1) : 0;
                int right = j < parents.size() ? parents.get(j) : 0;
                currents.add(left + right);
            }
            results.add(currents);
        }
        return results;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.generate(1));
        System.out.println(solution.generate(2));
        System.out.println(solution.generate(3));
        System.out.println(solution.generate(4));
        System.out.println(solution.generate(5));
    }

}
