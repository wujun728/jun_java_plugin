package cn.iocoder.algorithm.leetcode.no0666;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/path-sum-iv/
 */
class Solution {

    public int pathSum(int[] nums) {
        List<Integer> tree = new ArrayList<>();
        tree.add(null);
        // 构建树
        for (int num : nums) {
            this.add(tree, num);
        }

        // 计算
        int sum = 0;
        for (int i = 1; i < tree.size(); i++) {
            Integer num = tree.get(i);
            if (num == null) {
                continue;
            }
            if (this.isNotLeaf(tree, i)) {
                continue;
            }
            for (int j = i; j > 0; j = j / 2) {
                sum += tree.get(j);
            }
        }

        return sum;
    }

    private void add(List<Integer> tree, int num) {
        // 值
        int val = num % 10;
        num = num / 10;
        // 位置
        int levelIndex = num % 10;
        num = num / 10;
        // 层级
        int level = num;
        // 计算真正的 index
        int index = (int) Math.pow(2, level - 1) + levelIndex - 1;

        // 判断是否太小
        for (int i = tree.size(); i < index; i++) {
            tree.add(null);
        }
        tree.add(val);
    }

    private boolean isNotLeaf(List<Integer> tree, int index) {
        // 左边
        if (index * 2 < tree.size()
            && tree.get(index * 2) != null) {
            return true;
        }
        // 右边
        if (index * 2 + 1 < tree.size()
            && tree.get(index * 2 + 1) != null) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.pathSum(new int[]{113, 215, 211}));
        System.out.println(solution.pathSum(new int[]{113, 221}));
        System.out.println(solution.pathSum(new int[]{111,217,221,315,415}));
    }

}
