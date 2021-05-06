package cn.iocoder.algorithm.leetcode.no0210;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/course-schedule-ii/
 *
 * 拓扑排序，BFS 实现
 */
public class Solution {

    private int numCourses;
    private List<Set<Integer>> nextArray;
    private int[] needCountArray;

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        this.init(numCourses, prerequisites);
        return this.bfs();
    }

    private void init(int numCourses, int[][] prerequisites) {
        // 初始化
        this.numCourses = numCourses;
        this.nextArray = new ArrayList<>(numCourses); // 表示指定位置的课程，后置课程有哪些。
        this.needCountArray = new int[numCourses]; // 表示每个课程，一共有多少前置。
        for (int i = 0; i < numCourses; i++) {
            nextArray.add(new HashSet<>());
        }
        // 将 prerequisites 赋值到 nextArray 中
        for (int[] prerequisite : prerequisites) {
            nextArray.get(prerequisite[1]).add(prerequisite[0]);
            needCountArray[prerequisite[0]]++;
        }
    }

    private int[] bfs() {
        // 初始化 bfs 遍历节点
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (needCountArray[i] == 0) {
                queue.add(i);
            }
        }
        // bfs 遍历
        int[] result = new int[numCourses];
        int resultIndex = 0;
        while (!queue.isEmpty()) {
            // 获得节点
            int index = queue.poll();
            Set<Integer> values = nextArray.get(index);
            // 减少节点
            result[resultIndex] = index;
            resultIndex++;
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
        return resultIndex == numCourses ? result : new int[0];
    }

}
