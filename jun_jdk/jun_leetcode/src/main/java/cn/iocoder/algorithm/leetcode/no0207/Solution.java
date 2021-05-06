package cn.iocoder.algorithm.leetcode.no0207;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/course-schedule/
 *
 * 拓扑排序，通过 BFS 实现
 */
public class Solution {

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 初始化
        List<Set<Integer>> nextArray = new ArrayList<>(numCourses); // 表示指定位置的课程，后置课程有哪些。
        int[] needCountArray = new int[numCourses]; // 表示每个课程，一共有多少前置。
        for (int i = 0; i < numCourses; i++) {
            nextArray.add(new HashSet<>());
        }
        // 将 prerequisites 赋值到 nextArray 中
        for (int[] prerequisite : prerequisites) {
            nextArray.get(prerequisite[1]).add(prerequisite[0]);
            needCountArray[prerequisite[0]]++;
        }

        // 初始化 bfs 遍历节点
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (needCountArray[i] == 0) {
                queue.add(i);
            }
        }
        // bfs 遍历
        while (!queue.isEmpty()) {
            // 获得节点
            int index = queue.poll();
            Set<Integer> values = nextArray.get(index);
            // 减少节点
            numCourses--;
            // 计算后续节点
            if (values.isEmpty()) {
                continue;
            }
            for (Integer nextIndex : values) {
                // 如果前置的节点，剩余只有 1 个，说明可以遍历
                if (needCountArray[nextIndex] == 1) {
                    queue.add(nextIndex);
                    continue;
                }
                // 如果前置的节点，剩余不只 1 个，先移除自身 index ，等后续剩余为 1 个后，才能遍历
                needCountArray[nextIndex]--;
            }
        }

        return numCourses == 0;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.canFinish(2, new int[][]{{1, 0}}));
//        System.out.println(solution.canFinish(2, new int[][]{{1, 0}, {1, 0}}));
        System.out.println(solution.canFinish(4, new int[][]{{1, 0}, {2, 0}, {3, 1}, {3, 2}}));
    }

}
