package cn.iocoder.algorithm.leetcode.no0207;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用递归，检测是否存在环。
 * 不基于拓扑排实现。
 */
public class Solution03 {

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 初始化，每个节点的前置节点
        List<List<Integer>> prevNodess = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            prevNodess.add(new ArrayList<>());
        }
        for (int[] prerequisite : prerequisites) {
            prevNodess.get(prerequisite[0]).add(prerequisite[1]);
        }

        // 逐个节点计算，是否存在环
        boolean[] visits = new boolean[numCourses]; // 用于标记每一轮检测，是否已经访问过
        boolean[] globalVisits = new boolean[numCourses]; // 用于标记全局是否访问过这个节点，如果已经访问过，则无需在访问。这个是锦上添花的功能，提升性能。
        for (int node = 0; node < numCourses; node++) {
            if (this.hasCycle(node, visits, globalVisits, prevNodess)) {
                return false;
            }
        }
        return true;
    }

    private boolean hasCycle(int node, boolean[] visits, boolean[] globalVisits, List<List<Integer>> prevNodess) {
        if (visits[node]) {
            return true;
        }
        if (globalVisits[node]) { // 已经访问过，无需在遍历判断
            return false;
        }
        // 标记已访问
        visits[node] = true;
        globalVisits[node] = true;

        // 逐个前置节点遍历
        List<Integer> prevNodes = prevNodess.get(node);
        for (Integer prevNode : prevNodes) {
            if (this.hasCycle(prevNode, visits, globalVisits, prevNodess)) {
                return true;
            }
        }

        // 标记未访问
        visits[node] = false;

        return false;
    }

    public static void main(String[] args) {
        Solution03 solution = new Solution03();
//        System.out.println(solution.canFinish(2, new int[][]{{1, 0}})); // true
        System.out.println(solution.canFinish(2, new int[][]{{1, 0}, {0, 1}})); // false
//        System.out.println(solution.canFinish(4, new int[][]{{1, 0}, {2, 0}, {3, 1}, {3, 2}})); // true
    }

}
