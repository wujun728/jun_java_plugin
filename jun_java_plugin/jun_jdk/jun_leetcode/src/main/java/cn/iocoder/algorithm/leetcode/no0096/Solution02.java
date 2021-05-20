package cn.iocoder.algorithm.leetcode.no0096;

/**
 * 动态规划
 */
public class Solution02 {

    public int numTrees(int n) {
        // 创建数组，记录 n 节可创建的二叉搜索树
        int[] numTrees = new int[n + 1];
        numTrees[0] = numTrees[1] = 1;
        // 计算 i 个节点，可创建的二叉搜索树的数量
        // 以 i 为例子，则二叉搜索树，可以拆分成 [j, 1, i - j - 1] 三部分
        // 并且有一点要注意，1，2，3 和 4，5，6 两种 3 节点，实际创建的二叉树数量，是一致的。
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                numTrees[i] += numTrees[j] * numTrees[i - j - 1];
            }
        }

        // 返回结果
        return numTrees[n];
    }

}
