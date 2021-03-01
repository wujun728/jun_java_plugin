package cn.iocoder.algorithm.leetcode.no0207;

import java.util.*;

/**
 * 拓扑排序，通过 DFS 实现
 */
public class Solution02 {

    private int numCourses;

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 初始化
        this.numCourses = numCourses;
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

        // 初始化 dfs 遍历节点
        List<Integer> array = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (needCountArray[i] == 0) {
                array.add(i);
            }
        }
        for (int index : array) {
            this.dfs(index, nextArray, needCountArray);
        }

        return this.numCourses == 0;
    }

    private void dfs(int index, List<Set<Integer>> nextArray, int[] needCountArray) {
        // 获得节点
        Set<Integer> values = nextArray.get(index);
        // 减少节点
        this.numCourses--;
        // 计算后续节点
        if (values.isEmpty()) {
            return;
        }
        for (Integer nextIndex : values) {
            needCountArray[nextIndex]--;
            // 如果前置的节点，剩余大于 0 个，说明不可以遍历
            if (needCountArray[nextIndex] > 0) {
                continue;
            }
            this.dfs(nextIndex, nextArray, needCountArray);
        }
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
        System.out.println(solution.canFinish(2, new int[][]{{1, 0}}));
        System.out.println(solution.canFinish(2, new int[][]{{1, 0}, {1, 0}}));
        System.out.println(solution.canFinish(4, new int[][]{{1, 0}, {2, 0}, {3, 1}, {3, 2}}));
    }

}
