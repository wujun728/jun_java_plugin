package cn.iocoder.algorithm.leetcode.no0752;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/open-the-lock/
 *
 * BFS
 */
public class Solution {

    public int openLock(String[] deadends, String target) {
        // 初始化
        boolean[] visits = new boolean[10000];
        for (String deadend : deadends) {
            visits[Integer.valueOf(deadend)] = true;
        }
        if (visits[0]) {
            return -1;
        }

        int targetInt = Integer.valueOf(target);
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        visits[0] = true;
        int count = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int index = queue.poll();
                if (index == targetInt) {
                    return count;
                }
                this.genNextIndexs(queue, index, targetInt, visits);
            }
            count++;
        }

        return -1;
    }

    private void genNextIndexs(Queue<Integer> queue, int index, int target, boolean[] visits) {
        int indexClone = index;
        int multiplier = 1;
        for (int i = 0; i < 4; i++) {
            // 获取最后一位
            int j = indexClone % 10;
            indexClone = indexClone / 10;
//            int k = target % 10;
            target = target / 10;
//            if (k == j) {
//                // 增加乘数
//                multiplier *= 10;
//                continue;
//            }
            // 计算附加值
            int[] numbers = new int[]{(j + 9) % 10, (j + 1) % 10}; // + 9 相当于 - 1，+ 1 相当于 + 1 。
            for (int number : numbers) {
                int nextIndex = index + (number - j) * multiplier;
                if (visits[nextIndex]) {
                    continue;
                }
                visits[nextIndex] = true;
                queue.add(nextIndex);
            }
            // 增加乘数
            multiplier *= 10;
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        long now = System.currentTimeMillis();
//        System.out.println(solution.openLock(new String[]{"8887", "8889", "8878", "8988", "7888", "9888"}, "8888"));
        System.out.println(solution.openLock(new String[]{"0201","0101","0102","1212","2002"}, "0202"));
        System.out.println(System.currentTimeMillis() - now);
    }

}
