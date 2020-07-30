package cn.iocoder.algorithm.leetcode.no0565;

/**
 * https://leetcode-cn.com/problems/array-nesting/
 */
public class Solution {

    public int arrayNesting(int[] nums) {
        int max = 0;
        // 顺序遍历，并将已经遍历过的节点为 -1 ，避免重复遍历。
        // 关键点：能够顺序的原因是，嵌套数组，必然形成一个环。从任一一个节点进入，整个环的大小是一致的。
        for (int i = 0; i < nums.length; i++) {
            // 跳过，如果已经访问
            if (nums[i] == -1) {
                continue;
            }
            int counts = 0;
            // 从当前节点开始，不断遍历
            for (int j = i; nums[j] != -1;) {
                counts++;
                int tmp = nums[j];
                nums[j] = -1;
                j = tmp;
            }
            // 计算最大
            max = Math.max(max, counts);
        }
        return max;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.arrayNesting(new int[]{5, 4, 0, 3, 1, 6, 2}));
    }

}
