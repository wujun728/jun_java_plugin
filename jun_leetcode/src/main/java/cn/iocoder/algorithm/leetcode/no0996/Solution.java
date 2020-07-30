package cn.iocoder.algorithm.leetcode.no0996;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/number-of-squareful-arrays/
 *
 * 回溯算法，勉强通过，79 ms ，超过 5% 的解答。
 */
public class Solution {

    private static final int ROOT_INDEX = -1;

    private Map<Integer, List<Integer>> nextIndexesMap;
    private int[] A;
    private Map<Integer, Boolean> visits = new HashMap<>();
    private int result;

    public int numSquarefulPerms(int[] A) {
        // 初始化
        this.init(A);

        // 回溯算法
        this.backtracking(0, -1);
        return result;
    }

    private void init(int[] A) {
        this.A = A;
        // 初始化每个数字，可以组成平方根
        nextIndexesMap = new HashMap<>();
        for (int i = 0; i < A.length; i++) {
            // 初始化 nextIndexesMap
            for (int j = i + 1; j < A.length; j++) {
                if (!this.isSquare(A[i] + A[j])) {
                    continue;
                }
                // 添加 i 的映射
                nextIndexesMap.computeIfAbsent(i, k -> new ArrayList<>()).add(j);
                // 添加 j 的映射
                nextIndexesMap.computeIfAbsent(j, k -> new ArrayList<>()).add(i);
            }
            // 根路径，可以到任何位置
            nextIndexesMap.computeIfAbsent(ROOT_INDEX, k -> new ArrayList<>()).add(i);
            // 初始化 visits
            visits.put(i, false);
        }
        // 答案
        this.result = 0;
    }

    private boolean isSquare(int n) {
        double m = Math.sqrt(n);
        return m - (int) m == 0;
    }

    private void backtracking(int count, int index) {
        // 符合条件
        if (count == A.length) {
            result++;
            return;
        }

        // 获得下面要访问的
        List<Integer> nextIndexes = nextIndexesMap.get(index);
        if (nextIndexes == null || nextIndexes.isEmpty()) {
            return;
        }
        Set<Integer> numbers = new HashSet<>();
        for (int nextIndex : nextIndexes) {
            if (visits.get(nextIndex)) {
                continue;
            }
            // 判断当前位置，是否使用过该数字
            if (numbers.contains(A[nextIndex])) {
                continue;
            }
            numbers.add(A[nextIndex]);
            // 标记已访问
            visits.put(nextIndex, true);
            // 回溯
            this.backtracking(count + 1, nextIndex);
            // 标记未访问
            visits.put(nextIndex, false);
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        System.out.println(solution.numSquarefulPerms(new int[]{2, 2, 2}));
        System.out.println(solution.numSquarefulPerms(new int[]{0, 2, 2, 2}));
//        System.out.println(solution.numSquarefulPerms(new int[]{1, 17, 8}));
    }

}
