package cn.iocoder.algorithm.leetcode.no0675;

import java.util.*;

/**
 * https://leetcode-cn.com/problems/cut-off-trees-for-golf-event/
 *
 * 多次 BFS
 *
 * 527 ms ，超过 46%
 */
public class Solution {

    private static final int[][] DIRECTIONS = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    private List<List<Integer>> forest;
    private int n;
    private int m;

    private TreeMap<Integer, int[]> numberMap;

    public int cutOffTree(List<List<Integer>> forest) {
        // 初始化
        this.forest = forest;
        this.n = forest.size();
        this.m = forest.get(0).size();

        // 初始化整数映射
        this.initNumberMap();

        // bfs 遍历
//        int[] source = numberMap.firstEntry().getValue();
//        if (source[0] != 0 || source[1] != 0) {
//            return -1;
//        }
        int[] source = new int[]{0, 0};
        int sum = 0;
        for (int[] target : numberMap.values()) {
            int result = this.bfs(source, target);
            if (result == -1) {
                return -1;
            }
            // 增加走过步数，设置新的起点
            sum += result;
            source = target;
        }
        return sum;
    }

    private void initNumberMap() {
        this.numberMap = new TreeMap<>();
        for (int i = 0; i < n; i++) {
            List<Integer> list = forest.get(i);
            for (int j = 0; j < m; j++) {
                int number = list.get(j);
                if (number == 0) {
                    continue;
                }
                numberMap.putIfAbsent(number, new int[]{i, j}); // 保证每个数字，只被放一次
            }
        }
    }

    private int bfs(int[] source, int[] target) {
        if (source[0] == target[0] && source[1] == target[1]) {
            return 0;
        }
        Queue<int[]> queue = new LinkedList<>();
        queue.add(source);
        boolean[] visits = new boolean[n * m];
        visits[getIndex(source[0], source[1])] = true;
        // bfs 开始
        int length = 0;
        while (!queue.isEmpty()) {
            length++;
            int size = queue.size();
            for (int k = 0; k < size; k++) {
                int[] current = queue.poll();
                assert current != null;
                for (int[] direction : DIRECTIONS) {
                    // 判断是否到达目标
                    int x = current[0] + direction[0];
                    int y = current[1] + direction[1];
                    if (x == target[0] && y == target[1]) {
                        return length;
                    }
                    // 判断是否越界
                    if (!this.inArea(x, y)) {
                        continue;
                    }
                    // 判断是否不可走
                    if (forest.get(x).get(y) == 0) {
                        continue;
                    }
                    // 是否已经访问过
                    int index = getIndex(x, y);
                    if (visits[index]) {
                        continue;
                    }
                    // 添加到队列
                    queue.add(new int[]{x, y});
                    visits[index] = true;
                }
            }
        }
        return -1;
    }

    private int getIndex(int i, int j) {
        return i * m + j;
    }

    private boolean inArea(int i, int j) {
        return i >= 0 && i < n
                && j >= 0 && j < m;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        List<List<Integer>> forest = Arrays.asList(
//                Arrays.asList(1, 2, 3),
//                Arrays.asList(0, 0, 4),
//                Arrays.asList(7, 6, 5)
//        );
        List<List<Integer>> forest = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(0, 0, 0),
                Arrays.asList(7, 6, 5)
        );
//        List<List<Integer>> forest = Arrays.asList(
//                Arrays.asList(2, 3, 4),
//                Arrays.asList(0, 0, 5),
//                Arrays.asList(8, 7, 6)
//        );
//        List<List<Integer>> forest = Arrays.asList(
//                Arrays.asList(54581641, 64080174, 24346381, 69107959),
//                Arrays.asList(86374198, 61363882,68783324, 79706116),
//                Arrays.asList(668150, 92178815, 89819108, 94701471),
//                Arrays.asList(83920491, 22724204, 46281641, 47531096),
//                Arrays.asList(89078499, 18904913, 25462145, 60813308)
//        );
        System.out.println(solution.cutOffTree(forest));
    }

}
