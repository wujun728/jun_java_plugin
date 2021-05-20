package cn.iocoder.algorithm.leetcode.no0279;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/perfect-squares/
 *
 * BFS 思路
 */
public class Solution {

    public int numSquares(int n) {
        // 计算
        List<Integer> array = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n]; // 标记已经访问的节点，避免重复
        for (int i = 1, max = (int) Math.sqrt(n); i <= max; i++) {
            int result = i * i;
            if (result == n) {
                return 1;
            }
            array.add(result);
            queue.add(result);
            visited[i] = true;
        }

        int count = 1;
        while (true) {
            count++;
            int size = queue.size();
            // 逐层遍历
            for (int i = 0; i < size; i++) {
                int item = queue.poll();
                for (int number : array) {
                    // 判断是否等于指定值 n
                    int result = item + number;
                    if (result == n) {
                        return count;
                    }
                    // 超过了，所以不再重试
                    if (result > n) {
                        break;
                    }
                    // 未访问过，添加到队列
                    if (!visited[result]) {
                        queue.add(result);
                        visited[result] = true;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.numSquares(12));
//        System.out.println(solution.numSquares(13));
        System.out.println(solution.numSquares(7168));
    }

}
