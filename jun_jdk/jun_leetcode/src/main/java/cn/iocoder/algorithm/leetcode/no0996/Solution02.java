package cn.iocoder.algorithm.leetcode.no0996;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 在 {@link Solution} 的基础上，进行优化
 *
 * 参考 https://leetcode-cn.com/problems/number-of-squareful-arrays/solution/zheng-fang-xing-shu-zu-de-shu-mu-by-leetcode/ 博客，优化回溯的过程
 *
 * 将 nextIndexesMap 优化成 nextNumbersMap ，并增加 numberCountMap 计数
 *
 * 7ms ，超过 20% 的用户。
 *
 * 后来发现，相比 {@link Solution} 带来的性能提升，主要是因为去掉了 lambada 表达式。
 *
 * TODO 芋艿，https://leetcode-cn.com/problems/number-of-squareful-arrays/solution/java-hui-su-fa-by-jessenpan/ 的答案，速度更快，只使用一个 Map ，而本代码使用了 2 个。
 */
public class Solution02 {

    private Map<Integer, List<Integer>> nextNumbersMap;
    private int[] A;
    private Map<Integer, Integer> numberCountMap;
    private int result;

    public int numSquarefulPerms(int[] A) {
        // 初始化
        this.init(A);

        // 回溯算法
        for (int number : numberCountMap.keySet()) {
            this.backtracking(1, number);
        }
        return result;
    }

    private void init(int[] A) {
        this.A = A;
        // 计算每个数字的数量
        this.numberCountMap = new HashMap<>();
        for (int item : A) {
            numberCountMap.put(item, numberCountMap.getOrDefault(item, 0) + 1);
        }
        // 初始化每个数字，可以组成平方根
        nextNumbersMap = new HashMap<>();
        // 初始化 nextNumbersMap
        for (Integer x : numberCountMap.keySet()) {
            for (Integer y : numberCountMap.keySet()) {
                if (!isSquare(x + y)) {
                    continue;
                }
//                nextNumbersMap.computeIfAbsent(x, k -> new ArrayList<>()).add(y);
                List<Integer> nextNumbers = nextNumbersMap.get(x);
                if (nextNumbers == null) {
                    nextNumbersMap.put(x, nextNumbers = new ArrayList<>());
                }
                nextNumbers.add(y);
            }
        }
        // 答案
        this.result = 0;
    }

    private boolean isSquare(int n) {
        double m = Math.sqrt(n);
        return m - (int) m == 0;
    }

    private void backtracking(int count, int number) {
        // 符合条件
        if (count == A.length) {
            result++;
            return;
        }

        // 减少数量
        numberCountMap.put(number, numberCountMap.get(number) - 1);

        // 获得下面要访问的
        List<Integer> nextNumbers = nextNumbersMap.get(number);
        if (nextNumbers == null || nextNumbers.isEmpty()) {
            return;
        }
        for (int nextNumber : nextNumbers) {
            if (numberCountMap.get(nextNumber) <= 0) {
                continue;
            }
            // 回溯
            this.backtracking(count + 1, nextNumber);
        }

        // 增加数量
        numberCountMap.put(number, numberCountMap.get(number) + 1);
    }

    public static void main(String[] args) {
        Solution02 solution = new Solution02();
//        System.out.println(solution.numSquarefulPerms(new int[]{2, 2, 2}));
        System.out.println(solution.numSquarefulPerms(new int[]{0, 2, 2, 2}));
//        System.out.println(solution.numSquarefulPerms(new int[]{1, 17, 8}));
    }

}
