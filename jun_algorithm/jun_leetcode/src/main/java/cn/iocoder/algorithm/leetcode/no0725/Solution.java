package cn.iocoder.algorithm.leetcode.no0725;

import cn.iocoder.algorithm.leetcode.common.ListNode;

import java.util.Arrays;

/**
 * https://leetcode-cn.com/problems/split-linked-list-in-parts/
 */
public class Solution {

    // 代码可以进一步简化
    // 1. 如果 root 已经为空，不需要继续遍历
    // 2. current 不需要每次都赋值
    public ListNode[] splitListToParts(ListNode root, int k) {
        // 计算节点数
        assert k > 0;
        int counts = count(root);
        int avg = counts / k;
        int extra = counts % k;

        // 开始拆分
        ListNode[] results = new ListNode[k];
        for (int i = 0; i < k; i++) {
            // 虚拟头节点
            ListNode dummy = new ListNode(0);
            // 计算当前节点的长度
            int length = avg;
            if (extra > 0) {
                length += 1;
                extra -= 1;
            }
            // 开始
            ListNode current = dummy;
            for (int j = 0; j < length; j++) {
                current.next = root;
                current = current.next;
                root = root.next;
            }
            current.next = null;
            // 设置到结果
            results[i] = dummy.next;
        }

        return results;
    }

    private int count(ListNode head) {
        int counts = 0;
        while (head != null) {
            counts++;
            head = head.next;
        }
        return counts;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.splitListToParts(ListNode.create(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), 3)));
    }

}
