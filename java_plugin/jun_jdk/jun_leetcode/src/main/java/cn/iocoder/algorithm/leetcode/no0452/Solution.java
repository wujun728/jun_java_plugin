package cn.iocoder.algorithm.leetcode.no0452;

import java.util.Arrays;
import java.util.Comparator;

/**
 * https://leetcode-cn.com/problems/minimum-number-of-arrows-to-burst-balloons/
 *
 * 贪心算法
 *
 * 按照【结束值】升序，如果下一个【开始值】小于“最后“【结束值】，说明“最后”的箭，还可以射穿
 */
public class Solution {

    public int findMinArrowShots(int[][] points) {
        if (points.length == 0) {
            return 0;
        }
        // 按照【结束值】升序
        Arrays.sort(points, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });

        int count = 1;
        int[] last = points[0];
        for (int i = 1; i < points.length; i++) {
            int[] current = points[i];
            if (current[0] > last[1]) { // 如果下一个【开始值】大于“最后“【结束值】，说明“最后”的箭，无法射穿
                last = current;
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        int[][] points = new int[][]{{-1, 0}, {0, 1}, {1, 2}, {2, 3}, {0, 3}};
        int[][] points = new int[][]{{10, 16}, {2, 8}, {1, 6}, {7, 12}};
        System.out.println(solution.findMinArrowShots(points));
    }

}
