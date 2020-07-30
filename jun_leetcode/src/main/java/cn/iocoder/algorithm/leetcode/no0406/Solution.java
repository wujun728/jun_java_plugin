package cn.iocoder.algorithm.leetcode.no0406;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/queue-reconstruction-by-height/
 *
 * 贪心算法
 *
 * 1. 按照【高度】降序，按照【数量】升序
 * 2. 顺序将数字插入到【数量】位置。
 *
 * 因为，千米已经有比数字的【高度】高的元素在，所以插入到指定位置，一定符合。
 */
public class Solution {

    public int[][] reconstructQueue(int[][] people) {
        // 按照【高度】降序，按照【数量】升序
        Arrays.sort(people, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                if (a[0] == b[0]) {
                    return a[1] - b[1];
                }
                return b[0] - a[0];
            }
        });

        // 顺讯插入
        List<int[]> result = new ArrayList<>(people.length);
        for (int[] item : people) {
            result.add(item[1], item);
        }
        return result.toArray(new int[people.length][2]);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[][] people = new int[][]{{7,0}, {4,4}, {7,1}, {5,0}, {6,1}, {5,2}};
        System.out.println(Arrays.deepToString(solution.reconstructQueue(people)));
    }

}
