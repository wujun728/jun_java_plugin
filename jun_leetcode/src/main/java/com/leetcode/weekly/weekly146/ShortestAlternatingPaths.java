package com.leetcode.weekly.weekly146;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 颜色交替的最短路径
 *
 * @author BaoZhou
 * @date 2019/7/22 10:14
 */
public class ShortestAlternatingPaths {
    @Test
    public void test() {

        int n = 5;
        //int[][] red_edges = {{0, 1}};
        //int[][] blue_edges = {{0,0},{1, 2}};
        //int[][] red_edges = {{0, 1}, {1, 2}, {2, 3}, {3, 4}};
        //int[][] blue_edges = {{1, 2}, {2, 3}, {3, 1}};
        //int[][] red_edges = {{3, 2}, {4, 1}, {1, 4}, {2, 4}};
        //int[][] blue_edges = {{2, 3}, {0, 4}, {4, 3}, {4, 4}, {4, 0}, {1, 0}};
        int[][] red_edges = {{2, 2}, {0, 1}, {0, 3}, {0, 0}, {0, 4}, {2, 1}, {2, 0}, {1, 4}, {3, 4}};
        int[][] blue_edges = {{1, 3}, {0, 0}, {0, 3}, {4, 2}, {1, 0}};
        System.out.println(Arrays.toString(shortestAlternatingPaths(n, red_edges, blue_edges)));
    }


    public int[] shortestAlternatingPaths(int n, int[][] red_edges, int[][] blue_edges) {
        int[] red_first_result = new int[n];
        int[] blue_first_result = new int[n];
        int[][] red_map = new int[n][n];
        int[][] blue_map = new int[n][n];

        for (int i = 0; i < red_edges.length; i++) {
            red_map[red_edges[i][0]][red_edges[i][1]] = 1;
        }
        for (int i = 0; i < blue_edges.length; i++) {
            blue_map[blue_edges[i][0]][blue_edges[i][1]] = 1;
        }

        find(n, true, red_map, blue_map, red_first_result);
        find(n, false, red_map, blue_map, blue_first_result);

        //System.out.println(Arrays.toString(red_first_result));
        //System.out.println(Arrays.toString(blue_first_result));

        red_first_result[0] = 0;
        for (int i = 1; i < n; i++) {
            if ((red_first_result[i] > blue_first_result[i] && blue_first_result[i] != 0) || (blue_first_result[i] > 0 && red_first_result[i] == 0)) {
                red_first_result[i] = blue_first_result[i];
            }
            if (red_first_result[i] == 0) {
                red_first_result[i] = -1;
            }
        }
        return red_first_result;
    }

    class RedAndBlueNode {
        int value;
        boolean isRed;

        public RedAndBlueNode(int value, boolean isRed) {
            this.value = value;
            this.isRed = isRed;
        }
    }

    void find(int N, boolean red, int[][] red_map, int[][] blue_map, int[] result) {
        Queue<RedAndBlueNode> stack = new LinkedList<>();
        boolean[] red_record = new boolean[N];
        boolean[] blue_record = new boolean[N];
        for (int i = 0; i < N; i++) {
            if (red) {
                if (red_map[0][i] == 1) {
                    stack.add(new RedAndBlueNode(i, true));
                    result[i] = 1;
                }
            } else {
                if (blue_map[0][i] == 1) {
                    stack.add(new RedAndBlueNode(i, false));
                    result[i] = 1;
                }
            }

        }
        int length = 2;
        int curr_level_size = stack.size();
        int next_level_size = 0;
        while (!stack.isEmpty()) {
            RedAndBlueNode pop = stack.poll();
            curr_level_size--;
            for (int i = 0; i < N; i++) {
                if (pop.isRed) {
                    if (blue_map[pop.value][i] == 1 && !blue_record[i]) {
                        stack.add(new RedAndBlueNode(i, false));
                        blue_record[i] = true;
                        next_level_size++;
                        if (result[i] == 0) {
                            result[i] = length;
                        }
                    }
                } else {
                    if (red_map[pop.value][i] == 1 && !red_record[i]) {
                        stack.add(new RedAndBlueNode(i, true));
                        red_record[i] = true;
                        next_level_size++;
                        if (result[i] == 0) {
                            result[i] = length;
                        }
                    }
                }
            }
            if (curr_level_size == 0) {
                curr_level_size = next_level_size;
                next_level_size = 0;
                length++;
            }
        }
    }
}
