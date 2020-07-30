package cn.iocoder.algorithm.leetcode.no0841;

import java.util.List;

/**
 * https://leetcode-cn.com/problems/keys-and-rooms/
 *
 * DFS
 */
public class Solution {

    private int n;
    private boolean[] visits;
    private List<List<Integer>> rooms;

    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        // 初始化
        this.n = rooms.size();
        this.visits = new boolean[this.n];
        this.rooms = rooms;

        // dfs
        this.dfs(0);
        return n == 0;
    }

    private void dfs(int index) {
        if (visits[index]) {
            return;
        }
        // 标记已访问
        visits[index] = true;
        this.n--;

        // 遍历后续节点
        for (int nextIndex : rooms.get(index)) {
            this.dfs(nextIndex);
        }
    }

}
