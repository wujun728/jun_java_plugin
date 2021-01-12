package com.leetcode.weekly.weekly124;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

/**
 * 腐烂的橘子
 *
 * @author BaoZhou
 * @date 2019/2/17
 */
public class OrangesRotting {

    @Test
    public void test() {
        int[][] nums = new int[][]{{2, 2, 2}};
        System.out.println(orangesRotting(nums));
    }

    public int orangesRotting(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 2) {
                    grid[i][j] = 1;
                    findMinRoad(grid, i, j);
                }
            }
        }

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    return -1;
                }
                if (grid[i][j] < min) {
                    min = grid[i][j];
                }
            }
        }
        return min == 0 ? min : min * -1 - 1;

    }

    public void findMinRoad(int[][] arr, int x, int y) {
        //定义  上下左右四个方向
        int[][] direction = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        //创建队列
        LinkedList<Node> queue = new LinkedList<>();
        //创建开始节点
        Node start = new Node(x, y, -1);
        //把开始节点放入队列中，并做下标记，在原来数组中做标记
        queue.offer(start);
        arr[x][y] = -1;
        //循环操作队列，进行广度遍历
        Node temp = null;
        while (!queue.isEmpty()) {
            temp = queue.poll();
            //依次遍历这个节点的四个方向，查找还没有遍历的相连节点
            for (int i = 0; i < 4; i++) {
                int newX = temp.x + direction[i][0];
                int newY = temp.y + direction[i][1];
                //是否越界，坐标
                if (newX < 0 || newX >= arr.length || newY < 0 || newY >= arr[0].length) {
                    continue;
                }

                if (arr[newX][newY] == 0 || arr[newX][newY] == 2) {
                    continue;
                }

                if (arr[newX][newY] < 0) {
                    if (temp.dis - 1 > arr[newX][newY]) {
                        arr[newX][newY] = temp.dis - 1;
                    } else {
                        continue;
                    }
                }

                if (arr[newX][newY] == 1) {
                    arr[newX][newY] = temp.dis - 1;
                }

                //构造节点
                Node next = new Node(newX, newY, temp.dis - 1);
                queue.offer(next);
            }//for
        }//while

    }


    class Node {
        int x;
        int y;
        int dis;

        public Node(int x, int y, int dis) {
            this.x = x;
            this.y = y;
            this.dis = dis;
        }

    }
}

