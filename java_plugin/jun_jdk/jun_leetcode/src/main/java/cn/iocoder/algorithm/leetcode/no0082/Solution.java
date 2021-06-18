package cn.iocoder.algorithm.leetcode.no0082;

import cn.iocoder.algorithm.leetcode.common.ListNode;

/**
 * https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list-ii/
 */
public class Solution {

    public ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode prev = dummy; // 注意，此时，是站在 dummy 开始
        while (prev.next != null) {
            ListNode lastDuplicate = findDuplicateNode(prev.next);
            // 如果就是自己，说明没有相等的，直接下跳。
            if (prev.next == lastDuplicate) {
                prev = prev.next;
            // 如果不是自己，说明有重复的，跳到最后重复的下一个。
            } else {
                prev.next = lastDuplicate.next;
            }
        }

        return dummy.next;
    }

    private ListNode findDuplicateNode(ListNode node) {
        int val = node.val;
        while (node.next != null && node.next.val == val) {
            node = node.next;
        }
        return node;
    }

}
