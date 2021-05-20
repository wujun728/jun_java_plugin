package cn.iocoder.algorithm.leetcode.no0189;

/**
 * 使用环状替代
 *
 * 参考 https://leetcode-cn.com/problems/rotate-array/solution/xuan-zhuan-shu-zu-by-leetcode/ 方法三
 */
public class Solution02 {

    public void rotate(int[] nums, int k) {
        k = k % nums.length;
        int count = 0;
        for (int start = 0; count < nums.length; start++) {
            int current = start;
            int prev = nums[start];
            do {
                // 计算下一个节点
                int next = (current + k) % nums.length;
                // 临时存储下一个节点的值
                int tmp = nums[next];
                // 下一个节点，修改成前一个节点的值
                nums[next] = prev;
                // 记录 prev 为当前节点的值
                prev = tmp;
                // 设置下一个节点
                current = next;
                // 计数++
                count++;
            } while (current != start);
        }
    }

}
