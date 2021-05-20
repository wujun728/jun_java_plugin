package cn.iocoder.algorithm.leetcode.no0023;

import cn.iocoder.algorithm.leetcode.common.ListNode;

/**
 * https://leetcode-cn.com/problems/merge-k-sorted-lists/
 *
 * 基于 {@link cn.iocoder.algorithm.leetcode.no0021.Solution} 的思路，从头到尾，逐个合并。
 *
 * 时间复杂度是 O(k * N)
 */
public class Solution {

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        ListNode dummy = new ListNode(0);
        dummy.next = lists[0];
        for (int i = 1; i < lists.length; i++) {
            ListNode current = dummy;
            ListNode l1 = dummy.next;
            ListNode l2 = lists[i];
            while (l1 != null && l2 != null) {
                if (l1.val >= l2.val) {
                    current.next = l2; // 指向 l2 当前头节点
                    l2 = l2.next;
                } else {
                    current.next = l1; // 指向 l1 当前头节点
                    l1 = l1.next;
                }
                // 跳到下一个节点
                current = current.next;
            }

            // 补全
            current.next = l1 != null ? l1 : l2;
        }

        return dummy.next;
    }

}
